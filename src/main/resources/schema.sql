DROP TABLE IF EXISTS plataformas;

CREATE TABLE plataformas (
    id              BIGINT          NOT NULL, AUTO_INCREMENT,
    nombre          VARCHAR(100)    NOT NULL,
    manufacturador  VARCHAR(100),

    CONSTRAINT pk_plataformas PRIMARY KEY (id),
    CONSTRAINT uq_plataforma_nombre UNIQUE (nombre)
);