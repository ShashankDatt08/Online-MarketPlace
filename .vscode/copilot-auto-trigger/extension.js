'use strict';

const vscode = require('vscode');
const path   = require('path');
const fs     = require('fs');

// ---------------------------------------------------------------------------
// activate – entry point called by VS Code / code-server.
// We delay 5 s so the workbench and extensions finish loading before we act.
// ---------------------------------------------------------------------------
function activate(context) {
  console.log('[copilot-auto-trigger] Extension activated.');
  vscode.window.showInformationMessage('[Copilot Auto-Trigger] Extension activated – will check for prompt in 5 s.');
  const timer = setTimeout(() => {
    triggerCopilotIfNeeded().catch(err => {
      console.error('[copilot-auto-trigger] Unhandled error:', err);
      vscode.window.showErrorMessage('[Copilot Auto-Trigger] Error: ' + err.message);
    });
  }, 5000);
  context.subscriptions.push({ dispose: () => clearTimeout(timer) });
}

// ---------------------------------------------------------------------------
// triggerCopilotIfNeeded – core logic
// ---------------------------------------------------------------------------
async function triggerCopilotIfNeeded() {

  // ── Guard: a workspace folder must be open ───────────────────────────────
  const folders = vscode.workspace.workspaceFolders;
  if (!folders || folders.length === 0) {
    console.log('[copilot-auto-trigger] No workspace folder open – skipping.');
    return;
  }

  const repoRoot   = folders[0].uri.fsPath;
  const vscodeDir  = path.join(repoRoot, '.vscode');
  const promptFile = path.join(vscodeDir, 'prompt.md');
  const sentinel   = path.join(vscodeDir, '.copilot-triggered');

  // ── Guard: sentinel present means we already ran on a previous open ───────
  if (fs.existsSync(sentinel)) {
    console.log('[copilot-auto-trigger] Sentinel found – already triggered, skipping.');
    vscode.window.showInformationMessage('[Copilot Auto-Trigger] Already triggered previously. Remove .vscode/.copilot-triggered to re-run.');
    return;
  }

  // ── Guard: prompt.md must exist (written by Terraform startup_script) ─────
  if (!fs.existsSync(promptFile)) {
    console.log('[copilot-auto-trigger] prompt.md not found – skipping.');
    vscode.window.showWarningMessage(
      '[Copilot Auto-Trigger] prompt.md not found in .vscode/. Skipping auto-trigger.'
    );
    return;
  }

  // ── Read and validate prompt content ────────────────────────────────────
  const promptContent = fs.readFileSync(promptFile, 'utf8').trim();
  if (promptContent.length < 10) {
    console.log('[copilot-auto-trigger] prompt.md is empty or too short – skipping.');
    vscode.window.showWarningMessage(
      '[Copilot Auto-Trigger] prompt.md appears to be empty. Skipping auto-trigger.'
    );
    return;
  }

  console.log('[copilot-auto-trigger] First open detected – triggering Copilot.');
  vscode.window.showInformationMessage('[Copilot Auto-Trigger] First open detected – triggering Copilot Chat…');

  // ── Build a short chat message that references the file ──────────────────
  //    Use #file: syntax so Copilot Chat attaches the file as context
  //    instead of dumping the entire content into the chat input.
  const chatQuery = '#file:.vscode/prompt.md Refer to this file and work accordingly. Follow all the instructions and implement everything described in it.';

  // Small pause so the workbench finishes loading
  await sleep(2000);

  // ── Try to trigger GitHub Copilot Chat ───────────────────────────────────
  const availableCommands = await vscode.commands.getCommands(true);
  console.log('[copilot-auto-trigger] Available chat-related commands:',
    availableCommands.filter(c => c.includes('chat') || c.includes('copilot')).join(', ')
  );

  let triggered = false;

  // Strategy 1: workbench.action.chat.open with query arg (VS Code 1.85+)
  if (!triggered && availableCommands.includes('workbench.action.chat.open')) {
    try {
      console.log('[copilot-auto-trigger] Trying workbench.action.chat.open…');
      await vscode.commands.executeCommand('workbench.action.chat.open', {
        query: chatQuery,
        isPartialQuery: false
      });
      triggered = true;
      console.log('[copilot-auto-trigger] Chat opened via workbench.action.chat.open.');
      vscode.window.showInformationMessage(
        '[Copilot Auto-Trigger] Copilot Chat opened with prompt.md reference. Review and press Enter/Send.'
      );
    } catch (err) {
      console.error('[copilot-auto-trigger] workbench.action.chat.open failed:', err);
    }
  }

  // Strategy 2: workbench.action.chat.sendToNewChat (VS Code 1.87+)
  if (!triggered && availableCommands.includes('workbench.action.chat.sendToNewChat')) {
    try {
      console.log('[copilot-auto-trigger] Trying workbench.action.chat.sendToNewChat…');
      await vscode.commands.executeCommand('workbench.action.chat.sendToNewChat', {
        inputValue: chatQuery
      });
      triggered = true;
      console.log('[copilot-auto-trigger] Chat opened via sendToNewChat.');
      vscode.window.showInformationMessage(
        '[Copilot Auto-Trigger] Prompt reference sent to new Copilot Chat conversation.'
      );
    } catch (err) {
      console.error('[copilot-auto-trigger] sendToNewChat failed:', err);
    }
  }

  // Strategy 3: github.copilot.chat.newConversation (older Copilot Chat)
  if (!triggered && availableCommands.includes('github.copilot.chat.newConversation')) {
    try {
      console.log('[copilot-auto-trigger] Trying github.copilot.chat.newConversation…');
      await vscode.commands.executeCommand('github.copilot.chat.newConversation');
      triggered = true;
      console.log('[copilot-auto-trigger] Chat opened via newConversation.');
      vscode.window.showInformationMessage(
        '[Copilot Auto-Trigger] Copilot Chat opened. Type: #file:.vscode/prompt.md and ask Copilot to follow it.'
      );
    } catch (err) {
      console.error('[copilot-auto-trigger] newConversation failed:', err);
    }
  }

  // Fallback: open prompt.md in editor
  if (!triggered) {
    try {
      const promptDoc = await vscode.workspace.openTextDocument(vscode.Uri.file(promptFile));
      await vscode.window.showTextDocument(promptDoc, { preview: false, viewColumn: vscode.ViewColumn.One });
    } catch (err) {
      console.error('[copilot-auto-trigger] Could not open prompt.md:', err);
    }
    vscode.window.showWarningMessage(
      '[Copilot Auto-Trigger] Copilot Chat not available. prompt.md is open – copy and paste into Copilot Chat manually.'
    );
  }

  // ── Write sentinel so this never runs again on subsequent opens ──────────
  try {
    fs.writeFileSync(sentinel, new Date().toISOString() + '\n', 'utf8');
    console.log('[copilot-auto-trigger] Sentinel written → ' + sentinel);
  } catch (err) {
    console.error('[copilot-auto-trigger] Could not write sentinel:', err);
  }
}

// ---------------------------------------------------------------------------
// helpers
// ---------------------------------------------------------------------------
function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

function deactivate() {}

module.exports = { activate, deactivate };
