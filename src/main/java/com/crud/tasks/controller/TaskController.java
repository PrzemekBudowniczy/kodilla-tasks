package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final DbService service;
    private final TaskMapper taskMapper;

    @GetMapping
    public List<TaskDto> getTasks() {
        List<Task> tasks = service.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

//    @GetMapping("/id")
//    public TaskDto getTaskWithId(@RequestParam Long id) throws TaskNotFoundException {
//        Optional<Task> task = service.getTaskById(id);
//        return taskMapper.mapToTaskDto(task.orElseThrow(TaskNotFoundException::new));
//    }

    @GetMapping("/id")
    public ResponseEntity<TaskDto> getTaskWithId(@RequestParam Long id) throws TaskNotFoundException {
        Task task = service.getTaskById(id);
//        return new ResponseEntity<>(taskMapper.mapToTaskDto(task), HttpStatus.OK);
        return ResponseEntity.ok(taskMapper.mapToTaskDto(task));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        service.saveTask(task);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = service.saveTask(task);
        return ResponseEntity.ok(taskMapper.mapToTaskDto(savedTask));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTask(@RequestParam Long id) throws TaskNotFoundException {
        service.deleteTask(id);
        return ResponseEntity.ok().build();
    }

}
