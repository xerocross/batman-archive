package dev.adamcross.europa.service;

import dev.adamcross.europa.auth.IAuthenticationFacade;
import dev.adamcross.europa.model.ApplicationUser;
import dev.adamcross.europa.repositories.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationFacade {
    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getUsername() {
        return getAuthentication().getName();
    }

    public ApplicationUser getUser() {
        return applicationUserRepository.findByUsername(getUsername());
    }
}
