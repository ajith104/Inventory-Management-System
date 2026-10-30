import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { Router, RouterModule } from "@angular/router";
import { AuthService } from "../../services/auth";

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule,FormsModule,RouterModule],
    templateUrl: './login.html'
})
export class Login {
    username = ''
    password = ''
    errorMessage=''
    loading = false

    constructor(private authService:AuthService, private router: Router) {}

    login(): void {
        this.errorMessage = ''
        this.loading = true

        this.authService.login(this.username, this.password).subscribe({
            next: () => {
                this.authService.getCurrentuser().subscribe({
                    next: (user) => {
                        localStorage.setItem('role', user.role)
                        localStorage.setItem('username',user.username)
                        this.loading = false
                        this.router.navigate(['/dashboard'])
                    },
                     error: () => {
                        this.loading = false
                        this.errorMessage  = 'Faild to load user details. Please try again'
            }
                })
            },
            error: () => {
                this.loading = false
                this.errorMessage  = 'Invalid username or password, or server unavailable.'
            }
        })
    }
}