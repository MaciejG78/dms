package pl.com.bottega.dms.application;

import org.jboss.logging.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.com.bottega.dms.model.events.DocumentPublishEvent;

/**
 * Created by maciek on 12.03.2017.
 */

@Component
public class PrintDocumentScheduler {

    @EventListener
    public void documentPublished(DocumentPublishEvent event){
        Logger.getLogger(PrintDocumentScheduler.class).info("Scheduling document printing!");

    }
}
