/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.coyotech.rest;

import com.google.gson.Gson;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import org.utl.coyotech.controller.ControllerPlan;
import org.utl.coyotech.controller.ControladorUsuario;
import org.utl.coyotech.model.Plan;
import org.utl.coyotech.model.Usuario;

/**
 *
 * @author josue
 */
@Path("usuario")
public class UsuarioEndPoints {

    @Path("cambiar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response cambiarCredenciales(@FormParam("usuario") Usuario usuario, @FormParam("nuevoNombre") String nuevoNombre, @FormParam("nuevaContrasenia") String nuevaContrasenia) {
        ControladorUsuario controladorUsuario = new ControladorUsuario();
        String out = "";

        try {
            controladorUsuario.cambiar(usuario, nuevoNombre, nuevaContrasenia);
            out = """
                      {"result":"Usuario modificado exitosamente" }
                           """;
        } catch (Exception e) {
            out = """
                      {"error":"Error al modificar" }
                           """;
        }
        return Response.ok(out).build();
    }
}
