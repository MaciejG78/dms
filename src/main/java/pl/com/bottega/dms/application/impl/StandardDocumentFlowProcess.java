package pl.com.bottega.dms.application.impl;

import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.application.DocumentFlowProcess;
import pl.com.bottega.dms.model.DocumentRepository;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.EmployeeId;
import pl.com.bottega.dms.model.commands.ChangeDocumentCommand;
import pl.com.bottega.dms.model.commands.CreateDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;
import pl.com.bottega.dms.model.numbers.NumberGenerator;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;


/**
 * Created by maciek on 18.02.2017.
 */

//@Service - tylko w przypadku 1 i 2 sposobu
public class StandardDocumentFlowProcess implements DocumentFlowProcess {

    private NumberGenerator numberGenerator;
    private PrintCostCalculator printCostCalculator;
    private DocumentRepository documentRepository;
    private EmployeeId employeeId;

    public StandardDocumentFlowProcess(NumberGenerator numberGenerator, PrintCostCalculator printCostCalculator,
                                       DocumentRepository documentRepository) {
        this.numberGenerator = numberGenerator;
        this.printCostCalculator = printCostCalculator;
        this.documentRepository = documentRepository;
    }


    @Override
    @Transactional
    public DocumentNumber create(CreateDocumentCommand cmd) {
        Document document = new Document(cmd, numberGenerator);
        documentRepository.put(document);
        return document.getNumber();
    }


    @Override
    @Transactional
    public void change(ChangeDocumentCommand cmd) {
        DocumentNumber documentNumber = new DocumentNumber(cmd.getNumber());
        Document document = documentRepository.get(documentNumber);
        document.change(cmd);
    }

    @Override
    @Transactional
    public void verify(DocumentNumber documentNumber) {
        Document document = documentRepository.get(documentNumber);
        document.verify(employeeId);
    }

    @Override
    @Transactional
    public void publish(PublishDocumentCommand cmd) {
        DocumentNumber documentNumber = new DocumentNumber(cmd.getNumber());
        Document document = documentRepository.get(documentNumber);
        document.publish(cmd, printCostCalculator);
    }

    @Override
    @Transactional
    public void archive(DocumentNumber documentNumber) {
        Document document = documentRepository.get(documentNumber);
        document.archive(employeeId);

    }
}