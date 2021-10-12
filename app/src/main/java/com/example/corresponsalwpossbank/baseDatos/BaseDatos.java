package com.example.corresponsalwpossbank.baseDatos;

import static com.example.corresponsalwpossbank.constantes.Constantes.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.corresponsalwpossbank.modelos.Corresponsal;

public class BaseDatos extends SQLiteOpenHelper {


    @Override
    public void onCreate(SQLiteDatabase db) {
        String querryAdmin =
                "CREATE TABLE " + TABLA_ADMIN +
                        " (" + COLUMNA_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMNA_ADMIN_CORREO + " TEXT, " +
                        COLUMNA_ADMIN_CONTRASENA + " TEXT); ";
        db.execSQL(querryAdmin);


        String querryCorresponsal =
                "CREATE TABLE " + TABLA_CORRESPONSAL +
                        " (" + COLUMNA_CORRESPONSAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMNA_CORRESPONSAL_SALDO + " TEXT, " +
                        COLUMNA_CORRESPONSAL_CUENTA + " TEXT, " +
                        COLUMNA_CORRESPONSAL_NOMBRE + " TEXT, " +
                        COLUMNA_CORRESPONSAL_APELLIDO + " TEXT, " +
                        COLUMNA_CORRESPONSAL_CEDULA + " TEXT, " +
                        COLUMNA_CORRESPONSAL_TELEFONO + " TEXT, " +
                        COLUMNA_CORRESPONSAL_DIRECCION + " TEXT, " +
                        COLUMNA_CORRESPONSAL_CORREO + " TEXT, " +
                        COLUMNA_CORRESPONSAL_CONTRASENA + " TEXT, " +
                        COLUMNA_CORRESPONSAL_ESTADO + " TEXT); ";
        db.execSQL(querryCorresponsal);

        String querryCliente =
                "CREATE TABLE " + TABLA_CLIENTE +
                        " (" + COLUMNA_CLIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMNA_CLIENTE_SALDO + " TEXT, " +
                        COLUMNA_CLIENTE_NOMBRE + " TEXT, " +
                        COLUMNA_CLIENTE_APELLIDO + " TEXT, " +
                        COLUMNA_CLIENTE_CEDULA + " TEXT, " +
                        COLUMNA_CLIENTE_PIN + " TEXT, " +
                        COLUMNA_CLIENTE_TARJETA + " TEXT, " +
                        COLUMNA_CLIENTE_TARJETA_TIPO+ " TEXT, " +
                        COLUMNA_CLIENTE_TARJETA_CVV + " TEXT, " +
                        COLUMNA_CLIENTE_TARJETA_VEN + " TEXT, " +
                        COLUMNA_CLIENTE_ESTADO + " TEXT); ";
        db.execSQL(querryCliente);

        String querryTransaccion =
                "CREATE TABLE " + TABLA_TRANSACCION +
                        " (" + COLUMNA_TRANSACCION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMNA_TRANSACCION_TIPO + " TEXT, " +
                        COLUMNA_TRANSACCION_COMISION + " TEXT, " +
                        COLUMNA_TRANSACCION_VALOR_TOTAL + " TEXT, " +
                        COLUMNA_TRANSACCION_VALOR + " TEXT, " +
                        COLUMNA_TRANSACCION_CUENTA_CORRESPONAL + " TEXT, " +
                        COLUMNA_TRANSACCION_CEDULA_REMITENTE + " TEXT, " +
                        COLUMNA_TRANSACCION_CEDULA_RECEPTOR + " TEXT, " +
                        COLUMNA_TRANSACCION_TARJETA + " TEXT, " +
                        COLUMNA_TRANSACCION_CUOTAS + " TEXT, " +
                        COLUMNA_TRANSACCION_FECHA + " TEXT, " +
                        COLUMNA_TRANSACCION_HORA + " TEXT); ";
        db.execSQL(querryTransaccion);
    }

    public BaseDatos(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_CORRESPONSAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_CLIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_TRANSACCION);

    }


}
