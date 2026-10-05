import { Component, OnInit } from '@angular/core'
import { CommonModule } from '@angular/common'
import { DashboardService } from '../../services/dashboard'
import { Dashboard as DashboardModel } from '../../models/dashboard'
import { Product } from '../../models/product'
import { Purchase } from '../../models/purchase'
import { Sale } from '../../models/sale'

@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit {
  totalStockQuantity = 0
  totalTransactions = 0
  highestStockProduct: Product | null = null
  totalItemsSold = 0
  totalItemsPurchased = 0
  lowStockProducts: Product[] = []
  products: Product[] = []
  inventoryValue = 0
  recentPurchases: Purchase[] = []
  recentSales: Sale[] = []
  outOfStockCount=0
  dashboardStats: DashboardModel = {
    totalProducts: 0,
    totalSuppliers: 0,
    totalPurchases: 0,
    totalSales: 0
  }

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.loadDashboardStats()
    this.loadLowStockProducts()
    this.loadInventoryValue()
    this.loadRecentPurchases()
    this.loadRecentSales()
    this.loadProducts()
    this.loadTotalStockQuantity()
    this.loadOutOfStockCount()
    this.loadTotalItemsSold()
    this.loadTotalItemsPurchased()
  }

  loadDashboardStats(): void {
    this.dashboardService.getDashboardStats().subscribe({
      next: (data) => {
        this.dashboardStats = data
        this.totalTransactions=this.dashboardStats.totalPurchases + this.dashboardStats.totalSales
      },
      error: (error) => {
        console.error('Error while loading dashboard ststistics:',error)
      }
    })
  }

  loadLowStockProducts(): void {
      this.dashboardService.getLowStockProducts().subscribe({
        next: (data) => {
          this.lowStockProducts= data
        },
        error: (error) => {
          console.error("Error while loading low stock products:",error)
        }
      })
    }

    loadInventoryValue(): void {
      this.dashboardService.getInventoryValue().subscribe({
        next: (value) => {
          this.inventoryValue = value
        },
        error: (error) => {
          console.error('Error while loading iventory value:',error)
        }
      })
    }
  

    loadRecentPurchases(): void {
      this.dashboardService.getRecentPurchases().subscribe({
        next: (data) => {
          this.recentPurchases = data
        },
        error: (error) => {
          console.error('Error while loading recent purchases:',error)
        }
      })
    }

    loadRecentSales(): void {
      this.dashboardService.getRecentSales().subscribe({
        next: (data) => {
          this.recentSales = data
        },
        error: (error) => {
          console.error('Error while loading recent sales:',error)
        }
      })
    }

    loadProducts(): void {
      this.dashboardService.getProducts().subscribe({
        next: (data) => {
          this.products = data
          if(this.products.length > 0) {
              this.highestStockProduct=this.products.reduce((highest, product) => (product.quantity) > highest.quantity ? product : highest)
          } else{
            this.highestStockProduct = null
          }
          },
        error: (error) => {
          console.error('Error while loading products:',error)
        }
      })
    }

    loadTotalStockQuantity(): void {
      this.dashboardService.getTotalStockQuantity().subscribe({
        next: (value) => {
          this.totalStockQuantity = value
        },
        error: (error) => {
          console.error('Error while loading total stock quantity:',error)
        }
      })
    }

    loadOutOfStockCount(): void {
      this.dashboardService.getOutOfStockCount().subscribe({
        next: (data) => {
          this.outOfStockCount = data
        },
          error: (error) => {
            console.error('Error while loading out of stock count:',error)
        }

      })
    }

    loadTotalItemsSold(): void{
      this.dashboardService.getTotalItemsSold().subscribe({
        next: (data) => {
          this.totalItemsSold = data
        },
        error: (error) => {
            console.error('Error while loading total items sold:',error)
        }
      })
    }

    loadTotalItemsPurchased(): void{
      this.dashboardService.getTotalItemsPurchased().subscribe({
        next: (data) =>{
          this.totalItemsPurchased = data
        },
        error: (error) => {
            console.error('Error while loading total items purchased:',error)
        }
      })
    }
}
