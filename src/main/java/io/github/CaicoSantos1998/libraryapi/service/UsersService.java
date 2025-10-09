package io.github.CaicoSantos1998.libraryapi.service;

import io.github.CaicoSantos1998.libraryapi.model.Users;
import io.github.CaicoSantos1998.libraryapi.repository.RepositoryUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final RepositoryUsers repository;
    private final PasswordEncoder encoder;

    public void save(Users users) {
        var password = users.getPassword();
        users.setPassword(encoder.encode(password));
        repository.save(users);
    }

    public Users getByLogin(String login) {
        return repository.findByLogin(login);
    }
}
