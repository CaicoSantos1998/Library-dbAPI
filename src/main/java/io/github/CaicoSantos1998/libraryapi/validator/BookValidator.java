package io.github.CaicoSantos1998.libraryapi.validator;

import io.github.CaicoSantos1998.libraryapi.exceptions.FieldInvalidatedException;
import io.github.CaicoSantos1998.libraryapi.exceptions.RegisterDuplicatedException;
import io.github.CaicoSantos1998.libraryapi.model.Book;
import io.github.CaicoSantos1998.libraryapi.repository.RepositoryBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookValidator {

    private static final int YEAR_MANDATORY_PRICE = 2020;

    private final RepositoryBook repository;

    public void validate(Book book) {
        if(existsBookWithIsbn(book)) {
            throw new RegisterDuplicatedException("ISBN already registered!");
        }
        if(isPriceMandatoryNull(book)) {
            throw new FieldInvalidatedException("price", "Books published after 2020 have a mandatory price!");
        }
    }

    private boolean isPriceMandatoryNull(Book book) {
        return book.getPrice() == null && book.getDatePublication().getYear() >= YEAR_MANDATORY_PRICE;
    }

    private boolean existsBookWithIsbn(Book book) {
        Optional<Book> bookFound = repository.findByIsbn(book.getIsbn());

        if(book.getId()==null) {
            return bookFound.isPresent();
        }
        return bookFound
                .map(Book::getId)
                .stream()
                .anyMatch(id -> !id.equals(book.getId()));
    }
}
