package pl.com.bottega.dms.application.user;

/**
 * Created by maciek on 16.03.2017.
 */
public class PermissionsRequiredException extends RuntimeException {
    public PermissionsRequiredException(String msg) {
            super(msg);
        }
}
