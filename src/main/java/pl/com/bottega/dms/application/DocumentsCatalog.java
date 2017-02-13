package pl.com.bottega.dms.application;

import pl.com.bottega.dms.model.DocumentNumber;

/**
 * Created by macie on 12.02.2017.
 */
public interface DocumentsCatalog {

    DocumentSearchResults find(DocumentQuery documentQuery);

    DocumentDto get(DocumentNumber documentNumber);


}
