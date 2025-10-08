package io.github.CaicoSantos1998.libraryapi.repository;

import io.github.CaicoSantos1998.libraryapi.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RepositoryAuthor extends JpaRepository<Author, UUID> {

    List<Author> findByNameContainingIgnoreCase(String name);
    List<Author> findByNationalityContainingIgnoreCase(String nationality);
    List<Author> findByNameContainingIgnoreCaseAndNationalityContainingIgnoreCase(String name, String nationality);

    Optional<Author> findByNameAndDateBirthAndNationality(
            String name, LocalDate dateBirth, String nationality
    );
}
