import { NgModule } from "@angular/core";

import { AppComponent } from "./app.component";
import { FormsModule } from "@angular/forms";
import { HttpClient } from "@angular/common/http";
import { TodosComponent } from "./todos/todos.component";
import { RouterOutlet } from "@angular/router";
import { CommonModule } from "@angular/common";

@NgModule({
    declarations: [],
    imports: [CommonModule, FormsModule, RouterOutlet, AppComponent, TodosComponent],
    providers: [HttpClient],
    bootstrap: []
})
export class AppModule {}
