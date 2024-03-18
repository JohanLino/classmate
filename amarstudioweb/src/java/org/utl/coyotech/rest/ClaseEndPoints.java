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
import org.utl.coyotech.controller.ControllerClase;
import org.utl.coyotech.model.Clase;
 /*
 * @author ianq
 */

//http://localhost:8080/recuperacion/api/example/insert
@Path("example")
public class ClaseEndPoints {
    
    @Path("getAllClase")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClase(){
        ControllerClase ca = new ControllerClase();
       String out="";
       ControllerClase objCE= new ControllerClase();
       List<Clase> listaClase=objCE.getAllClase();
       Gson objGson=new Gson();
       out=objGson.toJson(listaClase);
        return Response.ok(out).build();
    }
    
    @Path("insertar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(@FormParam("c") @DefaultValue("") String clase){
        Gson objGson = new Gson();
//        System.out.println("Impreso" + clase);
        Clase c = objGson.fromJson(clase, Clase.class);
        
        String out="";
        
        ControllerClase objCC= new ControllerClase();
        try{
            int idDatoGenerado=objCC.insert(c);
            out="""
                 {"result":"Guardado correcto con id %s"}
               
                """;
            out= String.format(out, idDatoGenerado);
        }catch(SQLException ex){
            System.out.println(c);
            ex.printStackTrace();
            out="""
                 {"error":"Error al insertar datos" }
               
                """;
        }
        return Response.ok(out).build();
    }
    @Path("deleteClase")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteClase(@QueryParam("idC") @DefaultValue("0") String idC){
       String out="";
        try {
            
             ControllerClase objCS=new ControllerClase();
            objCS.delete(Integer.parseInt(idC));
            out="""
                {"result":"Clase eliminada exitosamente"}
                """;
            }
        catch (SQLException ex) {
            out="""
                {"error":"Hubo un error en la eliminacion"}
                """;
        }
        return Response.ok(out).build();
    }
   @Path("reactivarClase")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response reactivarClase(@QueryParam("idC") @DefaultValue("0") String idC){
       String out="";
        try {
            
             ControllerClase objCS=new ControllerClase();
            objCS.reactivar(Integer.parseInt(idC));
            out="""
                {"result":"Clase reactivada exitosamente"}
                """;
            }
        catch (SQLException ex) {
            out="""
                {"error":"Hubo un error en la eliminacion"}
                """;
        }
        return Response.ok(out).build();
    }
    @Path("updateClase")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClase(@FormParam("c") @DefaultValue("") String clase){
        Gson objGson = new Gson();
//        System.out.println("Impreso" + clase);
        Clase c = objGson.fromJson(clase, Clase.class);
        
        String out="";
        
        ControllerClase objCC= new ControllerClase();
        try{
            int idDatoGenerado=objCC.modificar(c);
            out="""
                 {"result":"modificado correcto con id %s"}
               
                """;
            out= String.format(out, idDatoGenerado);
        }catch(SQLException ex){
            System.out.println(c);
            ex.printStackTrace();
            out="""
                 {"error":"Error al modificar datos" }
               
                """;
        }
        return Response.ok(out).build();
    }
}
