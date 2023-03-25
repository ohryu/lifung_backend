package com.lifung.task.management.validation;


import com.lifung.task.management.constant.Status;
import com.lifung.task.management.model.TaskModel;
import com.lifung.task.management.repository.TaskRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
public class TaskValidationUT {

    @Autowired
    TaskRepository taskRepo;

    @Test
    public void validateTask_givenValidTask_shouldReturnEmptyError(){
        TaskModel task = TaskModel.builder()
                .title("Repair Mark IV suit")
                .assigneeId(1)
                .reporterId(2)
                .deadline(new Timestamp(System.currentTimeMillis() + 10000000))
                .status(Status.TODO)
                .build();
        Set<Integer> validUserIds = new HashSet<>();
        validUserIds.add(1);
        validUserIds.add(2);
        assert TaskValidation.validateTask(task, validUserIds).isEmpty();
    }

    @Test
    public void validateTask_givenTaskWithoutTitle_shouldReturnCorrectError(){
        TaskModel task = TaskModel.builder()
                .assigneeId(1)
                .reporterId(2)
                .deadline(new Timestamp(System.currentTimeMillis() + 10000000))
                .status(Status.TODO)
                .build();
        Set<Integer> validUserIds = new HashSet<>();
        validUserIds.add(1);
        validUserIds.add(2);
        assert "Must input title".equals(TaskValidation.validateTask(task, validUserIds).get(0));
    }

    @Test
    public void validateTask_givenTaskDeadlineInPast_shouldReturnCorrectError(){
        TaskModel task = TaskModel.builder()
                .title("Repair Mark IV suit")
                .assigneeId(1)
                .reporterId(2)
                .deadline(new Timestamp(System.currentTimeMillis() - 10000000))
                .build();
        Set<Integer> validUserIds = new HashSet<>();
        validUserIds.add(1);
        validUserIds.add(2);
        assert "Deadline must be after current time".equals(TaskValidation.validateTask(task,validUserIds).get(0));
    }

    @Test
    public void validateTask_givenInvalidAssignee_shouldReturnCorrectError(){
        TaskModel task = TaskModel.builder()
                .title("Repair Mark IV suit")
                .assigneeId(2)
                .reporterId(3)
                .deadline(new Timestamp(System.currentTimeMillis() + 10000000))
                .build();
        Set<Integer> validUserIds = new HashSet<>();
        validUserIds.add(3);
        validUserIds.add(4);
        assert "Assignee not found".equals(TaskValidation.validateTask(task, validUserIds).get(0));
    }

    @Test
    public void validateTask_givenInvalidReporter_shouldReturnCorrectError(){
        TaskModel task = TaskModel.builder()
                .title("Repair Mark IV suit")
                .reporterId(2)
                .assigneeId(3)
                .deadline(new Timestamp(System.currentTimeMillis() + 10000000))
                .status(Status.TODO)
                .build();
        Set<Integer> validUserIds = new HashSet<>();
        validUserIds.add(3);
        validUserIds.add(4);
        assert "Reporter not found".equals(TaskValidation.validateTask(task, validUserIds).get(0));
    }
}
