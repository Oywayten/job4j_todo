CREATE TABLE priority (
   id SERIAL PRIMARY KEY,
   name TEXT UNIQUE NOT NULL,
   position int
);
