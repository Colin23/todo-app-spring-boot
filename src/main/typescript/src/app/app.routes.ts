import {RouterModule, Routes} from '@angular/router';
import { TodosComponent } from './todos/todos.component';
import {NgModule} from "@angular/core";

export const routes: Routes = [
    {
        path: "todos",
        component: TodosComponent
    },
    {
        path: '',
        redirectTo: '/todos',
        pathMatch: 'full'
    }
];
