package br.com.mbe.todolist.domain.taskPriority;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Entity(name="taskPriority")
@Table(name="task_priority")
public class TaskPriority {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String priority;
	private Boolean enabled;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@JsonIgnore
	@OneToMany(mappedBy="priority")
	List<Task> tasks;
	
	public TaskPriority() {}
	
	public TaskPriority(UUID id, String priority, Boolean enabled, LocalDateTime createdAt,
			LocalDateTime updatedAt, List<Task> tasks) {
		this.id = id;
		this.priority = priority;
		this.enabled = enabled;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.tasks = tasks;
	}

	public TaskPriority(TaskPriorityDTO data) {
		this.id = UUID.randomUUID();
		this.priority = data.priority();
		this.createdAt = LocalDateTime.now();
		this.enabled = true;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Boolean getEnabled() {
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

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public void update(TaskPriority data) {
		
		if(data.getPriority() != null) {
			this.priority = data.getPriority();
		}
		
		if(data.getEnabled() != null) {
			this.enabled = data.getEnabled();
		}
		
		this.updatedAt = LocalDateTime.now();
	}
	
}
