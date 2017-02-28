package pl.com.bottega.dms.application;

import pl.com.bottega.dms.model.Confirmation;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.EmployeeId;

/**
 * Created by macie on 12.02.2017.
 */
public interface DocumentCatalog {

    DocumentSearchResults find(DocumentQuery documentQuery);

    DocumentDto get(DocumentNumber documentNumber);

}
