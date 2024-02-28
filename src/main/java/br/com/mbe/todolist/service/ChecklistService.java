package br.com.mbe.todolist.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.mbe.todolist.domain.checklist.Checklist;
import br.com.mbe.todolist.domain.checklist.dto.ChecklistJoinTasksDTO;
import br.com.mbe.todolist.domain.checklist.dto.CreateChecklistDTO;
import br.com.mbe.todolist.domain.checklist.dto.DetailChecklistDTO;
import br.com.mbe.todolist.domain.checklist.dto.UpdateChecklistDTO;
import br.com.mbe.todolist.domain.task.Task;
import br.com.mbe.todolist.domain.task.dto.DetailTaskDTO;
import br.com.mbe.todolist.domain.user.User;
import br.com.mbe.todolist.exception.ChecklistNotFoundedException;
import br.com.mbe.todolist.repository.IChecklistRepository;
import br.com.mbe.todolist.repository.ITaskRepository;
import br.com.mbe.todolist.repository.IUserRepository;
import jakarta.transaction.Transactional;

@Service
public class ChecklistService {

	@Autowired
	IChecklistRepository checklistRepos;

	@Autowired
	IUserRepository userRepos;

	@Autowired
	ITaskRepository taskRepos;
	
	@Autowired
	TaskService taskService;
	

	public List<ChecklistJoinTasksDTO> list() {
		
		try {
			
			List<ChecklistJoinTasksDTO> listWithTasksArr = new ArrayList<ChecklistJoinTasksDTO>();

			List<Checklist> checklists = checklistRepos.findByEnabled(1);

			checklists.sort(Comparator.comparing(Checklist::getCreatedAt).reversed()); // Class::method

			List<DetailChecklistDTO> checklistsDTO = checklists
					.stream()
					.map(checklist -> new DetailChecklistDTO(checklist))
					.toList();

			for (DetailChecklistDTO detailChecklist : checklistsDTO) {

				List<DetailTaskDTO> tasksJoinSelected = taskService.getTasksFromChecklist(detailChecklist.id());

				ChecklistJoinTasksDTO checklistsJoined = new ChecklistJoinTasksDTO(detailChecklist, tasksJoinSelected);

				listWithTasksArr.add(checklistsJoined);

			}

			return listWithTasksArr;
			
		} catch (Exception e) {
			System.out.println("error checklist service: method list(): " + e.getMessage());
			return null;
		}
		
	}

	
	public List<ChecklistJoinTasksDTO> listByUser(UUID userId) {

		List<ChecklistJoinTasksDTO> listWithTasksArr = new ArrayList<ChecklistJoinTasksDTO>();

		
		List<Checklist> checklists = checklistRepos.findByEnabledAndUserId(true, userId);

		checklists.sort(Comparator.comparing(Checklist::getCreatedAt).reversed()); // Class::method

		List<DetailChecklistDTO> checklistsDTO = checklists
				.stream()
				.map(checklist -> new DetailChecklistDTO(checklist))
				.toList();

		for (DetailChecklistDTO detailChecklist : checklistsDTO) {

			List<DetailTaskDTO> tasksJoinSelected = taskService.getTasksFromChecklist(detailChecklist.id());

			ChecklistJoinTasksDTO checklistsJoined = new ChecklistJoinTasksDTO(detailChecklist, tasksJoinSelected);

			listWithTasksArr.add(checklistsJoined);

		}

		return listWithTasksArr;

	}
	
	
	public List<ChecklistJoinTasksDTO> listDisabledByUser(@PathVariable UUID userId) {

		List<ChecklistJoinTasksDTO> listWithTasksArr = new ArrayList<ChecklistJoinTasksDTO>();

		List<Checklist> checklists = checklistRepos.findByEnabledAndUserId(false, userId);

		checklists.sort(Comparator.comparing(Checklist::getCreatedAt).reversed()); // Class::method

		List<DetailChecklistDTO> checklistsDTO = checklists
				.stream()
				.map(checklist -> new DetailChecklistDTO(checklist))
				.toList();

		for (DetailChecklistDTO detailChecklist : checklistsDTO) {

			List<DetailTaskDTO> tasksJoinSelected = taskService.getDisableTasksFromChecklist(detailChecklist.id());

			ChecklistJoinTasksDTO checklistsJoined = new ChecklistJoinTasksDTO(detailChecklist, tasksJoinSelected);

			listWithTasksArr.add(checklistsJoined);

		}

		return listWithTasksArr;

	}
	

