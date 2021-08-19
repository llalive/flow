package dev.lochness.flow.repository;

import dev.lochness.flow.domain.Task;
import dev.lochness.flow.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DirtiesContext
    void shouldReturnOnlyTasksForToday() {
        User user = createUser();
        testEntityManager.persist(Task.builder()
                .name("task 1")
                .bounty(1)
                .isComplete(Boolean.FALSE)
                .user(user)
                .date(LocalDate.now().minusDays(1))
                .build()
        );
        testEntityManager.persist(Task.builder()
                .name("task 2")
                .bounty(2)
                .isComplete(Boolean.FALSE)
                .user(user)
                .date(LocalDate.now())
                .build()
        );
        List<Task> tasks = taskRepository.findTasksForToday(user.getId());
        Assertions.assertThat(tasks).hasSize(1);
    }

    @Test
    @DirtiesContext
    void shouldReturnCorrectTasks() {
        User user = createUser();
        Task task = Task.builder()
                .name("task 1")
                .bounty(1)
                .isComplete(Boolean.FALSE)
                .user(user)
                .date(LocalDate.now())
                .build();
        task = testEntityManager.persist(task);
        Task actualTask = taskRepository.findTasksForToday(user.getId()).get(0);
        Assertions.assertThat(actualTask).isEqualToComparingFieldByField(task);
    }

    @Test
    void shouldNotReturnCompleteTasks() {
        User user = createUser();
        Task task = Task.builder()
                .name("task 1")
                .bounty(1)
                .isComplete(Boolean.TRUE)
                .user(user)
                .date(LocalDate.now())
                .build();
        task = testEntityManager.persist(task);
        List<Task> tasks = taskRepository.findTasksForToday(user.getId());
        Assertions.assertThat(tasks).isEmpty();
    }

    private User createUser() {
        return testEntityManager.persist(User.builder()
                .username("user")
                .email("email")
                .password("1111")
                .build());
    }
}