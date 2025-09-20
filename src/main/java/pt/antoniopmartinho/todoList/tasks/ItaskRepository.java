package pt.antoniopmartinho.todoList.tasks;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ItaskRepository extends JpaRepository<TaskModel, UUID> {

}
