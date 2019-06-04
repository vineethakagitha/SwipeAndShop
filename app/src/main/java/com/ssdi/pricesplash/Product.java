package com.ssdi.pricesplash;

import java.io.Serializable;
import java.util.Arrays;

public class Product implements Serializable {

    public String barcode_number;

    public String barcode_type;

    public String product_name;

    public String category;

    public String brand;

    public String manufacturer;

    public String size;

    public String[] images;
    public Store[] stores;
    public String[] reviews;
    public String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "barcode_number='" + barcode_number + '\'' +
                ", barcode_type='" + barcode_type + '\'' +
                ", product_name='" + product_name + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", size='" + size + '\'' +
                ", images=" + Arrays.toString(images) +
                ", stores=" + Arrays.toString(stores) +
                ", reviews=" + Arrays.toString(reviews) +
                '}';
    }

    public String getBarcode_number() {
        return barcode_number;
    }

    public void setBarcode_number(String barcode_number) {
        this.barcode_number = barcode_number;
    }

    public String getBarcode_type() {
        return barcode_type;
    }

    public void setBarcode_type(String barcode_type) {
        this.barcode_type = barcode_type;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public Store[] getStores() {
        return stores;
    }

    public void setStores(Store[] stores) {
        this.stores = stores;
    }

    public String[] getReviews() {
        return reviews;
    }

    public void setReviews(String[] reviews) {
        this.reviews = reviews;
    }
}
