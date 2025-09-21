package pt.antoniopmartinho.todoList.tasks;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import jakarta.servlet.http.HttpServletRequest;
import pt.antoniopmartinho.todoList.utils.Utils;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ItaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        UUID idUser = (UUID) request.getAttribute("idUser");
        taskModel.setIdUser(idUser);

        // Compare only the date part of startAt with today
        java.time.LocalDate today = java.time.LocalDate.now();
        if (taskModel.getStartAt() == null || taskModel.getDueDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date and ending date are required");
        }
        // Check if startAt or dueDate are in the past
        if (taskModel.getStartAt().toLocalDate().isBefore(today) || taskModel.getDueDate().toLocalDate().isBefore(today)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date and ending date cannot be in the past");
        }
        // Check if dueDate is before startAt
        if (taskModel.getDueDate().toLocalDate().isBefore(taskModel.getStartAt().toLocalDate())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ending date cannot be before start date");
        }

        TaskModel task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @RequestMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        UUID idUser = (UUID) request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID)idUser);
        return tasks; 
    }
    @PutMapping("/{id}")
    public ResponseEntity updateTask(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request){
        UUID idUser = (UUID) request.getAttribute("idUser");
        var task = this.taskRepository.findById(id).orElse(null);
        //Checks if task exists
        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task not found");
        }

        //Check if the task belongs to the user
        if(!task.getIdUser().equals(idUser)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not allowed to update this task");
        }
        Utils.copyNonNullProperties(taskModel, task);
        task.setId(id);
        task.setIdUser((UUID)idUser);
        var updatedTask = this.taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }
}

