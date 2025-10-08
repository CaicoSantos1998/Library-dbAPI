package io.github.CaicoSantos1998.libraryapi.repository;

import io.github.CaicoSantos1998.libraryapi.model.Author;
import io.github.CaicoSantos1998.libraryapi.model.Book;
import io.github.CaicoSantos1998.libraryapi.model.GenderBook;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryBook extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {

    List<Book> findByAuthor(Author author);

    List<Book> findByTitle(String title);

    Optional<Book> findByIsbn(String isbn);

    @Query("SELECT bk FROM Book AS bk ORDER BY bk.title, bk.price")
    List<Book> listAll();

    @Query("SELECT a FROM Book bk JOIN bk.author a")
    List<Author> listAuthorsBook();

    @Query("SELECT DISTINCT b.title FROM Book b ORDER BY b.title")
    List<String> listNamesBook();

    @Query("""
            SELECT bk.gender
            FROM Book bk
            JOIN bk.author ar
            WHERE ar.nationality = 'Brazilian'
            ORDER BY bk.gender
            """)
    List<String> listGendersBooks();

    @Query("SELECT bk FROM Book bk WHERE bk.gender = :gender ORDER BY bk.datePublication")
    List<Book> findByGender(@Param("gender") GenderBook genderBook);

    @Query("SELECT bk FROM Book bk WHERE bk.gender = ?1 ORDER BY ?2")
    List<Book> findByGenderPositionalParam (GenderBook genderBook, String nameProperties);

    @Modifying
    @Transactional
    @Query("DELETE FROM Book WHERE gender = ?1")
    void deleteByGender(GenderBook genderBook);

    @Modifying
    @Transactional
    @Query("UPDATE Book bk SET bk.datePublication = ?1, bk.title = ?2 WHERE bk.id = ?3")
    void updateDatePublication(LocalDate newDate, String title, UUID id);

    boolean existsByAuthor(Author author);
}
