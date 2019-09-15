package ch.assignment;

import ch.assignment.entry.CryptoCurrencyEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestCryptoCurrencyEntry {

    @Test
    public void negativeQuantityExpectedException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CryptoCurrencyEntry crypto = new CryptoCurrencyEntry("BTC", -20d);
        });
    }

    @Test
    public void longCurrencySymbolExpectedException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CryptoCurrencyEntry crypto = new CryptoCurrencyEntry("BTCCC", 10d);
        });
    }

    @Test
    public void symbolShouldBeUpperCase() {
        CryptoCurrencyEntry crypto = new CryptoCurrencyEntry("btc", 300d);
        assertNotNull(crypto.getDigitalCurrencySymbol());
        Assertions.assertTrue(crypto.getDigitalCurrencySymbol().compareTo("BTC") == 0);
    }
}
