import { Component } from '@angular/core';
import { RouterLink, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, CommonModule,],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  username = localStorage.getItem('username') || ''
  role = '' 

  constructor(private authService: AuthService, private router: Router) {
    this.authService.getCurrentuser().subscribe({
      next: (user) => {
        this.username = user.username
        this.role = user.role
        localStorage.setItem('role', user.role)
      },
      error: (error) => {
        console.error('Failed to fetch current user:', error)
      }
    })
  }

  logout(): void {
    this.authService.logout()
    this.router.navigate(['/login'])
  }
}
