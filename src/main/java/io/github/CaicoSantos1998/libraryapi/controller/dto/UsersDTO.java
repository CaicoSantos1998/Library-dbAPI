package io.github.CaicoSantos1998.libraryapi.controller.dto;

import java.util.List;

public record UsersDTO(String login, String password, List<String> roles) {
}
