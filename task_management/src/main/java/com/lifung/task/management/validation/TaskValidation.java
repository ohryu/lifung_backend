package com.lifung.task.management.validation;

import com.lifung.task.management.constant.Status;
import com.lifung.task.management.exception.BadRequestException;
import com.lifung.task.management.model.TaskModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TaskValidation {

    public static Status validateAndGetStatus(String statusString) {
        for (Status status : Status.values()) {
            if (status.toString().equals(statusString)) {
                return status;
            }
        }
        throw new BadRequestException("Invalid status");
    }

    public static List<String> validateTask(TaskModel task, Set<Integer> validUserIds) {
        List<String> error = new ArrayList<>();
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            error.add("Must input title");
        }
        if (task.getDeadline() != null
                && task.getDeadline().before(new Timestamp(System.currentTimeMillis()))) {
            error.add("Deadline must be after current time");
        }
        if (task.getAssigneeId() == 0) {
            error.add("Must input Assignee");
        } else if (!validUserIds.contains(task.getAssigneeId())) {
            error.add("Assignee not found");
        }
        if (task.getReporterId() == 0) {
            error.add("Must input Reporter");
        } else if (!validUserIds.contains(task.getReporterId())) {
            error.add("Reporter not found");
        }
        return error;
    }
}
