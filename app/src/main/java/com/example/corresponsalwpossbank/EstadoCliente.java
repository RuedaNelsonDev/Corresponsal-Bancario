package com.example.corresponsalwpossbank;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Cliente;

public class EstadoCliente extends AppCompatActivity {
    Metodos mtd;
    Cliente cliente;

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
    Button btnHabilitar;
    Button btnDeshabilitar;

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
    String estadoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_cliente);
        mtd = new Metodos(this);
        cliente = new Cliente();

        tvEstado = findViewById(R.id.tvClienteEstado);
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

        btnHabilitar = findViewById(R.id.btnClienteHabilitar);
        btnDeshabilitar = findViewById(R.id.btnClienteDeshabilitar);

        estado = tvEstado.getText().toString().trim();
        cliente.setEstado(estado);

        nombre = tvClienteNombre.getText().toString().trim();
        cliente.setNombre(nombre);

        apellido = tvClienteApellido.getText().toString().trim();
        cliente.setApellido(apellido);

        cedula = tvClienteCedula.getText().toString().trim();
        cliente.setCedula(cedula);

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


        getAndSetIntentData();

        btnHabilitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cliente.getEstado().equals("Deshabilitado")) {
                    estadoActual = "Habilitado";
                    cliente.setEstado(estadoActual);

                    confirmDialogHabilitar();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Cliente ya esta Habilitado", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDeshabilitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cliente.getEstado().equals("Habilitado")) {
                    estadoActual = "Deshabilitado";
                    cliente.setEstado(estadoActual);

                    confirmDialogDeshabilitar();

                }else {
                    Toast.makeText(getApplicationContext(), "Cliente ya esta Deshabilitado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void getAndSetIntentData () {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("tarjeta") && getIntent().hasExtra("nombre") &&
                getIntent().hasExtra("apellido") && getIntent().hasExtra("tipo")
                && getIntent().hasExtra("cedula") && getIntent().hasExtra("saldo") &&
                getIntent().hasExtra("vencimiento") && getIntent().hasExtra("cvv")) {
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

        if(cliente.getEstado().equals("Habilitado")){
            btnDeshabilitar.setVisibility(View.VISIBLE);
        } else {
            btnHabilitar.setVisibility(View.VISIBLE);
        }
    }
    void confirmDialogHabilitar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Habilitar " + cliente.getNombre() + " ?");
        builder.setMessage("Estas seguro que quieres HABILITAR " + cliente.getNombre() + " ?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mtd.actualizarEstadoCliente(cliente);
                Toast.makeText(getApplicationContext(), "Corresponsal Habilitado", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EstadoCliente.this, MostrarCliente.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    void confirmDialogDeshabilitar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deshabilitar " + cliente.getNombre() + " ?");
        builder.setMessage("Estas seguro que quieres DESHABILITAR " + cliente.getNombre() + " ?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mtd.actualizarEstadoCliente(cliente);
                Toast.makeText(getApplicationContext(), "Corresponsal Deshabilitado", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EstadoCliente.this, MostrarCliente.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MostrarCliente.class);
        startActivity(intent);
    }
}
