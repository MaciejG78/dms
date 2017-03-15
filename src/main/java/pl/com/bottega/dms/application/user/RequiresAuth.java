package pl.com.bottega.dms.application.user;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by maciek on 12.03.2017.
 */
public @interface RequiresAuth {

    String[] roles() default {};

}
