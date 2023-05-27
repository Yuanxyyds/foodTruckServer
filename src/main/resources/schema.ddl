DROP TABLE IF EXISTS UserPassword;

CREATE TABLE UserPassword
(
    uid      INTEGER PRIMARY KEY,
    password VARCHAR(20) UNIQUE NOT NULL,
    nickname VARCHAR(20) NOT NULL
);


