# --- !Ups

CREATE TABLE candidatos (
                              id int NOT NULL AUTO_INCREMENT,
                              nombre varchar(255),
                              partido varchar(255),
                              PRIMARY KEY (id)
)
CREATE TABLE votos (
                         id int NOT NULL AUTO_INCREMENT,
                         cedula varchar(10),
                         id_candidato int,
                         PRIMARY KEY (id)
)

INSERT INTO candidatos (nombre, partido) VALUES ('Juan', 'Partido 1');
INSERT INTO candidatos (nombre, partido) VALUES ('Pedro', 'Partido 2');
INSERT INTO candidatos (nombre, partido) VALUES ('Maria', 'Partido 3');
INSERT INTO candidatos (nombre, partido) VALUES ('Luis', 'Partido 4');

INSERT INTO votos (cedula, id_candidato) VALUES ('1234567890', 1);
INSERT INTO votos (cedula, id_candidato) VALUES ('1234567891', 1);
INSERT INTO votos (cedula, id_candidato) VALUES ('1234567892', 3);
INSERT INTO votos (cedula, id_candidato) VALUES ('1234567893', 2);
INSERT INTO votos (cedula, id_candidato) VALUES ('1234567894', 2);


# --- !Downs

DROP TABLE candidatos;
DROP TABLE votos;
