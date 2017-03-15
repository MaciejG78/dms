package pl.com.bottega.dms.infrastructure;

import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.DocumentRepository;
import pl.com.bottega.dms.model.exceptions.DocumentNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JPADocumentRepository implements DocumentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void put(Document document) {
        entityManager.persist(document);
    }

    @Override
    public Document get(DocumentNumber nr) {
        if (entityManager.find(Document.class, nr) != null) {
            return entityManager.find(Document.class, nr);
        }
        else {
                throw new DocumentNotFoundException(String.format("Document %s didn't exist", nr.getNumber()));
        }
    }
}
