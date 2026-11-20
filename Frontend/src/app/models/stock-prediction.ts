
export interface StockPrediction {
    productId: number;
    productName: string;
    currentStock: number;
    averageDailySales: number;
    predictedDaysRemaining: number;
}
