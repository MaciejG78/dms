package pl.com.bottega.dms.model;

import javax.persistence.Embeddable;

/**
 * Created by macie on 13.02.2017.
 */
@Embeddable
public class EmployeeId {

    private Long id;

    EmployeeId(){}


    public EmployeeId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeId that = (EmployeeId) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}