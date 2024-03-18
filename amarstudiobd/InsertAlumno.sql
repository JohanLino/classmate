USE amarstudio;

/*-----Procedimiento para agregar un Alumno------------------*/
DROP PROCEDURE IF EXISTS insertarAlumno;
DELIMITER //
CREATE PROCEDURE insertarAlumno(
    IN nombre_alumno VARCHAR(50), -- 1
    IN apellido_paterno VARCHAR(50), -- 2
    IN apellido_materno VARCHAR(50),-- 3
    IN fecha_nacimiento DATE,-- 4
    IN fecha_registro DATE,-- 5
    IN correo VARCHAR(50), -- 6
    IN telefono VARCHAR(50),-- 7
    IN id_clase INT, -- 8
	OUT id_alumno INT -- 9 OUT
)
BEGIN
    DECLARE monto_pago DECIMAL(10, 2);

    -- Obtener el monto del plan asociado a la clase
    SELECT precio INTO monto_pago
    FROM plan
    WHERE idPlan = (SELECT idPlan FROM clase WHERE idClase = id_clase);

    -- Insertar el nuevo alumno
    INSERT INTO alumno (nombre, apellidoPaterno, apellidoMaterno, fechaNacimiento, fechaRegistro, correo, telefono, idClase)
    VALUES (nombre_alumno, apellido_paterno, apellido_materno, fecha_nacimiento, fecha_registro, correo, telefono, id_clase);

    -- Obtener el ID del último alumno insertado
    SET id_alumno = LAST_INSERT_ID();

    -- Insertar el registro de pago para el nuevo alumno
    INSERT INTO pago (fechaPago, monto, estatus, idAlumno)
    VALUES (CURDATE(), monto_pago, 1, id_alumno);

END //
DELIMITER ;


CALL InsertarAlumno('Eber', 'Olivares', 'Gutierrez', '2000-01-01','2024-01-01', 'correo@example.com', '123456789', 1,@idAlumno);
/*Revisar que se haya hecho lo esperado*/
SELECT * FROM alumno;
SELECT * FROM pago;

