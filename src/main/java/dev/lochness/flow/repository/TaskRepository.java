package dev.lochness.flow.repository;

import dev.lochness.flow.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t " +
           "where t.user.id = :userId and t.date = current_date and t.isComplete = false " +
           "order by id asc")
    List<Task> findTasksForToday(Long userId);

}
