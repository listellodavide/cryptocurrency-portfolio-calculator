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
 * CryptoCurrencyEntry class represent parsed data from the input file. It contain only the digitalCurrencySymbol and
 * the quantity of coins at the moment invested in the market and stored on a digital wallet.
 * Please note there is check of digitalCurrencySymbol really does exist and is tradeable on the selected Digital
 * Currency Market Exchange.
 * Input Requirements:
 *      - quantity can be any positive double number (or integer) greater than zero.
 *      - symbol can be any 3 or for 4 letter symbol (lowercase will be stored uppercase)
 *        No validity check against a whitelist of valid symbol is performed beyond those basic requirements.
 * @since 1.0
 * @author davide.listello@gmail.com
 */
public class CryptoCurrencyEntry {

    private String digitalCurrencySymbol;

    private Double quantity;

    public CryptoCurrencyEntry(String symbol, Double quantity) throws IllegalArgumentException {
        setDigitalCurrencySymbol(symbol);
        setQuantity(quantity);
    }

    public String getDigitalCurrencySymbol() {
        return digitalCurrencySymbol;
    }

    public void setDigitalCurrencySymbol(String digitalCurrencySymbol) throws IllegalArgumentException {
        if(digitalCurrencySymbol.length() >= 3 && digitalCurrencySymbol.length() <= 4)
            this.digitalCurrencySymbol = digitalCurrencySymbol.toUpperCase();
        else throw new IllegalArgumentException("digital currency symbol should be between 3 and 4 characters only! " +
                "eg: BTC");
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        if(quantity > 0.0d)
            this.quantity = quantity;
        else throw new IllegalArgumentException("quantity of coins should be bigger than 0.0 !");
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
