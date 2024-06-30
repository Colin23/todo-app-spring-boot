import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable, map } from "rxjs";
import { Todo } from "./todo";

@Injectable({
    providedIn: "root"
})
export class TodoService {
    private backendUrl: string = "http://localhost:8080/api/v1/todos";

    constructor(private httpClient: HttpClient) {}

    public getTodos(): Observable<Todo[]> {
        return this.httpClient
            .get<Todo[]>(this.backendUrl)
            .pipe(
                map((data: Todo[]) =>
                    data.map(
                        (item: Todo) =>
                            new Todo(
                                item.id,
                                item.todo_title,
                                item.todo_description,
                                new Date(item.todo_created_at),
                                new Date(item.todo_due_at),
                                item.todo_completed
                            )
                    )
                )
            );
    }
}
