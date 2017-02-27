package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.exceptions.DocumentStatusException;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by maciek on 18.02.2017.
 */
@Entity
public class Confirmation {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "ownerId"))
    private EmployeeId owner;

    private LocalDateTime confirmationDate;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "proxyId"))
    private EmployeeId proxy; //Ten który potwierdza za ownera

    Confirmation(){}

    public Confirmation(EmployeeId employeeId){
        this.owner = employeeId;
    }

    public boolean isOwnedBy(EmployeeId employeeId){
        return employeeId.equals(owner);
    }

    public boolean isProxedBy(EmployeeId employeeId){
        return employeeId.equals(proxy);
    }

    public boolean isConfirmed(){
        return confirmationDate != null;
    }

    public void confirm() {
        if (!isConfirmed())
            confirmationDate = LocalDateTime.now();
        else
            throw new DocumentStatusException(String.format("Document was previously accepted"));
    }

    public void confirmFor(EmployeeId proxy) {
        confirm();
        this.proxy = proxy;
    }
    public LocalDateTime getConfirmationDate(){
        return confirmationDate;
    }

}
