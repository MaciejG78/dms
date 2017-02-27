package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.EmployeeId;

import java.util.Collection;

/**
 * Created by macie on 12.02.2017.
 */
public class PublishDocumentCommand {


    private EmployeeId id;
    private Collection<EmployeeId> recipients;
    private String number;

    public void setEmployeeId(EmployeeId id) {
        this.id = id;
    }

    public EmployeeId getEmployeeId() {
        return id;
    }

    public void setRecipients(Collection<EmployeeId> recipients){
        this.recipients = recipients;
    }

    public Collection<EmployeeId> getRecipients() {
        return recipients;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
