package dev.lochness.flow.controller;

import dev.lochness.flow.domain.User;
import dev.lochness.flow.security.CustomUserPrincipal;
import dev.lochness.flow.service.UserService;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Test
    @WithAnonymousUser
    void shouldDenyAccessToKarmaOperations() throws Exception {
        this.mockMvc.perform(delete("/api/users/karma"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(
            username = "test",
            authorities = {"USER"}
    )
    void shouldDenyAccessIfWrongMethod() throws Exception {
        this.mockMvc.perform(get("/api/users/karma"))
                .andExpect(status().isMethodNotAllowed());
    }

}
