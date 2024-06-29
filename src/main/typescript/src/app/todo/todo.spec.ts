import {Todo} from './todo';


describe('Todo', (): void => {
    it('should create an instance', (): void => {
        const todo = new Todo(
            1,
            "Test Todo",
            "Test description",
            new Date(),
            new Date(),
            false
        );
        expect(todo).toBeTruthy();
    });
});
