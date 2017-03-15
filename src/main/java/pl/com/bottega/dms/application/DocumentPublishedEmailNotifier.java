package pl.com.bottega.dms.application;

import org.jboss.logging.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import pl.com.bottega.dms.model.events.DocumentPublishEvent;

import javax.transaction.Transactional;

/**
 * Created by maciek on 12.03.2017.
 */

@Component
public class DocumentPublishedEmailNotifier {

    @TransactionalEventListener
    public void documentPublished(DocumentPublishEvent documentPublishEvent){
        Logger.getLogger(PrintDocumentScheduler.class).info("Mailing to recipients");
    }
}
