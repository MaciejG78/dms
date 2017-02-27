package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.Document;

/**
 * Created by maciek on 19.02.2017.
 */
public interface DocumentRepository {

    void put(Document document);

    Document get(DocumentNumber documentNumber);


}
