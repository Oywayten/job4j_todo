ALTER TABLE task ADD COLUMN priority_id INT REFERENCES priority(id);
