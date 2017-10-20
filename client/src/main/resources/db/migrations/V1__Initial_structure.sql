CREATE TABLE messages (
  id                 BIGINT PRIMARY KEY NOT NULL,
  title              text NOT NULL,
  text               text NOT NULL,
  author             VARCHAR(250) NOT NULL,
  created            TIMESTAMP NOT NULL,
  server_received    TIMESTAMP NOT NULL,
  client_received    TIMESTAMP NOT NULL
);

CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1 ;