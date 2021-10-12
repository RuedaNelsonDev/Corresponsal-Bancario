package com.example.corresponsalwpossbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Corresponsal;
import com.google.android.material.textfield.TextInputEditText;

public class RegistrarCorresponsal extends AppCompatActivity {
    Corresponsal corresponsal;
    Metodos mtd;
    Context context;

    TextInputEditText regisNombre;
    TextInputEditText regisApellido;
    TextInputEditText regisCedula;
    TextInputEditText regisTelefono;
    TextInputEditText regisDireccion;
    TextInputEditText regisCorreo;
    TextInputEditText regisContrasena;
    TextInputEditText regisConfirmContrasena;

    //Datos superios
    TextView tvRango;
    TextView tvNombre;
    TextView tvApellido;
    TextView tvSaldo;

    Button btnCorresRegistrar;

    String nombre;
    String apellido;
    String cedula;
    String telefono;
    String direccion;
    String correo;
    String contrasena;
    String saldo;
    String cuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_corresponsal);

        corresponsal = new Corresponsal();
        mtd = new Metodos(this);


        //inflar datos superior
        tvRango = findViewById(R.id.tvRango);
        tvRango.setText("Administrador");
        tvNombre = findViewById(R.id.tvNombre);
        tvNombre.setText("Banco");
        tvApellido = findViewById(R.id.tvApellido);
        tvApellido.setText("Wposs");
        tvSaldo = findViewById(R.id.tvSaldo);
        tvSaldo.setText("");

        regisNombre = findViewById(R.id.tietCorresNombre);
        regisApellido = findViewById(R.id.tietCorresApellido);
        regisCedula = findViewById(R.id.tietCorresCedula);
        regisTelefono = findViewById(R.id.tietCorresTelefono);
        regisDireccion = findViewById(R.id.tietCorresDireccion);
        regisCorreo = findViewById(R.id.tietCorresCorreo);
        regisContrasena = findViewById(R.id.tietCorresContrasena);
        regisConfirmContrasena = findViewById(R.id.tietCorresConfiContrasena);
        btnCorresRegistrar = findViewById(R.id.btnCorresRegistrar);
        btnCorresRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = regisNombre.getText().toString();
                corresponsal.setNombre(nombre);

                apellido = regisApellido.getText().toString();
                corresponsal.setApellido(apellido);

                cedula = regisCedula.getText().toString();
                corresponsal.setCedula(cedula);

                telefono = regisTelefono.getText().toString();
                corresponsal.setTelefono(telefono);

                direccion = regisDireccion.getText().toString();
                corresponsal.setDireccion(direccion);

                correo = regisCorreo.getText().toString();
                corresponsal.setCorreo(correo);

                contrasena = regisContrasena.getText().toString();
                corresponsal.setContrasena(contrasena);

                saldo = "2000000";
                corresponsal.setSaldo(saldo);

                cuenta = "Habilitado";
                corresponsal.setEstado(cuenta);

                String inicialCuenta = "254";
                corresponsal.setCuenta(mtd.sumarDerecha(inicialCuenta + corresponsal.getCedula(), 16, 2));


                if (regisNombre.getText().toString().equals("") || regisCedula.getText().toString().equals("")
                        || regisTelefono.getText().toString().equals("") || regisDireccion.getText().toString().equals("")
                        || regisCorreo.getText().toString().equals("") || regisContrasena.getText().toString().equals("")
                        || regisConfirmContrasena.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                } else {

                    if (regisContrasena.getText().toString().equals(regisConfirmContrasena.getText().toString())) {

                        if (mtd.validarCedulaCorresponsal(corresponsal)) {

                            Toast.makeText(getApplicationContext(), "Cedula ya usada", Toast.LENGTH_SHORT).show();
                        } else {
                            if (mtd.validarCorreoCorresponsal(corresponsal)) {

                                Toast.makeText(getApplicationContext(), "Correo ya registrado", Toast.LENGTH_SHORT).show();
                            } else {

                                if (mtd.validarCaracteresCorreo(regisCorreo)) {

                                    if(mtd.validarCaracteresContrasena(regisContrasena)) {

                                        Intent intent = new Intent(RegistrarCorresponsal.this, MostrarCuenta.class);

                                        intent.putExtra("id", String.valueOf(corresponsal.getId()));
                                        intent.putExtra("cuenta", String.valueOf(corresponsal.getCuenta()));
                                        intent.putExtra("nombre", String.valueOf(corresponsal.getNombre()));
                                        intent.putExtra("apellido", String.valueOf(corresponsal.getApellido()));
                                        intent.putExtra("cedula", String.valueOf(corresponsal.getCedula()));
                                        intent.putExtra("saldo", String.valueOf(corresponsal.getSaldo()));
                                        intent.putExtra("direccion", String.valueOf(corresponsal.getDireccion()));
                                        intent.putExtra("telefono", String.valueOf(corresponsal.getTelefono()));
                                        intent.putExtra("correo", String.valueOf(corresponsal.getCorreo()));
                                        intent.putExtra("contrasena", String.valueOf(corresponsal.getContrasena()));
                                        intent.putExtra("estado", String.valueOf(corresponsal.getContrasena()));

                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(), "Contraseña no valida", Toast.LENGTH_SHORT).show();
                                        regisContrasena.setError("Contraseña no valida");
                                    }
                                } else {
                                    regisCorreo.setError("Correo no valido");
                                }
                            }
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Los pins no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuAdministrador.class);
        startActivity(intent);
    }
}
