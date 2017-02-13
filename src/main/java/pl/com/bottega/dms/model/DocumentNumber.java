package pl.com.bottega.dms.model;

/**
 * Created by macie on 12.02.2017.
 */
public class DocumentNumber {
    private String number;

    public DocumentNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DocumentNumber)) return false;

        DocumentNumber that = (DocumentNumber) o;

        return number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
