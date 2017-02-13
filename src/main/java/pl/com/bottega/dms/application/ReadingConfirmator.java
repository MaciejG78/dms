package pl.com.bottega.dms.application;

import pl.com.bottega.dms.model.commands.ConfirmDocumentCommand;
import pl.com.bottega.dms.model.commands.ConfirmForDocumentCommand;

/**
 * Created by macie on 12.02.2017.
 */
public interface ReadingConfirmator {

    void confirm(ConfirmDocumentCommand cmd);

    void confirmFor(ConfirmForDocumentCommand cmd);


}
