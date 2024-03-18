package org.utl.coyotech.controller;

import java.sql.CallableStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.utl.coyotech.bd.MySQLConnection;
import org.utl.coyotech.model.Plan;

/**
 *
 * @author josue
 */
public class ControllerPlan {
    // Otros métodos de la clase

    public int insertarPlan(Plan plan) throws SQLException {
        String query = "{CALL insertarPlan(?, ?, ?, ?, ?, ?)}";
        MySQLConnection conMySQL = new MySQLConnection();
        Connection conn = conMySQL.open();
        CallableStatement cstm = conn.prepareCall(query);
        cstm.setString(1, plan.getNombrePlan());
        cstm.setDouble(2, plan.getPrecioPlan());
        cstm.setInt(3, plan.getCapacidad());
        cstm.setString(4, plan.getDescripcion());
        cstm.setInt(5, plan.getEstatus());
        cstm.registerOutParameter(6, java.sql.Types.INTEGER);
        cstm.executeUpdate();
        int idPlan = cstm.getInt(6);
        cstm.close();
        conn.close();
        conMySQL.close();
        return idPlan;
    }

    public int modificar(Plan plan) throws SQLException {
        // Generar la sentencia SQL
        String query = "{call modificarPlan(?,?,?,?,?,?,?)}";    //2.- Crear la conexion a la BD
        MySQLConnection conMySQL = new MySQLConnection();
        //3.- Se abre la conexion
        Connection conn = conMySQL.open();
        //4.- Crear un statement que llevara la consulta
        CallableStatement cstm = conn.prepareCall(query);
        //5.- Llenar todos los parametros de la llamada al procedure
        cstm.setInt(1, plan.getIdPlan());
        cstm.setString(2, plan.getNombrePlan());
        cstm.setDouble(3, plan.getPrecioPlan());
        cstm.setInt(4, plan.getCapacidad());
        cstm.setString(5, plan.getDescripcion());
        cstm.setInt(6, plan.getEstatus());
        //7.- Ejecutar la sentencia
        cstm.execute();
        //8.- Cerrar los objetos
        cstm.close();
        conn.close();
        conMySQL.close();
        return plan.getIdPlan();
    }

    public void eliminarPlan(int idPlan) throws SQLException {
        String query = "UPDATE plan SET estatus = 1 WHERE idPlan = " + idPlan;
        MySQLConnection conMySQL = new MySQLConnection();
        Connection conn = conMySQL.open();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(query);
        stmt.close();
        conn.close();
        conMySQL.close();
    }

    public List<Plan> getAll() throws SQLException {
        List<Plan> listaPlanes = new ArrayList<>();
        String query = "SELECT * FROM plan";
        MySQLConnection conMySQL = new MySQLConnection();
        Connection conn = conMySQL.open();
        PreparedStatement pstm = conn.prepareStatement(query);
        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            Plan plan = new Plan(
                    rs.getInt("idPlan"),
                    rs.getString("nombrePlan"),
                    rs.getDouble("precioPlan"),
                    rs.getInt("capacidad"),
                    rs.getString("descripcion"),
                    rs.getInt("estatus")
            );
            listaPlanes.add(plan);
        }
        rs.close();
        pstm.close();
        conn.close();
        conMySQL.close();
        return listaPlanes;
    }
}
