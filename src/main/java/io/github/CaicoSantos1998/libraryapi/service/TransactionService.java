package io.github.CaicoSantos1998.libraryapi.TransactionService;

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

@Service
public class service {

    @Autowired
    RepositoryBook repositoryBook;

    @Autowired
    RepositoryAuthor repositoryAuthor;

    @Transactional
    public void execute() {
        Author author = new Author();
        author.setName("J.K.Rowling");
        author.setNationality("United States");
        author.setDateBirth(LocalDate.of(1975, 10, 21));

        repositoryAuthor.save(author);

        Book book = new Book();
        book.setIsbn("8889-8779");
        book.setPrice(BigDecimal.valueOf(269));
        book.setGender(GenderBook.MYSTERY);
        book.setTitle("Harry Potter and the Order of the Phoenix");
        book.setDatePublication(LocalDate.of(2003, 06,21));
        book.setAuthor(author);

        repositoryBook.save(book);

        if(author.getName().equalsIgnoreCase("Brean Hick")) {
            throw new RuntimeException("Rollback!");
        }
    }
}
