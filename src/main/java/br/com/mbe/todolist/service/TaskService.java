package br.com.mbe.todolist.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mbe.todolist.domain.checklist.Checklist;
import br.com.mbe.todolist.domain.task.Task;
import br.com.mbe.todolist.domain.task.dto.CreateTaskDTO;
import br.com.mbe.todolist.domain.task.dto.DetailTaskDTO;
import br.com.mbe.todolist.domain.task.dto.ListTaskDTO;
import br.com.mbe.todolist.domain.task.dto.UpdateTaskDTO;
import br.com.mbe.todolist.domain.taskPriority.TaskPriority;
import br.com.mbe.todolist.exception.ChecklistNotFoundedException;
import br.com.mbe.todolist.exception.Date1BiggerThanDate2Exception;
import br.com.mbe.todolist.exception.TaskNotFoundedException;
import br.com.mbe.todolist.repository.IChecklistRepository;
import br.com.mbe.todolist.repository.ITaskPriorityRepository;
import br.com.mbe.todolist.repository.ITaskRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class TaskService {

	@Autowired
	ITaskRepository taskRepos;

	@Autowired
	IChecklistRepository checklistRepos;

	@Autowired
	ITaskPriorityRepository taskPriorityRepos;

	public List<ListTaskDTO> list(HttpServletRequest request) {

		List<ListTaskDTO> tasks = taskRepos.findByEnabled(true).stream().map(task -> {

			var startAtDateString = task.getStartAtDate() != null
					? task.getStartAtDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
					: null;
			var startAtTimeString = task.getStartAtTime() != null
					? task.getStartAtTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
					: null;
			var endAtDateString = task.getEndAtDate() != null
					? task.getEndAtDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
					: null;
			var endAtTimeString = task.getEndAtTime() != null
					? task.getEndAtTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
					: null;

			return new ListTaskDTO(task, task.getPriority().getPriority(), startAtDateString, startAtTimeString,
					endAtDateString, endAtTimeString);

		}).toList();

		return tasks;

	}

	public DetailTaskDTO getById(UUID taskId) {

		Optional<Task> taskSelected = taskRepos.findById(taskId);

		return taskSelected.map(task -> {

			var startAtDateString = task.getStartAtDate() != null
					? task.getStartAtDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
					: null;
			var startAtTimeString = task.getStartAtTime() != null
					? task.getStartAtTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
					: null;
			var endAtDateString = task.getEndAtDate() != null
					? task.getEndAtDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
					: null;
			var endAtTimeString = task.getEndAtTime() != null
					? task.getEndAtTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
					: null;

			DetailTaskDTO taskSelectedDTO = new DetailTaskDTO(task, task.getPriority().getPriority(), startAtDateString,
					startAtTimeString, endAtDateString, endAtTimeString);

			return taskSelectedDTO;

		}).orElseThrow(() -> new TaskNotFoundedException("Task not founded or does not exist"));

	}

	public List<DetailTaskDTO> getTasksFromChecklist(UUID checklistId) {

		List<DetailTaskDTO> tasks = taskRepos.getTasksWithIdList(checklistId).stream().map(task -> {

			var startAtDateString = task.getStartAtDate() != null
					? task.getStartAtDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
					: null;
			var startAtTimeString = task.getStartAtTime() != null
					? task.getStartAtTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
					: null;
			var endAtDateString = task.getEndAtDate() != null
					? task.getEndAtDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
					: null;
			var endAtTimeString = task.getEndAtTime() != null
					? task.getEndAtTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
					: null;

			return new DetailTaskDTO(task, task.getPriority().getPriority(), startAtDateString, startAtTimeString,
					endAtDateString, endAtTimeString);

		}).toList();

		return tasks;

	}

	public List<DetailTaskDTO> getDisableTasksFromChecklist(UUID checklistId) {

		List<DetailTaskDTO> tasks = taskRepos.getDisabledTasksWithIdList(checklistId).stream().map(task -> {

			var startAtDateString = task.getStartAtDate() != null
					? task.getStartAtDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
					: null;
			var startAtTimeString = task.getStartAtTime() != null
					? task.getStartAtTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
					: null;
			var endAtDateString = task.getEndAtDate() != null
					? task.getEndAtDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
					: null;
			var endAtTimeString = task.getEndAtTime() != null
					? task.getEndAtTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
					: null;

			return new DetailTaskDTO(task, task.getPriority().getPriority(), startAtDateString, startAtTimeString,
					endAtDateString, endAtTimeString);

		}).toList();

		return tasks;

	}

	public Task create(CreateTaskDTO data) {

		Checklist checklist = checklistRepos.getReferenceById(data.checklistId());

		if (checklist == null) {
			System.out.println("Error! This checklist doesn't exist");
		}

		TaskPriority taskPriority = taskPriorityRepos.findByPriority(data.priority());

		if (taskPriority == null) {
			System.out.println("Error! This priority doesn't exist");
		}

		
		if(data.startAtDate() != null && data.endAtDate() != null) {
			if (data.startAtDate().isAfter(data.endAtDate())) {
				throw new Date1BiggerThanDate2Exception("The start date cannot be bigger than the end date");
			}
		}
	
		
			Task taskToCreate = new Task(
					UUID.randomUUID(),
					data.title(),
					data.description(),
					false,
					taskPriority,
					data.startAtDate(),
					data.startAtTime(),
					data.endAtDate(),
					data.endAtTime(),
					LocalDateTime.now(),
					LocalDateTime.now(),
					true,
					checklist
			);
			

			Task taskCreated = taskRepos.save(taskToCreate);
			
			return taskCreated;
		

	}

	@Transactional
	public DetailTaskDTO update(UpdateTaskDTO data, UUID idTask) throws Exception {


		Optional<Task> task = taskRepos.findById(idTask);

		if (task.isPresent()) {
			
			if (data.title() != null && data.title().length() > 0) {
				task.get().setTitle(data.title());
			}
			
			if (data.description() != null && data.description().length() > 0) {
				task.get().setDescription(data.description());
			}
			
			if (data.done() != null) {
				task.get().setDone(data.done());
			}
			
			if (data.startAtDate() != null) {
				task.get().setStartAtDate(data.startAtDate());
			}

			if (data.startAtTime() != null) {
				task.get().setStartAtTime(data.startAtTime());
			}

			if (data.endAtDate() != null) {
				task.get().setEndAtDate(data.endAtDate());
			}

			if (data.endAtTime() != null) {
				task.get().setEndAtTime(data.endAtTime());
			}

			task.get().setUpdatedAt(LocalDateTime.now());

			if (data.enabled() != null) {
				task.get().setEnabled(data.enabled());
			}

			if (data.priority() != null) {
				TaskPriority taskPriority = taskPriorityRepos.findByPriority(data.priority());
				task.get().setPriority(taskPriority);
			}

			
			return null;
			
		} else {
			throw new TaskNotFoundedException("Task wasn't founded");
		}

	}

	public DetailTaskDTO delete(UUID idTask) throws Exception {

		Task task = taskRepos.getReferenceById(idTask);

		if (task == null) {
			throw new TaskNotFoundedException("Task not founded");
		}

		task.delete();

		String startAtDateString = task.getStartAtDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String startAtTimeString = task.getStartAtTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		String endAtDateString = task.getEndAtDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		String endAtTimeString = task.getEndAtTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

		DetailTaskDTO taskDeleted = new DetailTaskDTO(task, task.getPriority().getPriority(), startAtDateString,
				startAtTimeString, endAtDateString, endAtTimeString);

		return taskDeleted;

	}

	/*
	 * if (!task.getIdUser().equals(idUser)) {
	 * 
	 * return null;// custom Exception
	 * ResponseEntity.status(HttpStatus.BAD_REQUEST).
	 * body("Usuário sem permissão para alterar esta tarefa");
	 * 
	 * }
	 * 
	 * 
	 * Utils.copyNonNulProperties(taskModel, task);
	 */

}
