package pl.com.bottega.dms.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.com.bottega.dms.DmsApplication;
import pl.com.bottega.dms.application.DocumentDto;
import pl.com.bottega.dms.application.DocumentFlowProcess;
import pl.com.bottega.dms.application.DocumentCatalog;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.DocumentStatus;
import pl.com.bottega.dms.model.EmployeeId;
import pl.com.bottega.dms.model.commands.ChangeDocumentCommand;
import pl.com.bottega.dms.model.commands.CreateDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;

import java.util.Collection;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by maciek on 18.02.2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)  //test uruchomi się przy użyciu JUnit runnera
@SpringApplicationConfiguration(DmsApplication.class)
public class DocumentFlowTest {

    @Autowired
    private DocumentFlowProcess documentFlowProcess;

    @Autowired
    private DocumentCatalog documentCatalog;

    @Test
    public void shouldCreateDocument(){
        //when - I create document
        CreateDocumentCommand cmd = new CreateDocumentCommand();
        cmd.setTitle("Test");
        DocumentNumber documentNumber = documentFlowProcess.create(cmd);

        //then - the document is available in catalog
        DocumentDto dto = documentCatalog.get(documentNumber);
        assertThat(dto.getTitle()).isEqualTo("Test");
        assertThat(dto.getNumber()).isEqualTo(documentNumber.getNumber());

    }

    @Test
    public void shouldChangeDocument(){
        //given - I create document
        CreateDocumentCommand cmd = new CreateDocumentCommand();
        cmd.setTitle("Test");
        DocumentNumber documentNumber = documentFlowProcess.create(cmd);

        //when - the document is available in catalog
        DocumentDto dto = documentCatalog.get(documentNumber);
        ChangeDocumentCommand changeDocumentCommand = new ChangeDocumentCommand();
        changeDocumentCommand.setTitle("Test po zmianie");
        changeDocumentCommand.setNumber(dto.getNumber());
        documentFlowProcess.change(changeDocumentCommand);

        //then
        dto = documentCatalog.get(documentNumber);
        assertThat(dto.getTitle()).isEqualTo("Test po zmianie");
    }

    @Test
    public void shouldVerifyDocument(){
        //given - I create document
        CreateDocumentCommand cmd = new CreateDocumentCommand();
        cmd.setTitle("Test");
        DocumentNumber documentNumber = documentFlowProcess.create(cmd);

        //when - the document is available in catalog
        documentFlowProcess.verify(documentNumber);

        //then
        DocumentDto dto = documentCatalog.get(documentNumber);
        assertThat(dto.getStatus()).isEqualTo(DocumentStatus.VERIFIED);
    }

    @Test
    public void shouldPublishDocument(){
        //given - I create document
        CreateDocumentCommand cmd = new CreateDocumentCommand();
        cmd.setTitle("Test");
        DocumentNumber documentNumber = documentFlowProcess.create(cmd);
        documentFlowProcess.verify(documentNumber);

        //when - the document is available in catalog
        DocumentDto dto = documentCatalog.get(documentNumber);
        PublishDocumentCommand publishDocumentCommand = new PublishDocumentCommand();
        publishDocumentCommand.setNumber(dto.getNumber());
        publishDocumentCommand.setRecipients(new LinkedList<>());
        documentFlowProcess.publish(publishDocumentCommand);

        //then
        dto = documentCatalog.get(documentNumber);
        assertThat(dto.getStatus()).isEqualTo(DocumentStatus.PUBLISHED);
    }

    @Test
    public void shouldArchiveDocument(){
        //given - I create document
        CreateDocumentCommand cmd = new CreateDocumentCommand();
        cmd.setTitle("Test");
        DocumentNumber documentNumber = documentFlowProcess.create(cmd);
        documentFlowProcess.verify(documentNumber);
        DocumentDto dto = documentCatalog.get(documentNumber);
        PublishDocumentCommand publishDocumentCommand = new PublishDocumentCommand();
        publishDocumentCommand.setNumber(dto.getNumber());
        publishDocumentCommand.setRecipients(new LinkedList<>());
        documentFlowProcess.publish(publishDocumentCommand);

        //when - the document is available in catalog
        documentFlowProcess.archive(documentNumber);

        //then
        dto = documentCatalog.get(documentNumber);
        assertThat(dto.getStatus()).isEqualTo(DocumentStatus.ARCHIVED);
    }
}
