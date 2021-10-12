package com.example.corresponsalwpossbank.constantes;

public class Constantes {
    public static final String DATABASE_NOMBRE = "wpossBank.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLA_ADMIN = "admin";
    public static final String COLUMNA_ADMIN_ID = "admin_id";
    public static final String COLUMNA_ADMIN_CORREO = "admin_correo";
    public static final String COLUMNA_ADMIN_CONTRASENA = "admin_contrasena";

    public static final String TABLA_CORRESPONSAL = "corresponsal";
    public static final String COLUMNA_CORRESPONSAL_ID = "corresponsal_id";
    public static final String COLUMNA_CORRESPONSAL_SALDO = ("corresponsal_saldo");
    public static final String COLUMNA_CORRESPONSAL_CUENTA = ("corresponsal_cuenta");
    public static final String COLUMNA_CORRESPONSAL_NOMBRE = "corresponsal_nombre";
    public static final String COLUMNA_CORRESPONSAL_APELLIDO = "corresponsal_apellido";
    public static final String COLUMNA_CORRESPONSAL_CEDULA = "corresponsal_cedula";
    public static final String COLUMNA_CORRESPONSAL_TELEFONO = "corresponsal_telefono";
    public static final String COLUMNA_CORRESPONSAL_DIRECCION = "corresponsal_direccion";
    public static final String COLUMNA_CORRESPONSAL_CORREO = "corresponsal_correo";
    public static final String COLUMNA_CORRESPONSAL_CONTRASENA = "corresponsal_contrasena";
    public static final String COLUMNA_CORRESPONSAL_ESTADO = "corresponsal_estado";

    public static final String TABLA_CLIENTE = "cliente";
    public static final String COLUMNA_CLIENTE_ID = "cliente_id";
    public static final String COLUMNA_CLIENTE_SALDO = "cliente_saldo";
    public static final String COLUMNA_CLIENTE_NOMBRE = "cliente_nombre";
    public static final String COLUMNA_CLIENTE_APELLIDO = "cliente_apellido";
    public static final String COLUMNA_CLIENTE_CEDULA = "cliente_cedula";
    public static final String COLUMNA_CLIENTE_PIN = "cliente_pin";
    public static final String COLUMNA_CLIENTE_TARJETA = "cliente_tarjeta";
    public static final String COLUMNA_CLIENTE_TARJETA_TIPO = "cliente_tarjeta_tipo";
    public static final String COLUMNA_CLIENTE_TARJETA_CVV = "cliente_tarjeta_cvv";
    public static final String COLUMNA_CLIENTE_TARJETA_VEN = "cliente_tarjeta_ven";
    public static final String COLUMNA_CLIENTE_ESTADO = "cliente_estado";

    public static final String TABLA_TRANSACCION = "transaccion";
    public static final String COLUMNA_TRANSACCION_ID = "transaccion_id";
    public static final String COLUMNA_TRANSACCION_TIPO = "transaccion_tipo";
    public static final String COLUMNA_TRANSACCION_COMISION = "transaccion_comision";
    public static final String COLUMNA_TRANSACCION_VALOR_TOTAL = "transaccion_valor_total";
    public static final String COLUMNA_TRANSACCION_VALOR = "transaccion_valor";
    public static final String COLUMNA_TRANSACCION_CUENTA_CORRESPONAL = "transaccion_cuenta_corresponsal";
    public static final String COLUMNA_TRANSACCION_CEDULA_REMITENTE = "transaccion_cedula_remitente";
    public static final String COLUMNA_TRANSACCION_CEDULA_RECEPTOR = "transaccion_cedula_receptor";
    public static final String COLUMNA_TRANSACCION_TARJETA = "transaccion_tarjeta";
    public static final String COLUMNA_TRANSACCION_CUOTAS = "transaccion_cuotas";
    public static final String COLUMNA_TRANSACCION_FECHA = "transaccion_fecha";
    public static final String COLUMNA_TRANSACCION_HORA = "transaccion_hora";


    //Constantes Menu Administrador
    public static final String REGISTRAR_CORRESPONSAL = "Registrar Corresponsal";
    public static final String REGISTRAR_CORRESPONSAL_IMG = "https://cdn-icons-png.flaticon.com/512/2910/2910768.png";

    public static final String REGISTRAR_CLIENTE = "Registrar Cliente";
    public static final String REGISTRAR_CLIENTE_IMG = "https://cdn-icons-png.flaticon.com/512/3456/3456420.png";

    public static final String MOSTRAR_CORRESPONSAL = " Mostrar Corresponsal ";
    public static final String MOSTAR_CORRESPONSAL_IMG = "https://cdn-icons-png.flaticon.com/512/2910/2910808.png";

    public static final String MOSTRAR_CLIENTE = "Mostrar Cliente";
    public static final String MOSTRAR_CLIENTE_IMG = "https://cdn-icons-png.flaticon.com/512/2910/2910787.png";

    //Constantes Menu Corresponsal
    public static final String CORRESPONSAL_PAGO_TARJETA = "Pago con tarjeta";
    public static final String CORRESPONSAL_PAGO_TARJETA_IMG = "https://i.imgur.com/mPrCCJf.png";

    public static final String CORRESPONSAL_RETIRO = "Retiros WPOSS Bank";
    public static final String CORRESPONSAL_RETIRO_IMG = "https://i.imgur.com/c4JiX2b.png";

    public static final String CORRESPONSAL_DEPOSITO = "Depósitos WPOSS Bank";
    public static final String CORRESPONSAL_DEPOSITO_IMG = "https://i.imgur.com/Ng0gE40.png";

    public static final String CORRESPONSAL_TRANSFERENCIA = "Transferencias WPOSS Bank";
    public static final String CORRESPONSAL_TRANSFERENCIA_IMG = "https://i.imgur.com/MzMU86I.png";

    public static final String CORRESPONSAL_CONSULTAR_SALDO = "Consultas de saldo WPOSS Bank";
    public static final String CORRESPONSAL_CONSULTAR_SALDO_IMG = "https://i.imgur.com/A50aPjK.png";

    public static final String CORRESPONSAL_CREAR_CLIENTE = "Crear cuenta WPOSS Bank";
    public static final String CORRESPONSAL_CREAR_CLIENTE_IMG = "https://i.imgur.com/o049pXL.png";

    public static final String CORRESPONSAL_HISTORIAL = "Historial de transacciones";
    public static final String CORRESPONSAL_HISTORIAL_IMG = "https://i.imgur.com/Mm5Dzve.png";

    public static final String CORRESPONSAL_ACTUALIZAR = "Actualizar datos del corresponsal";
    public static final String CORRESPONSAL_ACTUALIZAR_IMG = "https://i.imgur.com/hwvYheK.png";
}
