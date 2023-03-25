package com.lifung.task.management.service;

import com.lifung.task.management.constant.Status;
import com.lifung.task.management.converter.TaskConverter;
import com.lifung.task.management.entity.Task;
import com.lifung.task.management.entity.User;
import com.lifung.task.management.exception.BadRequestException;
import com.lifung.task.management.exception.NotFoundException;
import com.lifung.task.management.exception.PermissionDeny;
import com.lifung.task.management.model.StatusModel;
import com.lifung.task.management.model.TaskModel;
import com.lifung.task.management.repository.TaskRepository;
import com.lifung.task.management.repository.UserRepository;
import com.lifung.task.management.validation.TaskValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserService userService;

    public List<Task> getAllTasks() {
        return (List<Task>) taskRepo.findAll();
    }

    public List<Task> getAllTasksOfUser(int userId) {
        User assignee = userService.getUser(userId);
        return taskRepo.findAllByAssignee(assignee);
    }

    public Task updateStatus(int userId, int taskId, StatusModel statusString) {
        Status status = TaskValidation.validateAndGetStatus(statusString.getStatus());
        Task task = taskRepo.findById(taskId).orElse(null);
        if(task == null){
            throw new NotFoundException("Task not found");
        }
        if(task.getAssigneeId() != userId){
            throw new PermissionDeny("You don't have permission to update this task");
        }
        task.setStatus(status);
        return taskRepo.save(task);
    }

    public List<Task> createTasks(List<TaskModel> tasks) {
        Set<Integer> userIds = tasks.stream().flatMap(t-> Stream.of(t.getAssigneeId(),t.getReporterId()))
                .collect(Collectors.toSet());
        List<User> users = (List<User>) userRepo.findAllById(userIds);
        userIds = users.stream().map(User::getId).collect(Collectors.toSet());
        Map<Integer, List<String>> errors = new HashMap<>();
        for (int i = 0; i< tasks.size(); i++) {
            List<String> error = TaskValidation.validateTask(tasks.get(i), userIds);
            if(!error.isEmpty()) {
                errors.put(i+1, error);
            }
        }
        if(!errors.isEmpty()){
            throw new BadRequestException(errors.toString());
        }
        return (List<Task>) taskRepo.saveAll(TaskConverter.toNewEntity(tasks));
    }

    public Task updateTask(int taskId, TaskModel taskModel) {
        Task task = taskRepo.findById(taskId).orElse(null);
        if(task == null){
            throw new NotFoundException("Task not found");
        }
        TaskValidation.validateTask(taskModel, new HashSet<>(taskId));
        task = TaskConverter.toEntity(task, taskModel);
        return taskRepo.save(task);
    }

    public void deleteTask(int taskId) {
        taskRepo.deleteById(taskId);
    }

    public boolean isTaskExist(int taskId) {
        return taskRepo.findById(taskId).orElse(null) != null;
    }
}
