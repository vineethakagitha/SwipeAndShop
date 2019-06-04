package com.ssdi.pricesplash;

import java.io.Serializable;

class Store implements Serializable {
    public String store_name;
    public String store_price;
    public String product_url;
    public String currency_code;
    public String currency_symbol;

    @Override
    public String toString() {
        return "Store{" +
                "store_name='" + store_name + '\'' +
                ", store_price='" + store_price + '\'' +
                ", product_url='" + product_url + '\'' +
                ", currency_code='" + currency_code + '\'' +
                ", currency_symbol='" + currency_symbol + '\'' +
                '}';
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_price() {
        return store_price;
    }

    public void setStore_price(String store_price) {
        this.store_price = store_price;
    }

    public String getProduct_url() {
        return product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }
}
