package io.github.CaicoSantos1998.libraryapi.service;

import io.github.CaicoSantos1998.libraryapi.exceptions.OperationNotPermittedException;
import io.github.CaicoSantos1998.libraryapi.model.Author;
import io.github.CaicoSantos1998.libraryapi.model.Users;
import io.github.CaicoSantos1998.libraryapi.repository.RepositoryAuthor;
import io.github.CaicoSantos1998.libraryapi.repository.RepositoryBook;
import io.github.CaicoSantos1998.libraryapi.security.SecurityService;
import io.github.CaicoSantos1998.libraryapi.validator.ValidatorAuthor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final RepositoryAuthor repository;
    private final ValidatorAuthor validator;
    private final RepositoryBook repositoryBook;
    private final SecurityService securityService;


    public Author saveAuthor(Author author) {
        validator.validate(author);
        Users users = securityService.getUserLogged();
        author.setUsers(users);
        return repository.save(author);
    }

    public void update(Author  author) {
        if(author.getId()==null) {
            throw new IllegalArgumentException("Author not exist in Database.");
        }
        validator.validate(author);
        repository.save(author);
    }

    public Optional<Author> getById(UUID id) {
        return  repository.findById(id);
    }

    public void delete(Author author) {
        if (hasBook(author)) {
            throw new OperationNotPermittedException("You cannot delete the author with books already registered!");
        }
        repository.delete(author);
    }

    public List<Author> searchAuthors(String name, String nationality) {
        if (name!=null && nationality!=null) {
            return repository.findByNameContainingIgnoreCaseAndNationalityContainingIgnoreCase(name, nationality);
        }

        if (name!=null) {
            return repository.findByNameContainingIgnoreCase(name);
        }
        if (nationality!=null){
            return repository.findByNationalityContainingIgnoreCase(nationality);
        }
        return repository.findAll();
    }

    public List<Author> searchByExample(String name, String nationality) {
        var author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Author> authorExample = Example.of(author, matcher);
        return repository.findAll(authorExample);
    }

    public boolean hasBook(Author author) {
        return repositoryBook.existsByAuthor(author);
    }
}
