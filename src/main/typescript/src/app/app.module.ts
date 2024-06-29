import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {FormsModule} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {TodosComponent} from "./todos/todos.component";
import {RouterOutlet} from "@angular/router";
import {CommonModule} from "@angular/common";

@NgModule({
    declarations: [
        TodosComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        RouterOutlet,
        AppComponent
    ],
    providers: [
        HttpClient
    ],
    bootstrap: []
})
export class AppModule { }
