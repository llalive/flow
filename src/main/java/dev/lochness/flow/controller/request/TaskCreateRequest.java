package dev.lochness.flow.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class TaskCreateRequest {
    private final String taskName;
    private final String bounty;
    private final LocalDate date;
}
