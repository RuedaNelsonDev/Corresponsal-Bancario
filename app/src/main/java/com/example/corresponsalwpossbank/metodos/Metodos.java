package com.example.corresponsalwpossbank.metodos;

import static com.example.corresponsalwpossbank.constantes.Constantes.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.example.corresponsalwpossbank.baseDatos.BaseDatos;
import com.example.corresponsalwpossbank.modelos.Administrador;
import com.example.corresponsalwpossbank.modelos.Cliente;
import com.example.corresponsalwpossbank.modelos.Corresponsal;
import com.example.corresponsalwpossbank.modelos.SharedPreference;
import com.example.corresponsalwpossbank.modelos.Transaccion;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class Metodos extends BaseDatos {
    Context context;
    Date date = new Date();


    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^*&+=])" +
                    "(?=\\S+$)" +
                    ".{8,20}" +
                    "$");

    public Metodos(Context context) {
        super(context);
    }


    /**
     * agregarCorresponsal > registra los datos en la TABLA CORRESPONSAL
     */
    public boolean agregarCorresponsal(Corresponsal corresponsal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMNA_CORRESPONSAL_SALDO, corresponsal.getSaldo());
        cv.put(COLUMNA_CORRESPONSAL_CUENTA, corresponsal.getCuenta());
        cv.put(COLUMNA_CORRESPONSAL_NOMBRE, corresponsal.getNombre());
        cv.put(COLUMNA_CORRESPONSAL_APELLIDO, corresponsal.getApellido());
        cv.put(COLUMNA_CORRESPONSAL_CEDULA, corresponsal.getCedula());
        cv.put(COLUMNA_CORRESPONSAL_TELEFONO, corresponsal.getTelefono());
        cv.put(COLUMNA_CORRESPONSAL_DIRECCION, corresponsal.getDireccion());
        cv.put(COLUMNA_CORRESPONSAL_CORREO, corresponsal.getCorreo());
        cv.put(COLUMNA_CORRESPONSAL_CONTRASENA, corresponsal.getContrasena());
        cv.put(COLUMNA_CORRESPONSAL_ESTADO, corresponsal.getEstado());

        long result = db.insert(TABLA_CORRESPONSAL, null, cv);

        if (result != -1) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * validarCorresponsal > valida en la TABLA CORRESPONSAL que el correo y la contraseña esten registrados
     */
    public boolean validarCorresponsal(Corresponsal corresponsal) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLA_CORRESPONSAL + " WHERE " + COLUMNA_CORRESPONSAL_CORREO + " = '" + corresponsal.getCorreo() +
                "' and " + COLUMNA_CORRESPONSAL_CONTRASENA + " = '" + corresponsal.getContrasena() + "' ";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

    /**
     * validarCorreoCorresponsal > valida en la TABLA CORRESPONSAL si el correo se encuentra registrado
     */
    public boolean validarCorreoCorresponsal(Corresponsal corresponsal) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLA_CORRESPONSAL + " WHERE " + COLUMNA_CORRESPONSAL_CORREO + " = '" + corresponsal.getCorreo() + "' ";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

    /**
     * leerCorresponsal > lee los corresponsales que hay en la TABLA CORRESPONSAL
     */
    public ArrayList<Corresponsal> leerCorresponsal() {
        ArrayList<Corresponsal> lista = new ArrayList<>();
        String query =
                "SELECT * FROM " + TABLA_CORRESPONSAL;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                Corresponsal corresponsal = new Corresponsal();
                corresponsal.setId(cursor.getString(0));
                corresponsal.setSaldo(cursor.getString(1));
                corresponsal.setCuenta(cursor.getString(2));
                corresponsal.setNombre(cursor.getString(3));
                corresponsal.setApellido(cursor.getString(4));
                corresponsal.setCedula(cursor.getString(5));
                corresponsal.setTelefono(cursor.getString(6));
                corresponsal.setDireccion(cursor.getString(7));
                corresponsal.setCorreo(cursor.getString(8));
                corresponsal.setContrasena(cursor.getString(9));
                corresponsal.setEstado(cursor.getString(10));

                lista.add(corresponsal);
            }
        }
        return lista;
    }

    /**
     * leerEstadoPorCorreo > lee por el correo el Estado de los corresponsales en la TABLA USUARIO
     */
    public Corresponsal leerEstadoPorCorreo(Corresponsal corresponsal) {

        String query =
                "SELECT * FROM " + TABLA_CORRESPONSAL + " WHERE " + COLUMNA_CORRESPONSAL_CORREO + " = '" + corresponsal.getCorreo() + "' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {

                corresponsal.setEstado(cursor.getString(10));
            }
        }
        return corresponsal;
    }

    /**
     * leerCorresponsalPorCorreo > lee por el correo  los datos del corresponsal de la TABLA CORRESPONSAL
     */
    public Corresponsal leerCorresponsalPorCorreo(Corresponsal corresponsal) {

        String query =
                "SELECT * FROM " + TABLA_CORRESPONSAL + " WHERE " + COLUMNA_CORRESPONSAL_CORREO + " = '" + corresponsal.getCorreo() + "' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                corresponsal.setId(cursor.getString(0));
                corresponsal.setSaldo(cursor.getString(1));
                corresponsal.setCuenta(cursor.getString(2));
                corresponsal.setNombre(cursor.getString(3));
                corresponsal.setApellido(cursor.getString(4));
                corresponsal.setCedula(cursor.getString(5));
                corresponsal.setCorreo(cursor.getString(8));
                corresponsal.setContrasena(cursor.getString(9));
                corresponsal.setEstado(cursor.getString(10));
            }
        }
        return corresponsal;
    }

    /**
     * validarCedulaCorresponsal > valida en la TABLA CORRESPONSAL si la cedula se encuentra registrado
     */
    public boolean validarCedulaCorresponsal(Corresponsal corresponsal) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLA_CORRESPONSAL + " WHERE " + COLUMNA_CORRESPONSAL_CEDULA + " = '" + corresponsal.getCedula() + "' ";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

    /**
     * actualizarEstadoCorresponsal > actualiza en la COLUMNA CORRESPONSAL ESTADO de la TABLA CORRESPONSAL
     */
    public boolean actualizarEstadoCorresponsal(Corresponsal corresponsal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COLUMNA_CORRESPONSAL_ESTADO, corresponsal.getEstado());

        long result = db.update(TABLA_CORRESPONSAL, cv, "corresponsal_id=?", new String[]{corresponsal.getId()});
        if (result == -1) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * actualizarSaldoCorresponsal > actualiza el saldo en la TABLA CORRESPONSAL
     */
    public boolean actualizarSaldoCorresponsal(Corresponsal corresponsal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMNA_CORRESPONSAL_SALDO, corresponsal.getSaldo());

        long result = db.update(TABLA_CORRESPONSAL, cv, "corresponsal_id=?", new String[]{corresponsal.getId()});
        if (result == -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * actualizarContrasenaCorresponsal > actualiza la contraseña en la TABLA CORRESPONSAL
     */
    public boolean actualizarContrasenaCorresponsal(Corresponsal corresponsal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMNA_CORRESPONSAL_CONTRASENA, corresponsal.getContrasena());

        long result = db.update(TABLA_CORRESPONSAL, cv, "corresponsal_correo=?", new String[]{corresponsal.getCorreo()});
        if (result == -1) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * actualizarEstadoCliente > actualiza en la COLUMNA CLIENTE ESTADO de la TABLA CLIENTE
     */
    public boolean actualizarEstadoCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COLUMNA_CLIENTE_ESTADO, cliente.getEstado());

        long result = db.update(TABLA_CLIENTE, cv, "cliente_id=?", new String[]{cliente.getId()});
        if (result == -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * agregarAdmin > registra los datos en la TABLA ADMINISTRADOR
     */
    public boolean agregarAdmin(Administrador admin) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMNA_ADMIN_CORREO, admin.getCorreoAdmin());
        cv.put(COLUMNA_ADMIN_CONTRASENA, admin.getContrasenaAdmin());

        long result = db.insert(TABLA_ADMIN, null, cv);

        if (result != -1) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * validarAdmin > valida en la TABLA ADMIN que el correo y la contraseña esten registrados
     */
    public boolean validarAdmin(Administrador admin) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMNA_ADMIN_CORREO, admin.getCorreoAdmin());
        cv.put(COLUMNA_ADMIN_CONTRASENA, admin.getContrasenaAdmin());


        String query = "SELECT * FROM " + TABLA_ADMIN + " WHERE " + COLUMNA_ADMIN_CORREO + " = '" + admin.getCorreoAdmin() +
                "' and " + COLUMNA_ADMIN_CONTRASENA + " = '" + admin.getContrasenaAdmin() + "' ";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }


    /**
     * agregarCliente > registra los datos en la TABLA CLIENTE
     */
    public boolean agregarCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMNA_CLIENTE_NOMBRE, cliente.getNombre());
        cv.put(COLUMNA_CLIENTE_APELLIDO, cliente.getApellido());
        cv.put(COLUMNA_CLIENTE_CEDULA, cliente.getCedula());
        cv.put(COLUMNA_CLIENTE_PIN, cliente.getPin());
        cv.put(COLUMNA_CLIENTE_SALDO, cliente.getSaldo());
        cv.put(COLUMNA_CLIENTE_TARJETA, cliente.getTarjeta());
        cv.put(COLUMNA_CLIENTE_TARJETA_TIPO, cliente.getTarjetaTipo());
        cv.put(COLUMNA_CLIENTE_TARJETA_CVV, cliente.getCvv());
        cv.put(COLUMNA_CLIENTE_TARJETA_VEN, cliente.getVen());
        cv.put(COLUMNA_CLIENTE_ESTADO, cliente.getEstado());

        long result = db.insert(TABLA_CLIENTE, null, cv);

        if (result != -1) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * leerCliente > lee los clientes que hay en la TABLA CLIENTE
     */
    public ArrayList<Cliente> leerCliente() {
        ArrayList<Cliente> lista = new ArrayList<>();
        String query =
                "SELECT * FROM " + TABLA_CLIENTE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                Cliente cliente = new Cliente();

                cliente.setId(cursor.getString(0));
                cliente.setSaldo(cursor.getString(1));
                cliente.setNombre(cursor.getString(2));
                cliente.setApellido(cursor.getString(3));
                cliente.setCedula(cursor.getString(4));
                cliente.setPin(cursor.getString(5));
                cliente.setTarjeta(cursor.getString(6));
                cliente.setTarjetaTipo(cursor.getString(7));
                cliente.setCvv(cursor.getString(8));
                cliente.setVen(cursor.getString(9));
                cliente.setEstado(cursor.getString(10));

                lista.add(cliente);
            }
        }
        return lista;
    }

    /**
     * validarCedulaCliente > valida en la TABLA CLIENTE si la cedula se encuentra registrado
     */
    public boolean validarCedulaCliente(Cliente cliente) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLA_CLIENTE + " WHERE " + COLUMNA_CLIENTE_CEDULA + " = '" + cliente.getCedula() + "' ";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }
    /**
     * validarCedulaCliente > valida en la TABLA CLIENTE si la cedula se encuentra registrado
     */
    public boolean validarCedulaPinCliente(Cliente cliente) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLA_CLIENTE + " WHERE " + COLUMNA_CLIENTE_CEDULA + " = '" + cliente.getCedula()
                + "' and " + COLUMNA_CLIENTE_PIN + " = '" + cliente.getPin() + "' ";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

    /**
     * validarCedulaCliente2 > valida en la TABLA CLIENTE si la cedula se encuentra registrado
     */
    public boolean validarCedulaCliente2(Transaccion transaccion) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLA_CLIENTE + " WHERE " + COLUMNA_CLIENTE_CEDULA + " = '" + transaccion.getCedulaReceptor() + "' ";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }


    /**
     * validarTarjetaCliente > valida en la TABLA CLIENTE si la tarjeta se encuentra registrada
     */
    public boolean validarTarjetaCliente(Cliente cliente) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLA_CLIENTE + " WHERE " + COLUMNA_CLIENTE_TARJETA + " = '" + cliente.getTarjeta()
                + "' and " + COLUMNA_CLIENTE_TARJETA_CVV + " = '" + cliente.getCvv()
                + "' and " + COLUMNA_CLIENTE_TARJETA_VEN + " = '" + cliente.getVen()
                + "' and " + COLUMNA_CLIENTE_NOMBRE + " = '" + cliente.getNombre()
                + "' and " + COLUMNA_CLIENTE_APELLIDO + " = '" + cliente.getApellido() + "' ";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

    /**
     * validarDepositoCliente > valida en la TABLA CLIENTE si la cedula se encuentra registrado
     */
    public boolean validarDepositoCliente(Cliente cliente) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLA_CLIENTE + " WHERE " + COLUMNA_CLIENTE_CEDULA + " = '" + cliente.getCedula()
                + "' and " + COLUMNA_CLIENTE_PIN + " = '" + cliente.getPin() + "' ";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }

    /**
     * validarPin > valida en la TABLA CLIENTE que el Pin sea el correcto.
     */
    public boolean validarPin(Cliente cliente) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMNA_CLIENTE_PIN, cliente.getPin());

        String query = "SELECT * FROM " + TABLA_CLIENTE + " WHERE " + COLUMNA_CLIENTE_PIN + " = '" + cliente.getPin() + "' ";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            return true;
        }
        return false;
    }


    /**
     * leerClientePorCedula > lee por la cedula el Estado del cliente en la TABLA CLIENTE
     */
    public Cliente leerClientePorCedula(Cliente cliente) {

        String query =
                "SELECT * FROM " + TABLA_CLIENTE + " WHERE " + COLUMNA_CLIENTE_CEDULA + " = '" + cliente.getCedula() + "' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                cliente.setId(cursor.getString(0));
                cliente.setSaldo(cursor.getString(1));
                cliente.setNombre(cursor.getString(2));
                cliente.setApellido(cursor.getString(3));
                cliente.setEstado(cursor.getString(10));
            }
        }
        return cliente;
    }

    /**
     * leerClientePorTarjeta > lee por la tarjeta el Estado del cliente en la TABLA CLIENTE
     */
    public Cliente leerClientePorTarjeta(Cliente cliente) {

        String query =
                "SELECT * FROM " + TABLA_CLIENTE + " WHERE " + COLUMNA_CLIENTE_TARJETA + " = '" + cliente.getTarjeta() + "' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                cliente.setId(cursor.getString(0));
                cliente.setSaldo(cursor.getString(1));
                cliente.setCedula(cursor.getString(4));
                cliente.setEstado(cursor.getString(10));
            }
        }
        return cliente;
    }

    /**
     * actualizarSaldoCliente > actualiza el saldo en la TABLA CLIENTE
     */
    public boolean actualizarSaldoCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMNA_CLIENTE_SALDO, cliente.getSaldo());

        long result = db.update(TABLA_CLIENTE, cv, "cliente_id=?", new String[]{cliente.getId()});
        if (result == -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * CREAR TARJETA CLIENTE
     */

    /**
     * inicialTarjeta > genera un número random entre 3 al 6
     */
    public static String inicialTarjeta() {

        int numero = (int) (3 + Math.round(Math.random() * 3));
        return "" + numero;
    }

    /**
     * sumarDerecha > añade un digito  repetidamente segun haga falta para completar un determinado número de caracteres
     */
    public static String sumarDerecha(String s, int len, int c) {
        s = s.trim();
        StringBuilder d = new StringBuilder(len);
        int fill = len - s.length();
        d.append(s);
        while (fill-- > 0)
            d.append(c);
        return d.toString();

    }

    /**
     * sumarDerecha > genera un número random menor que 1000
     */
    public static final String cvv() {

        long numeroRamdom = (long) (1000L * Math.random());
        return "" + numeroRamdom;
    }

    /**
     * ven > valida si el correo cumple con los caracteres necesarios
     * int mes, genera un número random entre 1-12
     * int ano, genera un número random entre 22 y 8 números mas osea hasta 30
     */
    public static String ven() {
        int mes = (int) (Math.random() * 12) + 1;
        int ano = (int) (22 + Math.round(Math.random() * 8));
        return "" + mes + "/" + ano;
    }


    /**
     * agregarTransaccion > registra los datos en la TABLA TRANSACCIÓN
     */
    public boolean agregarTransaccion(Transaccion transaccion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMNA_TRANSACCION_TIPO, transaccion.getTipo());
        cv.put(COLUMNA_TRANSACCION_COMISION, transaccion.getComision());
        cv.put(COLUMNA_TRANSACCION_VALOR_TOTAL, transaccion.getValorTotal());
        cv.put(COLUMNA_TRANSACCION_VALOR, transaccion.getValor());
        cv.put(COLUMNA_TRANSACCION_CUENTA_CORRESPONAL, transaccion.getCuentaCorresponsal());
        cv.put(COLUMNA_TRANSACCION_CEDULA_REMITENTE, transaccion.getCedulaRemitente());
        cv.put(COLUMNA_TRANSACCION_CEDULA_RECEPTOR, transaccion.getCedulaReceptor());
        cv.put(COLUMNA_TRANSACCION_TARJETA, transaccion.getTarjeta());
        cv.put(COLUMNA_TRANSACCION_CUOTAS, transaccion.getCuotas());
        cv.put(COLUMNA_TRANSACCION_FECHA, transaccion.getFecha());
        cv.put(COLUMNA_TRANSACCION_HORA, transaccion.getHora());

        long result = db.insert(TABLA_TRANSACCION, null, cv);

        if (result != -1) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * leerCorresponsal > lee los corresponsales que hay en la TABLA CORRESPONSAL
     */
    public ArrayList<Transaccion> leerTransaccion() {
        ArrayList<Transaccion> lista = new ArrayList<>();
        String query =
                "SELECT * FROM " + TABLA_TRANSACCION;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                Transaccion transaccion = new Transaccion();
                transaccion.setId(cursor.getString(0));
                transaccion.setTipo(cursor.getString(1));
                transaccion.setComision(cursor.getString(2));
                transaccion.setValorTotal(cursor.getString(3));
                transaccion.setValor(cursor.getString(4));
                transaccion.setCuentaCorresponsal(cursor.getString(5));
                transaccion.setCedulaRemitente(cursor.getString(6));
                transaccion.setCedulaReceptor(cursor.getString(7));
                transaccion.setTarjeta(cursor.getString(8));
                transaccion.setCuotas(cursor.getString(9));
                transaccion.setFecha(cursor.getString(10));
                transaccion.setHora(cursor.getString(11));

                lista.add(transaccion);
            }
        }
        return lista;
    }

    /**
     * validarCaracteresCorreo > valida si el correo cumple con los caracteres necesarios
     */
    public boolean validarCaracteresCorreo(TextInputEditText regisCorreo) {
        String loginInput = regisCorreo.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(loginInput).matches()) {
            regisCorreo.setError("correo invalido");
            return false;
        } else {
            return true;
        }
    }

    /**
     * validarCaracteresContrasena > valida si la contraseña cumple con los caracteres necesarios
     */
    public boolean validarCaracteresContrasena(TextInputEditText regisContrasena) {
        String contrasenaInput = regisContrasena.getText().toString().trim();

        if (contrasenaInput.isEmpty()) {
            regisContrasena.setError("El campo no puede estar vacío");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(contrasenaInput).matches()) {
            regisContrasena.setError("La contraseña es demasiado débil");
            return false;
        } else {
            return true;
        }
    }
    public String fechaPrestamo() {
        Date date = new Date();
        String formatoFecha = "yyyy-MM-dd";

        String fecha = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatoFecha);
            fecha = format.format(date);
        } catch (Exception exception) {
            Log.e("ERROR", exception.toString());
        }

        return fecha;

    }
    public void hora ( Transaccion transaccion){
        SimpleDateFormat h = new SimpleDateFormat("h:mm:a");
        String hora = h.format(date);
        transaccion.setHora(hora);
    }
}
