import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from '../model/comment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private httpClient: HttpClient) { }

  public getComments(dateForo: number): Observable<Comment[]> {
    return this.httpClient.get<Comment[]>('/Prod/comment/'+dateForo);
  }

  public createComment(comment: Comment): Observable<Comment> {
    return this.httpClient.put<Comment>('/Prod/comment',comment);
  }
  
}
