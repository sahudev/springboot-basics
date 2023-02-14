package basic.springboot.springbasics.Controllers;

import basic.springboot.springbasics.Services.Task;
import basic.springboot.springbasics.Services.TaskService;
import basic.springboot.springbasics.dtos.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
   ResponseEntity<List<Task>> getTasks(){
        return ResponseEntity.ok(taskService.getTasks());
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@RequestBody Task task){
        var newTask = taskService.createTask( task.getTitle(), task.getDescription(),task.getDueDate().toString());
        return ResponseEntity.created(URI.create("/tasks/" + newTask.getId())).body(newTask);
    }

    // Get a task by id
    @GetMapping("/tasks/{id}")
    ResponseEntity <Task> getTask(@PathVariable("id") Integer id){
        return  ResponseEntity.ok(taskService.getTaskById(id));
    }

    //Delete a task by Id
    @DeleteMapping("/tasks/{id}")
    ResponseEntity<Task> deleteTask( @PathVariable("id") Integer id){
        return ResponseEntity.accepted().body(taskService.deleteTask(id));
    }

    //Update task with given id
    @PatchMapping("/tasks/{id}")
   ResponseEntity<Task> updateTask(@PathVariable("id") Integer id, @RequestBody Task task){
        return ResponseEntity.accepted().body(taskService.updateTask(id,task.getTitle(),task.getDescription(),task.getDueDate().toString()));
    }

    // Creating an exception handler to capture any error generated in the controller.
    // Any exception generated in the controller, this function is called automatically
    @ExceptionHandler(TaskService.TaskNotFoundException.class)
    ResponseEntity<ErrorResponse> handleErrors(TaskService.TaskNotFoundException e){
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
}