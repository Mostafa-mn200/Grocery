package com.example.grocery.models;

import java.io.Serializable;

public class MyCartModel implements Serializable {
    String productName;
    String productPrice;
    String CurrentDate;
    String CurrentTime;
    int totalPrice;
    String totalQuantity;
    String documentId;

    public MyCartModel() {
    }

    public MyCartModel(String productName, String productPrice, String currentDate, String currentTime, int totalPrice, String totalQuantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        CurrentDate = currentDate;
        CurrentTime = currentTime;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCurrentDate() {
        return CurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        CurrentDate = currentDate;
    }

    public String getCurrentTime() {
        return CurrentTime;
    }

    public void setCurrentTime(String currentTime) {
        CurrentTime = currentTime;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
