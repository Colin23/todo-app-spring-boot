import {TestBed} from '@angular/core/testing';

import {TodoService} from './todo.service';

describe('TodoService', () => {
    let service: TodoService;

    beforeEach((): void => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(TodoService);
    });

    it('should be created', (): void => {
        expect(service).toBeTruthy();
    });
});
