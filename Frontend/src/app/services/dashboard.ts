import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { Dashboard } from '../models/dashboard'
import { Product } from '../models/product'
import { Purchase } from '../models/purchase'
import { Sale } from '../models/sale'

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private baseUrl='http://localhost:8080/dashboard'
  private productsUrl='http://localhost:8080/products'


  constructor(private http:HttpClient) {}

  getDashboardStats(): Observable<Dashboard> {
    return this.http.get<Dashboard>(`${this.baseUrl}/stats`)
  }

  getLowStockProducts(): Observable<Product[]>{
    return this.http.get<Product[]>(`${this.productsUrl}/low-stock`)
  }

  getInventoryValue(): Observable<number> {
    return this.http.get<number>(`${this.productsUrl}/inventory-value`)
  }

  getRecentPurchases(): Observable<Purchase[]>{
    return this.http.get<Purchase[]>('http://localhost:8080/purchases/recent')
  }

  getRecentSales(): Observable<Sale[]>{
    return this.http.get<Sale[]>('http://localhost:8080/sales/recent')
  }

  getProducts(): Observable<Product[]>{
    return this.http.get<Product[]>(this.productsUrl)
  }

  getTotalStockQuantity(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/total-stock`)
  }

  getOutOfStockCount(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/out-of-stock`)
  }

  getTotalItemsSold(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/total-items-sold`)
  }

  getTotalItemsPurchased(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/total-items-purchased`)
  }
}
