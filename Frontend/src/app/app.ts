import { Component, signal } from '@angular/core';
import { RouterOutlet, NavigationEnd, Router } from '@angular/router';
import { Navbar } from './components/navbar/navbar';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Navbar],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  showNavbar = true
  constructor(private router: Router) {
    this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe((event: NavigationEnd) => {
      this.showNavbar = event.urlAfterRedirects !== '/login'
    })
  }
}
