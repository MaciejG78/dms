package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.commands.*;
import pl.com.bottega.dms.model.exceptions.DocumentStatusException;
import pl.com.bottega.dms.model.numbers.NumberGenerator;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

import java.time.LocalDateTime;

import static pl.com.bottega.dms.model.DocumentStatus.*;

/**
 * Created by macie on 12.02.2017.
 */
public class Document {

    private DocumentNumber number;
    private DocumentStatus status;
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime verificationDate;
    private LocalDateTime publicationDate;
    private LocalDateTime changingDate;
    private EmployeeId createdEmployeeId;
    private EmployeeId verifierEmployeeId;
    private EmployeeId changerEmployeeId;
    private EmployeeId publisherEmployeeId;

    public Document(CreateDocumentCommand cmd, NumberGenerator numberGenerator) {
        this.number = numberGenerator.generate();
        this.status = DRAFT;
        this.title = cmd.getTitle();
        this.createDate = LocalDateTime.now();
        this.createdEmployeeId = cmd.getId();
    }

    public void change(ChangeDocumentCommand cmd) {
        if (getStatus().equals(DRAFT) || getStatus().equals(VERIFIED)) {
            this.title = cmd.getTitle();
            this.content = cmd.getContent();
            this.changingDate = LocalDateTime.now();
            this.changerEmployeeId = cmd.getId();
            this.status = DRAFT;
        } else
            throw new DocumentStatusException("Invalid document status");
    }

    public void verify(EmployeeId id) throws DocumentStatusException {
        if (getStatus().equals(DRAFT)) {
            setStatus(VERIFIED);
            this.verifierEmployeeId = id;
            this.verificationDate = LocalDateTime.now();
        } else
            throw new DocumentStatusException("Invalid document status");
    }

    public void archive() {
        if (!getStatus().equals(ARCHIVED)) {
            setStatus(ARCHIVED);
        }
    }

    public void publish(PublishDocumentCommand cmd, PrintCostCalculator printCostCalculator) {
        if (getStatus().equals(VERIFIED)) {
            this.publicationDate = LocalDateTime.now();
            this.publisherEmployeeId = cmd.getId();
            setStatus(PUBLISHED);
        } else
            throw new DocumentStatusException("Invalid document status");
    }

    public void confirm(ConfirmDocumentCommand cmd) {

    }

    public void confirmFor(ConfirmForDocumentCommand cmd) {

    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public DocumentNumber getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setVerificationDate(LocalDateTime verificationDate) {
        this.verificationDate = verificationDate;
    }

    public LocalDateTime getVerificationDate() {
        return verificationDate;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public LocalDateTime getChangingDate() {
        return changingDate;
    }

    public EmployeeId getCreatorEmploeeId() {
        return createdEmployeeId;
    }

    public EmployeeId getVerifierEmployeeId() {
        return verifierEmployeeId;
    }

    public EmployeeId getChangerEmployeeId() {
        return changerEmployeeId;
    }

    public EmployeeId getPublisherEmployeeId() {
        return publisherEmployeeId;
    }
}