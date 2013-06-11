/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.CECAR.logica;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Johan.Quintana
 */
public class Tarea {

    private String nombre;
    private String descripcion;
    private Date fecha;

    public Tarea(String nombre, String descripcion, Date fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public Tarea() {
        this.nombre = "";
        this.descripcion = "";
        this.fecha = new Date();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getStringFecha(boolean fechaCompleta) {

        String stringFecha = "";
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(this.fecha);
        stringFecha = calendario.get(Calendar.DATE) + "-" + (calendario.get(Calendar.MONTH) + 1) + "-" + calendario.get(Calendar.YEAR);
        if (fechaCompleta) {
            stringFecha += " " + calendario.get(Calendar.HOUR) + ":" + calendario.get(Calendar.MINUTE);
        }
        return stringFecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
