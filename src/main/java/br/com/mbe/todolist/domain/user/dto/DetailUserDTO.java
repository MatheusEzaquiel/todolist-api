package br.com.mbe.todolist.domain.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.mbe.todolist.domain.user.User;
import br.com.mbe.todolist.domain.user.UserRole;

public record DetailUserDTO(UUID id, String username, String password, String email, boolean enabled, LocalDateTime createdAt, LocalDateTime updatedAt, UserRole role) {

	public DetailUserDTO(User data) {
		this(data.getId(), data.getUsername(), data.getPassword(), data.getEmail(), data.isEnabled(), data.getCreatedAt(), data.getUpdatedAt(), data.getRole());
	}

}
