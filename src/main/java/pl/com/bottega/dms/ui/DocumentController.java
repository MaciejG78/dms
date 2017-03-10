package pl.com.bottega.dms.ui;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.dms.application.*;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.commands.*;

/**
 * Created by maciek on 05.03.2017.
 */
@RestController
@RequestMapping("/documents")
public class DocumentController {

    private DocumentFlowProcess documentFlowProcess;
    private DocumentCatalog documentCatalog;

    public DocumentController(DocumentFlowProcess documentFlowProcess, DocumentCatalog documentCatalog) {
        this.documentFlowProcess = documentFlowProcess;
        this.documentCatalog = documentCatalog;
    }

    @RequestMapping(method = RequestMethod.POST)  //Można użyć też @PostMapping
    public DocumentNumber create(@RequestBody CreateDocumentCommand cmd){
        return documentFlowProcess.create(cmd);
    }

    @PutMapping("/{documentNumber}")
    public void update(@PathVariable String documentNumber, @RequestBody ChangeDocumentCommand cmd){
        cmd.setNumber(documentNumber);
        documentFlowProcess.change(cmd);
    }

    @GetMapping("/{documentNumber}")
    public DocumentDto show(@PathVariable String documentNumber){
        return documentCatalog.get(new DocumentNumber(documentNumber));
    }

    @GetMapping
    public DocumentSearchResults search(DocumentQuery documentQuery){
        return documentCatalog.find(documentQuery);
    }

    @PostMapping("/{documentNumber}/verification")
    public void verify(@PathVariable String documentNumber){
        documentFlowProcess.verify(new DocumentNumber(documentNumber));
    }

    @PostMapping("/{documentNumber}/publication")
    public void publish(@PathVariable String documentNumber, @RequestBody PublishDocumentCommand cmd){
        cmd.setNumber(documentNumber);
        documentFlowProcess.publish(cmd);
    }

    @PostMapping("/{documentNumber}/archivization")
    public void archive(@PathVariable String documentNumber){
        documentFlowProcess.archive(new DocumentNumber(documentNumber));
    }

    @PostMapping("/{documentNumber}/confirmation")
    public void confirm(@PathVariable String documentNumber, @RequestBody ConfirmDocumentCommand cmd){
        cmd.setNumber(documentNumber);
        documentFlowProcess.confirm(cmd);
    }

    @PostMapping("/{documentNumber}/confirmationfor")
    public void confirmFor(@PathVariable String documentNumber, @RequestBody ConfirmForDocumentCommand cmd){
        cmd.setNumber(documentNumber);
        documentFlowProcess.confirmFor(cmd);
    }
}
