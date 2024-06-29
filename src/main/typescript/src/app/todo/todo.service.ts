import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Todo} from "./todo";

@Injectable({
    providedIn: 'root'
})
export class TodoService {

    private backendUrl: string = "http://localhost:8080/api/v1/todos";

    constructor(private httpClient: HttpClient) {
    }

    public getTodos(): Observable<Todo[]> {
        let asdf = this.httpClient.get<Todo[]>(this.backendUrl);
        console.log("asdf");
        console.log(asdf);
        return this.httpClient.get<Todo[]>(this.backendUrl).pipe(
            map(data => data.map(item => new Todo(
                item.id,
                item.title,
                item.description,
                new Date(item.createdAt),
                new Date(item.dueAt),
                item.completed
            )))
        );
    }
}
