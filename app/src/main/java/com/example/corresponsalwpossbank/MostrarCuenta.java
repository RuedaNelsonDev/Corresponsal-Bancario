package com.example.corresponsalwpossbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Cliente;
import com.example.corresponsalwpossbank.modelos.Corresponsal;

public class MostrarCuenta extends AppCompatActivity {

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

    Button btnAceptar;
    Button btnCancelar;

    String id;
    String cuenta;
    String nombre;
    String apellido;
    String cedula;
    String saldo;
    String telefono;
    String direccion;
    String correo;
    String contrasena;
    String estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_corresponsal);
        mtd = new Metodos(this);
        corresponsal = new Corresponsal();

        tvCorresNombre = findViewById(R.id.tvCorresNombre);
        tvCorresApellido = findViewById(R.id.tvCorresApellido);
        tvCorresCedula = findViewById(R.id.tvCorresCedula);
        tvCorresCuenta = findViewById(R.id.tvCorresCuenta);
        tvCorresSaldo = findViewById(R.id.tvCorresSaldo);
        tvCorresTelefono = findViewById(R.id.tvCorresTelefono);
        tvCorresDirección = findViewById(R.id.tvCorresDirección);
        tvCorresCorreo = findViewById(R.id.tvCorresCorreo);
        btnAceptar = findViewById(R.id.btnCuentaAceptar);
        btnAceptar.setVisibility(View.VISIBLE);
        btnCancelar = findViewById(R.id.btnCuentaCancelar);
        btnCancelar.setVisibility(View.VISIBLE);

        nombre = tvCorresNombre.getText().toString().trim();
        corresponsal.setNombre(nombre);

        apellido = tvCorresApellido.getText().toString().trim();
        corresponsal.setApellido(apellido);

        cedula = tvCorresCedula.getText().toString().trim();
        corresponsal.setCedula(cedula);

        cuenta = tvCorresCuenta.getText().toString().trim();
        corresponsal.setCuenta(cuenta);

        saldo = tvCorresSaldo.getText().toString().trim();
        corresponsal.setSaldo(saldo);

        telefono = tvCorresTelefono.getText().toString().trim();
        corresponsal.setTelefono(telefono);

        direccion = tvCorresDirección.getText().toString().trim();
        corresponsal.setDireccion(direccion);

        correo = tvCorresCorreo.getText().toString().trim();
        corresponsal.setCorreo(correo);

        estado = "Habilitado";
        corresponsal.setEstado(estado);

        getAndSetIntentData();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mtd.agregarCorresponsal(corresponsal);
                Toast.makeText(getApplicationContext(),  "Corresponsal registrado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MostrarCuenta.this, MenuAdministrador.class);
                startActivity(intent);
                finish();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MostrarCuenta.this, MenuAdministrador.class);
                startActivity(intent);
                finish();
            }
        });

    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("cuenta")
                && getIntent().hasExtra("nombre") && getIntent().hasExtra("apellido")
                && getIntent().hasExtra("cedula") && getIntent().hasExtra("saldo")
                && getIntent().hasExtra("telefono") && getIntent().hasExtra("direccion")
                && getIntent().hasExtra("correo") && getIntent().hasExtra("contrasena")) {
            //Obtener datos desde el Intent

            id = getIntent().getStringExtra("id");
            corresponsal.setId(id);

            cuenta = getIntent().getStringExtra("cuenta");
            corresponsal.setCuenta(cuenta);

            nombre = getIntent().getStringExtra("nombre");
            corresponsal.setNombre(nombre);

            apellido = getIntent().getStringExtra("apellido");
            corresponsal.setApellido(apellido);

            cedula = getIntent().getStringExtra("cedula");
            corresponsal.setCedula(cedula);

            saldo = getIntent().getStringExtra("saldo");
            corresponsal.setSaldo(saldo);

            telefono = getIntent().getStringExtra("telefono");
            corresponsal.setTelefono(telefono);

            direccion = getIntent().getStringExtra("direccion");
            corresponsal.setDireccion(direccion);

            correo = getIntent().getStringExtra("correo");
            corresponsal.setCorreo(correo);

            contrasena = getIntent().getStringExtra("contrasena");
            corresponsal.setContrasena(contrasena);

            //Configurando datos del Intent

            tvCorresNombre.setText(nombre);
            tvCorresApellido.setText(apellido);
            tvCorresCedula.setText(cedula);
            tvCorresCuenta.setText(cuenta);
            tvCorresSaldo.setText(saldo);
            tvCorresTelefono.setText(telefono);
            tvCorresDirección.setText(direccion);
            tvCorresCorreo.setText(correo);

        } else {
            Toast.makeText(this, "No hay datos", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, RegistrarCorresponsal.class);
        startActivity(intent);
    }
}