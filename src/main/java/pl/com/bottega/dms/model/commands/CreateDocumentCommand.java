package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.EmployeeId;

import java.time.LocalDateTime;

/**
 * Created by macie on 12.02.2017.
 */
public class CreateDocumentCommand {
    private String title;
    public EmployeeId id;

    public void setId(EmployeeId id) {
        this.id = id;
    }

    public EmployeeId getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}