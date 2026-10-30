import { inject } from "@angular/core";
import { CanActivateFn, Router } from "@angular/router";

export const authGuard: CanActivateFn = (route) => {
    const router = inject(Router)
    const token = localStorage.getItem('token')

    if(!token) {
        return router.createUrlTree(['/login'])
    }
    try {
        const payload = token.split('.')[1]
        const decodedPayload = JSON.parse(atob(payload))

        if(!decodedPayload.exp || decodedPayload.exp * 1000 <= Date.now()){

            localStorage.removeItem('token')
            localStorage.removeItem('username')
            localStorage.removeItem('role')
            return router.createUrlTree(['/login'])
        }
    }
        catch {
            localStorage.removeItem('token')
            localStorage.removeItem('username')
            localStorage.removeItem('role')
            return router.createUrlTree(['/dashboard'])
        }
        
    const allowedRoles = route.data['roles'] as string [] | undefined

    if(!allowedRoles){
        return true
    }

    const role = localStorage.getItem('role')

    if(role && allowedRoles.includes(role)){
        return true
    }

    return router.createUrlTree(['/dashboard'])   
}