package io.github.CaicoSantos1998.libraryapi.controller;

import io.github.CaicoSantos1998.libraryapi.controller.dto.ClientDTO;
import io.github.CaicoSantos1998.libraryapi.controller.mappers.ClientMapper;
import io.github.CaicoSantos1998.libraryapi.model.Client;
import io.github.CaicoSantos1998.libraryapi.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;
    private final ClientMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public void saveClient(@RequestBody @Valid ClientDTO dto) {
        Client client = mapper.toEntity(dto);
        service.save(client);
    }
}
