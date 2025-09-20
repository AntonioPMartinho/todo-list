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
    @Column(length = 255, nullable = false)
    private String description;
    @Column(length = 20, nullable = false)
    private String status;
    @Column(length = 20, nullable = false)
    private String dueDate;
    @Column(length = 20, nullable = false)
    private String priority;

    @CreationTimestamp
    private LocalDateTime creationDate;

    private UUID idUser;
}
    