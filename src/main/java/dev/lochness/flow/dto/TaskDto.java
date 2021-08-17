package dev.lochness.flow.dto;

import dev.lochness.flow.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TaskDto {

    private Long id;
    private String name;
    private int bounty;

    public static TaskDto toDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .bounty(task.getBounty())
                .build();
    }
}
