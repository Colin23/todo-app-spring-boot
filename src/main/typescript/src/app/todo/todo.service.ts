import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Todo} from "./todo";

@Injectable({
    providedIn: 'root'
})
export class TodoService {

    private backendUrl: string = "http://localhost:8080/api/v1/todos";
    httpClient: HttpClient = inject(HttpClient);

    public getTodos(): Observable<Todo[]> {
        return this.httpClient.get<Todo[]>(this.backendUrl).pipe(
            map(data => data.map(item => new Todo(item)))
        );
    }
}
