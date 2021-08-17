package dev.lochness.flow.controller;

import dev.lochness.flow.controller.request.TaskCreateRequest;
import dev.lochness.flow.domain.Task;
import dev.lochness.flow.dto.TaskDto;
import dev.lochness.flow.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TaskContoller extends AuthenticatedUserContoller {

    private final TaskService taskService;

    @GetMapping("/api/tasks")
    public ResponseEntity<List<TaskDto>> listTasks() {
        List<TaskDto> tasks = taskService.getTasksForToday(getCurrentUser());
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/api/tasks")
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskCreateRequest taskCreateRequest) {
        Task newTask = Task.from(taskCreateRequest);
        newTask.setUser(getCurrentUser());
        return ResponseEntity.ok(TaskDto.toDto(taskService.add(newTask)));
    }

    @GetMapping("/api/tasks/complete")
    public ResponseEntity<Integer> completeTask() {
        taskService.complete(getCurrentUser());
        return ResponseEntity.ok(getCurrentUser().getKarma());
    }

    @GetMapping("/api/tasks/skip")
    public ResponseEntity<Integer> skipTask() {
        taskService.skip(getCurrentUser());
        return ResponseEntity.ok(getCurrentUser().getKarma());
    }
}
