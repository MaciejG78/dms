package pl.com.bottega.dms.infrastructure.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import pl.com.bottega.dms.application.user.AuthRequiredException;
import pl.com.bottega.dms.application.user.CurrentUser;
import pl.com.bottega.dms.application.user.RequiresAuth;

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

    @Before("@within(pl.com.bottega.dms.application.user.RequiresAuth) || @annotation(pl.com.bottega.dms.application.user.RequiresAuth)")
    public void ensureAuth(RequiresAuth roles){
        if(currentUser.getEmployeeId() == null);
            throw new AuthRequiredException("You are not logged");
        outerLoop:
        if (roles.roles().length != 0){
            for (String role : roles.roles()){
                if (currentUser.getRoles().contains(role))
                    break outerLoop;
            }
            throw new AuthRequiredException("You have not enought privilages");
        }
    }
}
