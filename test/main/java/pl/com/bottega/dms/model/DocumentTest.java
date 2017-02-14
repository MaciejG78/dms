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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.com.bottega.dms.model.DocumentStatus.DRAFT;
import static pl.com.bottega.dms.model.DocumentStatus.VERIFIED;

/**
 * Created by macie on 12.02.2017.
 */
public class DocumentTest {

    private CreateDocumentCommand cmd;
    private NumberGenerator numberGenerator;
    private Document document;

    @Before
    public void setUp() {
        cmd = new CreateDocumentCommand();
        cmd.setTitle("test title");
        cmd.setCreateDate(LocalDateTime.now());
        numberGenerator = new StubNumberGenerator();
        document = new Document(cmd, numberGenerator);
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
        ChangeDocumentCommand changeDocumentCommand = new ChangeDocumentCommand();
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
        document.setStatus(VERIFIED);
        //then
        assertEquals(VERIFIED, document.getStatus());
    }

    @Test(expected = DocumentStatusException.class)
    public void shouldNotAllowVeryfingAlreadyVerifiedDocument() {
        // given - document is already verified
        document.verify(1L);
        // when - second verification attempt
        document.verify(2L);
        // then - nie ma tutaj żadych assercji bo testujemy czy kodzik produkcyjny wyrzuci wyjątek klasy DocumentStatusException
    }

    @Test
    public void shouldBeDraftAfterEdit() {
        ChangeDocumentCommand changeDocumentCommand = new ChangeDocumentCommand();
        changeDocumentCommand.setTitle("changed title");

        document.setStatus(VERIFIED);
        document.change(changeDocumentCommand);

        assertEquals(DocumentStatus.DRAFT, document.getStatus());
    }

    @Test
    public void shouldBePublishedAfterPublish() {
        PublishDocumentCommand publishDocumentCommand = new PublishDocumentCommand();
        StubPrintCostCalculator calculatePrintCost = new StubPrintCostCalculator();

        document.publish(publishDocumentCommand, calculatePrintCost);

        assertEquals(DocumentStatus.PUBLISHED, document.getStatus());
    }

    @Test(expected = DocumentStatusException.class)
    public void shouldNotAllowEditInStatusDifferentThanDraftOrVerified() {
        ChangeDocumentCommand changeDocumentCommand = new ChangeDocumentCommand();
        changeDocumentCommand.setTitle("changed title");
        PublishDocumentCommand publishDocumentCommand = new PublishDocumentCommand();
        StubPrintCostCalculator calculatePrintCost = new StubPrintCostCalculator();

        document.publish(publishDocumentCommand, calculatePrintCost);
        assertEquals(DocumentStatus.PUBLISHED, document.getStatus());
        document.change(changeDocumentCommand);
    }

    @Test
    public void shouldRememberCreateDate() {
        assertSameTime(LocalDateTime.now(), document.getCreateDate());
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