
package com.inventorymanagement.model;

public class StockPrediction {

    private Long productId;
    private String productName;
    private int currentStock;
    private double averageDailySales;
    private int predictedDaysRemaining;

    public StockPrediction(Long productId, String productName,
            int currentStock, double averageDailySales,
            int predictedDaysRemaining) {

        this.productId = productId;
        this.productName = productName;
        this.currentStock = currentStock;
        this.averageDailySales = averageDailySales;
        this.predictedDaysRemaining = predictedDaysRemaining;
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

    public int getPredictedDaysRemaining() {
        return predictedDaysRemaining;
    }
}