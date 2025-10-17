package io.github.CaicoSantos1998.libraryapi.security;

import io.github.CaicoSantos1998.libraryapi.model.Users;
import io.github.CaicoSantos1998.libraryapi.service.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtCustomAuthenticationFilter extends OncePerRequestFilter {

    private final UsersService usersService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(converterOrNot(authentication)) {
            String login = authentication.getName();
            Users users = usersService.getByLogin(login);
            if(users!=null) {
                authentication = new CustomAuthentication(users);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean converterOrNot(Authentication authentication) {
        return authentication != null & authentication instanceof JwtAuthenticationToken;

    }
}
