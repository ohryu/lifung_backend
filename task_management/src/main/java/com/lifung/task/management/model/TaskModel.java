package com.lifung.task.management.model;

import com.lifung.task.management.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskModel {
    private String title;
    private Timestamp deadline;
    private int assigneeId;
    private int reporterId;
    private Status status = Status.TODO;
}
