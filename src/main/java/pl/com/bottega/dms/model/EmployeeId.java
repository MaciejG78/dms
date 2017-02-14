package pl.com.bottega.dms.model;

/**
 * Created by macie on 13.02.2017.
 */
public class EmployeeId {
    Long id;

    EmployeeId(Long id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeId)) return false;

        EmployeeId that = (EmployeeId) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
