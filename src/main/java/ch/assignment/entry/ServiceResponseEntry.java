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
 * @since 1.0
 * @author davide.listello@gmail.com
 */
public class ServiceResponseEntry {

    private String digitalCurrencySymbol;

    private String fiatCurrencySymbol;

    private Double exchangeValue;

    private String errorMessage;

    public ServiceResponseEntry(String digitalCurrencySymbol, String fiatCurrencySymbol, Double exchangeValue, String errorMessage) {
        this.digitalCurrencySymbol = digitalCurrencySymbol;
        this.fiatCurrencySymbol = fiatCurrencySymbol;
        this.exchangeValue = exchangeValue;
        this.errorMessage = errorMessage;
    }

    public String getDigitalCurrencySymbol() {
        return digitalCurrencySymbol;
    }

    public void setDigitalCurrencySymbol(String digitalCurrencySymbol) {
        this.digitalCurrencySymbol = digitalCurrencySymbol;
    }

    public String getFiatCurrencySymbol() {
        return fiatCurrencySymbol;
    }

    public void setFiatCurrencySymbol(String fiatCurrencySymbol) {
        this.fiatCurrencySymbol = fiatCurrencySymbol;
    }

    public Double getExchangeValue() {
        return exchangeValue;
    }

    public void setExchangeValue(Double exchangeValue) {
        this.exchangeValue = exchangeValue;
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
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(digitalCurrencySymbol, fiatCurrencySymbol, exchangeValue, errorMessage);
    }
}
