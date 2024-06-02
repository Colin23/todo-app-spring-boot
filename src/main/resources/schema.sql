/* This query creates the To-Do table if it doesn't already exists. */
CREATE TABLE IF NOT EXISTS todo (
    id                  int             GENERATED ALWAYS AS IDENTITY    PRIMARY KEY, -- PK, not null, auto generated, integer
    todo_title          varchar(100)    NOT NULL,
    todo_description    varchar(255),
    todo_created_at     timestamp       NOT NULL    DEFAULT CURRENT_TIMESTAMP, -- This should not be edited by the user and should also be handled by the application.
    todo_due_at         timestamp,
    todo_completed      boolean         NOT NULL    DEFAULT false
);

/* Makes querying for completed To-Dos faster, as only this field has to be checked instead of the whole table. */
CREATE INDEX IF NOT EXISTS idx_todo_completed ON todo (todo_completed);
