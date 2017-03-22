package pl.com.bottega.dms.model.printing;

import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentType;

import java.math.BigDecimal;

/**
 * Created by maciek on 19.03.2017.
 */
public class ManualPrintCost implements PrintCostCalculator{

    BigDecimal priceToMultiply = new BigDecimal(30);
    PrintCostCalculator printCostCalculator;

    public ManualPrintCost(PrintCostCalculator printCostCalculator) {
        this.printCostCalculator = printCostCalculator;
    }

    @Override
    public BigDecimal calculateCost(Document document) {
        if (document.getType().equals(DocumentType.MANUAL))
            return document.getPrintCost().multiply(priceToMultiply).add(document.getPrintCost());
        return document.getPrintCost();
    }
}
