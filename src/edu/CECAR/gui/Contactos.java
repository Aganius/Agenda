package edu.CECAR.gui;

import edu.CECAR.logica.Contacto;
import edu.CECAR.logica.RMSOperaciones;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;

public class Contactos extends Form implements CommandListener {

    private Display display;
    private List listaOpciones;
    private Command comandoMenu = new Command("Atras", Command.EXIT, 0);
    private Command comandoGuardar = new Command("Guardar", Command.ITEM, 0);
    private Command comandoBuscar = new Command("Consultar", Command.ITEM, 0);
    private Command comandoEliminar = new Command("Eliminar", Command.ITEM, 0);
    private Command comandoWipe = new Command("Wipe Data", Command.ITEM, 0);
    private RecordStore rs;
    private TextField textFiedNombres;
    private TextField textFiedApellidos;
    private TextField textFiedTelefono;
    private TextField textFiedCelular;
    private TextField textFiedEmail;
    private TextField textFiedTwitter;

    public Contactos(Display display, List listaOpciones) {
        super("Contactos");

        this.listaOpciones = listaOpciones;
        this.display = display;

        textFiedNombres = new TextField("Nombres", "", 20, 0);
        textFiedApellidos = new TextField("Apellidos", "", 20, 0);
        textFiedTelefono = new TextField("Teléfono", "", 20, 0);
        textFiedCelular = new TextField("Celular", "", 20, 0);
        textFiedEmail = new TextField("Email", "", 20, 0);
        textFiedTwitter = new TextField("Twitter", "", 20, 0);

        append(textFiedNombres);
        append(textFiedApellidos);
        append(textFiedTelefono);
        append(textFiedCelular);
        append(textFiedEmail);
        append(textFiedTwitter);

        addCommand(comandoMenu);
        addCommand(comandoGuardar);
        addCommand(comandoBuscar);
        addCommand(comandoEliminar);
        addCommand(comandoWipe);
        setCommandListener(this);

    }

    public void guardarContacto(Contacto contacto) throws RecordStoreException {

        RMSOperaciones rmso = new RMSOperaciones();
        rmso.abrirZona("Contactos");
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeUTF(contacto.getNombres());
            dos.writeUTF(contacto.getApellidos());
            dos.writeUTF(contacto.getCelular());
            dos.writeUTF(contacto.getTelefono());
            dos.writeUTF(contacto.getEmail());
            dos.writeUTF(contacto.getTwitter());
            dos.close();
            rmso.adicionarRegistro(baos);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        Alert alerta = new Alert("Mensaje", "Se ha guardado exitosamente", null,
                AlertType.CONFIRMATION);
        alerta.setTimeout(1000);
        display.setCurrent(alerta, this);
        textFiedNombres.setString("");
        textFiedApellidos.setString("");
        textFiedTelefono.setString("");
        textFiedCelular.setString("");
        textFiedEmail.setString("");
        textFiedTwitter.setString("");
    }

    public void buscarContacto() throws IOException {

        RMSOperaciones rmso = new RMSOperaciones();
        rmso.abrirZona("Contactos");

//        System.out.println(rmso.listarRegistro());

        Contacto aux;
        Vector vector = null;
        try {
            vector = rmso.listarContacto(6);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }
        boolean encontrado = false;

        for (int i = 0; i < vector.size() && !encontrado; i++) {
            aux = (Contacto) vector.elementAt(i);
            if (aux.getCelular().equals(textFiedCelular.getString())) {

                textFiedNombres.setString(aux.getNombres());
                textFiedApellidos.setString(aux.getApellidos());
                textFiedTelefono.setString(aux.getTelefono());
                textFiedCelular.setString(aux.getCelular());
                textFiedEmail.setString(aux.getEmail());
                textFiedTwitter.setString(aux.getTwitter());
                encontrado = true;
                if (!encontrado) {
                    Alert alerta = new Alert("Mensaje", "Contacto no Encontrado", null,
                            AlertType.CONFIRMATION);
                    alerta.setTimeout(1000);
                    display.setCurrent(alerta, this);
                }
            } else if (!encontrado) {
                Alert alerta = new Alert("Mensaje", "Contacto no Encontrado", null,
                        AlertType.CONFIRMATION);
                alerta.setTimeout(1000);
                display.setCurrent(alerta, this);
            }

        }
    }

    public void eliminarContacto() throws IOException {
        Contacto contacto = new Contacto(
                textFiedNombres.getString(),
                textFiedApellidos.getString(),
                textFiedTelefono.getString(),
                textFiedCelular.getString(),
                textFiedEmail.getString(),
                textFiedTwitter.getString());
        RMSOperaciones rmso = new RMSOperaciones();
        rmso.abrirZona("Contactos");
        rmso.eliminarContacto(contacto);


    }

    public void eliminarRecordStore() {
        try {
            RMSOperaciones rmso = new RMSOperaciones();
            rmso.abrirZona("Contactos");

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
                    guardarContacto(new Contacto(
                            textFiedNombres.getString(),
                            textFiedApellidos.getString(),
                            textFiedTelefono.getString(),
                            textFiedCelular.getString(),
                            textFiedEmail.getString(),
                            textFiedTwitter.getString()));
                } catch (RecordStoreException ex) {
                    ex.printStackTrace();
                }
            } else if (c == comandoBuscar) {
                try {
                    buscarContacto();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (c == comandoEliminar) {
                try {
                    eliminarContacto();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else if (c == comandoWipe) {
                eliminarRecordStore();
            }
        }


    }
}