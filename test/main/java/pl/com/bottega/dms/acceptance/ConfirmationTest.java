package pl.com.bottega.dms.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.com.bottega.dms.DmsApplication;
import pl.com.bottega.dms.application.DocumentCatalog;
import pl.com.bottega.dms.application.DocumentDto;
import pl.com.bottega.dms.application.DocumentFlowProcess;
import pl.com.bottega.dms.application.ReadingConfirmator;
import pl.com.bottega.dms.model.Confirmation;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.DocumentStatus;
import pl.com.bottega.dms.model.EmployeeId;
import pl.com.bottega.dms.model.commands.ConfirmDocumentCommand;
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
        CreateDocumentCommand cmd = new CreateDocumentCommand();
        cmd.setTitle("Test");
        DocumentNumber documentNumber = documentFlowProcess.create(cmd);
        documentFlowProcess.verify(documentNumber);
        DocumentDto dto = documentCatalog.get(documentNumber);
        PublishDocumentCommand publishDocumentCommand = new PublishDocumentCommand();
        publishDocumentCommand.setNumber(dto.getNumber());
        Collection<EmployeeId> recipents = new LinkedList<>();
        recipents.add(new EmployeeId(1L));
        recipents.add(new EmployeeId(2L));
        publishDocumentCommand.setRecipients(recipents);
        documentFlowProcess.publish(publishDocumentCommand);
        dto = documentCatalog.get(documentNumber);

        //when - the document is available in catalog
        ConfirmDocumentCommand confirmDocumentCommand = new ConfirmDocumentCommand();
        confirmDocumentCommand.setEmployeeId(new EmployeeId(2L));
        confirmDocumentCommand.setNumber(dto.getNumber());
        readingConfirmator.confirm(confirmDocumentCommand);

        //then
        dto = documentCatalog.get(documentNumber);
        assertThat(dto.getConfirmations().contains("2"));

    }
    
}
