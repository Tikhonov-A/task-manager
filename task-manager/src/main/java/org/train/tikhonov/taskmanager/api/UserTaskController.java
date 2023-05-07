package org.train.tikhonov.taskmanager.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.train.tikhonov.taskmanager.dto.TaskDto;
import org.train.tikhonov.taskmanager.service.UserTaskService;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class UserTaskController {

    private final UserTaskService userTaskService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<TaskDto>> getAllUserTasks(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userTaskService.getAllUserTasks(userId));
    }


    @PatchMapping("/{userId}")
    public ResponseEntity<Map<String, String>> updateUserTask(
            @PathVariable Long userId,
            @RequestParam String taskId,
            @RequestBody TaskDto task
    ) {
        userTaskService.updateUserTaskById(userId, taskId, task);
        return ResponseEntity.ok().body(Map.of("message", "task with id " + taskId + " was updated"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> deleteUserTask(@PathVariable Long userId, @RequestParam String taskId) {
        userTaskService.deleteUserTaskById(userId, taskId);
        return ResponseEntity.ok().body(Map.of("message", "task with id " + taskId + " was deleted"));
    }


}
