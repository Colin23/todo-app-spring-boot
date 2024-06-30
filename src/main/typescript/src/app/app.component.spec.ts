import { ComponentFixture, TestBed } from "@angular/core/testing";
import { AppComponent } from "./app.component";

describe("AppComponent", (): void => {
    beforeEach(async (): Promise<void> => {
        await TestBed.configureTestingModule({
            imports: [AppComponent]
        }).compileComponents();
    });

    it("should create the app", (): void => {
        const fixture: ComponentFixture<AppComponent> = TestBed.createComponent(AppComponent);
        const app: AppComponent = fixture.componentInstance;
        expect(app).toBeTruthy();
    });

    it(`should have the 'Spring Boot - ToDo App' title`, (): void => {
        const fixture: ComponentFixture<AppComponent> = TestBed.createComponent(AppComponent);
        const app: AppComponent = fixture.componentInstance;
        expect(app.title).toEqual("Spring Boot - ToDo App");
    });
});
