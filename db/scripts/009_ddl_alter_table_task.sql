ALTER TABLE task ADD COLUMN priority_id int REFERENCES priority(id);
