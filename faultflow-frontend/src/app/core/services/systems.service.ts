import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {OutputSystemDto} from "../models/output-system-dto";
import {tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class SystemsService {
  private systemsUrl = 'rest/system'; // Adjust the URL as necessary

  constructor(private http: HttpClient) {}

  getSystems(): Observable<OutputSystemDto[]> {
    return this.http.get<OutputSystemDto[]>(this.systemsUrl);
  }


  downloadPetrinet(uuid: string): Observable<Blob> {
    const url = `${this.systemsUrl}/${uuid}/petrinet`;
    return this.http.get(url, { responseType: 'blob' }).pipe(
      tap((response: Blob) => {
        const downloadUrl = window.URL.createObjectURL(response);
        const link = document.createElement('a');
        link.href = downloadUrl;
        link.download = `petrinet-${uuid}.xpn`;
        link.click();
      })
    );
  }

  getSystemImage(uuid: string): Observable<string> {
    const url = `${this.systemsUrl}/${uuid}/image`;
    return this.http.get(url, { responseType: 'text' });
  }
}
