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
import com.example.corresponsalwpossbank.R;
import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Corresponsal;
import com.google.android.material.textfield.TextInputEditText;

public class ActualizarCorresponsal extends AppCompatActivity {
    Metodos mtd;
    Corresponsal corresponsal;

    TextInputEditText tietActualizarCorreo;
    TextInputEditText tietActualizarContrasenaActual;
    TextInputEditText regisContrasena;
    TextInputEditText tietActualizarConfiContrasenaNeva;

    //Datos superior
    TextView tvRango;
    TextView tvNombre;
    TextView tvApellido;
    TextView tvSaldo;
    String rangoCorresponsal;
    String nombreCorresponsal;
    String apellidoCorresponsal;
    String saldoCorresponsal;

    Button btnCorresActualizar;

    String correo;
    String contrasena;
    String nuevaContrasena;
    String nuevaContrasenaConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_corresponsal);
        mtd = new Metodos(this);
        corresponsal = new Corresponsal();

        tietActualizarCorreo = findViewById(R.id.tietActualizarCorreo);
        tietActualizarContrasenaActual = findViewById(R.id.tietActualizarContrasenaActual);
        regisContrasena = findViewById(R.id.tietActualizarContrasenaNueva);
        tietActualizarConfiContrasenaNeva = findViewById(R.id.tietActualizarConfiContrasenaNeva);
        btnCorresActualizar = findViewById(R.id.btnCorresActualizar);

        //inflar datosSuperior
        tvRango = findViewById(R.id.tvRango);
        rangoCorresponsal = getIntent().getStringExtra("rangoCorresponsal");
        corresponsal.setRango(rangoCorresponsal);
        tvRango.setText(corresponsal.getRango());

        tvNombre = findViewById(R.id.tvNombre);
        nombreCorresponsal = getIntent().getStringExtra("nombreCorresponsal");
        corresponsal.setNombre(nombreCorresponsal);
        tvNombre.setText(corresponsal.getNombre());

        tvApellido = findViewById(R.id.tvApellido);
        apellidoCorresponsal = getIntent().getStringExtra("apellidoCorresponsal");
        corresponsal.setApellido(apellidoCorresponsal);
        tvApellido.setText(corresponsal.getApellido());

        tvSaldo = findViewById(R.id.tvSaldo);
        saldoCorresponsal = getIntent().getStringExtra("saldoCorresponsal");
        corresponsal.setSaldo(saldoCorresponsal);
        tvSaldo.setText("$ " + corresponsal.getSaldo());


        correo = getIntent().getStringExtra("correoCorresponsal");
        corresponsal.setCorreo(correo);

        contrasena = getIntent().getStringExtra("contrasenaCorresponsal");
        corresponsal.setContrasena(contrasena);


        btnCorresActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                correo = tietActualizarCorreo.getText().toString();
                contrasena = tietActualizarContrasenaActual.getText().toString();
                nuevaContrasena = regisContrasena.getText().toString();
                nuevaContrasenaConfirm = tietActualizarConfiContrasenaNeva.getText().toString();

                if (tietActualizarCorreo.getText().toString().equals("") || tietActualizarContrasenaActual.getText().toString().equals("")
                        || regisContrasena.getText().toString().equals("")
                        || tietActualizarConfiContrasenaNeva.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();

                } else {
                    if (regisContrasena.getText().toString().equals(tietActualizarConfiContrasenaNeva.getText().toString())) {

                        if (mtd.validarCaracteresContrasena(regisContrasena)) {

                            if (corresponsal.getCorreo().equals(correo) || (corresponsal.getContrasena().equals(contrasena))) {

                                corresponsal.setContrasena(nuevaContrasena);

                                mtd.actualizarContrasenaCorresponsal(corresponsal);
                                Toast.makeText(getApplicationContext(), "La contraseña a sido actualizada", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActualizarCorresponsal.this, MenuCorresponsal.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Datos no registran", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Contraseña no valida", Toast.LENGTH_SHORT).show();
                            regisContrasena.setError("Contraseña no valida");
                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "Los contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuCorresponsal.class);
        startActivity(intent);
        finish();
    }
}