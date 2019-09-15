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
import ch.assignment.parser.JsonResponseParser;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TestJsonResponseParser class to test functionality of class JsonResponseParser
 * @since 1.0
 * @author davide.listello@gmail.com
 */
public class TestJsonResponseParser {

    private static String jsonErrorMsgString = "{\"Response\":\"Error\",\"Message\":\"There is no data for the symbol" +
            " ABBA .\",\"HasWarning\":false,\"Type\":2,\"RateLimit\":{},\"Data\":{},\"ParamWithError\":\"fsym\"}";

    private static String jsonExchangeString = "{\"EUR\":9316.4}";

    @Test
    void validateRegExpEuroExchange() throws ParseException {
        boolean finded = Pattern.compile("(?<=\"EUR\":)\\d+.?\\d+")
                // RegExp with a lookbehind "EUR": then match digit.digit
                // where . is optional and digit is at least 2 digit long
                .matcher(jsonExchangeString)
                .find();
        assertTrue(finded);
    }

    @Test
    void validateRegExpErrorMessage() throws ParseException {
        boolean finded = Pattern.compile("(?:\\\"|\\')([^\"]*)(?:\\\"|\\')(?=:)(?:\\:\\s*)(?:\\\")?(true|false|[-0-9]+[\\.]*[\\d]*(?=,)|[0-9a-zA-Z\\(\\)\\@\\:\\,\\/\\!\\+\\-\\.\\$\\ \\\\\\']*)(?:\\\")?")
                // RegExp that match " or ' none " then positive lookahead : whitespace match true¦false¦digit¦a-zA-Z@()
                // symbols at least one or more lookahead , one or more groups true¦false¦digit¦a-zA-Z@()symbols "
                .matcher(jsonErrorMsgString)
                .find();
        assertTrue(finded);
    }


    @Test
    void validateParserValidInput() throws ParseException {
        JsonResponseParser jsonParser = new JsonResponseParser();
        ServiceResponseEntry parsedEntry = jsonParser.parseServiceResponse(jsonExchangeString);
        System.out.println(parsedEntry);
        assertTrue(parsedEntry.getExchangeValue() == 9316.4d);
    }

    @Test
    void validateParserErrorMessage() throws ParseException {
        JsonResponseParser jsonParser = new JsonResponseParser();
        ServiceResponseEntry parsedEntry = jsonParser.parseServiceResponse(jsonErrorMsgString);
        System.out.println(parsedEntry);
        assertTrue(parsedEntry.getErrorMessage().length() > 0);
    }


}
