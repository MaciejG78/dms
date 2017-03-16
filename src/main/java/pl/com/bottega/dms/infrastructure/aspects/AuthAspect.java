package pl.com.bottega.dms.infrastructure.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.com.bottega.dms.application.user.AuthRequiredException;
import pl.com.bottega.dms.application.user.CurrentUser;
import pl.com.bottega.dms.application.user.RequiresAuth;
import pl.com.bottega.dms.application.user.PermissionsRequiredException;

/**
 * Created by maciek on 12.03.2017.
 */
@Component
@Aspect
public class AuthAspect {
    private CurrentUser currentUser;

    public AuthAspect(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }


    //@Before("@within(pl.com.bottega.dms.application.user.RequiresAuth) || @annotation(pl.com.bottega.dms.application.user.RequiresAuth)")
    @Before("execution(* *.*(..)) && @annotation(requiresAuth)")
    public void ensureAuth(RequiresAuth requiresAuth){

        if(currentUser.getEmployeeId() == null)
            throw new AuthRequiredException("You are not logged");

        if (!checkPermissions(requiresAuth))
            throw new PermissionsRequiredException("You haven't got required permissions");
    }

    public boolean checkPermissions(RequiresAuth requiresAuth) {
        for (String role : requiresAuth.roles()) {
            if (currentUser.getRoles().contains(role))
                return true;
        }
        return false;
    }
}