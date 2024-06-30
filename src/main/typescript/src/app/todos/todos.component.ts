import { Component, OnInit } from "@angular/core";
import { Todo } from "../todo/todo";
import { TodoService } from "../todo/todo.service";
import { CommonModule } from "@angular/common";

@Component({
    selector: "app-todos",
    templateUrl: "./todos.component.html",
    styleUrls: ["./todos.component.css"],
    imports: [CommonModule],
    standalone: true
})
export class TodosComponent implements OnInit {
    todos: Todo[] = [];

    constructor(private todoService: TodoService) {}

    ngOnInit(): void {
        this.todoService.getTodos().subscribe({
            next: (data: Todo[]): void => {
                this.todos = data;
            },
            error: (error): void => {
                console.error("Error fetching todos", error);
            },
            complete: (): void => {
                console.log("Todo fetching completed");
            }
        });
    }
}
