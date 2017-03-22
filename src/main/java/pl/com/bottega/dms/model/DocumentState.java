package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.commands.ChangeDocumentCommand;
import pl.com.bottega.dms.model.commands.ConfirmDocumentCommand;
import pl.com.bottega.dms.model.commands.ConfirmForDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

/**
 * Created by maciek on 19.03.2017.
 */
public interface DocumentState {

    public void change(ChangeDocumentCommand cmd);

    public void verify(EmployeeId employeeId);

    public void archive(EmployeeId employeeId);

    public void publish(PublishDocumentCommand cmd, PrintCostCalculator printCostCalculator);

    public void confirm(ConfirmDocumentCommand cmd);

    public void confirmFor(ConfirmForDocumentCommand cmd);

}
