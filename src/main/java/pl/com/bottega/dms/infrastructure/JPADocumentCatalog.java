package pl.com.bottega.dms.infrastructure;

import pl.com.bottega.dms.application.*;
import pl.com.bottega.dms.model.Confirmation;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.EmployeeId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
    public DocumentDto get(DocumentNumber documentNumber) {
        Document document = entityManager.find(Document.class, documentNumber);
        DocumentDto documentDto = new DocumentDto();
        documentDto.setNumber(documentNumber.getNumber());
        documentDto.setTitle(document.getTitle());
        documentDto.setStatus(document.getStatus());
        documentDto.setConfirmations(setConfirmationsToDto(document.getConfirmations()));
        return documentDto;
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
