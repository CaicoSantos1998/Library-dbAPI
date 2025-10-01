package io.github.CaicoSantos1998.libraryapi.repository;

import io.github.CaicoSantos1998.libraryapi.model.Author;
import io.github.CaicoSantos1998.libraryapi.model.Book;
import io.github.CaicoSantos1998.libraryapi.model.GenderBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class RepositoryBookTest {

    @Autowired
    RepositoryBook repositoryBook;

    @Autowired
    RepositoryAuthor repositoryAuthor;

    @Test
    void saveTest() {
        Book book = new Book();
        book.setIsbn("5253-4242");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(GenderBook.FICTION);
        book.setTitle("Other Book");
        book.setDatePublication(LocalDate.of(1980, 01,02));

        Author author = repositoryAuthor.findById(
                UUID.fromString("10c1600b-b230-4c8b-bead-a5bb99fbc3a4"))
                .orElse(null);

        book.setAuthor(author);

        repositoryBook.save(book);
    }

    @Test
    void saveCascadeTest() {
        Book book = new Book();
        book.setIsbn("5253-4242");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(GenderBook.FICTION);
        book.setTitle("Other Book");
        book.setDatePublication(LocalDate.of(1980, 01,02));

        Author author = new Author();
        author.setName("João");
        author.setNationality("Brazil");
        author.setDateBirth(LocalDate.of(1951, 01, 31));

        book.setAuthor(author);

        repositoryBook.save(book);
    }

    @Test
    void saveAuthorAndBookTest() {
        Book book = new Book();
        book.setIsbn("5253-4242");
        book.setPrice(BigDecimal.valueOf(100));
        book.setGender(GenderBook.FICTION);
        book.setTitle("Third Book");
        book.setDatePublication(LocalDate.of(1980, 01,02));

        Author author = new Author();
        author.setName("João");
        author.setNationality("Brazil");
        author.setDateBirth(LocalDate.of(1951, 01, 31));

        repositoryAuthor.save(author);

        book.setAuthor(author);

        repositoryBook.save(book);
    }

    @Test
    void updateAuthorOfBook(){
        var bookUpdated = repositoryBook.findById(
                UUID.fromString("96f0ab08-a89a-4747-9db0-d58dedb03ac5"))
                .orElse(null);

        var idAuthor = repositoryAuthor.findById(
                UUID.fromString("10c1600b-b230-4c8b-bead-a5bb99fbc3a4"))
                .orElse(null);

        bookUpdated.setAuthor(idAuthor);

        repositoryBook.save(bookUpdated);
    }

    @Test
    void delete() {
        UUID id = UUID.fromString("96f0ab08-a89a-4747-9db0-d58dedb03ac5");
        repositoryBook.deleteById(id);
    }

    @Test
    void deleteByCascade() {
        UUID id = UUID.fromString("45a63d37-2167-4596-806e-006a59914bf2");
        repositoryBook.deleteById(id);
    }

    @Test
    void searchByTitle() {
        List<Book> bookByTitle = repositoryBook.findByTitle("The haunted house robbery");
        bookByTitle.forEach(System.out::println);
    }

    @Test
    void searchByIsbn() {
        Book bookList = repositoryBook.findByIsbn("8889-8776");
        System.out.println(bookList);
    }

    @Test
    void listAllBookByQueryJPQL() {
        var result = repositoryBook.listAll();
        result.forEach(System.out::println);
    }

    @Test
    void listAuthorsBook() {
        var result = repositoryBook.listAuthorsBook();
        result.forEach(System.out::println);
    }

    @Test
    void listNamesBook() {
        var result = repositoryBook.listNamesBook();
        System.out.println("Title Books:");
        result.forEach(System.out::println);
    }

    @Test
    void listGendersBooks() {
        var result = repositoryBook.listGendersBooks();
        System.out.println("Genders of Books:");
        result.forEach(System.out::println);
    }

    @Test
    void listByGender() {
        var result = repositoryBook.findByGender(GenderBook.MYSTERY);
        System.out.println("Book ordered by genders: ");
        result.forEach(System.out::println);
    }

    @Test
    void listByGenderPositionalParam() {
        var result = repositoryBook.findByGenderPositionalParam(GenderBook.MYSTERY, "price");
        System.out.println("Ordered book by positional param: ");
        result.forEach(System.out::println);
    }

    @Test
    void deleteByGender() {
        repositoryBook.deleteByGender(GenderBook.FICTION);
    }

    @Test
    void updateTest() {
        repositoryBook.updateDatePublication(
                LocalDate.of(2000, 07,8),
                "Harry Potter and the Goblet of Fire",
                UUID.fromString("f830e186-b461-441b-a0b8-de126da87786"));

        System.out.println("Book updated with successfully!");
    }

}
