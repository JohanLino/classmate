/*
Base de datos para la gestion de un profesor de clases particulares(nombre chido pendiente)
AMARSTUDIO V2
CREADA POR: COYOTECH
FECHA:15/02/2024
ACTUALIZADO POR: JOHAN LINO
ACTULIZACION:13/03/2024

Nota de actualizacion: Se modificó la capacidad del campo Dia en la tabla Clase
*/


DROP DATABASE IF EXISTS amarstudio;
/*Fue nombrado en honor a la escuela de canto de donde se sacó la idea para el proyecto*/
CREATE DATABASE IF NOT EXISTS amarstudio;
USE amarstudio;

/*En la tabla usuario unicamente existirá un regsitro par gesionar el sistema ya que será de uso personal, por ello tampoco se tiene
 relación con otras tablas*/
CREATE TABLE IF NOT EXISTS usuario(
idUsuario INT PRIMARY KEY AUTO_INCREMENT,
nombreUsuario VARCHAR(25) NOT NULL,
contrasenia VARCHAR(255),
token VARCHAR(255) NULL
);

/*Almacena los distintos planes o modalidade disponibles a elgir en la escula
ej: INDIVIDUAL, GRUPAL O PAREJA, se relacionará con grupo ya que cada clase tendrá una modalidad establecida*/
CREATE TABLE IF NOT EXISTS plan(
idPlan INT PRIMARY KEY AUTO_INCREMENT,
nombrePlan VARCHAR(50) NOT NULL,
precio DECIMAL(10,2) NOT NULL,
capacidad INT NOT NULL,
descripcion VARCHAR(255) NOT NULL DEFAULT "Sin descripcion",
estatus INT NOT NULL DEFAULT 1 /*0=INACTIVO 1=ACTIVO*/
);


/*Almacena las clases existentes del usuario, se concetará con el alumno para identificar a que clase está asistiendo y apartir del idClase poder conectar
al idPlan para poder saber el cobro correspondiente ya*/
CREATE TABLE IF NOT EXISTS clase(
idClase INT PRIMARY KEY AUTO_INCREMENT,
nombreClase VARCHAR(50) NOT NULL,
dia VARCHAR(9) NOT NULL,
horaInicio TIME NOT NULL, /* Formato datos time 'HH:MM:SS'*/
horaFin TIME NOT NULL,
estatus INT NOT NULL DEFAULT 1, /*0=INACTIVO 1=ACTIVO*/
idPlan INT NOT NULL,
CONSTRAINT fk_idPlan_plan
FOREIGN KEY (idPlan) 
REFERENCES plan(idPlan)
ON DELETE CASCADE
ON UPDATE CASCADE
);

/*Almacena los registros de los alumnos, se conectará con pago para identificar que pago le corresponde a cada alumno */
CREATE TABLE IF NOT EXISTS alumno(
idAlumno INT PRIMARY KEY AUTO_INCREMENT,
nombre VARCHAR(50) NOT NULL,
apellidoPaterno VARCHAR(50) NOT NULL,
apellidoMaterno VARCHAR(50) NOT NULL,
fechaNacimiento DATE NOT NULL, /*"YYYY-MM-DD"*/
fechaRegistro DATE NOT NULL,
correo VARCHAR(50) NOT NULL,
telefono VARCHAR(50) NOT NULL,
estatus INT NOT NULL DEFAULT 1,
idClase INT NOT NULL,
CONSTRAINT fk_idClase_clase
FOREIGN KEY (idClase)
REFERENCES clase(idClase)
ON DELETE CASCADE
ON UPDATE CASCADE
);

/*Almacena el pago mensual de cada alumno
la logica detrás de la siguiente tabla es la siguiente
al insertar un alumno nuevo se generará un registro en la tabla pago relacionado con el estatus en realiazdo, 
al pasar la fecha de cobro establecida el registro actualizara el estado a pendiente hasta que el alumno paga y se actulizará la el estatus a realizado y le fecha a la
fecha en la que se realizó el pago para asi tener una mejor evidencia de los pagos
*/
CREATE TABLE IF NOT EXISTS pago(
idPago INT PRIMARY KEY AUTO_INCREMENT,
fechaPago DATE NOT NULL,
monto DECIMAL(10,2) NOT NULL,
estatus INT NOT NULL DEFAULT 1, /*0=PENDIENTE 1=REALIZADO */
idAlumno INT NOT NULL,
CONSTRAINT fk_idAlumno_alumno
FOREIGN KEY (idAlumno) 
REFERENCES alumno(idAlumno)
ON DELETE CASCADE
ON UPDATE CASCADE
);

/*Sugerencia: Tabla pago establecer un campo donde se espesifiqué en 
vencimiento de cada alumno, por ahora el estatus de cada Alumno se actualizara a pendiente al iniciar el mes*/
