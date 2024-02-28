package br.com.mbe.todolist.domain.user.dto;

import java.util.UUID;

import br.com.mbe.todolist.domain.user.User;
import br.com.mbe.todolist.domain.user.UserRole;

public record ListUserDTO(UUID id, String username, String password, String email, UserRole role) {

	public ListUserDTO(User data) {
		this(data.getId(), data.getUsername(), data.getPassword(), data.getEmail(), data.getRole());
	}

	public ListUserDTO(ListUserDTO data) {
		this(data.id(), data.username(), data.password(), data.email(), data.role());
	}

}
