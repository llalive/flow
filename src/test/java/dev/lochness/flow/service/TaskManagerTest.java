package dev.lochness.flow.service;

import dev.lochness.flow.domain.Task;
import dev.lochness.flow.domain.User;
import dev.lochness.flow.dto.TaskDto;
import dev.lochness.flow.repository.TaskRepository;
import dev.lochness.flow.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class TaskManagerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @Test
    @DirtiesContext
    void shouldReturnCorrectTasks() {
        User user = userRepository.save(buildUser());
        Task task = taskRepository.save(Task.builder()
                .name("name")
                .user(user)
                .build());
        TaskDto expectedTask = TaskDto.toDto(task);
        List<TaskDto> tasksForToday = taskService.getTasksForToday(user);
        Assertions.assertAll(() -> {
            Assertions.assertEquals(tasksForToday.size(), 1);
            Assertions.assertEquals(expectedTask, tasksForToday.get(0));
        });
    }

    @Test
    @DirtiesContext
    void shouldSuccessfullyAddTask() {
        long tasksCount = taskRepository.count();
        User user = userRepository.save(buildUser());
        Task actualTask = taskService.add(Task.builder()
                .name("name")
                .user(user)
                .build());
        Assertions.assertEquals(tasksCount + 1, taskRepository.count());
    }

    @Test
    @DirtiesContext
    void shouldSuccessfullyCompleteTask() {
        User user = userRepository.save(buildUser());
        Task task = taskRepository.save(Task.builder()
                .name("name")
                .user(user)
                .date(LocalDate.now())
                .bounty(3)
                .isComplete(Boolean.FALSE)
                .build());
        TaskDto expectedTask = TaskDto.toDto(task);
        int actualKarma = taskService.complete(user);
        List<TaskDto> tasksForToday = taskService.getTasksForToday(user);
        Optional<Task> completedTask = taskRepository.findAll()
                .stream()
                .filter(t -> t.getUser().getId() == user.getId())
                .findFirst();
        Assertions.assertAll(() -> {
            Assertions.assertEquals(0, tasksForToday.size());
            Assertions.assertTrue(completedTask.isPresent() && completedTask.get().getIsComplete());
            Assertions.assertEquals(13, actualKarma);
        });
    }

    @Test
    @DirtiesContext
    void shouldSuccessfullySkipTask() {
        User user = userRepository.save(buildUser());
        Task task = taskRepository.save(Task.builder()
                .name("name")
                .user(user)
                .date(LocalDate.now())
                .bounty(3)
                .isComplete(Boolean.FALSE)
                .build());
        TaskDto expectedTask = TaskDto.toDto(task);
        int actualKarma = taskService.skip(user);
        List<TaskDto> tasksForToday = taskService.getTasksForToday(user);
        Optional<Task> skipedTask = taskRepository.findAll()
                .stream()
                .filter(t -> t.getUser().getId() == user.getId())
                .findFirst();
        Assertions.assertAll(() -> {
            Assertions.assertEquals(0, tasksForToday.size());
            Assertions.assertTrue(skipedTask.isPresent() && skipedTask.get().getIsComplete());
            Assertions.assertEquals(7, actualKarma);
        });
    }

    private User buildUser() {
        return User.builder()
                .username("user")
                .email("email")
                .password("1111")
                .karma(10)
                .build();
    }
}