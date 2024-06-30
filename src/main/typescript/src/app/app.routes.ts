import { Routes } from "@angular/router";
import { TodosComponent } from "./todos/todos.component";

export const routes: Routes = [
    {
        path: "todos",
        component: TodosComponent
    },
    {
        path: "",
        redirectTo: "/todos",
        pathMatch: "full"
    }
];
