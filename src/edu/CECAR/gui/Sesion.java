package edu.CECAR.gui;

import java.io.IOException;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;

public class Sesion extends Form implements CommandListener {

    private Command comandoSesion;
    private TextField textFiedUsuario;
    private TextField textFieldPassword;
    private List listaOpciones;
    private Display display;
    private Image imagen;
    private Command comandoMenu = new Command("Atras", Command.EXIT, 0);

    public Sesion(TextField textFiedUsuario, TextField textFieldPassword, Command comandoSesion, Display display, List listaOpciones) {
        super("Sesión");
        this.comandoSesion = comandoSesion;
        this.listaOpciones = listaOpciones;
        this.display = display;

        try {
            imagen = Image.createImage("/img/logo.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        textFiedUsuario = new TextField("Usuario:", "", 20,
                TextField.ANY);
        textFieldPassword = new TextField("Password:", "", 20,
                TextField.PASSWORD);


        this.textFiedUsuario = textFiedUsuario;
        this.textFieldPassword = textFieldPassword;

        append(textFiedUsuario);
        append(textFieldPassword);
        append(imagen);

        addCommand(comandoSesion);
        addCommand(comandoMenu);

        setCommandListener(this);


    }

    public void mostrarMensaje() {
        Alert success = new Alert("Sesión Iniciada");
        textFiedUsuario.setString("");
        textFieldPassword.setString("");
        display.setCurrent(success, listaOpciones);
    }

    public void reintentar() {
        Alert error = new Alert("Información Incorrecta");
        error.setTimeout(300);
        textFiedUsuario.setString("");
        textFieldPassword.setString("");
        display.setCurrent(error, this);
    }

    public void validarUsuario(String name, String password) {
        if (name.equals("Johan") && password.equals("1234")) {
            mostrarMensaje();
        } else {
            reintentar();
        }
    }

    public void commandAction(Command c, Displayable d) {

        if (c == comandoSesion) {

            validarUsuario(textFiedUsuario.getString(),
                    textFieldPassword.getString());

        }

    }
}