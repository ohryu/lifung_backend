package com.lifung.task.management.converter;

import com.lifung.task.management.entity.Task;
import com.lifung.task.management.model.TaskModel;

import java.util.ArrayList;
import java.util.List;

public class TaskConverter {
    public static List<Task> toNewEntity(List<TaskModel> taskModels) {
        List<Task> tasks = new ArrayList<>();
        for (TaskModel taskModel : taskModels) {
            Task task = Task.builder()
                    .title(taskModel.getTitle())
                    .deadline(taskModel.getDeadline())
                    .assigneeId(taskModel.getAssigneeId())
                    .reporterId(taskModel.getReporterId())
                    .status(taskModel.getStatus())
                    .build();
            tasks.add(task);
        }
        return tasks;
    }

    public static Task toEntity(Task task, TaskModel taskModel) {
        if (taskModel.getTitle() != null) {
            task.setTitle(taskModel.getTitle());
        }
        task.setStatus(taskModel.getStatus());
        if (taskModel.getAssigneeId() != 0) {
            task.setAssigneeId(taskModel.getAssigneeId());
        }
        if (taskModel.getReporterId() != 0) {
            task.setReporterId(taskModel.getReporterId());
        }
        if (taskModel.getDeadline() != null) {
            task.setDeadline(taskModel.getDeadline());
        }
        return task;
    }
}
