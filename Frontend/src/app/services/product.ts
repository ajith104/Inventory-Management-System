import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { Product } from '../models/product'

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private baseUrl='http://localhost:8080/products'

  constructor(private http:HttpClient){}

  getAllProducts(): Observable<Product[]>{
    return this.http.get<Product[]>(this.baseUrl)
  }

  getProductById(id: number): Observable<Product>{
    return  this.http.get<Product>(`${this.baseUrl}/${id}`)
  }

  addProduct(product: Product): Observable<Product>{
    return this.http.post<Product>(`${this.baseUrl}/add`,product)
  }

  updateProduct(id: number,product: Product): Observable<Product>{
    return this.http.put<Product>(`${this.baseUrl}/${id}`,product)
  }

  deleteProduct(id: number): Observable<void>{
    return this.http.delete<void>(`${this.baseUrl}/${id}`)
  }

  getLowStockProducts(): Observable<Product[]>{
    return this.http.get<Product[]>(`${this.baseUrl}/low-stock`)
  }

  searchProducts(name: string): Observable<Product[]>{
    return this.http.get<Product[]>(`${this.baseUrl}/search/${name}`)
  }

  getInventoryValue(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/inventory-value`)
  }
}
