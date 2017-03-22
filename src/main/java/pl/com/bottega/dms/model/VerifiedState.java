package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.commands.ChangeDocumentCommand;
import pl.com.bottega.dms.model.commands.ConfirmDocumentCommand;
import pl.com.bottega.dms.model.commands.ConfirmForDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

import java.time.LocalDateTime;

import static pl.com.bottega.dms.model.DocumentStatus.ARCHIVED;
import static pl.com.bottega.dms.model.DocumentStatus.DRAFT;
import static pl.com.bottega.dms.model.DocumentStatus.PUBLISHED;

/**
 * Created by maciek on 19.03.2017.
 */
public class VerifiedState implements DocumentState {

    private Document document;

    public VerifiedState(Document document) {
        this.document = document;
    }

    @Override
    public void change(ChangeDocumentCommand cmd) {
        document.title = cmd.getTitle();
        document.content = cmd.getContent();
        document.status = DRAFT;
        document.changedAt = LocalDateTime.now();
        document.editorId = cmd.getEmployeeId();
        document.expiresAt = cmd.getExpiresAt();
        document.documentState = new DraftState(document);
    }

    @Override
    public void verify(EmployeeId employeeId) {
        throw new DocumentStatusException("Document should be DRAFT to VERIFY");
    }

    @Override
    public void archive(EmployeeId employeeId) {
        document.status = ARCHIVED;
        document.documentState = new ArchivedState(document);
    }

    @Override
    public void publish(PublishDocumentCommand cmd, PrintCostCalculator printCostCalculator) {
        document.status = PUBLISHED;
        document.publishedAt = LocalDateTime.now();
        document.publisherId = cmd.getEmployeeId();
        document.printCost = printCostCalculator.calculateCost(document);
        document.documentState = new PublishedState(document);
    }

    @Override
    public void confirm(ConfirmDocumentCommand cmd) {
        throw new DocumentStatusException("Document should be PUBLISH to CONFIRM");
    }

    @Override
    public void confirmFor(ConfirmForDocumentCommand cmd) {
        throw new DocumentStatusException("Document should be PUBLISH to CONFIRM");
    }
}
