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
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class UserTaskController {

    private final UserTaskService userTaskService;

    @GetMapping("")
    public ResponseEntity<List<TaskDto>> getAllUserTasks(@RequestParam UUID userId) {
        return ResponseEntity.ok().body(userTaskService.getAllUserTasks(userId));
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> addUserTasks(@RequestParam UUID userId, @RequestBody TaskDto task) {
        userTaskService.addUserTask(userId,task);
        return ResponseEntity.ok().body(Map.of("message", "task was added"));
    }


    @PatchMapping("/{taskId}")
    public ResponseEntity<Map<String, String>> updateUserTask(
            @RequestParam UUID userId,
            @PathVariable String taskId,
            @RequestBody TaskDto task
    ) {
        userTaskService.updateUserTaskById(userId, taskId, task);
        return ResponseEntity.ok().body(Map.of("message", "task with id " + taskId + " was updated"));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Map<String, String>> deleteUserTask(@RequestParam UUID userId, @PathVariable String taskId) {
        userTaskService.deleteUserTaskById(userId, taskId);
        return ResponseEntity.ok().body(Map.of("message", "task with id " + taskId + " was deleted"));
    }

}
