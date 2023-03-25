package com.lifung.task.management.service;

import com.lifung.task.management.DatabaseContainer;
import com.lifung.task.management.constant.Status;
import com.lifung.task.management.entity.Task;
import com.lifung.task.management.entity.User;
import com.lifung.task.management.exception.BadRequestException;
import com.lifung.task.management.exception.NotFoundException;
import com.lifung.task.management.exception.PermissionDeny;
import com.lifung.task.management.model.StatusModel;
import com.lifung.task.management.model.TaskModel;
import com.lifung.task.management.repository.TaskRepository;
import com.lifung.task.management.repository.UserRepository;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskServiceIT {

    @ClassRule
    public static DatabaseContainer databaseContainer = DatabaseContainer.getInstance();

    @Autowired
    TaskRepository taskRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    TaskService taskService;

    @Test
    public void getAllTasks_shouldReturnAllTasks() {
        initTasks();
        List<Task> tasks = taskService.getAllTasks();
        assert tasks.size() == 2;
    }

    @Test
    public void getAllTasksOfUser_givenValidUserId_shouldReturnTasksOfUser() {
        User user = initUser("Peter");
        Task task_1 = initTask("Task 1", new Timestamp(System.currentTimeMillis() + 10000000), user.getId());
        Task task_2 = initTask("Task 2", new Timestamp(System.currentTimeMillis() + 10000000), user.getId());
        List<Task> tasks = taskService.getAllTasksOfUser(user.getId());
        assert tasks.size()==2;
    }

    @Test
    public void getAllTasksOfUser_givenInvalidUserId_shouldThrowNotFoundException() {
        try {
            List<Task> tasks = taskService.getAllTasksOfUser(9999);
            assert false;
        } catch (Exception e) {
            assert e instanceof NotFoundException;
        }
    }

    @Test
    public void updateStatus_givenValidTaskIdAndStatus_shouldUpdateSuccessfully() {
        try {
            User user = initUser("Ben");
            Task task = initTask("Task 3", new Timestamp(System.currentTimeMillis() + 10000000), user.getId());
            Task updatedTask = taskService.updateStatus(user.getId(), task.getId(), new StatusModel("IN_PROGRESS"));
            assert updatedTask.getStatus() == Status.IN_PROGRESS;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void updateStatus_givenInValidTaskId_shouldThrowNotFoundException() {
        try {
            User user = initUser("Ben");
            Task updatedTask = taskService.updateStatus(user.getId(), 9999, new StatusModel("IN_PROGRESS"));
            assert false;
        } catch (Exception e) {
            assert e instanceof NotFoundException;
        }
    }

    @Test
    public void updateStatus_givenNullStatus_shouldThrowBadRequestException() {
        try {
            User user = initUser("Ben2");
            Task task = initTask("Task 3", new Timestamp(System.currentTimeMillis() + 10000000), user.getId());
            Task updatedTask = taskService.updateStatus(user.getId(), task.getId(), new StatusModel(null));
            assert false;
        } catch (Exception e) {
            assert e instanceof BadRequestException;
        }
    }

    @Test
    public void updateStatus_givenUserIdDiffFromTaskAssignee_shouldThrowPermissionDenyException() {
        try {
            User user = initUser("Ben3");
            User user2 = initUser("Ben4");
            Task task = initTask("Task 3", new Timestamp(System.currentTimeMillis() + 10000000), user.getId());
            Task updatedTask = taskService.updateStatus(user2.getId(), task.getId(), new StatusModel("IN_PROGRESS"));
            assert false;
        } catch (Exception e) {
            assert e instanceof PermissionDeny;
        }
    }

    @Test
    public void createTasks_givenTaskList_shouldCreateSuccessfully() {
        try {
            User assignee = initUser("May");
            TaskModel task_1 = TaskModel.builder()
                    .title("Task 4")
                    .deadline(new Timestamp(System.currentTimeMillis() + 1000000))
                    .assigneeId(assignee.getId())
                    .reporterId(assignee.getId())
                    .status(Status.TODO)
                    .build();
            TaskModel task_2 = TaskModel.builder()
                    .title("Task 5")
                    .deadline(new Timestamp(System.currentTimeMillis() + 1000000))
                    .assigneeId(assignee.getId())
                    .reporterId(assignee.getId())
                    .status(Status.TODO)
                    .build();
            taskService.createTasks(Arrays.asList(task_1, task_2));
            List<Task> tasks = taskService.getAllTasksOfUser(assignee.getId());
            assert tasks.size()==2;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void updateTask_givenValidTask_shouldUpdateSuccessfully(){
        try {
            User assignee = initUser("Marry");
            Task task = initTask("Task 6", new Timestamp(System.currentTimeMillis()+10000000), assignee.getId());
            TaskModel taskUpdate = TaskModel.builder()
                    .title("Task 6.1")
                    .assigneeId(task.getAssigneeId())
                    .reporterId(task.getReporterId())
                    .status(Status.TODO).build();
            Task result = taskService.updateTask(task.getId(), taskUpdate);
            assert result.getTitle().equals(taskUpdate.getTitle());
        }catch (Exception e){
            assert false;
        }
    }

    public void initTasks() {
        User user = initUser("Thor");
        Task task_1 = Task.builder()
                .title("Task 1")
                .status(Status.TODO)
                .assigneeId(user.getId())
                .reporterId(user.getId())
                .deadline(new Timestamp(System.currentTimeMillis() + 10000000))
                .build();
        Task task_2 = Task.builder()
                .title("Task 2")
                .status(Status.TODO)
                .assigneeId(user.getId())
                .reporterId(user.getId())
                .deadline(new Timestamp(System.currentTimeMillis() + 10000000))
                .build();
        taskRepo.saveAll(Arrays.asList(task_1, task_2));
    }

    @AfterEach
    public void clearDb() {
        taskRepo.deleteAll();
        userRepo.deleteAll();
    }

    public User initUser(String username) {
        User actualUser = User.builder()
                .username(username)
                .build();
        return userRepo.save(actualUser);
    }

    public Task initTask(String title, Timestamp deadline, int assigneeId) {
        Task task = Task.builder()
                .title(title)
                .assigneeId(assigneeId)
                .reporterId(assigneeId)
                .deadline(deadline)
                .status(Status.TODO).build();
        return taskRepo.save(task);
    }

}
