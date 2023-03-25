package com.lifung.task.management.repository;

import com.lifung.task.management.entity.Task;
import com.lifung.task.management.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Integer> {
    List<Task> findAllByAssignee(User assignee);
}
