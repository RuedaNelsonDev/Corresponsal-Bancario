package com.example.corresponsalwpossbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Administrador;
import com.example.corresponsalwpossbank.modelos.Corresponsal;
import com.example.corresponsalwpossbank.modelos.SharedPreference;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {


    Metodos mtd;
    Corresponsal corresponsal;
    Administrador admin;
    SharedPreference sp;

    TextInputEditText loginCorreo;
    TextInputEditText loginContrasena;
    Button loginIniciarSesion;

    String correo;
    String contrasena;
    String estado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mtd = new Metodos(this);
        sp = new SharedPreference(this);
        admin = new Administrador();
        corresponsal = new Corresponsal();

        loginCorreo = findViewById(R.id.tietLoginCorreo);
        loginContrasena = findViewById(R.id.tietLoginContrasena);
        loginIniciarSesion = findViewById(R.id.btnLoginIniciarSesion);


        loginIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                correo = loginCorreo.getText().toString();
                contrasena = loginContrasena.getText().toString();


                if (loginCorreo.getText().toString().equals("") || loginContrasena.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Requiere llenar todos los espacios ", Toast.LENGTH_SHORT).show();

                } else {

                    if (adminData()) {

                        Intent intent = new Intent(getApplicationContext(), MenuAdministrador.class);
                        startActivity(intent);

                    } else {

                        if (corresponsalData()) {

                            if (estadoCorresponsal()) {

                                sp.setCorresponsalActivo(correo);

                                Toast.makeText(getApplicationContext(), "Bienvenido Corresponsal", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), MenuCorresponsal.class);
                                startActivity(intent);

                            } else {

                                Toast.makeText(getApplicationContext(), "Corresponsal inhabilitado, comuniquese con el administrador", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Datos incorrectos, verifique o registrese", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    boolean adminData() {

        if (loginCorreo.getText().toString().equals(admin.getCorreoAdmin())
                && loginContrasena.getText().toString().equals(admin.getContrasenaAdmin())) {

            if (mtd.validarAdmin(admin)) {
                Toast.makeText(getApplicationContext(), "Bienvenido Admnistrador", Toast.LENGTH_SHORT).show();

            } else {
                mtd.agregarAdmin(admin);
                Toast.makeText(getApplicationContext(), "Bienvenido Nuevo Admnistrador", Toast.LENGTH_SHORT).show();

            }
            return true;

        }
        return false;
    }

    boolean corresponsalData() {
        corresponsal.setCorreo(correo);
        corresponsal.setContrasena(contrasena);

        if (mtd.validarCorresponsal(corresponsal)) {
            return true;
        }
        return false;
    }

    boolean estadoCorresponsal() {
        corresponsal = mtd.leerEstadoPorCorreo(corresponsal);
        estado = corresponsal.getEstado();
        corresponsal.setEstado(estado);

        if (corresponsal.getEstado().equals("Habilitado")) {


            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


}
