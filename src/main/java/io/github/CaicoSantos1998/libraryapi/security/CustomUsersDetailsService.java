package io.github.CaicoSantos1998.libraryapi.security;

import io.github.CaicoSantos1998.libraryapi.model.Users;
import io.github.CaicoSantos1998.libraryapi.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUsersDetailsService implements UserDetailsService {

    private final UsersService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users users = service.getByLogin(login);

        if(users==null) {
            throw new UsernameNotFoundException("User not found!");
        }

        return User.builder()
                .username(users.getLogin())
                .password(users.getPassword())
                .roles(users.getRoles().toArray(new String[users.getRoles().size()]))
                .build();
    }
}
