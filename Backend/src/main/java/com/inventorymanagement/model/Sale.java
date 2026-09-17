package com.inventorymanagement.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Sale {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private Long saleId;
	private Long productId;
	private Integer quantitySold;
	private LocalDate saleDate;
	public Long getSaleId() {
		return saleId;
	}
	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Integer getQuantitySold() {
		return quantitySold;
	}
	public void setQuantitySold(Integer quantitySold) {
		this.quantitySold = quantitySold;
	}
	public LocalDate getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}
	@Override
	public String toString() {
		return "Sale [saleId=" + saleId + ", productId=" + productId + ", quantitySold=" + quantitySold + ", saleDate="
				+ saleDate + "]";
	}
	public Sale(Long saleId, Long productId, Integer quantitySold, LocalDate saleDate) {
		super();
		this.saleId = saleId;
		this.productId = productId;
		this.quantitySold = quantitySold;
		this.saleDate = saleDate;
	}
	public Sale() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
