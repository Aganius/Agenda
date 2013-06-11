package edu.CECAR.logica;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import javax.microedition.rms.*;

public class RMSOperaciones {

    RecordStore rs;

    public RMSOperaciones() {
    }

    public boolean abrirZona(String nombreZona) {
        try {
            rs = RecordStore.openRecordStore(nombreZona, true);
            return true;
        } catch (RecordStoreException e) {
            e.toString();
            return false;
        }
    }

    public boolean adicionarRegistro(ByteArrayOutputStream baos) {
        byte[] mensaje;
        mensaje = baos.toByteArray();
        try {
            int aux = rs.addRecord(mensaje, 0, mensaje.length);
            System.out.println("" + aux);
            return true;
        } catch (RecordStoreException e) {
            e.toString();
            return false;
        }
    }

    public boolean adicionarRegistro(String valor) {
        byte[] mensaje;
        mensaje = valor.getBytes();
        try {
            rs.addRecord(mensaje, 0, mensaje.length);
            return true;
        } catch (RecordStoreException e) {
            e.toString();
            return false;
        }
    }

    public String listarRegistro() {
        byte[] reg = new byte[100];
        int tam;
        String buffer = "";
        try {
            for (int i = 1; i <= rs.getNumRecords(); i++) {
                tam = rs.getRecordSize(i);
                reg = rs.getRecord(i);
                buffer += "\n" + i + new String(reg, 0, tam);
            }
        } catch (RecordStoreException e) {
            System.out.println("Error de Lectura");
        }
        return buffer;
    }

    public Vector listarContacto(int numRegistros) throws RecordStoreException, IOException {
        byte[] reg = new byte[100];
        String[] buffer = new String[numRegistros];
        Vector datos = new Vector();
        Contacto contacto;
        RecordEnumeration re = null;
        int recordID = 0;

        try {
            re = rs.enumerateRecords(null, null, true);
            re.reset();
        } catch (RecordStoreNotOpenException ex) {
            ex.printStackTrace();
        }

        try {
            while (re.hasNextElement()) {
                recordID = re.nextRecordId();
                reg = rs.getRecord(recordID);

                error("" + recordID);
                ByteArrayInputStream bais = new ByteArrayInputStream(reg);
                DataInputStream dis = new DataInputStream(bais);
                for (int j = 0; j < numRegistros; j++) {
                    buffer[j] = dis.readUTF();
                }

                contacto = new Contacto(buffer[0], buffer[1], buffer[2], buffer[3], buffer[4], buffer[5]);
                datos.addElement(contacto);

            }
        } catch (InvalidRecordIDException ex) {
            error("" + recordID);
        }
        return datos;

//        try {
//            for (int i = 1; i <= rs.getNumRecords(); i++) {
//                reg = rs.getRecord(i);
//                ByteArrayInputStream bais = new ByteArrayInputStream(reg);
//                DataInputStream dis = new DataInputStream(bais);
//                for (int j = 0; j < numRegistros; j++) {
//                    buffer[j] = dis.readUTF();
//
//                }
//                contacto = new Contacto(buffer[0], buffer[1], buffer[2], buffer[3], buffer[4], buffer[5]);
//                datos.addElement(contacto);
//
//            }
//        } catch (RecordStoreException e) {
//            System.out.println("Error de Lectura");
//        }
//        return datos;
    }

    public Vector listarTareas() throws RecordStoreException, IOException {
        byte[] reg = new byte[100];
        String[] buffer = new String[2];
        Date fecha;
        Vector datos = new Vector();
        Tarea tarea;
        RecordEnumeration re = null;
        int recordID = 0;
        try {
            re = rs.enumerateRecords(null, null, true);
            re.reset();
        } catch (RecordStoreNotOpenException ex) {
            ex.printStackTrace();
        }
        try {
            while (re.hasNextElement()) {
                recordID = re.nextRecordId();
                reg = rs.getRecord(recordID);

                error("" + recordID);
                ByteArrayInputStream bais = new ByteArrayInputStream(reg);
                DataInputStream dis = new DataInputStream(bais);
                buffer[0] = dis.readUTF();
                buffer[1] = dis.readUTF();
                fecha = new Date(dis.readLong());

                tarea = new Tarea(buffer[0], buffer[1], fecha);
                datos.addElement(tarea);
            }
        } catch (InvalidRecordIDException ex) {
            error("" + recordID);
        }
        return datos;

    }

