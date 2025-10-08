package io.github.CaicoSantos1998.libraryapi.service;

import io.github.CaicoSantos1998.libraryapi.model.Author;
import io.github.CaicoSantos1998.libraryapi.model.Book;
import io.github.CaicoSantos1998.libraryapi.model.GenderBook;
import io.github.CaicoSantos1998.libraryapi.repository.RepositoryAuthor;
import io.github.CaicoSantos1998.libraryapi.repository.RepositoryBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    RepositoryBook repositoryBook;

    @Autowired
    RepositoryAuthor repositoryAuthor;

    @Transactional
    public void updatingNotUpdate() {
        var book = repositoryBook
                .findById(UUID.fromString("d3a36ab1-9bbb-4d71-ba73-99258cd678d3"))
                .orElse(null);

        book.setDatePublication(LocalDate.of(1997,06,26));
    }

    @Transactional
    public void execute() {
        Author author = new Author();
        author.setName("J.K.Rowling");
        author.setNationality("United States");
        author.setDateBirth(LocalDate.of(1975, 10, 21));

        repositoryAuthor.save(author);

        Book book = new Book();
        book.setIsbn("8889-8780");
        book.setPrice(BigDecimal.valueOf(289));
        book.setGender(GenderBook.MYSTERY);
        book.setTitle("Harry Potter and the Half-Blood Prince");
        book.setDatePublication(LocalDate.of(2005, 07,16));
        book.setAuthor(author);

        repositoryBook.save(book);

        if(author.getName().equalsIgnoreCase("J.K.Rowling")) {
            throw new RuntimeException("Rollback!");
        }
    }
}
