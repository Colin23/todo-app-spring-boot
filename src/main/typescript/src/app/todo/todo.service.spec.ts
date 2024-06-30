import { TestBed } from "@angular/core/testing";

import { TodoService } from "./todo.service";
import { provideHttpClient, withFetch } from "@angular/common/http";

describe("TodoService", (): void => {
    let service: TodoService;

    beforeEach((): void => {
        TestBed.configureTestingModule({
            providers: [TodoService, provideHttpClient(withFetch())]
        });
        service = TestBed.inject(TodoService);
    });

    it("should be created", (): void => {
        expect(service).toBeTruthy();
    });
});
