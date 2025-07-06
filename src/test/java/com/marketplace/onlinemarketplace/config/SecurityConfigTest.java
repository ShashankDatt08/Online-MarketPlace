package com.marketplace.onlinemarketplace.config;

import com.marketplace.onlinemarketplace.Jwt.JwtFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(SecurityConfig.class)
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtFilter jwtFilter;

    @Test
    void whenAccessSwaggerUiWithoutAuth_thenOk() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
               .andExpect(status().isOk());
    }

    @Test
    void whenAccessApiDocsWithoutAuth_thenOk() throws Exception {
        mockMvc.perform(get("/v3/api-docs/test"))
               .andExpect(status().isOk());
    }

    @Test
    void whenAccessSwaggerUiHtmlWithoutAuth_thenOk() throws Exception {
        mockMvc.perform(get("/swagger-ui.html"))
               .andExpect(status().isOk());
    }

    @Test
    void whenAccessRegisterWithoutAuth_thenOk() throws Exception {
        mockMvc.perform(get("/auth/register"))
               .andExpect(status().isOk());
    }

    @Test
    void whenAccessLoginWithoutAuth_thenOk() throws Exception {
        mockMvc.perform(get("/auth/login"))
               .andExpect(status().isOk());
    }

    @Test
    void whenAccessChangePasswordWithoutAuth_thenOk() throws Exception {
        mockMvc.perform(get("/auth/changepassword"))
               .andExpect(status().isOk());
    }

    @Test
    void whenAccessLogoutWithoutAuth_thenOk() throws Exception {
        mockMvc.perform(get("/auth/logout"))
               .andExpect(status().isOk());
    }

    @Test
    void whenAccessOtherEndpointWithoutAuth_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/some/other"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void whenAccessOtherEndpointWithAuth_thenNotFound() throws Exception {
        mockMvc.perform(get("/some/other"))
               .andExpect(status().isNotFound());
    }
}