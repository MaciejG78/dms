package pl.com.bottega.dms.application.user.impl;

import pl.com.bottega.dms.application.user.CurrentUser;
import pl.com.bottega.dms.model.EmployeeId;

import java.util.HashSet;
import java.util.Set;

public class StandardCurrentUser implements CurrentUser {

    private EmployeeId employeeId;

    @Override
    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    @Override
    public Set<String> getRoles() {
        Set<String> roles = new HashSet<>();
        if (getEmployeeId().getId() == 1) {
            roles.add("STAFF");
            roles.add("QUALITY_STAFF");
            roles.add("QUALITY_MANAGER");
        } else
            if (getEmployeeId().getId() == 2){
            roles.add("STAFF");
            roles.add("QUALITY_STAFF");
        }
        else {
                roles.add("STAFF");
            }
        return roles;
    }
}
