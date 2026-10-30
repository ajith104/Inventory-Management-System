import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = "http://localhost:8080/auth"
    constructor(private http: HttpClient) {}

    login(username: string, password:string): Observable<any>
    {
        return this.http.post<any>(`${this.apiUrl}/login`,{
            username,
            password
            }).pipe(
            tap(response => {
                localStorage.setItem('token',response.token)

                localStorage.setItem('username',username)
            })
        )
    }
    
    logout(): void {
        localStorage.removeItem('token')

        localStorage.removeItem('username')

        localStorage.removeItem('role')
    }

    getToken(): string | null {
        return localStorage.getItem('token')
    }

    isLoggedIn(): boolean {
        return !!this.getToken()
    }

    getCurrentuser(): Observable<{username: string, role: string}> {
        return this.http.get<{username: string, role: string}>(`${this.apiUrl}/me`)
    }
}