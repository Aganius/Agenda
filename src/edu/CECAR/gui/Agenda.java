package edu.CECAR.gui;

import java.io.IOException;
import java.util.Date;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class Agenda extends MIDlet implements CommandListener {
        
	private final Command comandoSalir = new Command("Salir", Command.EXIT, 0);
	private final Command comandoSeleccionar = new Command("Seleccionar", Command.ITEM, 0);
	private final Command comandoSesion = new Command("Sesion", Command.ITEM, 0);
	private final Sesion sesion;
	protected static Agenda ejemploMenu;

	private List listaOpciones;
	private Display display;
	
	private TextField textFiedUsuario;
	private TextField textFieldPassword;

	
	
	public Agenda() {
		
		configurarLista();

                
		listaOpciones.addCommand(comandoSalir);
		listaOpciones.addCommand(comandoSeleccionar);
		display = Display.getDisplay(this);

		ejemploMenu = this;
		sesion = new Sesion(textFiedUsuario, textFieldPassword, comandoSesion, display, listaOpciones);
                

	}

	protected void destroyApp(boolean unconditional)
			throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		
		display.setCurrent(sesion);

	}

	private void configurarLista() {

		listaOpciones = new List("Seleccionar Opciones", Choice.IMPLICIT);
		listaOpciones.setCommandListener(this);
		listaOpciones.append("Contactos", null);
		listaOpciones.append("Tareas", null);
		listaOpciones.append("Conversión de Monedas", null);               
		
	}

	public void commandAction(Command c, Displayable d) {
		// TODO Auto-generated method stub

		if (c == comandoSalir) {
			notifyDestroyed();
		} else if ((d == listaOpciones && c == List.SELECT_COMMAND)
				|| c == comandoSeleccionar) {
			if (listaOpciones.getSelectedIndex() == 0) {
				Contactos contactos = new Contactos(display, listaOpciones);
				display.setCurrent(contactos);
			} else if (listaOpciones.getSelectedIndex() == 1) {
				Tareas tareas = new Tareas(display, listaOpciones);
				display.setCurrent(tareas);
			} else if (listaOpciones.getSelectedIndex() == 2) {
				Conversiones conversiones = new Conversiones(display, listaOpciones);
				display.setCurrent(conversiones);
			}

		}

	}
	
}
