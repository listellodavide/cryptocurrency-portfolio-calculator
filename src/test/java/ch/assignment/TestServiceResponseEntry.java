package ch.assignment;

import ch.assignment.entry.ServiceResponseEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestServiceResponseEntry {

    @Test
    public void negativeExchangeValueExpectedException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ServiceResponseEntry responseEntry = new ServiceResponseEntry("BTC", "EUR", +0.0d, "");
        });
    }

    @Test
    public void longCurrencySymbolExpectedException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ServiceResponseEntry responseEntry = new ServiceResponseEntry("BTCCCC", "EUR", +20.0d, "");
        });
    }

    @Test
    public void negativeQuantityExpectedException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ServiceResponseEntry responseEntry = new ServiceResponseEntry("BTC", "EUR", +20.0d, "");
            responseEntry.setQuantity(-20.0d);
        });
    }

    @Test
    public void symbolShouldBeUpperCase() {
        ServiceResponseEntry responseEntry = new ServiceResponseEntry("btc", "EUR", +20.0d, "");
        assertNotNull(responseEntry.getDigitalCurrencySymbol());
        Assertions.assertTrue(responseEntry.getDigitalCurrencySymbol().compareTo("BTC") == 0);
    }

    @Test
    public void fiatShouldBeUpperCase() {
        ServiceResponseEntry responseEntry = new ServiceResponseEntry("BTC", "eur", +20.0d, "");
        assertNotNull(responseEntry.getFiatCurrencySymbol());
        Assertions.assertTrue(responseEntry.getFiatCurrencySymbol().compareTo("EUR") == 0);
    }
}
