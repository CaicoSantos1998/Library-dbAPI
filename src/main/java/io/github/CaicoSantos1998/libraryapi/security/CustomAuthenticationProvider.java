package io.github.CaicoSantos1998.libraryapi.security;

import io.github.CaicoSantos1998.libraryapi.model.Users;
import io.github.CaicoSantos1998.libraryapi.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsersService usersService;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String passwordEntered = authentication.getCredentials().toString();

        Users userFounded = usersService.getByLogin(login);

        if(userFounded==null) {
            throw getErrorUserNotFound();
        }

        String encryptedPassword = userFounded.getPassword();
        boolean passwordMatch = encoder.matches(passwordEntered, encryptedPassword);

        if(passwordMatch) {
            return new CustomAuthentication(userFounded);
        }

        throw getErrorUserNotFound();
    }

    private static UsernameNotFoundException getErrorUserNotFound() {
        return new UsernameNotFoundException("Incorrect username or password!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
