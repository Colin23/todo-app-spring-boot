export class Todo {
    id: number;
    title: string;
    description: string;
    createdAt: Date;
    dueAt: Date;
    completed: boolean;

    constructor(data: {
        id: number,
        title: string,
        description: string,
        createdAt: Date,
        dueAt: Date,
        completed: boolean
    }) {
        this.id = data.id;
        this.title = data.title;
        this.description = data.description;
        this.createdAt = new Date(data.createdAt);
        this.dueAt = new Date(data.dueAt);
        this.completed = data.completed;
    }
}
