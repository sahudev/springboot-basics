package basic.springboot.springbasics.Services;

import org.springframework.aot.generate.GeneratedMethods;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TaskService {
    private final List<Task> taskList;
    private final AtomicInteger taskId = new AtomicInteger(0);

    public static class TaskNotFoundException extends IllegalStateException {
        public TaskNotFoundException(Integer id) {
            super("Task Id " + id + " not found");
        }
    }

    public TaskService() {
        taskList = new ArrayList<>();
        taskList.add(new Task(taskId.incrementAndGet(),"Task 1", "Description 1", LocalDateTime.now().toString()));
        taskList.add(new Task(taskId.incrementAndGet(),"Task 2", "Description 2",LocalDateTime.now().toString()));
        taskList.add(new Task(taskId.incrementAndGet(),"Task 3", "Description 3",LocalDateTime.now().toString()));
    }

    public List<Task> getTasks(){
        return taskList;
    }

    public Task createTask(String title, String description, String dueDate){
       var newTask = new Task(taskId.incrementAndGet(), title, description, dueDate);
       taskList.add(newTask);
       return newTask;
    };

    public Task getTaskById(int id){
        return taskList.stream().filter(task -> task.getId() == id).findFirst().orElseThrow(()->new TaskNotFoundException(id));
    }

    public Task updateTask(int id, String title, String description, String dueDate){
        var task = getTaskById(id);
        if(title != null){ task.setTitle(title);};
        if (description!=null){task.setDescription(description);};
        if(dueDate!=null){ task.setDueDate(dueDate);}
        return task;
    }

    public void deleteTask(int id){
        taskList.removeIf(task -> task.getId() == id);
    }
}
