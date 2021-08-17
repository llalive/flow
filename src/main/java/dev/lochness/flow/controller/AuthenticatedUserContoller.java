package dev.lochness.flow.controller;

import dev.lochness.flow.domain.User;
import dev.lochness.flow.security.CustomUserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AuthenticatedUserContoller {

    protected final User getCurrentUser() {
        CustomUserPrincipal principal = (CustomUserPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getUser();
    }
}
