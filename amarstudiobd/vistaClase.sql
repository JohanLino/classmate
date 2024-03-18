
DROP VIEW IF EXISTS view_clases;
CREATE VIEW view_clases AS
SELECT idClase, nombreClase, dia, horaInicio, horaFin, c.estatus AS estatusClase, p.idPlan, nombrePlan, precio, capacidad, descripcion, p.estatus AS estatusPlan
FROM clase c JOIN plan p ON c.idPlan = p.idPlan ORDER BY
    CASE c.dia
        WHEN 'Lunes' THEN 1
        WHEN 'Martes' THEN 2
        WHEN 'Miércoles' THEN 3
        WHEN 'Jueves' THEN 4
        WHEN 'Viernes' THEN 5
        WHEN 'Sábado' THEN 6
        ELSE 7
    END,
    c.horaInicio; 

SELECT * FROM view_clases;