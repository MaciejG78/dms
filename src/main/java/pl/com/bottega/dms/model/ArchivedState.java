package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.commands.ChangeDocumentCommand;
import pl.com.bottega.dms.model.commands.ConfirmDocumentCommand;
import pl.com.bottega.dms.model.commands.ConfirmForDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

import static pl.com.bottega.dms.model.DocumentStatus.ARCHIVED;

/**
 * Created by maciek on 19.03.2017.
 */
public class ArchivedState implements DocumentState {
    private Document document;

    public ArchivedState(Document document) {
        this.document = document;
    }

    @Override
    public void change(ChangeDocumentCommand cmd) {
        throw new DocumentStatusException("Document should be DRAFT or VERIFIED to CHANGE");
    }

    @Override
    public void verify(EmployeeId employeeId) {
        throw new DocumentStatusException("Document should be DRAFT to VERIFY");
    }

    @Override
    public void archive(EmployeeId employeeId) {
        throw new DocumentStatusException("Document should be DRAFT, VERIFIED or PUBLISHED to ARCHIVE");
    }

    @Override
    public void publish(PublishDocumentCommand cmd, PrintCostCalculator printCostCalculator) {
        throw new DocumentStatusException("Document should be VERIFIED to PUBLISH");
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
