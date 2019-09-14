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
import ch.assignment.parser.FileParserContext;
import ch.assignment.parser.TextFileParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TestTextFileParser class to test functionality of class TextFileParser
 * @since 1.0
 * @author davide.listello@gmail.com
 */
public class TestTextFileParser {

    @Test
    public void readCryptoCurrencyInputFile() {
        File inputFile = new File("src/test/resources/bobs_crypto.txt");
        FileParserContext ctx = new FileParserContext();
        ctx.setInputParserStrategy(new TextFileParser());
        List<CryptoCurrencyEntry> entries = ctx.parseCryptoCurrencyInputFile(Paths.get(inputFile.getAbsolutePath()));
        for(CryptoCurrencyEntry entry : entries) {
            System.out.println(entry);
        }

        assertAll("validate parsed text input file",
                () -> assertTrue(entries.get(0).getDigitalCurrencySymbol().compareTo("BTC") == 0 &&
                        entries.get(0).getQuantity() == 10.0),
                () -> assertTrue(entries.get(1).getDigitalCurrencySymbol().compareTo("ETH") == 0 &&
                        entries.get(1).getQuantity() == 5.0),
                () -> assertTrue(entries.get(2).getDigitalCurrencySymbol().compareTo("XRP") == 0 &&
                        entries.get(2).getQuantity() == 2000.0),
                () -> assertTrue(entries.get(3).getDigitalCurrencySymbol().compareTo("TRX") == 0 &&
                        entries.get(3).getQuantity() == 10.0),
                () -> assertTrue(entries.get(4).getDigitalCurrencySymbol().compareTo("XMR") == 0 &&
                        entries.get(4).getQuantity() == 500.0),
                () -> assertTrue(entries.get(5).getDigitalCurrencySymbol().compareTo("DASH") == 0 &&
                        entries.get(5).getQuantity() == 450.0),
                () -> assertTrue(entries.get(6).getDigitalCurrencySymbol().compareTo("NEO") == 0 &&
                        entries.get(6).getQuantity() == 2300.25)
        );
    }
}
