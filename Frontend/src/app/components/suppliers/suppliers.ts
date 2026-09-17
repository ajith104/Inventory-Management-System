import { Component, OnInit } from '@angular/core'
import { CommonModule } from '@angular/common'
import { Supplier } from '../../models/supplier'
import { SupplierService } from '../../services/supplier'
import { FormsModule } from '@angular/forms'

@Component({
  selector: 'app-suppliers',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './suppliers.html',
  styleUrl: './suppliers.css',
})
export class Suppliers implements OnInit {

  suppliers: Supplier[]= []

  constructor(private supplierService: SupplierService){}

  ngOnInit(): void {
    this.loadSuppliers()
  }

  loadSuppliers(): void {
    this.supplierService.getAllSuppliers().subscribe({
      next: (data) => {
        this.suppliers = data
      },
      error: (error) => {
        console.error('Error while loading suppliers:',error)
      }
    });
  }

  showSupplierForm = false

  editingSupplierId: number | null = null

  newSupplier: Supplier = {
    supplierId: null,
    supplierName:'',
    email:'',
    phone:''
  };

  addSupplier(): void{
    if(this.newSupplier.supplierName.trim()==='' ||  this.newSupplier.email.trim()==='' ||  this.newSupplier.phone.trim()===''){
        alert('Please enter all supplier details')
        return
       }

    this.supplierService.addSupplier(this.newSupplier).subscribe({
      next: (data) => {
        console.log('Supplier added:',data)
        this.loadSuppliers()
        this.cancelEdit()
      },
      error:(error) => {
        console.error('Error while adding supplier:',error)
      }
    })
  }

  editSupplier(supplier: Supplier): void {
    this.editingSupplierId=supplier.supplierId

    this.newSupplier={
      supplierId:supplier.supplierId,
      supplierName:supplier.supplierName,
      email:supplier.email,
      phone:supplier.phone
    }
    this.showSupplierForm=true
  }

  updateSupplier(): void{
    if(this.newSupplier.supplierName.trim()==='' ||  this.newSupplier.email.trim()==='' ||  this.newSupplier.phone.trim()===''){
        alert('Please enter all supplier details')
        return
       }


    if(this.editingSupplierId===null){
      return
    }

    this.supplierService.updateSupplier(
      this.editingSupplierId,
      this.newSupplier).subscribe({
        next: (data) => {
          console.log('Supplier updated:', data)
          this.loadSuppliers()
          this.cancelEdit()
        },
      error:(error) => {
        console.error('Error while updating supplier:',error)
      }
    })
  }

  cancelEdit(): void{
    this.editingSupplierId=null
    this.showSupplierForm=false

    this.newSupplier={
      supplierId: null,
      supplierName: '',
      email:'',
      phone:''
    };
  }

  deleteSupplier(id: number): void {
    const confirmed = confirm('Are you sure, you want to delete this supplier ? ')

    if(!confirmed) {
      return
    }

    this.supplierService.deleteSupplier(id).subscribe({
      next:() => {
        console.log('Supplier deleted:',id)
        },
        error: (error) => {
          console.error('Error while detilig supplier:',error)
        }
    })
  }
}
