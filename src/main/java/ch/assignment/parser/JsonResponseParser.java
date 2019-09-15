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

import ch.assignment.entry.ServiceResponseEntry;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JsonResponseParser class follow the Strategy Pattern where JsonResponseParser is a concrete implementation of the
 * API interface definition, specific for parsing JSON Strings into ServiceResponseEntry objects
 * @since 1.0
 * @author davide.listello@gmail.com
 */
public class JsonResponseParser implements ServiceParserStrategy {
    // match " or ' none " then positive lookahead : whitespace match true¦false¦digit¦a-zA-Z@()symbols
    // at least one or more lookahead , one or more groups true¦false¦digit¦a-zA-Z@()symbols "
    private final Pattern errorMessage = Pattern.compile("(?:\\\"|\\')([^\"]*)(?:\\\"|\\')(?=:)(?:\\:\\s*)(?:\\\")?" +
            "(true|false|[-0-9]+[\\.]*[\\d]*(?=,)|[0-9a-zA-Z\\(\\)\\@\\:\\,\\/\\!\\+\\-\\.\\$\\ \\\\\\']*)(?:\\\")?");

    // lookbehind "EUR": then match digit.digit
    // where . is optional and digit is at least 2 digit long
    private final Pattern euroExchange = Pattern.compile("(?<=\"EUR\":)\\d+.?\\d+");

    public JsonResponseParser() {
    }

    public ServiceResponseEntry parseServiceResponse(String input) throws ParseException {
        // empty object with placeholder values
        ServiceResponseEntry parsedEntry = new ServiceResponseEntry("XXX", "EUR", 0.001d, "");
        StringBuilder errorMessages = new StringBuilder();
        // check if input is a valid JSON object contained inside a { .. } before proceed to check the regexp match
        if(input.charAt(0) == '{' && input.charAt(input.length()-1) == '}' && input.length() > 2) {
            Matcher euroExchangeMatcher = euroExchange.matcher(input);
            boolean euroExchangeFound = euroExchangeMatcher.find();
            Matcher errorMessageMatcher = errorMessage.matcher(input);
            boolean errorMessageFound = errorMessageMatcher.find();
            if (euroExchangeFound)
            {
                parsedEntry.setFiatCurrencySymbol("EUR");
                parsedEntry.setExchangeValue(Double.parseDouble(euroExchangeMatcher.group(0)));
            }
            else if(errorMessageFound) {
                while (errorMessageMatcher.find()) {
                    errorMessages.append(errorMessageMatcher.group());
                }
            }
        }
        else if(input.compareTo("{}") == 0) {
            throw new ParseException("Malformed input string, found an empty {} JSON object!", 0);
        } else throw new ParseException("Malformed input string, given "+input+" is not valid JSON object!", 0);

        if(errorMessages.length() > 0)
            parsedEntry.setErrorMessage(errorMessages.toString());
        return parsedEntry;
    }
    
}
