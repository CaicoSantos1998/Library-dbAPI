package io.github.CaicoSantos1998.libraryapi.controller.mappers;

import io.github.CaicoSantos1998.libraryapi.controller.dto.ClientDTO;
import io.github.CaicoSantos1998.libraryapi.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientDTO dto);
}
