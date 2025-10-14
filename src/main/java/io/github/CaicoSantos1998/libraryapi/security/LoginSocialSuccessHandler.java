package io.github.CaicoSantos1998.libraryapi.security;

import io.github.CaicoSantos1998.libraryapi.model.Users;
import io.github.CaicoSantos1998.libraryapi.service.UsersService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final String PASSWORD_STANDARD = "456789";

    private final UsersService usersService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        Users users = usersService.getByEmail(email);

        if(users == null) {
            users = registrationNewUsers(email);
        }
        authentication = new CustomAuthentication(users);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Users registrationNewUsers(String email) {
        Users users;
        users = new Users();
        users.setEmail(email);
        users.setLogin(getLoginByEmail(email));
        users.setPassword(PASSWORD_STANDARD);
        users.setRoles(List.of("USER"));

        usersService.save(users);
        return users;
    }

    private String getLoginByEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }
}
