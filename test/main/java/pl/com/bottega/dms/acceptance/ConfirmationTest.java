package pl.com.bottega.dms.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.com.bottega.dms.DmsApplication;
import pl.com.bottega.dms.application.*;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.EmployeeId;
import pl.com.bottega.dms.model.commands.ConfirmDocumentCommand;
import pl.com.bottega.dms.model.commands.ConfirmForDocumentCommand;
import pl.com.bottega.dms.model.commands.CreateDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;

import java.util.Collection;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by maciek on 27.02.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //test uruchomi się przy użyciu JUnit runnera
@SpringApplicationConfiguration(DmsApplication.class)
public class ConfirmationTest {

    @Autowired
    private ReadingConfirmator readingConfirmator;

    @Autowired
    private DocumentCatalog documentCatalog;

    @Autowired
    private DocumentFlowProcess documentFlowProcess;

    @Test
    public void shouldConfirmDocument() {
        //given - I create, validate and publish document
        DocumentNumber documentNumber = given().publishedDocument();
        EmployeeId employeeId = new EmployeeId(1L);

        //when - the document is available in catalog
        ConfirmDocumentCommand confirmCmd = new ConfirmDocumentCommand();
        confirmCmd.setEmployeeId(employeeId);
        confirmCmd.setNumber(documentNumber.getNumber());
        readingConfirmator.confirm(confirmCmd);

        //then
        DocumentDto dto = documentCatalog.get(documentNumber);
        assertThat(dto.getConfirmation(employeeId).getOwnerId()).isEqualTo(employeeId);
    }

    @Test
    public void shouldConfirmForDocument() {
        //given - I create, validate and publish document
        DocumentNumber documentNumber = given().publishedDocument();
        EmployeeId employeeId1 = new EmployeeId(1L);
        EmployeeId employeeId2 = new EmployeeId(2L);

        //when - the document is available in catalog
        ConfirmForDocumentCommand confirmCmd = new ConfirmForDocumentCommand();
        confirmCmd.setEmployeeId(employeeId1);
        confirmCmd.setConfirmingEmployeeId(employeeId2);
        confirmCmd.setNumber(documentNumber.getNumber());
        readingConfirmator.confirmFor(confirmCmd);

        //then
        DocumentDto dto = documentCatalog.get(documentNumber);
        assertThat(dto.getConfirmation(employeeId1).getOwnerId()).isEqualTo(employeeId1);
        assertThat(dto.getConfirmation(employeeId1).getProxyId()).isEqualTo(employeeId2);
    }

    public DocumentAssembler given() {
        return new DocumentAssembler();
    }

    class DocumentAssembler {

        public DocumentNumber publishedDocument() {
            CreateDocumentCommand createCmd = new CreateDocumentCommand();
            createCmd.setTitle("Testowy");
            DocumentNumber documentNumber = documentFlowProcess.create(createCmd);
            PublishDocumentCommand publishCmd = new PublishDocumentCommand();
            Collection<EmployeeId> recipents = new LinkedList<>();
            recipents.add(new EmployeeId(1L));

            documentFlowProcess.verify(documentNumber);
            publishCmd.setNumber(documentNumber.getNumber());
            publishCmd.setRecipients(recipents);
            documentFlowProcess.publish(publishCmd);
            return documentNumber;
        }

    }

}