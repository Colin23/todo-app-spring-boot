CREATE TABLE todo (
    id          varchar(255) NOT NULL,
    title       varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    created_at  date         NOT NULL,
    due_at      date         NOT NULL,
    version     INT,
    PRIMARY KEY (id)
);

INSERT INTO todo
(id, title, description, created_at, due_at, version)
VALUES (1, 'Hello Todo', 'Hello Todo description', CURRENT_DATE, current_date, 1);

INSERT INTO todo
(id, title, description, created_at, due_at, version)
VALUES (2, 'Hello Todo2', 'Hello Todo description2', CURRENT_DATE, current_date, 1);