package pt.antoniopmartinho.todoList.tasks;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "tb_task")
public class TaskModel {
    /*
     * ID
     * Title
     * Description
     * Status (To Do, In Progress, Done)
     * Creation Date
     * Due Date
     * Priority (Low, Medium, High)
     */
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 255, nullable = true)
    private String description;
    @Column(length = 20, nullable = false)
    private String status;
    @Column(length = 20, nullable = false)
    private LocalDateTime dueDate;
    @Column(length = 20, nullable = false)
    private String priority;
    @Column(nullable = false)
    private LocalDateTime startAt;

    @CreationTimestamp
    private LocalDateTime creationDate;

    private UUID idUser;

    public void setTitle(String title) throws Exception {
        if(title.length() > 50) {
            throw new Exception("Title cannot be longer than 50 characters");
        }
        this.title = title;

    }
}
