export class Todo {
    id: number;
    title: string;
    description: string;
    createdAt: Date;
    dueAt: Date;
    completed: boolean;

    constructor(id: number, title: string, description: string, createdAt: Date, dueAt: Date, completed: boolean) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = new Date(createdAt);
        this.dueAt = new Date(dueAt);
        this.completed = completed;
    }
}
