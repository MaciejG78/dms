package pl.com.bottega.dms.model.exceptions;

/**
 * Created by maciek on 14.03.2017.
 */
public class DocumentNotFoundException extends RuntimeException{
    public DocumentNotFoundException(String msg) {
            super(msg);
        }
}
