
package com.inventorymanagement.model;

public class ReorderSuggestion {

    private Long productId;
    private String productName;
    private int currentStock;
    private double averageDailySales;
    private int suggestedQuantity;

    public ReorderSuggestion(Long productId, String productName,
            int currentStock, double averageDailySales,
            int suggestedQuantity) {

        this.productId = productId;
        this.productName = productName;
        this.currentStock = currentStock;
        this.averageDailySales = averageDailySales;
        this.suggestedQuantity = suggestedQuantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public double getAverageDailySales() {
        return averageDailySales;
    }

    public int getSuggestedQuantity() {
        return suggestedQuantity;
    }
}