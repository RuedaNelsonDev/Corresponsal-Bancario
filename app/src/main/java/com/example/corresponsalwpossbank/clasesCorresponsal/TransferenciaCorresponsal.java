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

public class TransferenciaCorresponsal extends AppCompatActivity {

    Metodos mtd;
    Cliente cliente;
    Corresponsal corresponsal;
    Transaccion transaccion;
    SharedPreference sp;


    TextInputEditText tietCedula1;
    TextInputEditText tietCedula2;
    TextInputEditText tietPin;
    TextInputEditText tietPinConfirm;
    TextInputEditText tietValor;
    Button btnTransferir;

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
    String pin;
    String pinConfirm;
    String estado1;
    String estado2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia_corresponsal);
        mtd = new Metodos(this);
        sp = new SharedPreference(this);
        corresponsal = new Corresponsal();
        cliente = new Cliente();
        transaccion = new Transaccion();

        tietCedula1 = findViewById(R.id.tietPagoTransferenciaCedula1);
        tietCedula2 = findViewById(R.id.tietPagoTransferenciaCedula2);
        tietValor = findViewById(R.id.tietPagoTransferenciaValor);
        tietPin = findViewById(R.id.tietPagoTransferenciaPin);
        tietPinConfirm = findViewById(R.id.tietPagoTransferenciaConfirmPin);
        btnTransferir = findViewById(R.id.btnPagoTransferenciaTransferir);

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
        btnTransferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cedula1 = tietCedula1.getText().toString();
                cedula2 = tietCedula2.getText().toString();
                valor = tietValor.getText().toString();
                pin = tietPin.getText().toString();
                pinConfirm = tietPinConfirm.getText().toString();

                if (tietCedula1.getText().toString().equals("") || tietCedula2.getText().toString().equals("")
                        || tietValor.getText().toString().equals("") || tietPin.getText().toString().equals("")
                        || tietPinConfirm.getText().toString().equals("")) {

                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                }

                if (pin.equals(pinConfirm)){

                    cliente.setPin(pin);
                    cliente.setCedula(cedula1);
                    transaccion.setCedulaReceptor(cedula2);
                    transaccion.setValor(valor);

                    cliente = mtd.leerClientePorCedula(cliente);

                    if (mtd.validarDepositoCliente(cliente) && mtd.validarCedulaCliente2(transaccion) ){

                        estado1 = cliente.getEstado();

                        if (estado1.equals("Habilitado")) {

                            cliente.setCedula(cedula2);
                            transaccion.setCedulaRemitente(cedula1);

                            cliente = mtd.leerClientePorCedula(cliente);

                            estado2 = cliente.getEstado();

                            if (estado2.equals("Habilitado")) {

                                cliente.setCedula(cedula1);
                                transaccion.setCedulaReceptor(cedula2);

                                cliente.setId2(cliente.getId());
                                cliente.setSaldo2(cliente.getSaldo());

                                cliente = mtd.leerClientePorCedula(cliente);

                               int saldoCliente1 = Integer.parseInt(cliente.getSaldo());
                               int valorTransferencia = Integer.parseInt(valor);

                               if(saldoCliente1 > valorTransferencia){

                                   transaccion.setValorTotal(valor);
                                   int transferencia = Integer.parseInt(valor) - 1000;
                                   transaccion.setValor(String.valueOf(transferencia));

                                   Intent intent = new Intent(TransferenciaCorresponsal.this, ConfirmarTransferenciaCorresponsal.class);

                                   intent.putExtra("id", String.valueOf(cliente.getId()));
                                   intent.putExtra("id2", String.valueOf(cliente.getId2()));
                                   intent.putExtra("cedula1", String.valueOf(cliente.getCedula()));
                                   intent.putExtra("cedula2", String.valueOf(transaccion.getCedulaReceptor()));
                                   intent.putExtra("saldo1", String.valueOf(cliente.getSaldo()));
                                   intent.putExtra("saldo2", String.valueOf(cliente.getSaldo2()));
                                   intent.putExtra("valorTransferencia", String.valueOf(transaccion.getValor()));
                                   intent.putExtra("valorPagar", String.valueOf(transaccion.getValorTotal()));

                                   startActivity(intent);
                                   finish();
                               } else {

                                   Toast.makeText(getApplicationContext(), "Saldo del remitente insuficiente", Toast.LENGTH_SHORT).show();
                               }
                            }else{

                                Toast.makeText(getApplicationContext(), "Cliente Receptor no habilitado", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Cliente remitente no habilitado", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "Datos no registran", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), "Los pins no coinciden", Toast.LENGTH_SHORT).show();
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