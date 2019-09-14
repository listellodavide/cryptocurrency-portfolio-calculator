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

import ch.assignment.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * HttpWebClient class is used in Asynchronous callbacks to execute a GET HTTP request to the REST Service interface
 * Take into account HTTP 200 OK with Json containing positive and negative (error messages) from REST backend
 * In case of HTTP >299 class will parse the error stream anyway. It also possible to return the Headers of the response
 * @since 1.0
 * @author davide.listello@gmail.com
 */
public class HttpWebClient implements Callable<String> {

    String uri;

    public HttpWebClient(String uri) {
        this.uri = uri;
    }

    @Override
    public String call() throws Exception {
        return getRequestResponse();
    }

    public String getRequestResponse(){
        URL url;
        String response = "";

        try {
            url = new URL(this.uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            response = HttpWebClient.getRawResponse(con, false); // false = no headers are appended to response

        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public static String getRawResponse(HttpURLConnection con, Boolean includeHeaders) throws IOException {
        StringBuilder responseBuilder = new StringBuilder();

        if(includeHeaders)
        {
            responseBuilder.append(con.getResponseCode())
                    .append(" ")
                    .append(con.getResponseMessage())
                    .append("\n");

            con.getHeaderFields()
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getKey() != null)
                    .forEach(entry -> {
                        responseBuilder.append(entry.getKey())
                                .append(": ");

                        List<String> headerValues = entry.getValue();
                        Stream<String>  stream = Utility.getStreamFromIterator(headerValues.iterator());

                        stream.forEach(s -> responseBuilder.append(", ")
                                .append(s));

                        responseBuilder.append("\n");
                    });

        }
        Reader streamReader;

        if (con.getResponseCode() > 299) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();

        responseBuilder.append(content);

        return responseBuilder.toString();
    }
}
