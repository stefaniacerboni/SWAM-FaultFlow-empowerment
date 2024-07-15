import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImportService {
  private importUrl = 'rest/system/load';

  constructor(private http: HttpClient) {}

  importJson(json: string): Observable<any> {
    return this.http.post<any>(this.importUrl, json, { headers: { 'Content-Type': 'application/json' } });
  }
}
