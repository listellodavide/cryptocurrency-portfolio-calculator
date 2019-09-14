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

import ch.assignment.entry.ServiceResponseEntry;
import ch.assignment.http.HttpWebClientContext;
import ch.assignment.http.HttpWebClientManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TestHttpWebClient class to test functionality of class HttpWebClient
 * @since 1.0
 * @author davide.listello@gmail.com
 */
public class TestHttpWebClient {

    private static HttpWebClientContext ctx;

    @BeforeAll
    private static void testSetup() {
        ctx = new HttpWebClientContext();
        ctx.setHttpWebClientStrategy(new HttpWebClientManager());
    }

    @Test
    void getResponseMarketExchangeValueCryptoCurrency() throws InterruptedException {
        String[] inputs = { "BTC", "ETH", "XRP", "TRX", "XMR" };
        ArrayList<ServiceResponseEntry> serviceResponseEntryArrayList = ctx
                .requestRestInterfaceActualValueCurrencySymbols(inputs, "EUR");

        double epsDelta = 0.33d; // delta eps precision I expect a +/-33% value change over time because of high
        // volatility in the currency market to allow me to pass the test even when a change happen on crypto exchanges

        assertAll("Crypto-Currency Exchange response digital currency value in 33% range validation",
                () -> assertEquals(serviceResponseEntryArrayList.get(0).getExchangeValue(), 9380.18d, (9380.18d*epsDelta)),
                () -> assertEquals(serviceResponseEntryArrayList.get(1).getExchangeValue(), 169.46d, (169.46d*epsDelta)),
                () -> assertEquals(serviceResponseEntryArrayList.get(2).getExchangeValue(), 0.2383d, (0.2383d*epsDelta)),
                () -> assertEquals(serviceResponseEntryArrayList.get(3).getExchangeValue(), 0.01428d, (0.01428d*epsDelta)),
                () -> assertEquals(serviceResponseEntryArrayList.get(4).getExchangeValue(), 68.12d, (68.12d*epsDelta))
        );
    }

    @Test
    void getResponseMarketExchangeFakeCryptoCurrency() throws InterruptedException {
        String[] inputs = { "ABBA", "BTCCCC", "SLURP", "APPL", "DELL" };
        ArrayList<ServiceResponseEntry> serviceResponseEntryArrayList = ctx
                .requestRestInterfaceActualValueCurrencySymbols(inputs, "EUR");

        assertAll("Crypto-Currency Exchange response error message validation",
                () -> assertTrue(serviceResponseEntryArrayList.get(0).getErrorMessage().length() > 0),
                () -> assertTrue(serviceResponseEntryArrayList.get(1).getErrorMessage().length() > 0),
                () -> assertTrue(serviceResponseEntryArrayList.get(2).getErrorMessage().length() > 0),
                () -> assertTrue(serviceResponseEntryArrayList.get(3).getErrorMessage().length() > 0),
                () -> assertTrue(serviceResponseEntryArrayList.get(4).getErrorMessage().length() > 0)
        );
    }

    @Test
    void getResponseMarketExchangeRealAndFakeCryptoCurrency() throws InterruptedException {
        String[] inputs = { "ABBA", "BTC", "BTCCCC", "ETH", "SLURP", "XRP", "APPL", "TRX", "DELL", "XMR" };
        ArrayList<ServiceResponseEntry> serviceResponseEntryArrayList = ctx
                        .requestRestInterfaceActualValueCurrencySymbols(inputs, "EUR");

        double epsDelta = 0.33d; // delta eps precision I expect a +/-33% value change over time because of high
        // volatility in the currency market to allow me to pass the test even when a change happen on crypto exchanges

        List<ServiceResponseEntry> validCurrencyServiceResponses = serviceResponseEntryArrayList.stream()
                .filter(entry -> entry.getErrorMessage().length() == 0)
                .collect(Collectors.toList());

        assertAll("Crypto-Currency Exchange response digital currency value in 33% range validation",
                () -> assertEquals(validCurrencyServiceResponses.get(0).getExchangeValue(), 9380.18d, (9380.18d*epsDelta)),
                () -> assertEquals(validCurrencyServiceResponses.get(1).getExchangeValue(), 169.46d, (169.46d*epsDelta)),
                () -> assertEquals(validCurrencyServiceResponses.get(2).getExchangeValue(), 0.2383d, (0.2383d*epsDelta)),
                () -> assertEquals(validCurrencyServiceResponses.get(3).getExchangeValue(), 0.01428d, (0.01428d*epsDelta)),
                () -> assertEquals(validCurrencyServiceResponses.get(4).getExchangeValue(), 68.12d, (68.12d*epsDelta))
        );
    }

}
