--***********************************************
--****************USUARIO************************
CREATE TABLE CONFIGURACION.USUARIO(
ID NUMERIC PRIMARY KEY,
LOGIN VARCHAR(10) UNIQUE NOT NULL,
CLAVE VARCHAR(20) NOT NULL,
NOMBRE VARCHAR(100) NOT NULL,
ESTADO CHAR(1) NOT NULL CHECK (ESTADO IN ('A','I')),
USUCREA VARCHAR(10),
FECCREA DATE,
USUMOD VARCHAR(10),
FECMOD DATE
);
--===============================================