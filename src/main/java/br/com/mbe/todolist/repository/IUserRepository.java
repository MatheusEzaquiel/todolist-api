package br.com.mbe.todolist.repository;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.mbe.todolist.domain.user.User;
import br.com.mbe.todolist.domain.user.dto.ListUserDTO;

public interface IUserRepository extends JpaRepository<User, UUID> {
	
	UserDetails findByUsername(String username);
	
	/*@Query("SELECT USER FROM users u WHERE u.id = :idUser " +
			"INNER JOIN  ")
	User getUserData(UUID id);*/

	Collection<ListUserDTO> findByEnabled(boolean b);
}
