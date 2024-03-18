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

public class ControllerClase {

    public ArrayList<Clase> listaClases(ResultSet rs) throws SQLException {
        ArrayList<Clase> clases = new ArrayList<>();
        while (rs.next()) {
            Plan plan = new Plan();
            plan.setIdPlan(rs.getInt("idPlan"));
            plan.setNombrePlan(rs.getString("nombrePlan"));
            plan.setPrecioPlan(rs.getDouble("precio"));
            plan.setCapacidad(rs.getInt("capacidad"));
            plan.setDescripcion(rs.getString("descripcion"));
            plan.setEstatus(rs.getInt("estatusPlan"));

            Clase clase = new Clase();
            clase.setIdClase(rs.getInt("idClase"));
            clase.setNombreClase(rs.getString("nombreClase"));
            clase.setDía(rs.getString("dia"));
            clase.setHoraInicio(rs.getString("horaInicio"));
            clase.setHoraFin(rs.getString("horaFin"));
            clase.setEstatus(rs.getInt("estatusClase"));
            clase.setPlan(plan);

            clases.add(clase);
        }
        return clases;
    }

    public ArrayList<Clase> getAllClase() {
        MySQLConnection conMySQL = new MySQLConnection();
        ArrayList<Clase> clases = new ArrayList<>();
        String query = "SELECT * FROM view_alumnos";
        try {

            Connection conn = conMySQL.open();
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            clases = listaClases(rs);
            rs.close();
            conn.close();
            conMySQL.close();
            System.out.println("ControllerClase.getAll:" + clases.toString());
            return clases ;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("ControllerClase.getAll: algo salió mal...");
        return null;
    }
    
    public ArrayList<Clase> getAllHor() {
        ArrayList<Clase> listahorario = new ArrayList<>();
        try {
            //1) Crear la setencia SQL
            String query = "SELECT * FROM v_horario";
            //2) Se establece la conexion con la base de datos
        MySQLConnection conMySQL = new MySQLConnection();
            //3) Se abre la conexion y devuelve un tipo conexion
            Connection conn = conMySQL.open();
            //4) Se genera el statement para enviar la consulta
            PreparedStatement pstm = conn.prepareStatement(query);
            //5.Se prepara un ResultSet para obtener la respuesta de la BD
            ResultSet rs = pstm.executeQuery();
            //6) Recorrer el rs y extraer los datos

            while (rs.next()) {
                Clase c = new Clase();
                c.setNombreClase(rs.getString("nombreClase"));
                c.setDía(rs.getString("diaClase"));
                c.setHoraInicio(rs.getString("horaInicio"));
                c.setHoraFin(rs.getString("horaFin"));
                c.setEstatus(rs.getInt("estatusClase"));

                listahorario.add(c);
            }
            //7) Cerrar todos los objetos
            rs.close();
            pstm.close();
            conn.close();
            conMySQL.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //8) Devolver la informacion
        return listahorario;
    }

   

    public void delete(int idClase) throws SQLException {
        //1.- Crear la sentencia SQL
        String query = "UPDATE clase SET estatus=0 WHERE idClase=" + idClase + ";";
        //2.- Crear un objeto para la conexion con mySql
        MySQLConnection conMySQL = new MySQLConnection();
        //3. Se abre la conexion
        Connection conn = conMySQL.open();
        //4.- Crear un statement para enviar la query
        Statement stmt = conn.createStatement();
        //5.- Ejecutar la sentencia
        stmt.execute(query);
        //6.- Cerrar los objetos
        stmt.close();
        conn.close();
        conMySQL.close();
    }

    public void reactivar(int idClase) throws SQLException {
        //1.- Crear la sentencia SQL
        String query = "UPDATE clase SET estatus=1 WHERE idClase=" + idClase + ";";
        //2.- Crear un objeto para la conexion con mySql
        MySQLConnection conMySQL = new MySQLConnection();
        //3. Se abre la conexion
        Connection conn = conMySQL.open();
        //4.- Crear un statement para enviar la query
        Statement stmt = conn.createStatement();
        //5.- Ejecutar la sentencia
        stmt.execute(query);
        //6.- Cerrar los objetos
        stmt.close();
        conn.close();
        conMySQL.close();
    }

    public boolean verificarHorario(Clase c) throws SQLException {
        // Suponiendo que tengas una conexión establecida a tu base de datos
        boolean dato = false;
        String query = "SELECT * FROM clase WHERE dia = ? AND (horaInicio <= ? AND horaFin >= ?)";
        MySQLConnection conMySQL = new MySQLConnection();
        //3.- Se abre la conexion
        Connection conn = conMySQL.open();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, c.getDía());
        statement.setString(2, c.getHoraFin());
        statement.setString(3, c.getHoraInicio());

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            // Hay una superposición, no se puede registrar la nueva clase
            dato = true;
            return dato;
        } else {
            // No hay superposiciones, se puede registrar la nueva clase
            // Insertar la nueva clase en la base de datos
            return dato;
        }

    }

