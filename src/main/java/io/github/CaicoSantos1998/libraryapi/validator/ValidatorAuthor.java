package io.github.CaicoSantos1998.libraryapi.validator;

import io.github.CaicoSantos1998.libraryapi.exceptions.RegisterDuplicatedException;
import io.github.CaicoSantos1998.libraryapi.model.Author;
import io.github.CaicoSantos1998.libraryapi.repository.RepositoryAuthor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidatorAuthor {

    private final RepositoryAuthor repository;

    public ValidatorAuthor(RepositoryAuthor repository) {
        this.repository = repository;
    }

    public void validate(Author author) {
        if(existsAuthor(author)) {
            throw new RegisterDuplicatedException("Author already exists in the Database!");
        }
    }

    private boolean existsAuthor(Author author) {
        Optional<Author> authorFound = repository.findByNameAndDateBirthAndNationality(
                author.getName(), author.getDateBirth(), author.getNationality()
        );

        if(author.getId()==null) {
            return authorFound.isPresent();
        }

        if(authorFound.isEmpty()) {
            return false;
        }

        return !author.getId().equals(authorFound.get().getId());
    }
}
