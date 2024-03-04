package br.com.mbe.todolist.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import br.com.mbe.todolist.domain.auth.dto.RegisterDTO;
import br.com.mbe.todolist.domain.user.User;
import br.com.mbe.todolist.domain.user.dto.DetailUserDTO;
import br.com.mbe.todolist.domain.user.dto.ListUserDTO;
import br.com.mbe.todolist.domain.user.dto.UpdateUserDTO;
import br.com.mbe.todolist.domain.user.dto.UserActivityDTO;
import br.com.mbe.todolist.domain.user.dto.UserWithToken;
import br.com.mbe.todolist.exception.ShortPasswordException;
import br.com.mbe.todolist.exception.UserAlredyExistException;
import br.com.mbe.todolist.exception.UserNotFoundedException;
import br.com.mbe.todolist.repository.IChecklistRepository;
import br.com.mbe.todolist.repository.ITaskRepository;
import br.com.mbe.todolist.repository.IUserRepository;
import br.com.mbe.todolist.service.auth.TokenService;
import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	IUserRepository userRepos;
	
	@Autowired
	IChecklistRepository checklistRepos;
	
	@Autowired
	ITaskRepository taskRepos;
	
	@Autowired
	TokenService tokenService;
	
	public List<ListUserDTO> list() {
		
		List<ListUserDTO> usersSelected = userRepos.findByEnabled(true)
				.stream()
				.map(user -> new ListUserDTO(user))
				.toList();
	
		
		return usersSelected;
				
	}

	public DetailUserDTO getById(UUID userId) {
		
		Optional<User> userSelected = userRepos.findById(userId);
		
		return userSelected.map(user -> {
			
			DetailUserDTO userDTO = new DetailUserDTO(user);
			
			return userDTO;

		}).orElseThrow(() -> new UserNotFoundedException("User not founded"));
		
		
	}
	
	public DetailUserDTO create(RegisterDTO data) {
		
		User userSelected = (User) userRepos.findByUsername(data.username());

		if (userSelected != null) throw new UserAlredyExistException("A user with this username alredy exist");
		
		if(data.password().length() < 8 && data.password() == "") throw new ShortPasswordException("The password is short, It's should be bigger than 8 caracters");
		
		String encryptedpassword = new BCryptPasswordEncoder().encode(data.password());
		
		User user = new User(UUID.randomUUID(), data.username().toLowerCase(), encryptedpassword, data.email().toLowerCase(), LocalDateTime.now(), data.role());

		return new DetailUserDTO(userRepos.save(user));

	}
	
	@Transactional
	public UserWithToken updateById(UpdateUserDTO data, UUID userId) {
		
		Optional<User> userSelected = userRepos.findById(userId);
		
		if(userSelected.isPresent()) {
			
				if(data.username() != null && data.username() != "") {
					userSelected.get().setUsername(data.username());
				}
				
				if(data.email() != null && data.email() != "") {
					userSelected.get().setEmail(data.email());
				}
				
				if(data.password() != null && !data.password().isEmpty()) {
					System.out.println("password data: " + data.password());
					System.out.println("new password set");
					String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
					userSelected.get().setPassword(encryptedPassword);
				} else {
					System.out.println("string pass empty");
				}
				
				userSelected.get().setUpdatedAt(LocalDateTime.now());

				var userUpdated = userRepos.save(userSelected.get());
			
			//Generating a new token
			String tokenAuth = tokenService.generateToken(userUpdated);
			System.out.println(tokenAuth);
			
			DetailUserDTO userDTO = new DetailUserDTO(userUpdated);
			
			return new UserWithToken(userDTO, tokenAuth);
			
		}
		
		throw new UserNotFoundedException("User not founded or doesn't exist");
		
	}
	
	
	public UserActivityDTO getMyActivity(UUID userId) {
		
		Optional<User> userVerify = userRepos.findById(userId);
		
		if(userVerify.isEmpty()) {
			throw  new UserNotFoundedException("User not founded or doesn't exist"); 
		}
		
		Integer totalChecklists = checklistRepos.count(userId);
		Integer totalArchivedChecklists = checklistRepos.countArchiveds(userId);
		Integer totalLateTasks = taskRepos.countLateTasksByUser(userId);
		
		return new UserActivityDTO(totalChecklists, totalArchivedChecklists, totalLateTasks);
	}
	
}
