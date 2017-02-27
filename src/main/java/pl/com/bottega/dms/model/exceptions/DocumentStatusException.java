package pl.com.bottega.dms.model.exceptions;

/**
 * Created by macie on 13.02.2017.
 */
public class DocumentStatusException extends RuntimeException {
    public DocumentStatusException(String msg) {
        super(msg);
    }
}