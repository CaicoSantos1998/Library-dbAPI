package io.github.CaicoSantos1998.libraryapi.service;

import io.github.CaicoSantos1998.libraryapi.model.Book;
import io.github.CaicoSantos1998.libraryapi.model.GenderBook;
import io.github.CaicoSantos1998.libraryapi.repository.RepositoryBook;
import io.github.CaicoSantos1998.libraryapi.validator.BookValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.CaicoSantos1998.libraryapi.repository.specs.BookSpecs.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final RepositoryBook  repository;
    private final BookValidator validator;

    public Book save(Book book) {
        validator.validate(book);
        return repository.save(book);
    }

    public Optional<Book> getById(UUID id) {
        return repository.findById(id);
    }

    public void delete(Book book) {
        repository.delete(book);
    }

    public Page<Book> search(
            String isbn,
            String title,
            String nameAuthor,
            GenderBook gender,
            Integer publicationYear,
            Integer page,
            Integer pageSize) {
        Specification<Book> specs = Specification.where((root, query, cb) ->
                cb.conjunction() );
        if(isbn!=null) {
            specs = specs.and(isbnEqual(isbn));
        }
        if(title!=null) {
            specs = specs.and(titleLike(title));
        }
        if (gender!=null) {
            specs = specs.and(genderEqual(gender));
        }
        if(publicationYear!=null) {
            specs = specs.and(publicationYearEqual(publicationYear));
        }
        if(nameAuthor!=null) {
            specs = specs.and(nameAuthorLike(nameAuthor));
        }
        Pageable pageable = PageRequest.of(0, 10);

        return repository.findAll(specs, pageable);
    }

    public void update(Book book) {
        if(book.getId()==null) {
            throw new IllegalArgumentException("To update, the book must already be saved!!");
        }
        validator.validate(book);
        repository.save(book);
    }
}
