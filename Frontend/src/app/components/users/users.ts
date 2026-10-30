import { Component, OnInit } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { User } from '../../models/user'
import { FormsModule } from '@angular/forms'

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './users.html',
  styleUrl: './users.css',
})
export class Users implements OnInit {
  newUsername = ''
  newPassword = ''
  newRole = 'EMPLOYEE'
  currentRole = localStorage.getItem('role')
  successMessage = ''
  showAddForm = false
  users: User[] = []
  loading = true
  errorMessage = ''
  editingUserId: number | null = null
  selectedRole = 'EMPLOYEE'

  private apiUrl = "http://localhost:8080/users"

  constructor(private http:HttpClient) {}

  ngOnInit(): void {
    this.loadUsers()
  }

  loadUsers(): void {
    this.loading = true
    this.errorMessage = ''

    this.http.get<User[]>(this.apiUrl).subscribe({
      next: (data) => {
        this.users = data;
        this.loading = false
      },
      error: (error) => {
          console.error('Error while fetching users:',error)
          this.errorMessage = 'Unable to load users, Please try again...'
          this.loading = false
        }
    })
  }

  createUser(): void {
    this.errorMessage = ''
    this.successMessage = ''

    const userData = {
      username: this.newUsername,
      password: this.newPassword,
      role: this.newRole
    }

    this.http.post('http://localhost:8080/auth/register',userData).subscribe({
      next : () => {
        this.successMessage = 'User created successfully!'
        this.newUsername = ''
        this.newPassword = ''
        this.newRole = 'EMPLOYEE'
        this.showAddForm = false
        this.loadUsers()
      },
      error: (error) => {
          console.error('Error while creating users:',error)
          this.errorMessage = 'Unable to craete users'
          this.loading = false
        }
    })
  }

  deleteUser(user: User): void {
    if (!confirm(`Are you sure you want to delete ${user.username}?`)) {
      return
    }

    this.errorMessage = ''
    this.successMessage = ''

    this.http.delete(`${this.apiUrl}/${user.userId}`).subscribe({
      next: () => {
        this.successMessage = 'User deleted successfully!'
        this.loadUsers()
      },
      error: (error) => {
        console.error('Error while deleting user:', error)
        this.errorMessage =
        error.status === 403 ? 'You are not allowed to delete this user.' : 'Unable to delete user. Please try again.'
      }
    })
  }

  startEditUser(user: User): void {
    this.editingUserId = user.userId
    this.selectedRole = user.role
    this.errorMessage = ''
    this.successMessage = ''
  }

  cancelEditUser(): void {
    this.editingUserId = null
  }

  saveUserRole(user: User): void {
    this.errorMessage = ''
    this.successMessage = ''

    this.http.put<void>(
      `${this.apiUrl}/${user.userId}`,
      { role: this.selectedRole }
      ).subscribe({
        next: () => {
          this.successMessage = 'User role updated successfully!'
          this.editingUserId = null
          this.loadUsers()
        },
        error: (error) => {
        console.error('Error while updating user role:', error)

        this.errorMessage =
          error.status === 403 ? 'You are not allowed to edit this user.' : 'Unable to update user role. Please try again.'
      }
    })
  }

  resetUserForm(): void {
    this.newUsername = ''
    this.newPassword = ''
    this.newRole = 'EMPLOYEE'
    this.successMessage = ''
    this.errorMessage = ''
  }
}
