import { Component, OnInit } from '@angular/core'
import { CommonModule } from '@angular/common'
import { FormsModule } from '@angular/forms'
import { Sale } from '../../models/sale'
import { Product } from '../../models/product'
import { SaleService } from '../../services/sale'
import { ProductService } from '../../services/product'


@Component({
  selector: 'app-sales',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './sales.html',
  styleUrl: './sales.css',
})
export class Sales implements OnInit {
  sales: Sale[]=[]
  products: Product[]=[]

  showSaleForm= false
  editingSaleId: number | null = null

  newSale: Sale={
    saleId: null,
    productId: 0,
    quantitySold: 0,
    saleDate: ''
  }

  constructor(private saleService: SaleService, private productService: ProductService) {}

  ngOnInit(): void{
    this.loadSales()
    this.loadProducts()
  }

  loadSales(): void{
    this.saleService.getAllSale().subscribe({
      next: (data) => {
        this.sales=data
      },
      error: (error) => {
        console.error("Error while loading sales:",error)
      }
    })
  }

  loadProducts(): void{
    this.productService.getAllProducts().subscribe({
      next: (data) => {
        this.products=data
      },
      error: (error) => {
        console.error("Error while loading products:",error)
      }
    })
  }

  addSale(): void {
    if(this.newSale.productId <=0 || this.newSale.quantitySold<=0 || this.newSale.saleDate === ''){
      alert('Please enter valid sale details: ')
      return
    }

    this.saleService.addSale(this.newSale).subscribe({
      next: (data) =>{
        console.log("Sale added:",data)
        this.loadSales()
        this.loadProducts()
        this.cancelEdit()
      },
      error: (error) => {
        console.error("Error while adding products:",error)

        alert(error?.error?.message || 'Unable to addsale. Please check the available stock')
      }
    });
  }

  cancelEdit(): void{
    this.editingSaleId=null
    this.showSaleForm=false

    this.newSale={
      saleId: null,
      productId: 0,
      quantitySold: 0,
      saleDate: ''
    }
  }

  editSale(sale: Sale): void{
    this.editingSaleId=sale.saleId

    this.newSale={
      saleId: sale.saleId,
      productId: sale.productId,
      quantitySold: sale.quantitySold,
      saleDate: sale.saleDate
    }

    this.showSaleForm=true
  }

  updateSale(): void{
    if(this.editingSaleId===null || this.newSale.productId <=0 || this.newSale.quantitySold<=0 || this.newSale.saleDate === ''){
      alert('Please enter valid sale details: ')
      return
    }
  this.saleService.updateSale(this.editingSaleId,this.newSale).subscribe({
      next: (data) =>{
        console.log("Sale updated:",data)
        this.loadSales()
        this.loadProducts()
        this.cancelEdit()
      },
      error: (error) => {
        console.error("Error while updating sale:",error)

        alert(error?.error?.message || 'Unable to update sale. Please check the available stock')
      }
    });
  }

  deleteSale(id: number): void{
    if(!confirm('Are you sure you want to delete this sale?')){
      return
    }

    this.saleService.deleteSale(id).subscribe({
      next: (data) =>{
        console.log("Sale deleted:",data)
        this.loadSales()
        this.loadProducts()
      },
      error: (error) => {
        console.error("Error while deleting sale:",error)
      }
    })
  }
}
