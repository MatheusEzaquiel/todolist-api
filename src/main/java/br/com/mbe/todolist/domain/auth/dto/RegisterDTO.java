package br.com.mbe.todolist.domain.auth.dto;

import br.com.mbe.todolist.domain.user.UserRole;

public record RegisterDTO(String username, String email, String password, boolean enabled, UserRole role) {

}
