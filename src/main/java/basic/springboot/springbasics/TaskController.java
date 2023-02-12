package basic.springboot.springbasics;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TaskController {
    private final List<Task> taskList;
    private final AtomicInteger taskid = new AtomicInteger(0);
    public TaskController() {
        taskList = new ArrayList<>();
        taskList.add(new Task(taskid.incrementAndGet(),"Task 1", "Description 1", new Date()));
        taskList.add(new Task(taskid.incrementAndGet(),"Task 2", "Description 2", new Date()));
        taskList.add(new Task(taskid.incrementAndGet(),"Task 3", "Description 3", new Date()));
    }

    @GetMapping("/tasks")
    List<Task> getTasks(){
        return taskList;
    }

    @PostMapping("/tasks")
    Task createTask(@RequestBody Task task){
        var newTask = new Task(taskid.incrementAndGet(), task.getTitle(),task.getDescription(),task.getDueDate());
        taskList.add(newTask);
        return newTask;
    }

    // Get a task by id
    @GetMapping("/tasks/{id}")
    Task getTask(@PathVariable("id") Integer id){
        //Todo: Implement this method
        //Todo: Return 404 if task is not found
        for(int i =0;i<taskList.size();i++){
            if(taskList.get(i).id==id){
                return taskList.get(i);
            }
        }
        //return 404 when task is not found
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Task not found"
        );
    }

    //Delete a task by Id
    @DeleteMapping("/tasks/{id}")
    void deleteTask(@PathVariable("id") Integer id){
        //Todo: Implement this method
        //Todo: Return 404 if task is not found
        Task findTask = null;
        for(int i =0;i<taskList.size();i++){
            if(taskList.get(i).id==id){
                findTask = taskList.get(i);
                taskList.remove(i);
            }
        }
        if(findTask==null){
            //return 404;
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Task not found"
            );
        }
    }

    //Update task with given id
    @PatchMapping("/tasks/{id}")
    Task updateTask(@PathVariable("id") Integer id, @RequestBody Task task){
        // code for patching
        for(int i =0;i<taskList.size();i++){
            if(taskList.get(i).id==id){
                taskList.get(i).setTitle(task.title);
                taskList.get(i).setDescription(task.description);
                taskList.get(i).setDueDate(task.dueDate);
                return taskList.get(i);
            }
        }
        //return 404 as task not found;
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Task not found"
        );
    }
}