package br.com.mbe.todolist.domain.user.dto;

public record UserWithToken(DetailUserDTO user, String token) {

}
