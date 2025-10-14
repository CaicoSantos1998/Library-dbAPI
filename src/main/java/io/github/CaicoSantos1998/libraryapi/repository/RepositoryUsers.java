package io.github.CaicoSantos1998.libraryapi.repository;

import io.github.CaicoSantos1998.libraryapi.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryUsers extends JpaRepository<Users, UUID> {

    Users findByLogin(String login);

    Users findByEmail(String email);
}
