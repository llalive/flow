package dev.lochness.flow.controller;

import dev.lochness.flow.dto.TaskDto;
import dev.lochness.flow.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @Test
    @WithUserDetails("test")
    void shouldAllowAccessToTaskManager() throws Exception {
        given(taskService.getTasksForToday(any()))
                .willReturn(List.of(TaskDto
                        .builder()
                        .id(1L)
                        .build()));
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void shouldDenyAccessToTaskManagerForAnonymous() throws Exception {
        this.mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isFound());
    }

    @Test
    @WithUserDetails("test")
    void shouldDenyAccessToAdminPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("admin")
    void shouldAllowAccessToAdminPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

}
