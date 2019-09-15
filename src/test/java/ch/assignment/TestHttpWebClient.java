/**
 * Copyright (c) 2019-present, Davide Listello.
 *
 * Licensed under the MIT License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package ch.assignment;

import ch.assignment.entry.CryptoCurrencyEntry;
import ch.assignment.entry.ServiceResponseEntry;
import ch.assignment.http.HttpWebClientContext;
import ch.assignment.http.HttpWebClientManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TestHttpWebClient class to test functionality of class HttpWebClient
 * @since 1.0
 * @author davide.listello@gmail.com
 */
public class TestHttpWebClient {

    private static HttpWebClientContext ctx;
    private static double epsDelta = 0.33d; // delta eps precision I expect a +/-33% value change over time because of
    // high  volatility in the currency market to allow me to pass the test even when a change happen on crypto exchanges

    @BeforeAll
    private static void testSetup() {
        ctx = new HttpWebClientContext();
        ctx.setHttpWebClientStrategy(new HttpWebClientManager());
    }

    @Test
    void getResponseMarketExchangeValueCryptoCurrency() throws InterruptedException, MalformedURLException {
        List<CryptoCurrencyEntry> inputs = Arrays.asList(
                new CryptoCurrencyEntry("BTC", 10d),
                new CryptoCurrencyEntry("ETH",5.24d),
                new CryptoCurrencyEntry("XRP", 2000.24d),
                new CryptoCurrencyEntry("TRX", 1925.24d),
                new CryptoCurrencyEntry("XMR",106580.856689d));
        List<ServiceResponseEntry> serviceResponseEntryArrayList = ctx
                .requestRestInterfaceActualValueCurrencySymbols(inputs, "EUR");

        assertEquals(serviceResponseEntryArrayList.size(), 5);
        assertAll("Crypto-Currency Exchange response digital currency value in 33% range validation",
                () -> assertEquals(serviceResponseEntryArrayList.get(0).getExchangeValue(), 9380.18d, (9380.18d*epsDelta)),
                () -> assertEquals(serviceResponseEntryArrayList.get(1).getExchangeValue(), 169.46d, (169.46d*epsDelta)),
                () -> assertEquals(serviceResponseEntryArrayList.get(2).getExchangeValue(), 0.2383d, (0.2383d*epsDelta)),
                () -> assertEquals(serviceResponseEntryArrayList.get(3).getExchangeValue(), 0.01428d, (0.01428d*epsDelta)),
                () -> assertEquals(serviceResponseEntryArrayList.get(4).getExchangeValue(), 68.12d, (68.12d*epsDelta))
        );
    }

    @Test
    void getResponseMarketExchangeFakeCryptoCurrency() throws InterruptedException, MalformedURLException {
        List<CryptoCurrencyEntry> inputs = Arrays.asList(
                new CryptoCurrencyEntry("ABBA", 10d),
                new CryptoCurrencyEntry("XXX",5.24d),
                new CryptoCurrencyEntry("ZIP", 2000.24d),
                new CryptoCurrencyEntry("APPL", 1925.24d),
                new CryptoCurrencyEntry("DELL",106580.856689d));
        List<ServiceResponseEntry> serviceResponseEntryList = ctx
                .requestRestInterfaceActualValueCurrencySymbols(inputs, "EUR");

        assertEquals(serviceResponseEntryList.size(), 1);
        assertAll("Crypto-Currency Exchange response digital currency value in 33% range validation",
                () -> assertEquals(0.0003149d,
                        serviceResponseEntryList.get(0).getExchangeValue(),
                        (0.0003149d*epsDelta))
        );
    }

    @Test
    void getResponseMarketExchangeRealAndFakeCryptoCurrency() throws InterruptedException, MalformedURLException {
        List<CryptoCurrencyEntry> inputs = Arrays.asList(
                new CryptoCurrencyEntry("ABBA", 10d),
                new CryptoCurrencyEntry("BTC",5.24d),
                new CryptoCurrencyEntry("XXX", 2000.24d),
                new CryptoCurrencyEntry("ETH", 1925.24d),
                new CryptoCurrencyEntry("ZIP", 10d),
                new CryptoCurrencyEntry("XRP",5.24d),
                new CryptoCurrencyEntry("APPL", 2000.24d),
                new CryptoCurrencyEntry("TRX", 1925.24d),
                new CryptoCurrencyEntry("DELL",106580.856689d),
                new CryptoCurrencyEntry("XMR",106580.856689d));
        List<ServiceResponseEntry> serviceResponseEntryList = ctx
                        .requestRestInterfaceActualValueCurrencySymbols(inputs, "EUR");

        assertEquals(6, serviceResponseEntryList.size());
        assertAll("Crypto-Currency Exchange response digital currency value in 33% range validation",
                () -> assertEquals(9287.18d,
                        serviceResponseEntryList.get(0).getExchangeValue(),
                        (9287.18d*epsDelta)),
                () -> assertEquals(170.46d,
                        serviceResponseEntryList.get(1).getExchangeValue(),
                        (170.46d*epsDelta)),
                () -> assertEquals(0.0003149d,
                        serviceResponseEntryList.get(2).getExchangeValue(),
                        (0.0003149d*epsDelta)),
                () -> assertEquals(0.2373d,
                        serviceResponseEntryList.get(3).getExchangeValue(),
                        (0.2373d*epsDelta)),
                () -> assertEquals(0.01428d,
                        serviceResponseEntryList.get(4).getExchangeValue(),
                        (0.01428d*epsDelta)),
                () -> assertEquals(67.76d,
                        serviceResponseEntryList.get(5).getExchangeValue(),
                        (67.76d*epsDelta))
        );
    }

}
