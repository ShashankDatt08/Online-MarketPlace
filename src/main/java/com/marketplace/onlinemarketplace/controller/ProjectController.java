package com.marketplace.onlinemarketplace.controller;

import com.marketplace.onlinemarketplace.entity.ClientProfile;
import com.marketplace.onlinemarketplace.entity.ProjectRequest;
import com.marketplace.onlinemarketplace.entity.Projects;
import com.marketplace.onlinemarketplace.service.ProjectService;
import com.marketplace.onlinemarketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createProject(@RequestBody ProjectRequest projectRequest) {
        try {
            Projects project = projectService.createProject(projectRequest);
            return ResponseEntity.ok("Project created");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Error Creating Project" + " " + e.getMessage());
        }
    }

        @GetMapping("/all")
        public ResponseEntity<List<Projects>> getProjects() {
            try {
                List<Projects> project =  projectService.getAllClientProfile();
                return ResponseEntity.ok(project);
            } catch (Exception e) {
                return ResponseEntity.status(400).body(null);
            }

    }

        @GetMapping("/{projectId}")
        public ResponseEntity getProjectById(@PathVariable Long projectId) {
        try {

            Projects projects = projectService.getProjectById(projectId);
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Please Enter a valid Project Id");
        }

    }

        @PutMapping("/update/{id}")
        public ResponseEntity<String> updateProject(@PathVariable Long id, @RequestBody ProjectRequest projectRequest) {
            try {
                projectRequest.setId(id);
                Projects updatedProject = projectService.updateProject(projectRequest);
                return ResponseEntity.ok("Project updated successfully with ID: " + updatedProject.getId());
            } catch (Exception e) {
                return ResponseEntity.status(400).body("Error updating project: " + e.getMessage());
            }

        }

        @DeleteMapping("/delete/{id}/{clientId}")
        public ResponseEntity deleteProject(@PathVariable Long id , @PathVariable Long clientId) {
            try{
                projectService.deleteProject(id,clientId);
                return ResponseEntity.ok("Project deleted successfully");
            } catch (Exception e) {
                return ResponseEntity.status(400).body("Error deleting project: " + e.getMessage());
            }
        }
}
