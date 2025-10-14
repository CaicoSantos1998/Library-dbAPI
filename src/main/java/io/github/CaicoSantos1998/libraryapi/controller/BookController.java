package io.github.CaicoSantos1998.libraryapi.controller;

import io.github.CaicoSantos1998.libraryapi.controller.dto.RegistrationBookDTO;
import io.github.CaicoSantos1998.libraryapi.controller.dto.ResultSearchBookDTO;
import io.github.CaicoSantos1998.libraryapi.controller.mappers.BookMapper;
import io.github.CaicoSantos1998.libraryapi.model.Book;
import io.github.CaicoSantos1998.libraryapi.model.GenderBook;
import io.github.CaicoSantos1998.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController implements GenericController {

    private final BookService service;
    private final BookMapper mapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERATOR', 'ADMIN')")
    public ResponseEntity<Void> save(@RequestBody @Valid RegistrationBookDTO rbDTO) {
        Book book = mapper.toEntity(rbDTO);
        service.save(book);
        URI uri = generateHeaderLocation(book.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'OPERATOR', 'ADMIN')")
    public ResponseEntity<ResultSearchBookDTO> getDetails(@PathVariable String id) {
        return service.getById(UUID.fromString(id))
                .map(book -> {
                    var BookDTO = mapper.toDTO(book);
                    return ResponseEntity.ok(BookDTO);
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        return service.getById(UUID.fromString(id))
                .map(book -> {
                    service.delete(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'OPERATOR', 'ADMIN')")
    public ResponseEntity<Page<ResultSearchBookDTO>> search(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "title", required = false)
            String title,
            @RequestParam(value = "nameAuthor", required = false)
            String nameAuthor,
            @RequestParam(value = "gender", required = false)
            GenderBook gender,
            @RequestParam(value = "publicationYear", required = false)
            Integer publicationYear,
            @RequestParam(value = "page", defaultValue = "0")
            Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10")
            Integer pageSize
            ) {
        Page<Book> pageResult = service.search(
                isbn, title, nameAuthor, gender, publicationYear, page, pageSize);

        Page<ResultSearchBookDTO> result = pageResult.map(mapper::toDTO);

        return ResponseEntity.ok(result);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERATOR', 'ADMIN')")
    public ResponseEntity<Object> update(
            @PathVariable String id,
            @RequestBody @Valid RegistrationBookDTO dto) {
        return service.getById(UUID.fromString(id))
                .map(book -> {
                    Book entityAux = mapper.toEntity(dto);
                    book.setTitle(entityAux.getTitle());
                    book.setDatePublication(entityAux.getDatePublication());
                    book.setGender(entityAux.getGender());
                    book.setPrice(entityAux.getPrice());
                    book.setAuthor(entityAux.getAuthor());

                    service.update(book);
                    return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build() );
    }
}