    public boolean eliminarContacto(Contacto contacto) throws IOException {
        boolean eliminado = false;

        byte[] reg = new byte[100];
        String[] buffer = new String[6];
        RecordEnumeration re = null;
        int recordID = 0;

        try {
            re = rs.enumerateRecords(null, null, true);
            re.reset();
        } catch (RecordStoreNotOpenException ex) {
            ex.printStackTrace();
        }

        try {
            while (re.hasNextElement()) {
                recordID = re.nextRecordId();
                reg = rs.getRecord(recordID);

                error("" + recordID);
                ByteArrayInputStream bais = new ByteArrayInputStream(reg);
                DataInputStream dis = new DataInputStream(bais);
                for (int j = 0; j < 6; j++) {
                    buffer[j] = dis.readUTF();
                }
                contacto = new Contacto(buffer[0], buffer[1], buffer[2], buffer[3], buffer[4], buffer[5]);
                if (contacto.getTelefono().equals(buffer[2]) && contacto.getCelular().equals(buffer[3])) {
                    rs.deleteRecord(recordID);
                    eliminado = true;
                }
            }
        } catch (InvalidRecordIDException ex) {
            error("" + recordID);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }

        return eliminado;

//        try {
//
//            for (int i = 1; i <= rs.getNumRecords(); i++) {
//                reg = rs.getRecord(i);
//                ByteArrayInputStream bais = new ByteArrayInputStream(reg);
//                DataInputStream dis = new DataInputStream(bais);
//                for (int j = 0; j < 6; j++) {
//                    buffer[j] = dis.readUTF();
//
//                }
//                contacto = new Contacto(buffer[0], buffer[1], buffer[2], buffer[3], buffer[4], buffer[5]);
//
//
//                if (contacto.getTelefono().equals(buffer[2]) && contacto.getCelular().equals(buffer[3])) {
//                    rs.deleteRecord(i);
//                    eliminado = true;
//                }
//            }
//        } catch (RecordStoreException e) {
//            System.out.println("Error de Lectura");
//        }
//
//        return eliminado;
    }

    public boolean eliminarTarea(Tarea tarea) throws IOException {
        boolean eliminado = false;

        byte[] reg = new byte[100];
        String[] buffer = new String[2];
        Date fecha;
        RecordEnumeration re = null;
        int recordID = 0;

        try {
            re = rs.enumerateRecords(null, null, true);
            re.reset();
        } catch (RecordStoreNotOpenException ex) {
            ex.printStackTrace();
        }

        try {
            while (re.hasNextElement()) {
                recordID = re.nextRecordId();
                reg = rs.getRecord(recordID);

                error("" + recordID);
                ByteArrayInputStream bais = new ByteArrayInputStream(reg);
                DataInputStream dis = new DataInputStream(bais);
                buffer[0] = dis.readUTF();
//                buffer[1] = dis.readUTF();
//                fecha = new Date(dis.readLong());

                if (tarea.getNombre().equals(buffer[0])) {
                    rs.deleteRecord(recordID);
                    eliminado = true;
                }
            }
        } catch (InvalidRecordIDException ex) {
            error("error" + recordID);
        } catch (RecordStoreException ex) {
            ex.printStackTrace();
        }

        return eliminado;

    }

    public String[] listarRegistro(int r, int numRegistros) {
        byte[] reg = new byte[100];
        ByteArrayInputStream bais;
        DataInputStream dis;
        String[] datos = new String[numRegistros];
        try {
            reg = rs.getRecord(r);
            bais = new ByteArrayInputStream(reg);
            dis = new DataInputStream(bais);
            datos[0] = dis.readUTF();
            datos[1] = dis.readUTF();
            datos[2] = dis.readUTF();
            datos[3] = dis.readUTF();
            datos[4] = dis.readUTF();
            datos[5] = dis.readUTF();
        } catch (RecordStoreException e) {
            System.out.println("Error de Lectura");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return datos;
    }

    private void error(String str) {
        System.err.println("Msg: " + str);
    }

    public boolean cerrarZona() {
        try {
            rs.closeRecordStore();
            return true;
        } catch (RecordStoreException e) {
            e.toString();
            return false;
        }
    }
}