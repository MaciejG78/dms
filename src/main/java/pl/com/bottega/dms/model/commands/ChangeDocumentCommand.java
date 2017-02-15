package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.EmployeeId;

/**
 * Created by macie on 12.02.2017.
 */
public class ChangeDocumentCommand {
    private String title;
    private String content;
    private EmployeeId id;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setId(EmployeeId id) {
        this.id = id;
    }

    public EmployeeId getId() {
        return id;
    }
}