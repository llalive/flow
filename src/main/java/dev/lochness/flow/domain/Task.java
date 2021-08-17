package dev.lochness.flow.domain;

import com.google.common.primitives.Ints;
import dev.lochness.flow.controller.request.TaskCreateRequest;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Builder.Default
    @Column(name = "date")
    private LocalDate date = LocalDate.now();

    @Column(name = "bounty")
    private int bounty;

    @Builder.Default
    @Column(name = "is_complete")
    private Boolean isComplete = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    public static Task from(TaskCreateRequest taskCreateRequest) {
        return Task.builder()
                .name(taskCreateRequest.getTaskName())
                .bounty(Ints.tryParse(taskCreateRequest.getBounty()))
                .build();
    }

}