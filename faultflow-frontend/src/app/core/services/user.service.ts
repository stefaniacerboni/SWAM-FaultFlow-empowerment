import { Injectable } from '@angular/core';
import {AuthService} from "./auth.service";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'rest/profile';

  constructor(private http: HttpClient, private authService: AuthService) {}

  getCurrentUser(): Observable<any> {
    const token = this.authService.getToken();
    if (!token) {
      return of(null); // No token, user is not logged in
    }

    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.get<any>(this.apiUrl, { headers }).pipe(
      catchError(this.handleError<any>('getCurrentUser'))
    );
  }
  updateUserProfile(user: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.authService.getToken()}`
    });
    return this.http.put<any>(`${this.apiUrl}`, user, { headers });
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}
