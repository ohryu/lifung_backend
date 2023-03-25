package com.lifung.task.management.controllers;

import com.lifung.task.management.constant.Status;
import com.lifung.task.management.model.StatusModel;
import com.lifung.task.management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/{userId}/task")
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@GetMapping()
	public ResponseEntity<Object> getAllTasksOfUser(@PathVariable int userId) {
		return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTasksOfUser(userId));
	}

	@PatchMapping("/{taskId}")
	public ResponseEntity<Object> updateStatus(@PathVariable int userId, @PathVariable int taskId, @RequestBody StatusModel status) {
		return ResponseEntity.status(HttpStatus.OK).body(taskService.updateStatus(userId, taskId, status));
	}
}
