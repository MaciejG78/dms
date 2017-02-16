package pl.com.bottega.dms.model;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.dms.model.commands.ChangeDocumentCommand;
import pl.com.bottega.dms.model.commands.CreateDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;
import pl.com.bottega.dms.model.exceptions.DocumentStatusException;
import pl.com.bottega.dms.model.numbers.NumberGenerator;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.com.bottega.dms.model.DocumentStatus.VERIFIED;

/**
 * Created by maciek on 12.02.2017.
 */
public class DocumentTest {

    private CreateDocumentCommand cmd;
    private NumberGenerator numberGenerator;
    private Document document;
    private StubPrintCostCalculator calculatePrintCost;
    private ChangeDocumentCommand changeDocumentCommand;
    private PublishDocumentCommand publishDocumentCommand;
    //private EmployeeId id;

    @Before
    public void setUp() {
        cmd = new CreateDocumentCommand();
        cmd.setTitle("test title");
        cmd.id = new EmployeeId(1L);
        numberGenerator = new StubNumberGenerator();
        document = new Document(cmd, numberGenerator);
        calculatePrintCost = new StubPrintCostCalculator();
        changeDocumentCommand = new ChangeDocumentCommand();
        publishDocumentCommand = new PublishDocumentCommand();
    }

    @Test
    public void shouldBeDraftAfterCreate() {
        assertEquals(DocumentStatus.DRAFT, document.getStatus());
    }

    @Test
    public void shouldGenerateNumberOnCreate() {
        assertEquals(new DocumentNumber("1"), document.getNumber());
    }

    @Test
    public void shouldSetTitleOnCreate() {
        assertEquals("test title", document.getTitle());
    }

    @Test
    public void shouldChangeTitleAndContent() {
        changeDocumentCommand.setTitle("changed title");
        changeDocumentCommand.setContent("changed content");

        document.change(changeDocumentCommand);

        assertEquals("changed title", document.getTitle());
        assertEquals("changed content", document.getContent());
    }

    @Test
    public void shouldChangeStatusAtVerifiedAfterVerify() {
        //given

        //when
        EmployeeId someEmployeeId = new EmployeeId(1L);
        document.verify(someEmployeeId);
        //then
        assertEquals(VERIFIED, document.getStatus());
    }

    @Test(expected = DocumentStatusException.class)
    public void shouldNotAllowVeryfingAlreadyVerifiedDocument() {
        // given - document is already verified
        EmployeeId someEmployeeId = new EmployeeId(2L);
        document.verify(someEmployeeId);
        // when - second verification attempt
        EmployeeId otherEmployeeId = new EmployeeId(3L);
        document.verify(otherEmployeeId);
        // then - nie ma tutaj żadych assercji bo testujemy czy kodzik produkcyjny wyrzuci wyjątek klasy DocumentStatusException
    }

    @Test
    public void shouldBeDraftAfterEdit() {
        EmployeeId someEmployeeId = new EmployeeId(2L);
        document.verify(someEmployeeId);

        changeDocumentCommand.setTitle("changed title");
        document.change(changeDocumentCommand);

        assertEquals(DocumentStatus.DRAFT, document.getStatus());
    }

    @Test
    public void shouldBePublishedAfterPublish() {
        EmployeeId someEmployeeId = new EmployeeId(2L);
        document.verify(someEmployeeId);

        StubPrintCostCalculator calculatePrintCost = new StubPrintCostCalculator();
        document.publish(publishDocumentCommand, calculatePrintCost);

        assertEquals(DocumentStatus.PUBLISHED, document.getStatus());
    }

    @Test(expected = DocumentStatusException.class)
    public void shouldNotAllowEditInStatusDifferentThanDraftOrVerified() {
        StubPrintCostCalculator calculatePrintCost = new StubPrintCostCalculator();
        document.publish(publishDocumentCommand, calculatePrintCost);

        changeDocumentCommand.setTitle("changed title");
        document.change(changeDocumentCommand);
    }

    @Test
    public void shouldRememberCreateDate() {
        assertSameTime(LocalDateTime.now(), document.getCreateDate());
    }

    @Test
    public void shouldRememberLastVerificationDate() {

        EmployeeId someEmployeeId = new EmployeeId(2L);
        document.verify(someEmployeeId);

        assertSameTime(LocalDateTime.now(), document.getVerificationDate());
    }

