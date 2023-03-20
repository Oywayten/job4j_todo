DROP TABLE IF EXISTS task;

CREATE TABLE task (
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(35)      NOT NULL,
    description TEXT      NOT NULL,
    created     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    done        BOOLEAN   NOT NULL DEFAULT FALSE,
    CONSTRAINT description_created UNIQUE (description, created)
);