package com.example.corresponsalwpossbank.clasesCorresponsal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corresponsalwpossbank.MenuAdministrador;
import com.example.corresponsalwpossbank.MenuCorresponsal;
import com.example.corresponsalwpossbank.MostrarTarjeta;
import com.example.corresponsalwpossbank.R;
import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Cliente;
import com.example.corresponsalwpossbank.modelos.Corresponsal;
import com.example.corresponsalwpossbank.modelos.SharedPreference;
import com.example.corresponsalwpossbank.modelos.Transaccion;

public class MostrarTarjetaCliente extends AppCompatActivity {

    Metodos mtd;
    SharedPreference sp;
    Cliente cliente;
    Corresponsal corresponsal;
    Transaccion transaccion;

    TextView tvClienteNombre;
    TextView tvClienteApellido;
    TextView tvClienteCedula;
    TextView tvClienteSaldo;
    TextView tvTarjeta;
    TextView tvTarjetaTipo;
    TextView tvNombre;
    TextView tvApellido;
    TextView tvVencimiento;
    TextView tvCvv;
    TextView tvEstado;
    Button btnAceptar;
    Button btnCancelar;

    String id;
    String tarjeta;
    String tipo;
    String nombre;
    String apellido;
    String vencimiento;
    String cvv;
    String cedula;
    String saldo;
    String pin;
    String estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_cliente);
        mtd = new Metodos(this);
        sp = new SharedPreference(this);
        cliente = new Cliente();
        corresponsal = new Corresponsal();
        transaccion = new Transaccion();

        tvClienteNombre = findViewById(R.id.tvClienteNombre);
        tvClienteApellido = findViewById(R.id.tvClienteApellido);
        tvClienteCedula = findViewById(R.id.tvClienteCedula);
        tvClienteSaldo = findViewById(R.id.tvClienteSaldo);
        tvTarjeta = findViewById(R.id.tvTarjetaNumero);
        tvTarjetaTipo = findViewById(R.id.tvTarjetaTipo);
        tvNombre = findViewById(R.id.tvTarjetaNombre);
        tvApellido = findViewById(R.id.tvTarjetaApellido);
        tvVencimiento = findViewById(R.id.tvTarjetaVen);
        tvCvv = findViewById(R.id.tvTarjetaCvv);
        tvEstado = findViewById(R.id.tvClienteEstado);
        btnAceptar = findViewById(R.id.btnTarjetaAceptar);
        btnAceptar.setVisibility(View.VISIBLE);
        btnCancelar = findViewById(R.id.btnTarjetaCancelar);
        btnCancelar.setVisibility(View.VISIBLE);

        nombre = tvClienteNombre.getText().toString().trim();
        cliente.setNombre(nombre);

        apellido = tvClienteApellido.getText().toString().trim();
        cliente.setApellido(apellido);

        cedula = tvClienteCedula.getText().toString().trim();
        cliente.setApellido(cedula);

        saldo = tvClienteSaldo.getText().toString().trim();
        cliente.setSaldo(saldo);

        tarjeta = tvTarjeta.getText().toString().trim();
        cliente.setTarjeta(tarjeta);

        tipo = tvTarjetaTipo.getText().toString().trim();
        cliente.setTarjetaTipo(tipo);

        nombre = tvNombre.getText().toString().trim();
        cliente.setNombre(nombre);

        apellido = tvApellido.getText().toString().trim();
        cliente.setApellido(apellido);

        vencimiento = tvVencimiento.getText().toString().trim();
        cliente.setVen(vencimiento);

        cvv = tvCvv.getText().toString().trim();
        cliente.setCvv(cvv);

        estado = tvEstado.getText().toString().trim();
        cliente.setEstado(estado);

        getAndSetIntentData();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                actualizarCorresponsal();

                mtd.agregarCliente(cliente);

                agregarTransaccion();

                Toast.makeText(getApplicationContext(), "Cliente registrado", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MostrarTarjetaCliente.this, MenuCorresponsal.class);
                startActivity(intent);
                finish();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MostrarTarjetaCliente.this, MenuCorresponsal.class);
                startActivity(intent);
                finish();
            }
        });

    }

    /**
     * getAndSetIntentData > obtiente los datos desde el Intent y los setea, infla los TextViews
     */
    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("tarjeta")
                && getIntent().hasExtra("nombre") && getIntent().hasExtra("apellido")
                && getIntent().hasExtra("tipo") && getIntent().hasExtra("cedula")
                && getIntent().hasExtra("saldo") && getIntent().hasExtra("vencimiento")
                && getIntent().hasExtra("cvv")) {
            //Obtener datos desde el Intent

            id = getIntent().getStringExtra("id");
            cliente.setId(id);

            tarjeta = getIntent().getStringExtra("tarjeta");
            cliente.setTarjeta(tarjeta);

            nombre = getIntent().getStringExtra("nombre");
            cliente.setNombre(nombre);

            apellido = getIntent().getStringExtra("apellido");
            cliente.setApellido(apellido);

            tipo = getIntent().getStringExtra("tipo");
            cliente.setTarjetaTipo(tipo);

            cedula = getIntent().getStringExtra("cedula");
            cliente.setCedula(cedula);

            saldo = getIntent().getStringExtra("saldo");
            cliente.setSaldo(saldo);

            vencimiento = getIntent().getStringExtra("vencimiento");
            cliente.setVen(vencimiento);

            cvv = getIntent().getStringExtra("cvv");
            cliente.setCvv(cvv);

            pin = getIntent().getStringExtra("pin");
            cliente.setPin(pin);

            estado = getIntent().getStringExtra("estado");
            cliente.setEstado(estado);


            //Configurando datos del Intent

            tvTarjeta.setText(tarjeta);
            tvNombre.setText(nombre);
            tvClienteNombre.setText(nombre);
            tvApellido.setText(apellido);
            tvClienteApellido.setText(apellido);
            tvTarjetaTipo.setText(tipo);
            tvClienteCedula.setText(cedula);
            tvClienteSaldo.setText(saldo);
            tvVencimiento.setText(vencimiento);
            tvCvv.setText(cvv);
            tvEstado.setText(estado);

        } else {
            Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * actualizarCorresponsal > llama los datos del corresponsal por el usuario activo,
     * le resta el saldo de la creación de la cuenta Cliente y le suma el valor de cración de la cuenta
     */
    void actualizarCorresponsal() {

        corresponsal.setCorreo(sp.getCorresponsalActivo());
        corresponsal = mtd.leerCorresponsalPorCorreo(corresponsal);

        int nuevoSaldoCorresponsal = Integer.parseInt(corresponsal.getSaldo()) - Integer.parseInt(cliente.getSaldo()) + 10000;
        corresponsal.setSaldo(String.valueOf(nuevoSaldoCorresponsal));
        mtd.actualizarSaldoCorresponsal(corresponsal);
    }

    /**
     * agregarTransaccion > toma los datos de la transferencia y los guarda en la Tabla Transaccion
     */
    void agregarTransaccion() {

        transaccion.setCuentaCorresponsal(corresponsal.getCuenta());
        transaccion.setCedulaReceptor(cedula);
        transaccion.setCedulaRemitente(corresponsal.getCedula());
        transaccion.setTipo("Nueva Cuenta");

        String comision = "10000";
        transaccion.setComision(comision);

        int valorTotal = Integer.parseInt(cliente.getSaldo()) + 10000;
        transaccion.setValorTotal(String.valueOf(valorTotal));
        transaccion.setValor(cliente.getSaldo());

        String fecha = String.valueOf(mtd.fechaPrestamo());
        transaccion.setFecha(fecha);
        mtd.hora(transaccion);

        mtd.agregarTransaccion(transaccion);
    }@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuCorresponsal.class);
        startActivity(intent);
        finish();
    }

}