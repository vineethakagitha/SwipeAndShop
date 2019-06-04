package com.ssdi.pricesplash;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseObject implements Serializable {

    ArrayList<Product> products;

    @Override
    public String toString() {
        return "ResponseObject{" +
                "products=" + products +
                '}';
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}
