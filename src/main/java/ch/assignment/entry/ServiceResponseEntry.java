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
package ch.assignment.entry;

import java.util.Objects;

/**
 * ServiceResponseEntry class represent json data parsed from the REST service response
 * This data class hold information gathered from parsing the file and stored inside the CryptoCurrencyEntry and
 * by querying the REST API webservice interface with an Http Client.
 * This information were kept together to be able to compute the portfolio value of single currency based on the amount
 * of coins invested and the overall total value of all digital coins at current EURO market value.
 * @since 1.0
 * @author davide.listello@gmail.com
 */
public class ServiceResponseEntry {

    private String digitalCurrencySymbol;

    private String fiatCurrencySymbol;

    private Double exchangeValue;

    private Double quantity;

    private String errorMessage;

    public ServiceResponseEntry(String digitalCurrencySymbol, String fiatCurrencySymbol, Double exchangeValue, String errorMessage) throws IllegalArgumentException{
        setDigitalCurrencySymbol(digitalCurrencySymbol);
        setFiatCurrencySymbol(fiatCurrencySymbol);
        setExchangeValue(exchangeValue);
        this.errorMessage = errorMessage;
    }

    public String getDigitalCurrencySymbol() {
        return digitalCurrencySymbol;
    }

    public void setDigitalCurrencySymbol(String digitalCurrencySymbol) {
        if(digitalCurrencySymbol.length() >= 3 && digitalCurrencySymbol.length() <= 4)
            this.digitalCurrencySymbol = digitalCurrencySymbol.toUpperCase();
        else throw new IllegalArgumentException("digital currency symbol should be between 3 and 4 characters only! " +
                "eg: BTC");
    }

    public String getFiatCurrencySymbol() {
        return fiatCurrencySymbol;
    }

    public void setFiatCurrencySymbol(String fiatCurrencySymbol) throws IllegalArgumentException {
        if(fiatCurrencySymbol.length() == 3)
            this.fiatCurrencySymbol = fiatCurrencySymbol.toUpperCase();
        else throw new IllegalArgumentException("fiat currency symbol should be 3 characters only! eg: EUR");
    }

    public Double getExchangeValue() {
        return exchangeValue;
    }

    public void setExchangeValue(Double exchangeValue) {
        if(exchangeValue > 0.0d)
            this.exchangeValue = exchangeValue;
        else throw new IllegalArgumentException("The exchange value of a digital currency should never be 0.0 or " +
                "negative. Something wrong happened, please retry again.");
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        if(quantity > 0.0d)
            this.quantity = quantity;
        else throw new IllegalArgumentException("quantity of coins should be bigger than 0.0 !");
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ServiceResponseEntry{ ");
        sb.append("digitalCurrencySymbol=\"" + digitalCurrencySymbol + "\",");
        sb.append("fiatCurrencySymbol=\"" + fiatCurrencySymbol + "\",");
        sb.append("exchangeValue=\"" + exchangeValue + "\"");
        sb.append("quantity=\"" + quantity + "\"");
        if(!errorMessage.isEmpty()) {
            sb.append(",errorMessage=\"" + errorMessage + '\"');
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceResponseEntry)) return false;
        ServiceResponseEntry that = (ServiceResponseEntry) o;
        return Objects.equals(digitalCurrencySymbol, that.digitalCurrencySymbol) &&
                Objects.equals(fiatCurrencySymbol, that.fiatCurrencySymbol) &&
                Objects.equals(exchangeValue, that.exchangeValue) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(digitalCurrencySymbol, fiatCurrencySymbol, exchangeValue, quantity, errorMessage);
    }
}
