DROP TABLE IF EXISTS task;

CREATE TABLE task (
    id          SERIAL PRIMARY KEY,
    description TEXT NOT NULL ,
    created     TIMESTAMP NOT NULL DEFAULT current_timestamp,
    done        BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT description_created UNIQUE (description, created)
);