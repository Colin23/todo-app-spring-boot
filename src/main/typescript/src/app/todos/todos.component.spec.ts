import {ComponentFixture, TestBed} from '@angular/core/testing';

import {TodosComponent} from './todos.component';
import {provideHttpClient, withFetch} from "@angular/common/http";

describe("TodosComponent", (): void => {
    let component: TodosComponent;
    let fixture: ComponentFixture<TodosComponent>;

    beforeEach(async (): Promise<void> => {
        await TestBed.configureTestingModule({
            imports: [TodosComponent],
            providers: [provideHttpClient(withFetch())]
        })
            .compileComponents();

        fixture = TestBed.createComponent(TodosComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', (): void => {
        expect(component).toBeTruthy();
    });
});
