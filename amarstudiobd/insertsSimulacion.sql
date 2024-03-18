/*
Datos precargados para probar funciones
*/

USE amarstudio;

/*Insert de Plan*/
INSERT INTO plan(nombrePlan,precio,capacidad,descripcion)
			VALUES ("Individual", 1600.00, 1, "Clase con atencion personalizada a un unico alumno");

INSERT INTO plan(nombrePlan,precio,capacidad,descripcion)
			VALUES ("Doble", 1200.00, 2, "Clase para 2 Alumnos simultaneamente");
            
INSERT INTO plan(nombrePlan,precio,capacidad,descripcion)
			VALUES ("Grupal", 800.00, 8, "Clase grupal");

/*Insert de Clase*/

INSERT INTO clase(nombreClase,dia,horaInicio,horaFin,idPlan)
			VALUES("CI LEONARDO SANCHEZ","Lunes",'10:00:00','11:00:00',1);
            
INSERT INTO clase(nombreClase,dia,horaInicio,horaFin,idPlan)
			VALUES("CI_DISPONIBLE","Lunes",'11:00:00','12:00:00',1);
            
INSERT INTO clase(nombreClase,dia,horaInicio,horaFin,idPlan)
			VALUES("CD_EberYOrlando","Martes",'12:00:00','13:00:00',2);

INSERT INTO clase(nombreClase,dia,horaInicio,horaFin,idPlan)
			VALUES("CG_Mi1213","Miercoles",'12:00:00','13:00:00',3);

/*Insert de Alumno*/
INSERT INTO alumno(nombre,apellidoPaterno,apellidoMaterno,fechaNacimiento,fechaRegistro,correo,telefono,idClase)
			VALUES("Leonardo","Sanchez","Martinez","2002/08/29","2023/01/01","leo@gmail.com","4771234568",1);

INSERT INTO alumno(nombre,apellidoPaterno,apellidoMaterno,fechaNacimiento,fechaRegistro,correo,telefono,idClase)
			VALUES("Eber","Olivarez","Gutierrez","2002/11/28","2023/02/01","eber@gmail.com","4777896814",2);

INSERT INTO alumno(nombre,apellidoPaterno,apellidoMaterno,fechaNacimiento,fechaRegistro,correo,telefono,idClase)
			VALUES("Orlando","Nuñez","Camargo","2002/05/28","2023/02/02","orlando@gmail.com","4777897814",2);

INSERT INTO alumno(nombre,apellidoPaterno,apellidoMaterno,fechaNacimiento,fechaRegistro,correo,telefono,idClase)
			VALUES("Eber","Olivarez","Gutierrez","2002/11/28","2023/02/01","eber@gmail.com","4777896814",3)


            
