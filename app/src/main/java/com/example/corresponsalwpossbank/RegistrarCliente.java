package com.example.corresponsalwpossbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Cliente;
import com.google.android.material.textfield.TextInputEditText;

public class RegistrarCliente extends AppCompatActivity {
    Metodos mtd;
    Cliente cliente;

    TextView tvRango;
    TextView tvNombre;
    TextView tvApellido;
    TextView tvSaldo;

    LinearLayout lnDatosSuperior;
    TextInputEditText regisNombre;
    TextInputEditText regisApellido;
    TextInputEditText regisCedula;
    TextInputEditText regisSaldo;
    TextInputEditText regisPin;
    TextInputEditText regisConfirmPin;
    Button btnClienteRegistrar;

    String nombre;
    String apellido;
    String cedula;
    String pin;
    String saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_cliente);
        mtd = new Metodos(this);
        cliente = new Cliente();

        tvRango = findViewById(R.id.tvRango);
        tvRango.setText("Administrador");
        tvNombre = findViewById(R.id.tvNombre);
        tvNombre.setText("Banco");
        tvApellido = findViewById(R.id.tvApellido);
        tvApellido.setText("Wposs");
        tvSaldo = findViewById(R.id.tvSaldo);
        tvSaldo.setText("");

        lnDatosSuperior= findViewById(R.id.datosLayout);
        lnDatosSuperior.setVisibility(View.VISIBLE);
        regisNombre = findViewById(R.id.tietClienteNombre);
        regisApellido = findViewById(R.id.tietClienteApellido);
        regisCedula = findViewById(R.id.tietClienteCedula);
        regisSaldo = findViewById(R.id.tietClienteSaldo);
        regisPin = findViewById(R.id.tietClientePin);
        regisConfirmPin = findViewById(R.id.tietCLienteConfiPin);
        btnClienteRegistrar = findViewById(R.id.btnClienteRegistrar);
        btnClienteRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = regisNombre.getText().toString();
                cliente.setNombre(nombre);

                apellido = regisApellido.getText().toString();
                cliente.setApellido(apellido);

                cedula = regisCedula.getText().toString();
                cliente.setCedula(cedula);

                pin = regisPin.getText().toString();
                cliente.setPin(pin);

                saldo = regisSaldo.getText().toString();
                cliente.setSaldo(saldo);

                String inicialTarjeta = mtd.inicialTarjeta();

                cliente.setTarjeta(mtd.sumarDerecha(inicialTarjeta + cliente.getCedula(), 16, 4));
                cliente.setCvv(mtd.cvv());
                cliente.setVen(mtd.ven());

                String estado = "Habilitado";
                cliente.setEstado(estado);


                int numeroTarjeta;
                numeroTarjeta = Integer.parseInt(inicialTarjeta);

                switch (numeroTarjeta) {
                    case 3:
                        String tarjetaTipo = "American Express";
                        cliente.setTarjetaTipo(tarjetaTipo);
                        break;
                    case 4:
                        String tarjetaTipo2 = "Visa";
                        cliente.setTarjetaTipo(tarjetaTipo2);
                        break;
                    case 5:
                        String tarjetaTipo3 = "MasterCard";
                        cliente.setTarjetaTipo(tarjetaTipo3);
                        break;
                    case 6:
                        String tarjetaTipo4 = "UnionPay";
                        cliente.setTarjetaTipo(tarjetaTipo4);
                        break;

                    default:
                        Toast.makeText(getApplicationContext(), "Tarjeta invalida", Toast.LENGTH_SHORT).show();
                }


                if (regisNombre.getText().toString().equals("") || regisApellido.getText().toString().equals("")
                        || regisCedula.getText().toString().equals("") || regisPin.getText().toString().equals("")
                        || regisConfirmPin.getText().toString().equals("") || regisSaldo.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                } else {
                    if (regisPin.getText().toString().equals(regisConfirmPin.getText().toString())) {

                        if (mtd.validarCedulaCliente(cliente)) {
                            Toast.makeText(getApplicationContext(), "Cedula ya usada", Toast.LENGTH_SHORT).show();
                        } else {

                            if (Integer.parseInt(cliente.getSaldo()) > 10000) {

                                int nuevoSaldoCliente = Integer.parseInt(cliente.getSaldo()) - 10000;
                                cliente.setSaldo(String.valueOf(nuevoSaldoCliente));

                                Intent intent = new Intent(RegistrarCliente.this, MostrarTarjeta.class);

                                intent.putExtra("id", String.valueOf(cliente.getId()));
                                intent.putExtra("tarjeta", String.valueOf(cliente.getTarjeta()));
                                intent.putExtra("nombre", String.valueOf(cliente.getNombre()));
                                intent.putExtra("apellido", String.valueOf(cliente.getApellido()));
                                intent.putExtra("cedula", String.valueOf(cliente.getCedula()));
                                intent.putExtra("saldo", String.valueOf(cliente.getSaldo()));
                                intent.putExtra("tipo", String.valueOf(cliente.getTarjetaTipo()));
                                intent.putExtra("vencimiento", String.valueOf(cliente.getVen()));
                                intent.putExtra("cvv", String.valueOf(cliente.getCvv()));
                                intent.putExtra("pin", String.valueOf(cliente.getPin()));
                                intent.putExtra("estado", String.valueOf(cliente.getEstado()));

                                startActivity(intent);
                                finish();
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