
package com.inventorymanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventorymanagement.model.ReorderSuggestion;
import com.inventorymanagement.model.StockPrediction;

@Service
public class ReorderService {

    private static final int FORECAST_DAYS = 7;
    private static final int SAFETY_STOCK = 10;

    @Autowired
    private PredictionService predictionService;

    public List<ReorderSuggestion> getReorderSuggestions() {

        List<StockPrediction> predictions =
                predictionService.getStockPredictions();

        List<ReorderSuggestion> suggestions = new ArrayList<>();

        for (StockPrediction prediction : predictions) {

            double averageDailySales = prediction.getAverageDailySales();

            // Skip products without sales data
            if (averageDailySales <= 0) {
                if (prediction.getCurrentStock() < SAFETY_STOCK) {
					suggestions.add(new ReorderSuggestion(
							prediction.getProductId(),
							prediction.getProductName(),
							prediction.getCurrentStock(),
							averageDailySales,
							(SAFETY_STOCK*2) - prediction.getCurrentStock()
					));
				}
                continue;
            }

            int predictedDemand =
                    (int) Math.ceil(averageDailySales * FORECAST_DAYS);

            int targetStock = predictedDemand + SAFETY_STOCK;

            int suggestedQuantity =
                    targetStock - prediction.getCurrentStock();

            if (suggestedQuantity > 0) {

                suggestions.add(new ReorderSuggestion(
                        prediction.getProductId(),
                        prediction.getProductName(),
                        prediction.getCurrentStock(),
                        averageDailySales,
                        suggestedQuantity
                ));
            }
        }

        return suggestions;
    }
}