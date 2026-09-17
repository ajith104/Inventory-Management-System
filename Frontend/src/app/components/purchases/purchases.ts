import { Component, OnInit } from '@angular/core'
import { CommonModule } from '@angular/common'
import { FormsModule } from '@angular/forms'
import { Purchase } from '../../models/purchase'
import { Product } from '../../models/product'
import { PurchaseService } from '../../services/purchase'
import { ProductService } from '../../services/product'

@Component({
  selector: 'app-purchases',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './purchases.html',
  styleUrl: './purchases.css',
})
export class Purchases implements OnInit {
  purchases: Purchase[] = []
  products: Product[] = []

  showPurchaseForm = false
  editingPurchaseId: number | null =null;

  newPurchase: Purchase = {
    purchaseId:null,
    productId:0,
    quantityPurchased:0,
    purchaseDate: ''
  };

  constructor(private purchaseService: PurchaseService, private productService: ProductService) {}

  ngOnInit(): void {
    this.loadPurchases()
    this.loadProducts()
  }

  loadPurchases(): void{
    this.purchaseService.getAllPurchases().subscribe({
      next: (data) => {
        this.purchases = data
      },
      error: (error) => {
        console.error('Error while loading purchases:',error)
      }
    })
  }

    loadProducts(): void{
    this.productService.getAllProducts().subscribe({
      next: (data) => {
        this.products = data
      },
      error: (error) => {
        console.error('Error while loading purchases:',error)
      }
    })
  }

  addPurchase(): void {
    if(this.newPurchase.productId <=0  || this.newPurchase.quantityPurchased <=0  || this.newPurchase.purchaseDate === ''){
      alert('Please enter a valid purchase details....')
      return
    }

    this.purchaseService.addPurchase(this.newPurchase).subscribe({
      next: (data) => {
        console.log('purchase added:',data)
        this.loadProducts()
        this.loadPurchases()
        this.cancelEdit()
      },
      error: (error) => {
        console.error('Error while adding purchase')
      }
      })
  }

  cancelEdit(): void {
    this.editingPurchaseId= null
    this.showPurchaseForm= false
    this.newPurchase = {
      purchaseId:null,
      productId:0,
      quantityPurchased:0,
      purchaseDate:''
    }
  }

  editPurchase(purchase: Purchase): void{
    this.editingPurchaseId=purchase.purchaseId;
    this.newPurchase= {
      purchaseId: purchase.purchaseId,
      productId: purchase.productId,
      quantityPurchased: purchase.quantityPurchased,
      purchaseDate: purchase.purchaseDate
    };
    this.showPurchaseForm=true;
  }

  updatePurchase(): void {
    if(this.editingPurchaseId===null || this.newPurchase.productId<=0 || this.newPurchase.quantityPurchased<=0 || this.newPurchase.purchaseDate===''){
      alert('Please enter valid purchase details...')
      return
    }

    this.purchaseService.updatePurchase(this.editingPurchaseId,this.newPurchase).subscribe({
      next: (data) => {
        console.log('Purchase updated:',data)
        this.loadPurchases()
        this.loadProducts()
        this.cancelEdit()
      },
      error : (error) => {
        console.error('Error while updating purchhase:',error)
      }
    })
  }

  deletePurchase(id: number): void {
    if(!confirm('Are you sure you want to delete this purchase?')){
      return
    }

    this.purchaseService.deletePurchase(id).subscribe({
      next: (data) => {
        console.log('Purchase deleted')
        this.loadPurchases()
        this.loadProducts
      },
      error: (error) => {
        console.error('Error while deleting purchase:',error)
      }
    })
  }
}
