package br.com.mbe.todolist.domain.auth.dto;

import java.util.UUID;

public record LoginResponseDTO(String menssage, String token, UUID id) {


}
