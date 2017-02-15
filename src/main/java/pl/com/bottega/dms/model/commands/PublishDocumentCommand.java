package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.EmployeeId;

/**
 * Created by macie on 12.02.2017.
 */
public class PublishDocumentCommand {


    private EmployeeId id;

    public void setId(EmployeeId id) {
        this.id = id;
    }

    public EmployeeId getId() {
        return id;
    }
}
