package com.example.corresponsalwpossbank;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Cliente;
import com.example.corresponsalwpossbank.modelos.Corresponsal;

public class EstadoCorresponsal extends AppCompatActivity {
    Metodos mtd;
    Corresponsal corresponsal;

    TextView tvCorresNombre;
    TextView tvCorresApellido;
    TextView tvCorresCedula;
    TextView tvCorresCuenta;
    TextView tvCorresSaldo;
    TextView tvCorresTelefono;
    TextView tvCorresDirección;
    TextView tvCorresCorreo;
    TextView tvCorresEstado;

    Button btnHabilitar;
    Button btnDeshabilitar;

    String id;
    String cuenta;
    String nombre;
    String apellido;
    String cedula;
    String saldo;
    String telefono;
    String direccion;
    String correo;
    String estado;
    String estadoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_corresponsal);
        mtd = new Metodos(this);
        corresponsal = new Corresponsal();

        tvCorresNombre = findViewById(R.id.tvCorresNombre);
        tvCorresApellido = findViewById(R.id.tvCorresApellido);
        tvCorresCedula = findViewById(R.id.tvCorresCedula);
        tvCorresSaldo = findViewById(R.id.tvCorresSaldo);
        tvCorresCuenta = findViewById(R.id.tvCorresCuenta);
        tvCorresTelefono = findViewById(R.id.tvCorresTelefono);
        tvCorresDirección = findViewById(R.id.tvCorresDirección);
        tvCorresCorreo = findViewById(R.id.tvCorresCorreo);
        tvCorresEstado = findViewById(R.id.tvCorresEstado);

        btnHabilitar = findViewById(R.id.btnCuentaHabilitar);

        btnDeshabilitar = findViewById(R.id.btnCuentaDeshabilitar);

        nombre = tvCorresNombre.getText().toString().trim();
        corresponsal.setNombre(nombre);

        apellido = tvCorresApellido.getText().toString().trim();
        corresponsal.setApellido(apellido);

        cedula = tvCorresCedula.getText().toString().trim();
        corresponsal.setCedula(cedula);

        saldo = tvCorresSaldo.getText().toString().trim();
        corresponsal.setSaldo(saldo);

        cuenta = tvCorresCuenta.getText().toString().trim();
        corresponsal.setCuenta(cuenta);

        telefono = tvCorresTelefono.getText().toString().trim();
        corresponsal.setTelefono(telefono);

        direccion = tvCorresDirección.getText().toString().trim();
        corresponsal.setDireccion(direccion);

        correo = tvCorresCorreo.getText().toString().trim();
        corresponsal.setCorreo(correo);

        estado = tvCorresEstado.getText().toString().trim();
        corresponsal.setEstado(estado);

        getAndSetIntentData();

        btnHabilitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (corresponsal.getEstado().equals("Deshabilitado")) {
                    estadoActual = "Habilitado";
                    corresponsal.setEstado(estadoActual);

                    confirmDialogHabilitar();
                } else {
                    Toast.makeText(getApplicationContext(), "Corresponsal ya esta Habilitado", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDeshabilitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (corresponsal.getEstado().equals("Habilitado")) {
                    estadoActual = "Deshabilitado";
                    corresponsal.setEstado(estadoActual);

                    confirmDialogDeshabilitar();

                } else {
                    Toast.makeText(getApplicationContext(), "Corresponsal ya esta Deshabilitado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nombre") &&
                getIntent().hasExtra("apellido") && getIntent().hasExtra("cedula")
                && getIntent().hasExtra("cuenta") && getIntent().hasExtra("saldo") &&
                getIntent().hasExtra("telefono") && getIntent().hasExtra("direccion") &&
                getIntent().hasExtra("correo")) {
            //Obtener datos desde el Intent

            id = getIntent().getStringExtra("id");
            corresponsal.setId(id);


            nombre = getIntent().getStringExtra("nombre");
            corresponsal.setNombre(nombre);

            apellido = getIntent().getStringExtra("apellido");
            corresponsal.setApellido(apellido);

            cedula = getIntent().getStringExtra("cedula");
            corresponsal.setCedula(cedula);

            cuenta = getIntent().getStringExtra("cuenta");
            corresponsal.setCuenta(cuenta);

            saldo = getIntent().getStringExtra("saldo");
            corresponsal.setSaldo(saldo);

            telefono = getIntent().getStringExtra("telefono");
            corresponsal.setTelefono(telefono);

            direccion = getIntent().getStringExtra("direccion");
            corresponsal.setDireccion(direccion);

            correo = getIntent().getStringExtra("correo");
            corresponsal.setCorreo(correo);

            estado = getIntent().getStringExtra("estado");
            corresponsal.setEstado(estado);

            //Configurando datos del Intent

            tvCorresNombre.setText(nombre);
            tvCorresApellido.setText(apellido);
            tvCorresCedula.setText(cedula);
            tvCorresCuenta.setText(cuenta);
            tvCorresSaldo.setText(saldo);
            tvCorresTelefono.setText(telefono);
            tvCorresDirección.setText(direccion);
            tvCorresCorreo.setText(correo);
            tvCorresEstado.setText(estado);

        } else {
            Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show();
        }

        if (corresponsal.getEstado().equals("Habilitado")) {
            btnDeshabilitar.setVisibility(View.VISIBLE);
        } else {
            btnHabilitar.setVisibility(View.VISIBLE);
        }
    }

    void confirmDialogHabilitar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Habilitar " + corresponsal.getNombre() + " ?");
        builder.setMessage("Estas seguro que quieres HABILITAR " + corresponsal.getNombre() + " ?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mtd.actualizarEstadoCorresponsal(corresponsal);
                Toast.makeText(getApplicationContext(), "Corresponsal Habilitado", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EstadoCorresponsal.this, MostrarCorresponsal.class);
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
        builder.setTitle("Deshabilitar " + corresponsal.getNombre() + " ?");
        builder.setMessage("Estas seguro que quieres DESHABILITAR " + corresponsal.getNombre() + " ?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mtd.actualizarEstadoCorresponsal(corresponsal);
                Toast.makeText(getApplicationContext(), "Corresponsal Deshabilitado", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EstadoCorresponsal.this, MostrarCorresponsal.class);
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
        Intent intent = new Intent(this, MostrarCorresponsal.class);
        startActivity(intent);
    }

}
