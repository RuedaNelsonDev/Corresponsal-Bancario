package com.example.corresponsalwpossbank.clasesCorresponsal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class DepositoCorresponsal extends AppCompatActivity {

    Metodos mtd;
    Cliente cliente;
    Corresponsal corresponsal;
    Transaccion transaccion;
    SharedPreference sp;


    TextInputEditText tietCedula1;
    TextInputEditText tietCedula2;
    TextInputEditText tietValor;
    Button btnDepositar;

    //Datos superior
    TextView tvRango;
    TextView tvNombre;
    TextView tvApellido;
    TextView tvSaldo;
    String rangoCorresponsal;
    String nombreCorresponsal;
    String apellidoCorresponsal;
    String saldoCorresponsal;

    String cedula1;
    String cedula2;
    String valor;
    String estado2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito_corresponsal);
        mtd = new Metodos(this);
        sp = new SharedPreference(this);
        corresponsal = new Corresponsal();
        cliente = new Cliente();
        transaccion = new Transaccion();

        tietCedula1 = findViewById(R.id.tietPagoDepositoCedula1);
        tietCedula2 = findViewById(R.id.tietPagoDepositoCedula2);
        tietValor = findViewById(R.id.tietPagoDepositoValor);
        btnDepositar = findViewById(R.id.btnPagoDepositoDepositar);


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

        btnDepositar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cedula1 = tietCedula1.getText().toString();
                cedula2 = tietCedula2.getText().toString();
                valor = tietValor.getText().toString();

                if (tietCedula1.getText().toString().equals("") || tietCedula2.getText().toString().equals("")
                        || tietValor.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                }

                cliente.setCedula(cedula2);
                transaccion.setCedulaRemitente(cedula1);
                transaccion.setValor(valor);

                cliente = mtd.leerClientePorCedula(cliente);

                if (mtd.validarCedulaCliente(cliente)) {

                    estado2 = cliente.getEstado();
                    cliente.setEstado(estado2);

                    if (cliente.getEstado().equals("Habilitado")) {

                        corresponsal.setCorreo(sp.getCorresponsalActivo());
                        corresponsal = mtd.leerCorresponsalPorCorreo(corresponsal);

                        int saldoCorresposal = Integer.parseInt(corresponsal.getSaldo());
                        int valorDeposito = Integer.parseInt(valor);


                        if (saldoCorresposal > valorDeposito) {

                            confirmDialogDepositar();
                        } else {

                            Toast.makeText(getApplicationContext(), "Saldo del corresponsal insuficiente", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        Toast.makeText(getApplicationContext(), "Cliente al que se va a enviar no habilitado", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), "Datos no registran", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void confirmDialogDepositar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Valor de comisión");
        builder.setMessage("¿ Desea pagar por aparte el valor de la comisión ?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(getApplicationContext(), "El valor de la comisión se cobrara aparte del dinero a depositar", Toast.LENGTH_SHORT).show();

                int valorPagar = Integer.parseInt(valor) + 1000;
                transaccion.setValorTotal(String.valueOf(valorPagar));

                String valorFinalDeposito = valor;

                transaccion.setValor(valorFinalDeposito);


                Intent intent = new Intent(DepositoCorresponsal.this, ConfirmarDepositoCorresponsal.class);

                intent.putExtra("id", String.valueOf(cliente.getId()));
                intent.putExtra("cedula1", String.valueOf(transaccion.getCedulaRemitente()));
                intent.putExtra("cedula2", String.valueOf(cliente.getCedula()));
                intent.putExtra("saldo2", String.valueOf(cliente.getSaldo()));
                intent.putExtra("valorPagar", String.valueOf(transaccion.getValorTotal()));
                intent.putExtra("valorFinalDeposito", String.valueOf(transaccion.getValor()));

                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "El valor de la comisión se cobra del dinero a depositar", Toast.LENGTH_SHORT).show();

                int valorFinalDeposito = Integer.parseInt(valor) - 1000;
                transaccion.setValor(String.valueOf(valorFinalDeposito));

                String valorPagar = valor;
                transaccion.setValorTotal(valorPagar);

                Intent intent = new Intent(DepositoCorresponsal.this, ConfirmarDepositoCorresponsal.class);

                intent.putExtra("id", String.valueOf(cliente.getId()));
                intent.putExtra("cedula1", String.valueOf(transaccion.getCedulaRemitente()));
                intent.putExtra("cedula2", String.valueOf(cliente.getCedula()));
                intent.putExtra("saldo2", String.valueOf(cliente.getSaldo()));
                intent.putExtra("valorPagar", String.valueOf(transaccion.getValorTotal()));
                intent.putExtra("valorFinalDeposito", String.valueOf(transaccion.getValor()));

                startActivity(intent);
                finish();
            }
        });
        builder.create().show();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuCorresponsal.class);
        startActivity(intent);
        finish();
    }
}