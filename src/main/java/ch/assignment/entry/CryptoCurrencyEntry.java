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
 * CryptoCurrencyEntry class represent parsed data from the input file
 * @since 1.0
 * @author davide.listello@gmail.com
 */
public class CryptoCurrencyEntry {

    private String digitalCurrencySymbol;

    private Double quantity;

    private Double exchangeValue;

    public CryptoCurrencyEntry(String symbol, Double quantity) {
        this.digitalCurrencySymbol = symbol;
        this.quantity = quantity;
    }

    public String getDigitalCurrencySymbol() {
        return digitalCurrencySymbol;
    }

    public void setDigitalCurrencySymbol(String symbol) {
        this.digitalCurrencySymbol = symbol;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CryptoCurrencyEntry{" +
                "digitalCurrencySymbol='" + digitalCurrencySymbol + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CryptoCurrencyEntry)) return false;
        CryptoCurrencyEntry that = (CryptoCurrencyEntry) o;
        return Objects.equals(digitalCurrencySymbol, that.digitalCurrencySymbol) &&
                Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(digitalCurrencySymbol, quantity);
    }
}
