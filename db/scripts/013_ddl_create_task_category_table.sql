CREATE TABLE task_category (
    task_id     INT NOT NULL REFERENCES task (id),
    category_id INT NOT NULL REFERENCES category (id)
);

