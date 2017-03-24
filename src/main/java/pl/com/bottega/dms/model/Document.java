package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.commands.*;
import pl.com.bottega.dms.model.numbers.NumberGenerator;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static pl.com.bottega.dms.model.DocumentStatus.*;

@Entity
public class Document {

    private static final int CHARS_COUNT_PER_PAGE = 1800;

    @EmbeddedId
    private DocumentNumber number;

    @Transient
    DocumentState documentState;

    @Enumerated(EnumType.STRING)
    DocumentStatus status;
    String title;
    String content;
    LocalDateTime createdAt;
    LocalDateTime verifiedAt;
    LocalDateTime publishedAt;
    LocalDateTime changedAt;
    LocalDateTime expiresAt;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "creatorId"))
    private EmployeeId creatorId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "verifierId"))
    EmployeeId verifierId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "editorId"))
    EmployeeId editorId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "publisherId"))
    EmployeeId publisherId;
    BigDecimal printCost;
    @Enumerated(EnumType.STRING)
    DocumentType documentType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "documentNumber")
    private Set<Confirmation> confirmations;

    Document() {
    }


    public Document(CreateDocumentCommand cmd, DocumentNumber number) {
        this.number = number;
        this.status = DRAFT;
        this.documentState = new DraftState(this);
        this.title = cmd.getTitle();
        this.createdAt = LocalDateTime.now();
        this.creatorId = cmd.getEmployeeId();
        this.confirmations = new HashSet<>();
        this.documentType = cmd.getDocumentType();
    }


    public void change(ChangeDocumentCommand cmd) {
        documentState.change(cmd);
    }

    public void verify(EmployeeId employeeId) {
        documentState.verify(employeeId);
    }

    public void archive(EmployeeId employeeId) {
        documentState.archive(employeeId);
    }

    public void publish(PublishDocumentCommand cmd, PrintCostCalculator printCostCalculator) {
        documentState.publish(cmd, printCostCalculator);
        createConfirmations(cmd);
    }

    private void createConfirmations(PublishDocumentCommand cmd) {
        for (EmployeeId employeeId : cmd.getRecipients()) {
            confirmations.add(new Confirmation(employeeId));
        }
    }

    public void confirm(ConfirmDocumentCommand cmd) {
        documentState.confirm(cmd);
    }

    public void confirmFor(ConfirmForDocumentCommand cmd) {
        documentState.confirmFor(cmd);
    }

    @PostLoad
    public void checkState(){
        if (status == DRAFT)
            documentState = new DraftState(this);
        if (status == VERIFIED)
            documentState = new VerifiedState(this);
        if (status == PUBLISHED)
            documentState = new PublishedState(this);
        if (status == ARCHIVED)
            documentState = new ArchivedState(this);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public EmployeeId getCreatorId() {
        return creatorId;
    }

    public EmployeeId getVerifierId() {
        return verifierId;
    }

    public EmployeeId getEditorId() {
        return editorId;
    }

    public EmployeeId getPublisherId() {
        return publisherId;
    }

    public BigDecimal getPrintCost() {
        return printCost;
    }

    public void setPrintCost(BigDecimal printCost) {
        this.printCost = printCost;
    }

    public boolean isConfirmedBy(EmployeeId employeeId) {
        return getConfirmation(employeeId).isConfirmed();
    }

    public Set<Confirmation> getConfirmations() {
        return Collections.unmodifiableSet(confirmations);
    }

    public Confirmation getConfirmation(EmployeeId employeeId) {
        for (Confirmation confirmation : confirmations) {
            if (confirmation.isOwnedBy(employeeId))
                return confirmation;
        }
        throw new DocumentStatusException(String.format("No confirmation for %s", employeeId));
    }

    public DocumentType getType() {
        return documentType;
    }

    public int getPagesCount() {
        if (content == null)
            return 0;
        return content.length() / CHARS_COUNT_PER_PAGE +
                (content.length() % CHARS_COUNT_PER_PAGE == 0 ? 0 : 1);
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void export(DocumentBuilder builder) {
        builder.buildTitle(title);
        builder.buildContent(content);
        builder.buildNumber(number);
        builder.buildCreatedAt(createdAt);
        builder.buildStatus(status);
        builder.buildType(documentType);
        for (Confirmation confirmation : confirmations)
            builder.buildConfirmation(confirmation.getOwner(), confirmation.getProxy(), confirmation.getConfirmationDate());


    }
}
