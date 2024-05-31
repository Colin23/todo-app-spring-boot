CREATE TABLE IF NOT EXISTS todo (
    id          varchar(255) NOT NULL,
    title       varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    created_at  date         NOT NULL,
    due_at      date         NOT NULL,
    version     INT,
    PRIMARY KEY (id)
);