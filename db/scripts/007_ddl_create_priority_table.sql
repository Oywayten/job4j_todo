CREATE TABLE priority (
   id SERIAL PRIMARY KEY,
   name TEXT UNIQUE NOT NULL DEFAULT 'normal',
   position int
);
