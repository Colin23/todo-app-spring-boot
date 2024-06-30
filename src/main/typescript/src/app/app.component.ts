import { Component } from "@angular/core";
import { RouterLink, RouterLinkActive, RouterOutlet } from "@angular/router";

@Component({
    selector: "app-root",
    templateUrl: "./app.component.html",
    styleUrl: "./app.component.css",
    imports: [RouterOutlet, RouterLinkActive, RouterLink],
    standalone: true
})
export class AppComponent {
    title: string = "Spring Boot - ToDo App";
}
