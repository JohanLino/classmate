/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.utl.coyotech.controller;

import org.utl.coyotech.model.Usuario;

/**
 *
 * @author josue
 */
public class ControladorUsuario {
     // Cambia el nombre de usuario y la contraseña de un usuario
    public void cambiar(Usuario usuario, String nuevoNombre, String nuevaContrasenia) {
        if (usuario != null) {
            // Cambiar el nombre de usuario y la contraseña
            usuario.setNombreUsuario(nuevoNombre);
            usuario.setContrasenia(nuevaContrasenia);
            System.out.println("Credenciales cambiadas: " + usuario.getNombreUsuario());
        } else {
            System.out.println("Error: usuario nulo.");
        }
    }
}
