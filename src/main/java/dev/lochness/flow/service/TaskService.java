package dev.lochness.flow.service;

import dev.lochness.flow.domain.Task;
import dev.lochness.flow.domain.User;
import dev.lochness.flow.dto.TaskDto;

import java.util.List;

public interface TaskService {

    List<TaskDto> getTasksForToday(User user);

    Task add(Task task);

    int complete(User user);

    int skip(User user);
}
