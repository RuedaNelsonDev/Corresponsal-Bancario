package com.example.corresponsalwpossbank.clasesCorresponsal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corresponsalwpossbank.MenuCorresponsal;
import com.example.corresponsalwpossbank.R;
import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Cliente;
import com.example.corresponsalwpossbank.modelos.Corresponsal;
import com.example.corresponsalwpossbank.modelos.SharedPreference;
import com.example.corresponsalwpossbank.modelos.Transaccion;
import com.google.android.material.textfield.TextInputEditText;

public class RetiroCorresponsal extends AppCompatActivity {

    Metodos mtd;
    SharedPreference sp;
    Corresponsal corresponsal;
    Cliente cliente;
    Transaccion transaccion;


    TextInputEditText tietCedula;
    TextInputEditText tietPin;
    TextInputEditText tietCorfimPin;
    TextInputEditText tietValor;
    Button btnRetirar;

    //Datos superior
    TextView tvRango;
    TextView tvNombre;
    TextView tvApellido;
    TextView tvSaldo;
    String rangoCorresponsal;
    String nombreCorresponsal;
    String apellidoCorresponsal;
    String saldoCorresponsal;

    String cedula;
    String pin;
    String confirmPin;
    String valor;
    String estado;
    String saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retiro_corresponsal);
        mtd = new Metodos(this);
        sp = new SharedPreference(this);
        corresponsal = new Corresponsal();
        cliente = new Cliente();
        transaccion = new Transaccion();

        tietCedula = findViewById(R.id.tietPagoRetiroCedula);
        tietPin = findViewById(R.id.tietPagoRetiroPin);
        tietCorfimPin = findViewById(R.id.tietPagoRetiroConfiPin);
        tietValor = findViewById(R.id.tietPagoRetiroValor);
        btnRetirar = findViewById(R.id.btnPagoRetiroRetirar);

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

        btnRetirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cedula = tietCedula.getText().toString();
                pin = tietPin.getText().toString();
                confirmPin = tietCorfimPin.getText().toString();
                valor = tietValor.getText().toString();

                if (tietCedula.getText().toString().equals("") || tietPin.getText().toString().equals("")
                        || tietCorfimPin.getText().toString().equals("") || tietValor.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                } else {

                    cliente.setCedula(cedula);
                    cliente.setPin(pin);
                    transaccion.setValor(valor);

                    cliente = mtd.leerClientePorCedula(cliente);

                    estado = cliente.getEstado();
                    cliente.setEstado(estado);

                    saldo = cliente.getSaldo();

                    if (pin.equals(confirmPin)) {

                        if (mtd.validarCedulaPinCliente(cliente)) {

                            if (cliente.getEstado().equals("Habilitado")) {

                                corresponsal.setCorreo(sp.getCorresponsalActivo());
                                corresponsal = mtd.leerCorresponsalPorCorreo(corresponsal);
                                String saldoCorresposal = corresponsal.getSaldo();

                                if (Integer.parseInt(saldoCorresposal) > Integer.parseInt(valor)) {

                                    int saldoRestarCliente = Integer.parseInt(valor) + 2000;
                                    transaccion.setValorTotal(String.valueOf(saldoRestarCliente));

                                    if (Integer.parseInt(saldo) > saldoRestarCliente) {
                                        Intent intent = new Intent(RetiroCorresponsal.this, ConfirmarRetiroCorresponsal.class);

                                        intent.putExtra("id", String.valueOf(cliente.getId()));
                                        intent.putExtra("cedula", String.valueOf(cliente.getCedula()));
                                        intent.putExtra("saldo", String.valueOf(cliente.getSaldo()));
                                        intent.putExtra("valor", String.valueOf(transaccion.getValor()));
                                        intent.putExtra("valorTotal", String.valueOf(transaccion.getValorTotal()));

                                        startActivity(intent);
                                        finish();
                                    } else {

                                        Toast.makeText(getApplicationContext(), "Saldo del cliente insuficiente", Toast.LENGTH_SHORT).show();
                                    }
                                } else {

                                    Toast.makeText(getApplicationContext(), "Saldo del corresponsal insuficiente", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Cliente no esta Habilitado", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Datos no registran", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "Los Pins no coinciden", Toast.LENGTH_SHORT).show();
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