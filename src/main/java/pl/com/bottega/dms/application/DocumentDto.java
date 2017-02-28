package pl.com.bottega.dms.application;

import pl.com.bottega.dms.model.DocumentStatus;
import pl.com.bottega.dms.model.EmployeeId;
import pl.com.bottega.dms.model.exceptions.DocumentStatusException;

import java.util.Set;

/**
 * Created by macie on 12.02.2017.
 */
public class DocumentDto {
    private String number;
    private String title;
    private DocumentStatus status;
    private Set<ConfirmationDto> confirmations;

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

    public Set<ConfirmationDto> getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Set<ConfirmationDto> confirmations) {
        this.confirmations = confirmations;
    }

    public ConfirmationDto getConfirmation(EmployeeId employeeId) {
        for (ConfirmationDto confirmationDto : confirmations) {
            if (confirmationDto.getOwnerId().equals(employeeId))
                return confirmationDto;
        }
        throw new DocumentStatusException(String.format("This document not contain confirmation for %d", employeeId));
    }

}
