
package com.inventorymanagement.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.model.Product;
import com.inventorymanagement.model.Sale;
import com.inventorymanagement.model.StockPrediction;
import com.inventorymanagement.repository.ProductRepository;
import com.inventorymanagement.repository.SaleRepository;

@Service
public class PredictionService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    private double calculateAverageDailySales(List<Sale> recentSales, Long productId) {

        int recentSalesTotal = 0;
        int olderSalesTotal = 0;

        LocalDate today = LocalDate.now();
        LocalDate recentStartDate = today.minusDays(6);

        for (Sale sale : recentSales) {

            if (productId != null && productId.equals(sale.getProductId())
                    && !sale.getSaleDate().isAfter(today)) {

                if (!sale.getSaleDate().isBefore(recentStartDate)) {
                    recentSalesTotal += sale.getQuantitySold();
                } else {
                    olderSalesTotal += sale.getQuantitySold();
                }
            }
        }

        double recentAverage = recentSalesTotal / 7.0;
        double olderAverage = olderSalesTotal / 23.0;

        return (recentAverage * 0.60) + (olderAverage * 0.40);
    }

    public int predictDaysRemaning(int currentStock, double averageDailySales) {

        if (averageDailySales <= 0) {
            return -1;
        }

        return (int) Math.floor(currentStock / averageDailySales);
    }

    public List<StockPrediction> getStockPredictions() {

        LocalDate startDate = LocalDate.now().minusDays(29);

        List<Sale> recentSales =
                saleRepository.findBySaleDateGreaterThanEqual(startDate);

        List<Product> products = productRepository.findAll();

        List<StockPrediction> predictions = new ArrayList<>();

        for (Product product : products) {

            double averageDailySales =
                    calculateAverageDailySales(recentSales, product.getProductId());

            int predictedDaysRemaining =
                    predictDaysRemaning(product.getQuantity(), averageDailySales);

            predictions.add(new StockPrediction(
                    product.getProductId(),
                    product.getProductName(),
                    product.getQuantity(),
                    averageDailySales,
                    predictedDaysRemaining
            ));
        }

        return predictions;
    }
}