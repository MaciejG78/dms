package pl.com.bottega.dms.model.printing;

import pl.com.bottega.dms.model.Document;

import java.math.BigDecimal;

/**
 * Created by macie on 12.02.2017.
 */
public interface PrintCostCalculator {

    BigDecimal calculateCost(Document document);



}
