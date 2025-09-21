package pt.antoniopmartinho.todoList.tasks;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;


public interface ItaskRepository extends JpaRepository<TaskModel, UUID> {
        List<TaskModel> findByIdUser(UUID idUser);
}
