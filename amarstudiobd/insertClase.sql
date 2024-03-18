DROP PROCEDURE IF NOT EXISTS insertarClase
DELIMITER //
CREATE PROCEDURE insertarClase
(
	IN nombre_clase VARCHAR(50), -- 1
    IN dia_clase VARCHAR(10),-- 2
    IN horaInicio_clase TIME,-- 3
    IN horaFin_clase TIME,-- 4
    IN estatus_clase INT,-- 5
    IN idPlan_clase INT,-- 6
    OUT	idClase_clase INT-- 7
)
BEGIN
	INSERT INTO clase(nombreClase,dia,horaInicio,horaFin,idPlan)
			VALUES(nombre_clase,dia_clase,horaInicio_clase,horaFin_clase,estatus_clase,idPlan_clase);
END

DELIMITER //