/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CECAR.logica;

/**
 *
 * @author Johan.Quintana
 */
public class Contacto {

    private String nombres;
    private String apellidos;
    private String telefono;
    private String celular;
    private String email;
    private String twitter;

    public Contacto(String nombres, String apellidos, String telefono, String celular, String email, String twitter) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
        this.twitter = twitter;
    }

    public Contacto() {
    }
    
    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    
    
}
