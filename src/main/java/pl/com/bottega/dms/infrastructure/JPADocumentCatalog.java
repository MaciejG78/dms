package pl.com.bottega.dms.infrastructure;

import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.application.*;
import pl.com.bottega.dms.model.Confirmation;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.EmployeeId;
import pl.com.bottega.dms.model.exceptions.DocumentStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by maciek on 18.02.2017.
 */
public class JPADocumentCatalog implements DocumentCatalog{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DocumentSearchResults find(DocumentQuery documentQuery) {
        return null;
    }

    @Override
    @Transactional
    public DocumentDto get(DocumentNumber documentNumber) {
        //Document document = entityManager.find(Document.class, documentNumber);
        Query query = entityManager.createQuery("FROM Document d LEFT JOIN FETCH d.number c WHERE d.number = :documentNumber");
        query.setParameter("documentNumber", documentNumber);
        DocumentDto documentDto = new DocumentDto();
        Collection<Document> documents = query.getResultList();
        if (!documents.isEmpty()){
        for (Document document : documents) {
                documentDto.setNumber(documentNumber.getNumber());
                documentDto.setTitle(document.getTitle());
                documentDto.setStatus(document.getStatus());
                documentDto.setConfirmations(setConfirmationsToDto(document.getConfirmations()));
                return documentDto;
            }
        }
        throw new DocumentStatusException(String.format("No document with %d number", documentNumber));
    }

    public Set<ConfirmationDto> setConfirmationsToDto(Set<Confirmation> confirmations) {
        Set<ConfirmationDto> cDto = new HashSet<>();
        for (Confirmation confirmation : confirmations){
            ConfirmationDto confirmationDto = new ConfirmationDto();
            confirmationDto.setOwnerId(confirmation.getOwner());
            EmployeeId proxyId = confirmation.getProxy();
            if (proxyId != null) confirmationDto.setProxyId(confirmation.getProxy());
            confirmationDto.setConfirmationDate(confirmation.getConfirmationDate());
            cDto.add(confirmationDto);
        }
        return cDto;
    }

}
