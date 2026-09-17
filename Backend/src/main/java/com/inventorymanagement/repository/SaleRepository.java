package com.inventorymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventorymanagement.model.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{

}
