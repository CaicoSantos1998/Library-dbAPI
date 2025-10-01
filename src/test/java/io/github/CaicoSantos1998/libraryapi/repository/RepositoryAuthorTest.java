package io.github.CaicoSantos1998.libraryapi.repository;

import io.github.CaicoSantos1998.libraryapi.model.Author;
import io.github.CaicoSantos1998.libraryapi.model.Book;
import io.github.CaicoSantos1998.libraryapi.model.GenderBook;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class RepositoryAuthorTest {

    @Autowired
    RepositoryAuthor repositoryAuthor;

    @Autowired
    RepositoryBook repositoryBook;

    @Test
    public void saveTest() {
        Author author = new Author();
        author.setName("Maria");
        author.setNationality("Brazil");
        author.setDateBirth(LocalDate.of(1951, 05, 31));

        var saveAuthor = repositoryAuthor.save(author);
        System.out.println("Author saved successfully : " + saveAuthor);
    }

    @Test
    public void updateTest() {
        var id = UUID.fromString("45f30b13-987e-465b-9478-46cc072fd005");

        Optional<Author> authorExist = repositoryAuthor.findById(id);

        if(authorExist.isPresent()) {

            Author authorFound = authorExist.get();
            System.out.println("Author's details: ");
            System.out.println(authorFound);

            authorFound.setDateBirth(LocalDate.of(1960, 01, 30));

            repositoryAuthor.save(authorFound);

        } else {
            System.out.println("Author not found!");
        }
    }

    @Test
    public void listTest() {
        List<Author> authorList = repositoryAuthor.findAll();
        authorList.forEach(System.out::println);
    }

    @Test
    public void countTest() {
        System.out.println("Numbers of Authors: " + repositoryAuthor.count());
    }

    @Test
    public void deleteByIdTest() {
        var id = UUID.fromString("8b5b2fbe-a6af-4e41-aed5-1ae2ac619a65");

        repositoryAuthor.deleteById(id);

        System.out.println("Author deleted");
    }

    @Test
    public void deleteTest() {
        var id = UUID.fromString("5cfd503a-5f64-4b24-a19f-7774bac69685");
        var findById = repositoryAuthor.findById(id).get();
        repositoryAuthor.delete(findById);
    }

    @Test
    void saveAuthorWithBook() {
        Author author = new Author();
        author.setName("Brean Hick");
        author.setNationality("United States");
        author.setDateBirth(LocalDate.of(1975, 10, 21));

        Book book = new Book();
        book.setIsbn("5546-8777");
        book.setPrice(BigDecimal.valueOf(204));
        book.setGender(GenderBook.MYSTERY);
        book.setTitle("The BIG");
        book.setDatePublication(LocalDate.of(1989, 11,02));
        book.setAuthor(author);

        Book book2 = new Book();
        book2.setIsbn("5546-8776");
        book2.setPrice(BigDecimal.valueOf(650));
        book2.setGender(GenderBook.MYSTERY);
        book2.setTitle("The BIG ONE");
        book2.setDatePublication(LocalDate.of(1990, 05, 20));
        book2.setAuthor(author);

        author.setBooks(new ArrayList<>());
        author.getBooks().add(book);
        author.getBooks().add(book2);

        repositoryAuthor.save(author);

//        repositoryBook.saveAll(author.getBooks());

    }

    @Test
    void listBookAuthor() {
        var id = UUID.fromString("f830e186-b461-441b-a0b8-de126da87786");
        var listAuthor = repositoryAuthor.findById(id).get();

        List<Book> bookList = repositoryBook.findByAuthor(listAuthor);
        listAuthor.setBooks(bookList);

        listAuthor.getBooks().forEach(System.out::println);
    }
}
