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
import ch.assignment.parser.FileParserContext;
import ch.assignment.parser.TextFileParser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.List;

/**
 * App class that parse input arguments and call Digital Currency Portfolio Calculator
 * @since 1.0
 * @author davide.listello@gmail.com
 */
public class App {
    private double portfolioTotalValueInEuro = 0.0d;

    public static void main(String[] args) throws InterruptedException {
        App app = new App();
        Path inputFullpath;
        File currentDir = new File("./");
        System.out.println("Current directory location on disk: " + currentDir.getAbsolutePath());
        if (args.length > 0) {
            try {
                inputFullpath = currentDir.toPath().resolve(Paths.get(args[0]));
                System.out.println("Given input file digital currency portfolio location: " + inputFullpath.toString());
                System.out.println("Calculating portfolio value by processing input and calling REST API..Please wait..");
                app.calculatePortfolioValue(inputFullpath);
            } catch ( IOException e) {
                    e.printStackTrace();
                    System.err.println("Unable to open given file path: "+ currentDir.toString() + args[0] +
                            ". Please check if the given relative path to input file exist.");
                    System.exit(1);
            } catch (InputMismatchException e) {
                e.printStackTrace();
                System.err.println("Argument"+ currentDir.toString() + args[0] +" must be a valid relative file path." +
                        " eg: src\\test\\resources\\bobs_crypto.txt");
                System.exit(1);
            }

        }
        else {
            System.out.println("usage: [FILE] \n eg: src\\test\\resources\\bobs_crypto.txt");
        }
    }

    private void calculatePortfolioValue(Path filepath) throws InterruptedException, MalformedURLException {
        FileParserContext fileParserContext = new FileParserContext();
        fileParserContext.setInputParserStrategy(new TextFileParser());
        List<CryptoCurrencyEntry> entries = fileParserContext.parseCryptoCurrencyInputFile(filepath);
        HttpWebClientContext httpWebClientContext = new HttpWebClientContext();
        httpWebClientContext.setHttpWebClientStrategy(new HttpWebClientManager());
        List<ServiceResponseEntry> serviceResponseEntryArrayList = httpWebClientContext
                .requestRestInterfaceActualValueCurrencySymbols(entries, "EUR");

        serviceResponseEntryArrayList.forEach(entry -> {
            System.out.println(String.format("The actual value of 1 %s in exchange is %.2f %s. In the portfolio " +
                            "there are %.2f %s so total value is %.2f %s",
                    entry.getDigitalCurrencySymbol(),
                    entry.getExchangeValue(),
                    entry.getFiatCurrencySymbol(),
                    entry.getQuantity(),
                    entry.getDigitalCurrencySymbol(),
                    (entry.getExchangeValue()*entry.getQuantity()),
                    entry.getFiatCurrencySymbol()));
            this.portfolioTotalValueInEuro += (entry.getExchangeValue()*entry.getQuantity());
        });

        System.out.println(String.format("The overall portfolio total value is %.2f EUR", this
                .portfolioTotalValueInEuro));

    }

}
