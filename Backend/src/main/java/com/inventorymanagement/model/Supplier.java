package com.inventorymanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Supplier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long supplierId;
	private String supplierName;
	private String email;
	private String phone;
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", supplierName=" + supplierName + ", email=" + email + ", phone="
				+ phone + "]";
	}
	public Supplier(Long supplierId, String supplierName, String email, String phone) {
		super();
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.email = email;
		this.phone = phone;
	}
	public Supplier() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
