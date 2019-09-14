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
package ch.assignment.parser;

import ch.assignment.entry.CryptoCurrencyEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TextFileParser class follow the Strategy Pattern and implement the FileParserStrategy interface for the API
 * where this implementation deal with parsing text file into list of CryptoCurrencyEntry
 * file have structure like SYMBOL=3000.10
 * @since 1.0
 * @author davide.listello@gmail.com
 */
public class TextFileParser implements FileParserStrategy {

    @Override
    public List<CryptoCurrencyEntry> parseCryptoCurrencyInputFile(Path filename) {
        List<CryptoCurrencyEntry> cryptoCurrency  = new ArrayList<>();
        try (Stream<String> stream = Files.lines(filename)) {
            cryptoCurrency = stream
                    .filter(line -> !line.isEmpty())
                    .map(s -> s.split("=", 2))
                    .map(input -> new CryptoCurrencyEntry(input[0], Double.parseDouble(input[1])))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cryptoCurrency;
    }
}