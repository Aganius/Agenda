package edu.CECAR.gui;

import edu.CECAR.logica.Contacto;
import edu.CECAR.logica.RMSOperaciones;
import edu.CECAR.logica.Tarea;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;

public class Tareas extends Form implements CommandListener {

    private Display display;
    private List listaOpciones;
    private Command comandoMenu = new Command("Atras", Command.EXIT, 0);
    private Command comandoGuardar = new Command("Guardar", Command.ITEM, 0);
    private Command comandoBuscar = new Command("Consultar", Command.ITEM, 0);
    private Command comandoEliminar = new Command("Eliminar", Command.ITEM, 0);
    private Command comandoWipe = new Command("Wipe Data", Command.ITEM, 0);
    private TextField textFiedNombre;
    private TextField textFiedDescripcion;
    private DateField dateFieldFecha;
    private Date date = new Date();

    public Tareas(Display display, List listaOpciones) {
        super("Tareas");

        this.listaOpciones = listaOpciones;
        this.display = display;


        textFiedNombre = new TextField("Nombre", "", 40, 0);
        textFiedDescripcion = new TextField("Descripción", "", 40, 0);
        dateFieldFecha = new DateField("Fecha", DateField.DATE_TIME);
        dateFieldFecha.setDate(date);


        append(textFiedNombre);
        append(textFiedDescripcion);
        append(dateFieldFecha);

        addCommand(comandoMenu);
        addCommand(comandoGuardar);
        addCommand(comandoBuscar);
        addCommand(comandoEliminar);
        addCommand(comandoWipe);
        setCommandListener(this);
    }

    public void guardarTarea(Tarea tarea) throws RecordStoreException {

        RMSOperaciones rmso = new RMSOperaciones();
        rmso.abrirZona("Tareas");
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeUTF(tarea.getNombre());
            dos.writeUTF(tarea.getDescripcion());
            dos.writeLong((tarea.getFecha().getTime()));
            dos.close();
            rmso.adicionarRegistro(baos);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        Alert alerta = new Alert("Mensaje", "Se ha guardado exitosamente", null,
                AlertType.CONFIRMATION);
        alerta.setTimeout(1000);
        display.setCurrent(alerta, this);
        textFiedNombre.setString("");
        textFiedDescripcion.setString("");
        dateFieldFecha.setDate(date);
    }

    public void buscarTarea() throws IOException {

        RMSOperaciones rmso = new RMSOperaciones();
        rmso.abrirZona("Tareas");
        Tarea aux;
        Vector vector = null;
        try {
            vector = rmso.listarTareas();
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        boolean encontrado = false;

        for (int i = 0; i < vector.size() && !encontrado; i++) {
            aux = (Tarea) vector.elementAt(i);
            if (aux.getNombre().equals(textFiedNombre.getString())) {

                textFiedDescripcion.setString(aux.getDescripcion());
                dateFieldFecha.setDate(aux.getFecha());
                encontrado = true;
                if (!encontrado) {
                    Alert alerta = new Alert("Mensaje", "Contacto no Encontrado", null,
                            AlertType.CONFIRMATION);
                    alerta.setTimeout(1000);
                    display.setCurrent(alerta, this);
                }
            }

        }
    }

    public void eliminarTarea() throws IOException {
        Tarea tarea = new Tarea();

        tarea.setNombre(textFiedNombre.getString());
        RMSOperaciones rmso = new RMSOperaciones();
        rmso.abrirZona("Tareas");
        rmso.eliminarTarea(tarea);


    }
    public void eliminarRecordStore() {
        try {
            RMSOperaciones rmso = new RMSOperaciones();
            rmso.abrirZona("Tareas");

            rmso.cerrarZona();
            RecordStore.deleteRecordStore("Contactos");
        } catch (RecordStoreNotFoundException e) {
            error("no se encuentra el record store");
        } catch (RecordStoreException e) {
            error("Algo pasó");
        }
    }

    private void error(String str) {
        System.err.println("Msg: " + str);
    }
    
    public void commandAction(Command c, Displayable d) {

        if (c == comandoMenu) {
            display.setCurrent(listaOpciones);
        } else {
            if (c == comandoGuardar) {
                try {
                    guardarTarea(new Tarea(
                            textFiedNombre.getString(),
                            textFiedDescripcion.getString(),
                            dateFieldFecha.getDate()));
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }
            } else if (c == comandoBuscar) {
                try {
                    buscarTarea();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
             else if (c == comandoEliminar) {
                try {
                    eliminarTarea();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (c == comandoWipe) {
                eliminarRecordStore();


            }
        }
    }
}