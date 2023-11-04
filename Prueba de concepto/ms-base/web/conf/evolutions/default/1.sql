# CEIBA schema

# --- !Ups

CREATE TABLE USUARIO(
    ID  INT NOT NULL,
    NOMBRE varchar(255) NOT NULL,
    CLAVE varchar(255) NOT NULL,
    FECHACREACION date NOT NULL,
    PRIMARY KEY (ID)
);

# --- !Downs

DROP TABLE USERS;
