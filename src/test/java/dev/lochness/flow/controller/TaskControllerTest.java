package dev.lochness.flow.controller;

import dev.lochness.flow.dto.TaskDto;
import dev.lochness.flow.service.TaskService;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.thymeleaf.util.StringUtils;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext
@AutoConfigureMockMvc
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @Test
    @WithUserDetails("test")
    void shouldReturnTasksList() throws Exception {
        given(taskService.getTasksForToday(any()))
                .willReturn(List.of(TaskDto
                        .builder()
                        .id(1L)
                        .build()));
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().string(new BaseMatcher<>() {
                    @Override
                    public boolean matches(Object tasks) {
                        return StringUtils.contains(tasks, "1");
                    }

                    @Override
                    public void describeTo(Description description) {
                    }
                }));
    }

    @Test
    @WithAnonymousUser
    void shouldDenyAccessToTasksList() throws Exception {
        this.mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isFound());
    }

    @Test
    @WithUserDetails("test")
    void shouldReturnOkOnComplete() throws Exception {
        when(taskService.complete(any()))
                .thenReturn(2);
        mockMvc.perform(get("/api/tasks/complete"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("test")
    void shouldReturnOkSkip() throws Exception {
        when(taskService.skip(any()))
                .thenReturn(0);
        mockMvc.perform(get("/api/tasks/skip"))
                .andExpect(status().isOk());
    }

}
