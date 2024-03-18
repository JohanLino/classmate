package org.utl.coyotech.controller;

import org.utl.coyotech.bd.MySQLConnection;
import org.utl.coyotech.model.Alumno;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.locks.StampedLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.utl.coyotech.model.Clase;
import org.utl.coyotech.model.Plan;

/**
 *
 * @author Coyotech
 */
public class ControllerAlumno {

    //metodo para insertar un nuevo alumno
    public int addAlumno(Alumno a) throws SQLException {
        //Se crea objeto para la conexion con la base de datos
        MySQLConnection conMySQL = new MySQLConnection();
        //Se crea la query que llamara al procedure para insertar un nuevo Alumno y lo relaciona con un pago;
        String insertQuery = "{call insertarAlumno(?,?,?,?,?,?,?,?,?)}";
        // Consulta para obtener el último ID insertado
        String idQuery = "SELECT LAST_INSERT_ID() AS id";
        int idAlumno = 0;
        try (Connection conn = conMySQL.open(); CallableStatement cstm = conn.prepareCall(insertQuery); Statement stmt = conn.createStatement()) {
            // Se indica en que posicion toma cada atributo en el procedure
            cstm.setString(1, a.getNombre());
            cstm.setString(2, a.getApellidoPaterno());
            cstm.setString(3, a.getApellidoMaterno());
            cstm.setString(4, a.getFechaNacimiento());
            cstm.setString(5, a.getFechaRegistro());
            cstm.setString(6, a.getCorreo());
            cstm.setString(7, a.getTelefono());
            cstm.setInt(8, a.getClase().getIdClase());
            //El 9 es un paramtro de salida
            cstm.registerOutParameter(9, Types.INTEGER);
            //Se ejecuta el el procedure
            cstm.execute();

            ResultSet rs = stmt.executeQuery(idQuery);
            if (rs.next()) {
                a.setIdAlumno(rs.getInt("id"));
            }
        }
        System.out.println("ControllerAlumno.addAlumno" + a.toString());
        return a.getIdAlumno();
    }

    //Metodo para actualizar los datos de un alumno
    public int modifyAlumno(Alumno a) throws SQLException {
        // Se crea la conexion con la base de datos
        MySQLConnection conMySQL = new MySQLConnection();
        //Query que permite modificar, desde el front se va hara lo necesario para que solo lleguen datos validos
        String modifyQuery = "UPDATE alumno SET nombre = '%s', apellidoPaterno = '%s', apellidoMaterno = '%s', fechaNacimiento = '%s', correo = '%s' , telefono = '%s' , idClase = '%d' WHERE idAlumno ='%d' ;";
        //Remplazo de valores
        modifyQuery = String.format(modifyQuery, a.getNombre(), a.getApellidoPaterno(), a.getApellidoMaterno(), a.getFechaNacimiento(), a.getCorreo(), a.getTelefono(), a.getClase().getIdClase(), a.getIdAlumno());
        Connection conn = conMySQL.open();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(modifyQuery);
        //Verificar resultado
        System.out.println("AlumnoController.modify" + a.toString());
        return a.getIdAlumno();
    }

    //Metodo para poner un estatus inactivo a un Alumno 
    public boolean inactiveAlumno(int idAlumno) {
        MySQLConnection conMySQL = new MySQLConnection();
        try {
            String query = "UPDATE alumno SET estatus = 0  WHERE idAlumno =" + idAlumno + ";";
            Connection conn = conMySQL.open();
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            stmt.close();
            conn.close();
            conMySQL.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //Metodo para volver a activar un alumno
    public boolean activarAlumno(int idAlumno) {
        MySQLConnection conMySQL = new MySQLConnection();
        try {
            String query = "UPDATE alumno SET estatus = 1  WHERE idAlumno =" + idAlumno + ";";
            Connection conn = conMySQL.open();
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            stmt.close();
            conn.close();
            conMySQL.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public ArrayList<Alumno> listaAlumnos(ResultSet rs) throws SQLException {
        ArrayList<Alumno> alumnos = new ArrayList<>();
        while (rs.next()) {
            Plan plan = new Plan();
            plan.setPrecioPlan(rs.getDouble("precio"));
            Clase clase = new Clase();
            clase.setNombreClase(rs.getString("nombreClase"));
            clase.setPlan(plan);
            Alumno alumno = new Alumno(rs.getInt("idAlumno"), rs.getString("nombre"), rs.getNString("apellidoPaterno"), rs.getString("apellidoMaterno"), rs.getString("fechaNacimiento"), rs.getString("fechaRegistro"), rs.getString("correo"), rs.getString("telefono"), rs.getInt("estatus"), clase);
            alumnos.add(alumno);
        }
        return alumnos;
    }

    public ArrayList<Alumno> getAllAlumnos() {
        MySQLConnection conMySQL = new MySQLConnection();
        ArrayList<Alumno> alumnos = new ArrayList<>();
        String query = "SELECT * FROM view_alumnos";
        try {

            Connection conn = conMySQL.open();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            alumnos = listaAlumnos(rs);
            rs.close();
            conn.close();
            conMySQL.close();
            System.out.println("ControllerAlumno.getAll:" + alumnos.toString());
            return alumnos;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("ControllerAlumno.getAll: algo salió mal...");
        return null;
    }

    public ArrayList<Alumno> buscarAlumno(String parametro) {
        try {
            ArrayList<Alumno> alumnos = new ArrayList<>();
            MySQLConnection conMySQL = new MySQLConnection();
            String query = "SELECT * FROM view_Alumnos WHERE "
                    + "nombre LIKE ? OR "
                    + "apellidoPaterno LIKE ? OR "
                    + "apellidoMaterno LIKE ? OR "
                    + "correo LIKE ? OR "
                    + "telefono LIKE ?;";
            Connection conn = conMySQL.open();
            PreparedStatement pstmt = conn.prepareCall(query);
            for (int i = 1; i <= 5; i++) {
                pstmt.setString(i, "%" + parametro + "%");
            }

            ResultSet rs = pstmt.executeQuery();
            alumnos = listaAlumnos(rs);
            System.out.println("ControllerAlumno.buscarAlumno:" + alumnos.toString());
            return alumnos;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("ControllerAlumno.buscarAlumno: algo salió mal...");
        return null;
    }

}
