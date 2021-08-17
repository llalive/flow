package dev.lochness.flow.service;

import dev.lochness.flow.domain.Task;
import dev.lochness.flow.domain.User;
import dev.lochness.flow.dto.TaskDto;
import dev.lochness.flow.exceptions.NotFoundException;
import dev.lochness.flow.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskManager implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskManager(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    @Override
    public List<TaskDto> getTasksForToday(User user) {
        return taskRepository.findTasksForToday(user.getId())
                .stream()
                .map(TaskDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Task add(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public int complete(User user) {
        Task currentTask = completeTask(user.getId());
        return userService.addKarma(user, currentTask.getBounty())
                .getKarma();
    }

    @Override
    public int skip(User user) {
        Task currentTask = completeTask(user.getId());
        return userService.removeKarma(user, currentTask.getBounty())
                .getKarma();
    }

    private Task completeTask(Long userId) {
        Task currentTask = getCurrentTask(userId);
        currentTask.setIsComplete(Boolean.TRUE);
        return taskRepository.save(currentTask);
    }

    private Task getCurrentTask(Long userId) {
        return taskRepository.findTasksForToday(userId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("У текущего пользователя отсутствуют незавершённые задачи"));
    }
}
