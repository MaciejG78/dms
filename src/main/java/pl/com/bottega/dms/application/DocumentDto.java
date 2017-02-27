package pl.com.bottega.dms.application;

import pl.com.bottega.dms.model.Confirmation;
import pl.com.bottega.dms.model.DocumentStatus;

import java.util.LinkedList;

/**
 * Created by macie on 12.02.2017.
 */
public class DocumentDto {
    private String number;
    private String title;
    private DocumentStatus status;
    private LinkedList<Confirmation> confirmations;

    public String getTitle() {
        return title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public LinkedList<Confirmation> getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(LinkedList<Confirmation> confirmations) {
        this.confirmations = confirmations;
    }
}
