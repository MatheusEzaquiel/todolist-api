package br.com.mbe.todolist.domain.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.mbe.todolist.domain.checklist.Checklist;
import br.com.mbe.todolist.domain.task.dto.CreateTaskDTO;
import br.com.mbe.todolist.domain.taskPriority.TaskPriority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name="Task")
@Table(name="tasks")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name="id")
	private UUID id;

	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="done")
	private Boolean done;
	
	@Column(name="start_at_date")
	private LocalDate startAtDate;
	
	@Column(name="start_at_time")
	private LocalTime startAtTime;
	
	@Column(name="end_at_date")
	private LocalDate endAtDate;
	
	@Column(name="end_at_time")
	private LocalTime endAtTime;
	
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	@Column(name="enabled")
	private Boolean enabled;

	@Column(name="position")
	private Integer position;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "checklist_id")
	private Checklist checklist;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "priority_id")
	private TaskPriority priority;

	public Task() {}

	public Task(UUID id, String title, String description, Boolean done, TaskPriority priority,
			LocalDate startAtDate, LocalTime startAtTime,
			LocalDate endAtDate, LocalTime endAtTime,
			LocalDateTime createdAt, LocalDateTime updatedAt,
			Boolean enabled, Checklist checklist) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.done = done;
		this.priority = priority;
		this.startAtDate = startAtDate;
		this.startAtTime = startAtTime;
		this.endAtDate = endAtDate;
		this.endAtTime = endAtTime;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
		this.checklist = checklist;
	}
	
	public Task(UUID id, String title, Boolean done, String description, TaskPriority priority,
			LocalDate startAtDate, LocalTime startAtTime,
			LocalDate endAtDate, LocalTime endAtTime,
			LocalDateTime createdAt, LocalDateTime updatedAt,
			Boolean enabled) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.done = done;
		this.priority = priority;
		this.startAtDate = startAtDate;
		this.startAtTime = startAtTime;
		this.endAtDate = endAtDate;
		this.endAtTime = endAtTime;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
	}
	
	
	public Task(
			UUID id,
			CreateTaskDTO data,
			TaskPriority taskPriority,
			LocalDateTime createdAt,
			LocalDateTime updatedAt,
			Boolean enabled,
			Checklist checklist) {

		this.id = id;
		this.title = data.title();
		this.description = data.description();
		this.done = false;
		this.priority = taskPriority;
		this.startAtDate = data.startAtDate();
		this.startAtTime = data.startAtTime();
		this.endAtDate = data.endAtDate();
		this.endAtTime = data.endAtTime();
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.enabled = true;
		this.checklist = checklist;
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

	public void setTitle(String title) throws Exception {

		if (title.length() > 60) {

			throw new Exception("The title have to be less than 60 caracters");

		}

		this.title = title;

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Boolean isDone() {
		return this.done;
	}
	
	public void setDone(Boolean done) {
		this.done = done;
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}

	public LocalDate getStartAtDate() {
		return startAtDate;
	}

	public void setStartAtDate(LocalDate startAtDate) {
		this.startAtDate = startAtDate;
	}

	public LocalTime getStartAtTime() {
		return startAtTime;
	}

	public void setStartAtTime(LocalTime startAtTime) {
		this.startAtTime = startAtTime;
	}
	
	public LocalDate getEndAtDate() {
		return endAtDate;
	}

	public void setEndAtDate(LocalDate endAtDate) {
		this.endAtDate = endAtDate;
	}

	public LocalTime getEndAtTime() {
		return endAtTime;
	}

	public void setEndAtTime(LocalTime endAtTime) {
		this.endAtTime = endAtTime;
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

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Checklist getChecklist() {
		return checklist;
	}

	public void setChecklist(Checklist checklist) {
		this.checklist = checklist;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public void delete() {
		this.enabled = false;
		this.updatedAt = LocalDateTime.now();
	}
	
	public void enable() {
		this.enabled = true;
		this.updatedAt = LocalDateTime.now();
	}
}
