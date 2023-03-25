package com.lifung.task.management.controllers.admin;

import com.lifung.task.management.model.TaskModel;
import com.lifung.task.management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tasks")
public class AdminTaskController {

    @Autowired
    TaskService taskService;

    @PostMapping()
    public ResponseEntity<Object> createTask(@RequestBody List<TaskModel> tasks){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTasks(tasks));
    }

    @GetMapping()
    public ResponseEntity<Object> getAllTasks(){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasks());
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Object> updateTask(@PathVariable int taskId, @RequestBody TaskModel task){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(taskId, task));
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable int taskId){
        taskService.deleteTask(taskId);
    }
}
