package pl.com.bottega.dms.model.commands;

import java.time.LocalDateTime;

/**
 * Created by macie on 12.02.2017.
 */
public class CreateDocumentCommand {
    private String title;
    private LocalDateTime createDate;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}