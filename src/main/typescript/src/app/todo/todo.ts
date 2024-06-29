export class Todo {
    id: number;
    todo_title: string; // has to be named like this because of the JSON field name.
    todo_description: string; // has to be named like this because of the JSON field name.
    todo_created_at: Date; // has to be named like this because of the JSON field name.
    todo_due_at: Date; // has to be named like this because of the JSON field name.
    todo_completed: boolean; // has to be named like this because of the JSON field name.

    constructor(id: number, title: string, description: string, createdAt: Date, dueAt: Date, completed: boolean) {
        this.id = id;
        this.todo_title = title;
        this.todo_description = description;
        this.todo_created_at = new Date(createdAt);
        this.todo_due_at = new Date(dueAt);
        this.todo_completed = completed;
    }
}
