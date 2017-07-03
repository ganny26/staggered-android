package com.bacon.demo.application;

/**
 * Created by serajam on 6/25/2017.
 */

public class Product {

    private int id;
    private String productBrand;
    private int price;
    private String productName;

    public Product(String productName, int price, String productBrand, int id) {
        this.productName = productName;
        this.price = price;
        this.productBrand = productBrand;
        this.id = id;
    }

    public int getId() {
        return id;

    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
