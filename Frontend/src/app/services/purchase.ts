import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable, observable } from 'rxjs'
import { Purchase } from '../models/purchase'
import { Purchases } from '../components/purchases/purchases'

@Injectable({
  providedIn: 'root',
})
export class PurchaseService {
  private baseUrl = 'http://localhost:8080/purchases'
  constructor(private http:HttpClient) {}
  
  getAllPurchases():
    Observable<Purchase[]> {
      return this.http.get<Purchase[]>(this.baseUrl)
    }

    getPurchaseById(id: number):
      Observable<Purchase> {
        return this.http.get<Purchase>(`${this.baseUrl}/${id}`)
      }

      addPurchase(purchase: Purchase):
        Observable<Purchase> {
          return this.http.post<Purchase>(`${this.baseUrl}/add`,purchase)
        }

    updatePurchase(id: number,purchase: Purchase):
      Observable<Purchase> {
        return this.http.put<Purchase>(`${this.baseUrl}/${id}`,purchase)
      }

    deletePurchase(id: number):
      Observable<void>{
        return this.http.delete<void>(`${this.baseUrl}/${id}`)
      }
}
