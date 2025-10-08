package io.github.CaicoSantos1998.libraryapi.controller;

import io.github.CaicoSantos1998.libraryapi.controller.dto.AuthorDTO;
import io.github.CaicoSantos1998.libraryapi.controller.mappers.AuthorMapper;
import io.github.CaicoSantos1998.libraryapi.model.Author;
import io.github.CaicoSantos1998.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("authors")
@RequiredArgsConstructor
public class AuthorController implements GenericController {

    private final AuthorService service;
    private final AuthorMapper mapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorDTO dto) {
        Author author = mapper.toEntity(dto);
        service.saveAuthor(author);
        URI uri = generateHeaderLocation(author.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDTO> getDetail(@PathVariable String id) {
        var idAuthor = UUID.fromString(id);
        Optional<Author> authorOptional = service.getById(idAuthor);

        return service.getById(idAuthor).map(author -> {
            AuthorDTO dto = mapper.toDTO(author);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        var idAuthor = UUID.fromString(id);
        Optional<Author> authorOptional = service.getById(idAuthor);

        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.delete(authorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> search(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "nationality", required = false) String nationality) {
        List<Author> resultList = service.searchByExample(name, nationality);
        List<AuthorDTO> list = resultList.stream().map(mapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody @Valid AuthorDTO dto) {
        var idAuthor = UUID.fromString(id);
        Optional<Author> authorOptional = service.getById(idAuthor);

        if (authorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var author = authorOptional.get();
        author.setName(dto.name());
        author.setNationality(dto.nationality());
        author.setDateBirth(dto.dateBirth());

        service.update(author);

        return ResponseEntity.noContent().build();
    }

}
