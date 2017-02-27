package pl.com.bottega.dms.model.printing;

import pl.com.bottega.dms.model.Document;

import java.math.BigDecimal;

/**
 * Created by macie on 12.02.2017.
 */
public class RGBPrintCostCalculator implements PrintCostCalculator{


    public BigDecimal calculateCost(Document document) {
        return new BigDecimal(50);
    }
}
