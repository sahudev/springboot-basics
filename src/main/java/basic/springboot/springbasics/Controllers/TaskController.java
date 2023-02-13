package basic.springboot.springbasics.Controllers;

import basic.springboot.springbasics.Services.Task;
import basic.springboot.springbasics.Services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    List<Task> getTasks(){
        return taskService.getTasks();
    }

    @PostMapping("/tasks")
    Task createTask(@RequestBody Task task){
        var newTask = taskService.createTask( task.getTitle(), task.getDescription(),task.getDueDate().toString());
        return newTask;
    }

    // Get a task by id
    @GetMapping("/tasks/{id}")
    Task getTask(@PathVariable("id") Integer id){
        return  taskService.getTaskById(id);
    }

    //Delete a task by Id
    @DeleteMapping("/tasks/{id}")
    void deleteTask(@PathVariable("id") Integer id){
        taskService.deleteTask(id);
    }

    //Update task with given id
    @PatchMapping("/tasks/{id}")
    Task updateTask(@PathVariable("id") Integer id, @RequestBody Task task){
        return taskService.updateTask(id,task.getTitle(),task.getDescription(),task.getDueDate().toString());
    }
}