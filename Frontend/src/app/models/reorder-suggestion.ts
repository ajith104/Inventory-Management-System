export interface ReorderSuggestion {
    productId: number;
    productName: string;
    currentStock: number;
    averageDailySales: number;
    suggestedQuantity: number;
}