    public int insert(Clase c) throws SQLException {
        //1.- Generar la sentencia SQL
        String query = "{call insertarClase(?,?,?,?,?,?,?,?,?,?,?,?}";
        //2.- Crear la conexion a la BD
        MySQLConnection conMySQL = new MySQLConnection();
        //3.- Se abre la conexion
        Connection conn = conMySQL.open();
        //4.- Crear un statement que llevara la consulta   prepareStatement, Statement y calleblestatement
        CallableStatement cstm = conn.prepareCall(query);
        //5.- Llenar todos los parametros de la llamada al procedure
        if (verificarHorario(c) == false) {
        cstm.setString(1, c.getPlan().getNombrePlan());
        cstm.setDouble(2, c.getPlan().getPrecioPlan());
        cstm.setInt(3, c.getPlan().getCapacidad());
        cstm.setString(4, c.getPlan().getDescripcion());
        cstm.setInt(5, c.getPlan().getEstatus());

        cstm.setString(6, c.getNombreClase());
        cstm.setString(7, c.getDía());
        cstm.setString(8, c.getHoraInicio());
        cstm.setString(9, c.getHoraFin());
        cstm.setInt(10, c.getEstatus());

        cstm.registerOutParameter(11, Types.INTEGER);
        cstm.registerOutParameter(12, Types.INTEGER);
        
            //6.- Ejecutar la sentencia
            cstm.execute();
            //7.- Obtener todos los parametros de retorno
            c.getPlan().setIdPlan(cstm.getInt(11));
            c.setIdClase(cstm.getInt(12));
            //8.- Cerrar los objetos

        }
       
        cstm.close();
        conn.close();
        conMySQL.close();
        return c.getIdClase();
    }

    public int modificar(Clase c) throws SQLException {
        // Generar la sentencia SQL
        String query = "{call modificarClase(?,?,?,?,?,?,?,?,?,?,?,?)}";

        // Crear la conexión a la BD
        MySQLConnection conMySQL = new MySQLConnection();
        Connection conn = conMySQL.open();
        CallableStatement cstm = conn.prepareCall(query);

        // Llenar todos los parámetros de la llamada al procedimiento
        if(verificarHorario(c)==false){
        cstm.setString(1, c.getPlan().getNombrePlan());
        cstm.setDouble(2, c.getPlan().getPrecioPlan());
        cstm.setInt(3, c.getPlan().getCapacidad());
        cstm.setString(4, c.getPlan().getDescripcion());
        cstm.setInt(5, c.getPlan().getEstatus());

        cstm.setString(6, c.getNombreClase());
        cstm.setString(7, c.getDía());

        cstm.setString(8, c.getHoraInicio());
        cstm.setString(9, c.getHoraFin());
        cstm.setInt(10, c.getEstatus());

        cstm.setInt(11, c.getPlan().getIdPlan());
        cstm.setInt(12, c.getIdClase());

        cstm.execute();
        }
        // Cerrar los objetos
        cstm.close();
        conn.close();
        conMySQL.close();
        // Devolver el ID del empleado
        return c.getIdClase();
    }


}
