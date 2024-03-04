package br.com.mbe.todolist.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.mbe.todolist.domain.checklist.Checklist;

public interface IChecklistRepository extends JpaRepository<Checklist, UUID>{
	
	List<Checklist> findByEnabledAndUserId(boolean i, UUID userId);
	
	List<Checklist> findByEnabled(Boolean i);
	
	@Query("SELECT COUNT(c.id) FROM Checklist c where enabled = true AND c.user.id = :userId")
	Integer count(@Param("userId") UUID userId);
	
	@Query("SELECT COUNT(c.id) FROM Checklist c where enabled = false AND c.user.id = :userId")
	Integer countArchiveds(@Param("userId") UUID userId);
	
	
	

	
}
