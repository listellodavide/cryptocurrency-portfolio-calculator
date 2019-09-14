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
    public static void main(String[] args) throws InterruptedException {
        Path filepath;
        if (args.length > 0) {
            try {
                filepath = Paths.get(args[0]);
                FileParserContext ctx = new FileParserContext();
                ctx.setInputParserStrategy(new TextFileParser());
                List<CryptoCurrencyEntry> entries = ctx.parseCryptoCurrencyInputFile(filepath);
                System.out.println(entries);

            } catch (InputMismatchException e) {
                System.err.println("Argument" + args[0] + " must be a valid file path. eg: C:\\bobs_crypto.txt");
                System.exit(1);
            }
        }
        else {
            System.out.println("usage: [FILE] \n eg: C:\\bobs_crypto.txt");
        }
    }


}
