package com.example.dheerajp.uploadseal;

/**
 * Created by Dheeraj P on 5/1/2017.
 */

public class Product {

    public String productId;
    public String productName;
    public String description;
    public String manufacturingDate;
    public String expiryDate;
    public String batchNummber;
    public String productImage;

    public Product(String productId, String productName, String manufacturingDate, String expiryDate, String batchNumber , String description, String productImage){
        this.productId = productId;
        this.productName = productName;
        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
        this.batchNummber = manufacturingDate;
        this.description = description;
        this.productImage = productImage;
        this.batchNummber = batchNumber;
    }

    public String getProductId(){
        return productId;
    }

    public String getProductName(){
        return productName;
    }

    public String getManufacturingDate() {
        return manufacturingDate;
    }

    public String getExpiryDate(){
        return expiryDate;
    }

    public  String getDescription(){
        return description;
    }

    public String getBatchNummber(){
        return batchNummber;
    }

    public String getProductImage(){
        return productImage;
    }
}
