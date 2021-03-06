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

import java.nio.file.Path;
import java.util.List;

/**
 * FileParserContext class follow the Strategy Pattern where FileParserContext allow clients to
 * consume the API
 * @since 1.0
 * @author davide.listello@gmail.com
 */
public class FileParserContext {
    private FileParserStrategy parser;

    public void setInputParserStrategy(FileParserStrategy parser) {
        this.parser = parser;
    }

    public List<CryptoCurrencyEntry> parseCryptoCurrencyInputFile(Path filename) {
        return parser.parseCryptoCurrencyInputFile(filename);
    }

}
