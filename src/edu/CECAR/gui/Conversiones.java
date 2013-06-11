package edu.CECAR.gui;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStore;
import edu.CECAR.logica.TablaConversion;

public class Conversiones extends Form implements CommandListener {

    private Display display;
    private List listaOpciones;
    private Command comandoMenu = new Command("Atras", Command.EXIT, 0);
    private Command comandoConvertir = new Command("Convertir", Command.ITEM, 0);
    private RecordStore rs;
    private TextField textFiedMonedaOrigen;
    private TextField textFiedMonedaDestino;
    private ChoiceGroup listaOrigen;
    private ChoiceGroup listaDestino;

    public Conversiones(Display display, List listaOpciones) {
        super("Conversiones");

        this.listaOpciones = listaOpciones;
        this.display = display;

        listaOrigen = new ChoiceGroup("Divisa de Origen", Choice.EXCLUSIVE);
        listaOrigen.append("COP", null);
        listaOrigen.append("USD", null);
        listaOrigen.append("EUR", null);
        textFiedMonedaOrigen = new TextField("Moneda Origen", "", 20, 0);
        listaDestino = new ChoiceGroup("Divisa Destino", ChoiceGroup.EXCLUSIVE);
        listaDestino.append("COP", null);
        listaDestino.append("USD", null);
        listaDestino.append("EUR", null);
        textFiedMonedaDestino = new TextField("Moneda Destino", "", 20, 0);


        append(listaOrigen);
        append(textFiedMonedaOrigen);
        append(listaDestino);
        append(textFiedMonedaDestino);


        addCommand(comandoMenu);
        addCommand(comandoConvertir);
        setCommandListener(this);

    }

    private void convertirDivisas() {
        if (listaOrigen.isSelected(0)) {
            if (listaDestino.isSelected(0)) {
                textFiedMonedaDestino.setString(textFiedMonedaOrigen.getString());
            } else if (listaDestino.isSelected(1)) {
                textFiedMonedaDestino.setString("" + TablaConversion.pesoADolar(Float.valueOf(textFiedMonedaOrigen.getString()).floatValue(), 0.00053F));
            } else if (listaDestino.isSelected(2)) {
                textFiedMonedaDestino.setString("" + TablaConversion.pesoAEuro(Float.valueOf(textFiedMonedaOrigen.getString()).floatValue(), 0.00040F));
            }
        } else if (listaOrigen.isSelected(1)) {
            if (listaDestino.isSelected(0)) {
                textFiedMonedaDestino.setString("" + TablaConversion.dolarAPeso(Float.valueOf(textFiedMonedaOrigen.getString()).floatValue(), 1899.15F));
            } else if (listaDestino.isSelected(1)) {
                textFiedMonedaDestino.setString(textFiedMonedaOrigen.getString());
            } else if (listaDestino.isSelected(2)) {
                textFiedMonedaDestino.setString("" + TablaConversion.dolarAEuro(Float.valueOf(textFiedMonedaOrigen.getString()).floatValue(), 0.75655F));
            }
        } else if (listaOrigen.isSelected(2)) {
            if (listaDestino.isSelected(0)) {
                textFiedMonedaDestino.setString("" + TablaConversion.euroAPeso(Float.valueOf(textFiedMonedaOrigen.getString()).floatValue(), 2506.22F));
            } else if (listaDestino.isSelected(1)) {
                textFiedMonedaDestino.setString("" + TablaConversion.euroADolar(Float.valueOf(textFiedMonedaOrigen.getString()).floatValue(), 1.31422F));
            } else if (listaDestino.isSelected(2)) {
                textFiedMonedaDestino.setString(textFiedMonedaOrigen.getString());
            }
        }
    }

    private void error(String str) {
        System.err.println("Msg: " + str);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == comandoMenu) {
            display.setCurrent(listaOpciones);
        } else {
            if (c == comandoConvertir) {
                convertirDivisas();
            }
        }


    }
}