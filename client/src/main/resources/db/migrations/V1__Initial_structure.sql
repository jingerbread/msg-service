CREATE TABLE messages (
  id          BIGINT PRIMARY KEY NOT NULL,
  title       text NOT NULL,
  text        text NOT NULL,
  author      VARCHAR(250) NOT NULL,
  created     TIMESTAMP NOT NULL,
  received    TIMESTAMP NOT NULL
);