package pl.com.bottega.dms.model.exceptions;

import pl.com.bottega.dms.model.DocumentNumber;

/**
 * Created by maciek on 14.03.2017.
 */
public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(DocumentNumber nr) {
        super(String.format("Document with number %s does not exist", nr.getNumber()));
    }
}