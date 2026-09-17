package com.inventorymanagement.service;

import org.springframework.stereotype.Service;

@Service
public class PredictionService {
	public int predictDaysRemaning(int currentStock, int averageDailySales) {
		if (averageDailySales == 0) {
			return 0;
		}
		return currentStock / averageDailySales;
	}

}