	public ChecklistJoinTasksDTO getById(UUID checklistId) {
		
		Optional<Checklist> checklistSelected = checklistRepos.findById(checklistId);
		
		return checklistSelected.map(checklist -> {
			
			DetailChecklistDTO checklistSelectedDTO = new DetailChecklistDTO(checklist);

			List<DetailTaskDTO> tasksJoinSelected = taskService.getTasksFromChecklist(checklistId);
			
			

			ChecklistJoinTasksDTO checklists = new ChecklistJoinTasksDTO(checklistSelectedDTO, tasksJoinSelected);

			return checklists;
			
		})
			.orElseThrow(() -> new ChecklistNotFoundedException("The list was not founded or doesn't exist"));
		
	}

	
	public DetailChecklistDTO create(CreateChecklistDTO data) {

		User user = userRepos.getReferenceById(data.userId());

		if (user == null) throw new UsernameNotFoundException("User not founded!");
		

		LocalDateTime currentDate = LocalDateTime.now();
		
		UUID id = UUID.randomUUID();

		Checklist checklist = new Checklist(id, data.title(), true, currentDate, null, user);
		
		DetailChecklistDTO checklistCreated = new DetailChecklistDTO(checklistRepos.save(checklist));

		return checklistCreated;

	}
	
	@Transactional
	public DetailChecklistDTO update(UpdateChecklistDTO data, UUID idChecklist) throws Exception{

		Optional<Checklist> checklist = checklistRepos.findById(idChecklist);

		if (checklist.isPresent()) {
			
			System.out.println("enter");
			
			Checklist checklistUpdated = checklist.get().update(checklist.get(), data);
			
			System.out.println("go update()" + checklistUpdated.getTitle());
			
			var c = checklistRepos.save(checklistUpdated);
			System.out.println("saved for second way [updated]");
			
			return new DetailChecklistDTO(c);
			
		}
		
		throw new ChecklistNotFoundedException("This checklist wasn't founded!");

	}

	
	public DetailChecklistDTO delete(UUID idChecklist) {

		Checklist checklist = checklistRepos.getReferenceById(idChecklist);

		if (checklist == null) {
			return null; // custom Exception ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa
							// // Não encontrada");
		}

		checklist.delete(checklist.getId());

		//delete all tasks associates
		List<Task> tasksAssociates = taskRepos.findByChecklistId(checklist.getId());

		for (Task task : tasksAssociates) {
			task.delete();
		}

		DetailChecklistDTO checklistDeleted = new DetailChecklistDTO(checklist);

		return checklistDeleted;

	}
	
	@Transactional
	public void unarchive(UUID checklistId) throws Exception {
		
		System.out.println("unarchive list");
		
		Optional<Checklist> checklistToUnarchive = checklistRepos.findById(checklistId);
	
		checklistToUnarchive.ifPresent((checklist) -> {
			
			checklist.setEnabled(true);
			checklistRepos.save(checklist);
			
			//enable all tasks associates
			List<Task> tasksAssociates = taskRepos.findByChecklistId(checklist.getId());
			
			for (Task task : tasksAssociates) {
				task.setEnabled(true);
				taskRepos.save(task);	
			}
			
			//DetailChecklistDTO checklistDTO = new DetailChecklistDTO(checklist);
			
		});
		
	}

}