    @Test
    public void shouldRememberPublicationDate() {
        EmployeeId someEmployeeId = new EmployeeId(2L);
        document.verify(someEmployeeId);

        document.publish(publishDocumentCommand, calculatePrintCost);

        assertSameTime(LocalDateTime.now(), document.getPublicationDate());
    }

    @Test
    public void shouldRememberLastChangeDate() {

        changeDocumentCommand.setTitle("changed title");
        document.change(changeDocumentCommand);

        assertSameTime(LocalDateTime.now(), document.getChangingDate());
    }

    @Test
    public void shouldRememberCreatorEmployeeId() {

        EmployeeId creatorEmployeeId = new EmployeeId(1L);
        assertEquals(creatorEmployeeId, document.getCreatorEmploeeId());
    }

    @Test
    public void shouldRememberVerifierEmployeeId() {

        EmployeeId verifierEmployeeId = new EmployeeId(2L);
        document.verify(verifierEmployeeId);

        assertEquals(verifierEmployeeId, document.getVerifierEmployeeId());
    }

    @Test
    public void shouldRememberChangerEmployeeId() {

        EmployeeId changerEmployeeId = new EmployeeId(3L);
        changeDocumentCommand.setId(changerEmployeeId);
        changeDocumentCommand.setContent("changed content");
        document.change(changeDocumentCommand);

        assertEquals(changerEmployeeId, document.getChangerEmployeeId());
    }

    @Test
    public void shouldRememberPublisherEmployeeId() {
        EmployeeId verifierEmployeeId = new EmployeeId(4L);
        document.verify(verifierEmployeeId);

        EmployeeId publisherEmployeeId = new EmployeeId(5L);
        publishDocumentCommand.setId(publisherEmployeeId);
        document.publish(publishDocumentCommand, calculatePrintCost);

        assertEquals(publisherEmployeeId, document.getPublisherEmployeeId());
    }

    @Test
    public void shouldBeArchivedInAnyStatus() {

        document.archive();

        assertEquals(DocumentStatus.ARCHIVED, document.getStatus());
    }

    @Test(expected = DocumentStatusException.class)
    public void shouldNotAllowChangeInStatusArchived() {

        document.archive();

        changeDocumentCommand.setTitle("changed title");
        document.change(changeDocumentCommand);
    }

    @Test(expected = DocumentStatusException.class)
    public void shouldNotAllowVerifyInStatusArchived() {

        document.archive();

        EmployeeId someEmployeeId = new EmployeeId(4L);
        document.verify(someEmployeeId);
    }

    @Test(expected = DocumentStatusException.class)
    public void shouldNotAllowPublishInStatusArchived() {

        document.archive();

        EmployeeId publisherEmployeeId = new EmployeeId(4L);
        publishDocumentCommand.setId(publisherEmployeeId);
        document.publish(publishDocumentCommand, calculatePrintCost);
    }

    @Test(expected = DocumentStatusException.class)
    public void shouldNotAllowPublishInStatusDraft() {

        EmployeeId publisherEmployeeId = new EmployeeId(4L);
        publishDocumentCommand.setId(publisherEmployeeId);

        document.publish(publishDocumentCommand, calculatePrintCost);
    }


    @Test
    public void shouldBePublishedInStatusVerified() {
        EmployeeId verifierEmployeeId = new EmployeeId(4L);
        document.verify(verifierEmployeeId);

        EmployeeId publisherEmployeeId = new EmployeeId(5L);
        publishDocumentCommand.setId(publisherEmployeeId);
        document.publish(publishDocumentCommand, calculatePrintCost);

        assertEquals(DocumentStatus.PUBLISHED, document.getStatus());
    }

    class StubNumberGenerator implements NumberGenerator {
        public DocumentNumber generate() {
            return new DocumentNumber("1");
        }
    }

    class StubPrintCostCalculator implements PrintCostCalculator {
        public BigDecimal calculateCost(Document document) {
            BigDecimal bdec = new BigDecimal("0.12");
            return bdec;
        }
    }

    private static final Long DATE_EPS = 500L;

    private void assertSameTime(LocalDateTime expected, LocalDateTime actual) {
        assertTrue(ChronoUnit.MILLIS.between(expected, actual) < DATE_EPS);
    }

}