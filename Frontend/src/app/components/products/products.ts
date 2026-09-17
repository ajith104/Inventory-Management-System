import { Component,OnInit } from '@angular/core'
import { Product } from '../../models/product'
import { ProductService } from '../../services/product'
import { CommonModule } from '@angular/common'
import { FormsModule } from '@angular/forms'

@Component({
  selector: 'app-products',
  standalone:true,
  imports: [CommonModule, FormsModule],
  templateUrl: './products.html',
  styleUrl: './products.css',
})
export class Products implements OnInit {
  products: Product[] = []
  searchName = ''
  isSearching = false
  showProductForm = false
  showLowStock= false
  inventoryValue = 0
  newProduct: Product = { 
    productId: null,
    productName: '',
    category: '',
    price: 0,
    quantity: 0
  }
  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts()
    this.loadInventoryValue()
  }

  loadProducts(): void {
    this.productService.getAllProducts().subscribe(data => {this.products=data;})
  }

  addProduct(): void {
        if(
          this.newProduct.productName.trim() === '' ||
          this.newProduct.category.trim() === '' ||
          this.newProduct.price <=0 ||
          this.newProduct.quantity < 0
        ){
          alert('Please enter valid product details')
          return
        }

        this.productService.addProduct(this.newProduct).subscribe({
          next:(data) => {
            console.log('Product Added:', data);
              this.loadProducts()
              this.cancelEdit()

            this.newProduct={
                productId: null,
                productName: '',
                category: '',
                price: 0,
                quantity:0
            }
        },
        error: (error) => {
          console.error('Error while adding product:',error);
        }
      })
    }

    editingProductId: number | null=null

    editProduct(product: Product): void{
      this.editingProductId=product.productId
      this.newProduct={
        productId: product.productId,
        productName: product.productName,
        category: product.category,
        price: product.price,
        quantity: product.quantity
      }
      this.showProductForm = true
    }

    updateProduct(): void{
      if(this.editingProductId ===null){
        return
      }

      if(this.newProduct.productName.trim() === '' ||  this.newProduct.category.trim() === '' ||  this.newProduct.price <=0 ||  this.newProduct.quantity < 0){
          alert('Please enter valid product details')
          return
        }

      this.productService.updateProduct(
        this.editingProductId,
        this.newProduct
      ).subscribe({
        next: (data) =>{
          console.log('Product Updated:',data)
          this.loadProducts()
          this.cancelEdit()
        },
        error: (error) => {
          console.error('Error while updating product:',error)
        }
      })
    }

    cancelEdit(): void {
      this.editingProductId = null
      this.showProductForm = false
      this.newProduct = {
        productId: null,
        productName: '',
        category: '',
        price: 0,
        quantity: 0
      }
    }

    deleteProduct(id: number): void {
      if(!confirm('Are you sure you want to delete this product ?')){
        return 
      }

      this.productService.deleteProduct(id).subscribe({
        next: () => {
          console.log('Product Deleted:',id)
          this.loadProducts()
        },
        error: (error) => {
          console.error("Error while deleting product")
        }
      })
    }

    searchProducts(): void{
      const name=this.searchName.trim()

      if(name=== ''){
        this.loadProducts;
        this.isSearching = true
      }

      this.productService.searchProducts(name).subscribe({
        next: (data) => {
          this.products = data
          this.isSearching = true
        },
        error: (error) => {
          console.error('Error while searching products:',error)
        }
      })
    }

    clearSearch(): void{
      this.searchName = '',
      this.isSearching = false,
      this.loadProducts()
    }

    loadLowStockProducts(): void {
      this.productService.getLowStockProducts().subscribe({
        next: (data) => {
          this.products= data
          this.showLowStock = true
          this.isSearching = false
        },
        error: (error) => {
          console.error("Error while loading low stock products:",error)
        }
      })
    }

    showAllProducts(): void{
      this.searchName='',
      this.isSearching = false,
      this.showLowStock=false,
      this.loadProducts()
    }
    

    loadInventoryValue(): void{
      this.productService.getInventoryValue().subscribe({
        next: (value) => {
          this.inventoryValue = value
        },
        error: (error) => {
          console.error('Error while loading iventory value:',error)
        }
      })
    }
}
