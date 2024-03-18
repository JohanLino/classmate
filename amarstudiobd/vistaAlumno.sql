USE amarstudio;

DROP VIEW IF EXISTS view_alumnos;
CREATE VIEW  view_alumnos AS 
SELECT  idAlumno, nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, fechaRegistro, correo, telefono, a.estatus,
		c.idClase, nombreClase, dia, horaInicio, horaFin,
        p.idPlan, precio, capacidad, descripcion
FROM alumno a JOIN clase c ON  a.idClase=c.idClase JOIN plan p ON c.idPlan = p.idPlan;

SELECT * FROM view_alumnos;


