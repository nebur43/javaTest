import { Injectable } from '@angular/core';
import { Foro } from '../model/foro';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ForoService {

  constructor(private httpClient: HttpClient) { 

  }

  public getForos(): Observable<Foro[]> {
    return this.httpClient.get<Foro[]>('/Prod/foro');
  }

  public getForo(id: number): Observable<Foro> {
    return this.httpClient.get<Foro>('/Prod/foro/'+id);
  }

  public createForo(foro: Foro): Observable<Foro> {
    return this.httpClient.put<Foro>('/Prod/foro',foro);
  }
}
