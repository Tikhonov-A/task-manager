package org.train.tikhonov.taskmanager.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.train.tikhonov.taskmanager.dto.TaskRequest;
import org.train.tikhonov.taskmanager.dto.TaskResponse;
import org.train.tikhonov.taskmanager.service.UserTaskService;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class UserTaskController {

    private final UserTaskService userTaskService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResponse> getAllUserTasks(@RequestParam UUID userId) {
        return userTaskService.getAllUserTasks(userId);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> addUserTasks(@RequestParam UUID userId, @RequestBody TaskRequest task) {
        userTaskService.addUserTask(userId,task);
        return Map.of("message", "task was added");
    }


    @PatchMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> updateUserTask(
            @RequestParam UUID userId,
            @PathVariable String taskId,
            @RequestBody TaskRequest task
    ) {
        userTaskService.updateUserTaskById(userId, taskId, task);
        return Map.of("message", "task with id " + taskId + " was updated");
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> deleteUserTask(@RequestParam UUID userId, @PathVariable String taskId) {
        userTaskService.deleteUserTaskById(userId, taskId);
        return Map.of("message", "task with id " + taskId + " was deleted");
    }

}
