package io.github.CaicoSantos1998.libraryapi.controller;

import io.github.CaicoSantos1998.libraryapi.controller.dto.UsersDTO;
import io.github.CaicoSantos1998.libraryapi.controller.mappers.UsersMapper;
import io.github.CaicoSantos1998.libraryapi.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService service;
    private final UsersMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody UsersDTO dto) {
        var user = mapper.toEntity(dto);
        service.save(user);
    }

}
