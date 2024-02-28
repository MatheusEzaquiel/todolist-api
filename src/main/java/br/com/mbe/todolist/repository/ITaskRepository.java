package br.com.mbe.todolist.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.mbe.todolist.domain.task.Task;
import br.com.mbe.todolist.domain.task.dto.ListTaskDTO;

public interface ITaskRepository extends JpaRepository<Task, UUID>{

	List<Task> findByEnabled(Boolean enabled);
	
	//List<DetailTaskDTO> findByChecklist(UUID checklistId);
	
	@Query(value = 
			"SELECT t " + 
			"FROM Task t " +
			"WHERE t.checklist.id = :checklistId " +
			"AND t.enabled = true " +
			"ORDER BY t.createdAt ASC")
	List<Task> getTasksWithIdList(@Param("checklistId") UUID checklistId);
	
	@Query(value = 
			"SELECT t " + 
			"FROM Task t " +
			"WHERE t.checklist.id = :checklistId " +
			"AND t.enabled = false " +
			"ORDER BY t.createdAt ASC")
	List<Task> getDisabledTasksWithIdList(@Param("checklistId") UUID checklistId);
	
	
	@Query(value = 
			"SELECT t FROM Task t")
	List<Task> getAll();

	List<Task> findByChecklistId(UUID id);
	
	@Query("SELECT COUNT(t.id) FROM Task t WHERE t.checklist.user.id = :userId AND t.endAtDate < CURRENT_DATE")
	Integer countLateTasksByUser(@Param("userId") UUID userId);
	
}
