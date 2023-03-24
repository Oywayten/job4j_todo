CREATE TABLE todo_user (
    id       SERIAL PRIMARY KEY,
    name     TEXT NOT NULL,
    login    TEXT NOT NULL UNIQUE ,
    password TEXT NOT NULL
);