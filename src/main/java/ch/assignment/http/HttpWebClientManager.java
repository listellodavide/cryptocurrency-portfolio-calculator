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
package ch.assignment.http;

import ch.assignment.entry.CryptoCurrencyEntry;
import ch.assignment.entry.ServiceResponseEntry;
import ch.assignment.parser.JsonResponseParser;
import ch.assignment.parser.ServiceResponseParserContext;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * HttpWebClientManager class follow the Strategy Pattern where HttpWebClientManager is an implementation of the
 * HttpWebClientStrategy interface where a Callable Future<T> is used to manage multiple asynchronous calls to backend
 * REST interface and then collect results and return an ArrayList of ServiceResponseEntry
 * @since 1.0
 * @author davide.listello@gmail.com
 */
public class HttpWebClientManager implements HttpWebClientStrategy {

    @Override
    public ArrayList<ServiceResponseEntry>
    requestRestInterfaceActualValueCurrencySymbols(@NotNull List<CryptoCurrencyEntry> digitalCurrencySymbolsArray,
                                                   String fiatCurrencySymbol) throws InterruptedException {

        int cpuCoresCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(cpuCoresCount);

        Future<String>[] results = new Future[digitalCurrencySymbolsArray.size()];

        ServiceResponseParserContext ctx = new ServiceResponseParserContext();
        ctx.setServiceResponseParserStrategy(new JsonResponseParser());
        ArrayList<ServiceResponseEntry> serviceResponseEntryArrayList = new ArrayList();
        for (int i = 0; i < digitalCurrencySymbolsArray.size(); i++) {
            results[i] = executorService.submit(new HttpWebClient("https://min-api.cryptocompare" +
                    ".com/data/price?fsym="+digitalCurrencySymbolsArray.get(i).getDigitalCurrencySymbol()+"&tsyms="+fiatCurrencySymbol
                    .toUpperCase
                    ()));
        }

        for (int i = 0; i < digitalCurrencySymbolsArray.size(); i++) {
            try {
                String response = results[i].get(30, TimeUnit.SECONDS);
                // System.out.println("HttpClient Response: " + response);
                ServiceResponseEntry entry = ctx.parseServiceResponse(response);
                entry.setDigitalCurrencySymbol(digitalCurrencySymbolsArray.get(i).getDigitalCurrencySymbol());
                entry.setQuantity(digitalCurrencySymbolsArray.get(i).getQuantity());
                serviceResponseEntryArrayList.add(entry);
            } catch (Exception e) {
                // interrupts if there is any possible error
                results[i].cancel(true);
            }
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        return serviceResponseEntryArrayList;
    }
}
