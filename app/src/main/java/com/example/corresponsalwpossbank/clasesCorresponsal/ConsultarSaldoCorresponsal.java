package com.example.corresponsalwpossbank.clasesCorresponsal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ConsultarSaldoCorresponsal extends AppCompatActivity {
    Metodos mtd;
    SharedPreference sp;
    Cliente cliente;
    Corresponsal corresponsal;
    Transaccion transaccion;

    TextInputEditText tietCedula;
    TextInputEditText tietPin;
    TextInputEditText tietConfirmPin;

    TextView tvCedula;
    TextView tvSaldo;
    TextView tvEstado;
    LinearLayout lnConsulCLiente;

    Button btnConsultar;

    String estado;
    String cedula;
    String pin;
    String confirmPin;
    String saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_saldo_corresponsal);
        mtd = new Metodos(this);
        sp = new SharedPreference(this);
        cliente = new Cliente();
        corresponsal = new Corresponsal();
        transaccion = new Transaccion();

        tietCedula = findViewById(R.id.tietConsultaSaldoCedula);
        tietPin = findViewById(R.id.tietConsultaSaldoPin);
        tietConfirmPin = findViewById(R.id.tietConsultaSaldoConfirmPin);
        btnConsultar = findViewById(R.id.btnConsultaSaldoConsultar);

        tvEstado = findViewById(R.id.tvConsulClienteEstado);
        tvCedula = findViewById(R.id.tvConsulClienteCedula);
        tvSaldo = findViewById(R.id.tvConsulClienteSaldo);

        lnConsulCLiente = findViewById(R.id.lnConsulCLiente);


        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cedula = tietCedula.getText().toString();
                pin = tietPin.getText().toString();
                confirmPin = tietConfirmPin.getText().toString();

                if (tietCedula.getText().toString().equals("") || tietPin.getText().toString().equals("")
                        || tietConfirmPin.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                }

                if (pin.equals(confirmPin)) {

                    cliente.setCedula(cedula);
                    cliente.setPin(pin);

                    if (mtd.validarDepositoCliente(cliente)) {

                        mtd.leerClientePorCedula(cliente);
                        if (Integer.parseInt(cliente.getSaldo()) > 1000) {

                            corresponsal.setCorreo(sp.getCorresponsalActivo());
                            corresponsal = mtd.leerCorresponsalPorCorreo(corresponsal);

                            int nuevoSaldoCliente = Integer.parseInt(cliente.getSaldo()) - 1000;
                            cliente.setSaldo(String.valueOf(nuevoSaldoCliente));
                            mtd.actualizarSaldoCliente(cliente);

                            int nuevoSaldoCorresponsal = Integer.parseInt(corresponsal.getSaldo()) + 1000 ;
                            corresponsal.setSaldo(String.valueOf(nuevoSaldoCorresponsal));

                            mtd.actualizarSaldoCorresponsal(corresponsal);

                            String comision = "1000";
                            transaccion.setComision(comision);

                            transaccion.setCuentaCorresponsal(corresponsal.getCuenta());
                            transaccion.setCedulaRemitente(cedula);
                            transaccion.setTipo("Consulta");
                            transaccion.setValorTotal(transaccion.getComision());
                            transaccion.setValor(transaccion.getComision());



                            String fecha = String.valueOf(mtd.fechaPrestamo());
                            transaccion.setFecha(fecha);
                            mtd.hora(transaccion);

                            mtd.agregarTransaccion(transaccion);

                            estado = tvEstado.getText().toString().trim();
                            cedula = tvCedula.getText().toString().trim();
                            saldo = tvSaldo.getText().toString().trim();

                            estado = cliente.getEstado();
                            saldo = cliente.getSaldo();
                            cedula = cliente.getCedula();

                            tvCedula.setText(cedula);
                            tvSaldo.setText(saldo);
                            tvEstado.setText(estado);

                            lnConsulCLiente.setVisibility(View.VISIBLE);


                            tietCedula.setText("");
                            tietPin.setText("");
                            tietConfirmPin.setText("");

                        } else {

                            Toast.makeText(getApplicationContext(), "Saldo insuficiente", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "Datos no registran", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), "Pins no coinciden", Toast.LENGTH_SHORT).show();
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