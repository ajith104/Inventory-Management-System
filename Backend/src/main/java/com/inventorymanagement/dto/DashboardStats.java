package com.inventorymanagement.dto;

public class DashboardStats {
	private long totalProducts;
	private long totalSales;
	private long totalPurchases;
	private long totalSuppliers;
	public DashboardStats() {}

	public DashboardStats(long totalProducts, long totalSales, long totalPurchases, long totalSuppliers) {
		
		this.totalProducts = totalProducts;
		this.totalSales = totalSales;
		this.totalPurchases = totalPurchases;
		this.totalSuppliers = totalSuppliers;
	}
	public long getTotalProducts() {
		return totalProducts;
	}
	public void setTotalProducts(long totalProducts) {
		this.totalProducts = totalProducts;
	}
	public long getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(long totalSales) {
		this.totalSales = totalSales;
	}
	public long getTotalPurchases() {
		return totalPurchases;
	}
	public void setTotalPurchases(long totalPurchases) {
		this.totalPurchases = totalPurchases;
	}
	public long getTotalSuppliers() {
		return totalSuppliers;
	}
	public void setTotalSuppliers(long totalSuppliers) {
		this.totalSuppliers = totalSuppliers;
	}
	
	
	
	

}
