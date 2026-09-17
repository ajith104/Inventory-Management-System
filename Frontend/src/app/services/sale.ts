import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { Sale } from '../models/sale'

@Injectable({
  providedIn: 'root',
})
export class SaleService {
  private baseUrl='http://localhost:8080/sales'
  
  constructor(private http:HttpClient) {}

  getAllSale(): Observable<Sale[]> {
    return this.http.get<Sale[]>(this.baseUrl)
  }

  getSaleById(id: number): Observable<Sale> {
    return this.http.get<Sale>(`${this.baseUrl}/${id}`)
  }

  addSale(sale: Sale): Observable<Sale> {
    return this.http.post<Sale>(`${this.baseUrl}/add`,sale)
  }

  updateSale(id: number, sale: Sale): Observable<Sale> {
    return this.http.put<Sale>(`${this.baseUrl}/${id}`,sale)
  }

  deleteSale(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`)
  }
}
