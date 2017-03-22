package pl.com.bottega.dms.model.printing;

import pl.com.bottega.dms.model.Document;

import java.math.BigDecimal;

/**
 * Created by maciek on 19.03.2017.
 */
public class PagesPrintCost implements PrintCostCalculator{

    public static final BigDecimal HIGH_PAGES_LIMIT = new BigDecimal(100);
    public static final BigDecimal PRICE_TO_ADD = new BigDecimal(0.40);

    PrintCostCalculator decorator;

    public PagesPrintCost(PrintCostCalculator decorator) {
        this.decorator = decorator;
    }

    @Override
    public BigDecimal calculateCost(Document document) {
        BigDecimal cost = new BigDecimal(document.getPagesCount());
        if (cost.equals(HIGH_PAGES_LIMIT))
            return cost.add(PRICE_TO_ADD);
        return cost;
    }
}
