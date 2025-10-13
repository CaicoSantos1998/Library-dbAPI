package io.github.CaicoSantos1998.libraryapi.security;

import io.github.CaicoSantos1998.libraryapi.model.Users;
import io.github.CaicoSantos1998.libraryapi.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsersService usersService;

    public Users getUserLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof CustomAuthentication customAuth) {
            return customAuth.getUsers();
        }
        return null;
    }

}
