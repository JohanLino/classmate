package org.utl.coyotech.controller;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.utl.coyotech.model.Alumno;
import org.utl.coyotech.model.Clase;

/**
 *
 * @author Coyotech
 */
public class AlumnoPruebas {

    public static void main(String[] args) {

        //probarGetAll()
        //probarBusquedaAlumno();
        //probarInactivar();
        //probarActivar();
        //

    }

    public static void probarInsertar() {

        ControllerAlumno ca = new ControllerAlumno();
        Clase c = new Clase();
        c.setIdClase(1);
        Alumno a = new Alumno("Johan", "Lino", "Moreno", "2002-07-18", "2024-01-01", "joha.antonio25@gmail.com", "4771256903", 1, c);

        try {
            ca.addAlumno(a);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void probarModifyAlumno() {
        ControllerAlumno ca = new ControllerAlumno();
        Clase c = new Clase();
        c.setIdClase(1);
        Alumno a = new Alumno(14, "Johan", "Lino", "Moreno", "2002-07-18", "2024-01-01", "johan.antonio25@gmail.com", "4771256903", 1, c);

        try {
            ca.modifyAlumno(a);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void probarGetAll() {
        ControllerAlumno ca = new ControllerAlumno();
        ca.getAllAlumnos();

    }

    public static void probarInactivar() {
        ControllerAlumno ca = new ControllerAlumno();
        System.out.println(ca.inactiveAlumno(1));
    }

    public static void probarActivar() {
        ControllerAlumno ca = new ControllerAlumno();
        System.out.println(ca.activarAlumno(1));
    }

    public static void probarBusquedaAlumno() {
        ControllerAlumno ca = new ControllerAlumno();
        ca.buscarAlumno("Or");
    }
}
