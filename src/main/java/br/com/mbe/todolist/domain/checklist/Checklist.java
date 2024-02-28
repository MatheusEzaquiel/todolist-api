package br.com.mbe.todolist.domain.checklist;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.mbe.todolist.domain.checklist.dto.UpdateChecklistDTO;
import br.com.mbe.todolist.domain.task.Task;
import br.com.mbe.todolist.domain.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name="Checklist")
@Table(name="checklists")
public class Checklist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String title;
	private Boolean enabled;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@JsonIgnore
	@OneToMany(mappedBy="checklist")
	List<Task> tasks;
	
	
	public Checklist() {}
	
	public Checklist(UUID id, String title, Boolean enabled, LocalDateTime created_at, LocalDateTime updated_at, User user) {
		this.id = id;
		this.title = title;
		this.enabled = enabled;
		this.createdAt = created_at;
		this.updatedAt = updated_at;
		this.user = user;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Checklist update(Checklist checklist, UpdateChecklistDTO data) {
		
		if(data.title() != null && data.title() != "") {
			checklist.setTitle(data.title());
		}
		
		//if() checklist.setEnabled(true);
		
		if(data.enabled() != checklist.isEnabled() && data.enabled() != null) {
			checklist.setEnabled(data.enabled());
		}
		
		checklist.setUpdatedAt(LocalDateTime.now());
		
		return checklist;
	
	}
	
	public void delete(UUID id) {
		this.enabled = false;
	}
}
