package pl.com.bottega.dms.application;

import pl.com.bottega.dms.model.EmployeeId;

import java.time.LocalDateTime;

/**
 * Created by maciek on 27.02.2017.
 */
public class ConfirmationDto {

    private LocalDateTime confirmationDate;
    private EmployeeId ownerId;
    private EmployeeId proxyId;

   public LocalDateTime getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(LocalDateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public EmployeeId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(EmployeeId ownerId) {
        this.ownerId = ownerId;
    }

    public EmployeeId getProxyId() {
        return proxyId;
    }

    public void setProxyId(EmployeeId proxyId) {
        this.proxyId = proxyId;
    }
}
