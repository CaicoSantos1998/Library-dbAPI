package io.github.CaicoSantos1998.libraryapi.controller.mappers;

import io.github.CaicoSantos1998.libraryapi.controller.dto.UsersDTO;
import io.github.CaicoSantos1998.libraryapi.model.Users;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    Users toEntity(UsersDTO dto);
}
