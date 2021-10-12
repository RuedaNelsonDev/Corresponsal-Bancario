package com.example.corresponsalwpossbank.clasesCorresponsal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.corresponsalwpossbank.MenuCorresponsal;
import com.example.corresponsalwpossbank.R;
import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Cliente;
import com.example.corresponsalwpossbank.modelos.Corresponsal;
import com.example.corresponsalwpossbank.modelos.SharedPreference;
import com.example.corresponsalwpossbank.modelos.Transaccion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfirmarDepositoCorresponsal extends AppCompatActivity {

    Metodos mtd;
    Cliente cliente;
    Transaccion transaccion;
    Corresponsal corresponsal;
    SharedPreference sp;

    TextView tvCedula;
    TextView tvCedulaReceptor;
    TextView tvValorPagar;
    TextView tvValorDeposito;
    TextView tvTipo;
    TextView tvValorComision;
    Button btnPagar;
    Button btnCancelar;

    TextView txtPagoCedula;
    TextView txtPagoValorTotal;
    TextView txtPagoComision;
    TextView txtCedulaReceptor;

    String id;
    String valorPagar;
    String valorDeposito;
    String tipoTransaccion;
    String cedula1;
    String cedula2;
    String saldo;
    String comision;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pago_corresponsal);
        mtd = new Metodos(this);
        cliente = new Cliente();
        transaccion = new Transaccion();
        corresponsal = new Corresponsal();
        sp = new SharedPreference(this);

        comision = "1000";
        transaccion.setComision(comision);

        tvCedula = findViewById(R.id.tvPagoCedula);
        tvCedula.setVisibility(View.VISIBLE);
        txtCedulaReceptor = findViewById(R.id.txtPagoCedulaReceptor);
        txtCedulaReceptor.setVisibility(View.VISIBLE);
        tvCedulaReceptor = findViewById(R.id.tvPagoCedulaReceptor);
        tvCedulaReceptor.setVisibility(View.VISIBLE);
        tvValorPagar = findViewById(R.id.tvPagoConfirmValorTotal);
        tvValorPagar.setVisibility(View.VISIBLE);
        tvValorDeposito = findViewById(R.id.tvPagoConfirmValor);
        tvValorDeposito.setVisibility(View.VISIBLE);
        tvTipo = findViewById(R.id.tvPagoTipo);
        txtPagoComision = findViewById(R.id.txtPagoComision);
        txtPagoComision.setVisibility(View.VISIBLE);
        tvValorComision = findViewById(R.id.tvPagoValorComision);
        tvValorComision.setVisibility(View.VISIBLE);
        btnPagar = findViewById(R.id.btnPagoConfirmPagar);
        btnCancelar = findViewById(R.id.btnPagoConfirmCancelar);

        txtPagoCedula = findViewById(R.id.txtPagoCedula);
        txtPagoCedula.setVisibility(View.VISIBLE);
        txtPagoValorTotal =findViewById(R.id.txtPagoValorTotal);
        txtPagoValorTotal.setVisibility(View.VISIBLE);

        cedula1 = tvCedula.getText().toString().trim();
        transaccion.setCedulaRemitente(cedula1);

        valorDeposito = tvValorDeposito.getText().toString().trim();
        transaccion.setValorTotal(valorDeposito);

        tipoTransaccion = tvTipo.getText().toString().trim();
        transaccion.setTipo(tipoTransaccion);

        getAndSetIntentData();

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                corresponsal.setCorreo(sp.getCorresponsalActivo());
                corresponsal = mtd.leerCorresponsalPorCorreo(corresponsal);

                nuevoSaldoCliente2();
                nuevoSaldoCorresponsal();

                transaccion.setCuentaCorresponsal(corresponsal.getCuenta());
                transaccion.setCedulaRemitente(cedula1);
                transaccion.setCedulaReceptor(cedula2);


                mtd.agregarTransaccion(transaccion);
                Toast.makeText(getApplicationContext(), "Pago exitoso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ConfirmarDepositoCorresponsal.this, TransaccionExitosa.class);
                startActivity(intent);
                finish();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmarDepositoCorresponsal.this, TransaccionRechazada.class);
                startActivity(intent);
                finish();
            }
        });
    }
    /**
     * getAndSetIntentData > obtiente los datos desde el Intent y los setea, infla los TextViews
     */
    void getAndSetIntentData() {

        //obtención de datos desde el intent

        id = getIntent().getStringExtra("id");
        cliente.setId(id);

        cedula1 = getIntent().getStringExtra("cedula1");
        transaccion.setCedulaRemitente(cedula1);

        cedula2 = getIntent().getStringExtra("cedula2");
        cliente.setCedula(cedula2);

        saldo = getIntent().getStringExtra("saldo2");
        cliente.setSaldo(saldo);

        valorDeposito = getIntent().getStringExtra("valorFinalDeposito");
        transaccion.setValor(valorDeposito);

        valorPagar = getIntent().getStringExtra("valorPagar");
        transaccion.setValorTotal(valorPagar);

        tipoTransaccion = "Depositar";
        transaccion.setTipo(tipoTransaccion);
        //inflado de los TextView

        tvCedula.setText(cedula1);
        tvCedulaReceptor.setText(cedula2);
        tvValorPagar.setText(valorPagar);
        tvValorDeposito.setText(valorDeposito);
        tvValorComision.setText(comision);
        tvTipo.setText(tipoTransaccion);
    }

    /**
     * nuevoSaldoCliente2 > resta del saldo del cliente el valor de la cuota y lo actualiza en la TABLA CLIENTE
     */
    void nuevoSaldoCliente2() {

        int actualSaldoCliente = Integer.parseInt(cliente.getSaldo());
        int valorDeposito = Integer.parseInt(transaccion.getValor());
        int nuevoSaldoCliente = actualSaldoCliente + valorDeposito;

        cliente.setSaldo(String.valueOf(nuevoSaldoCliente));
        mtd.actualizarSaldoCliente(cliente);
    }

    /**
     * nuevoSaldoCorresponsal > suma el saldo del cliente con el valor total del pago y lo actualiza en la TABLA CORRESPONSAL
     */
    void nuevoSaldoCorresponsal() {

        int actualSaldoCorresponsal = Integer.parseInt(corresponsal.getSaldo());
        int valorDeposito = Integer.parseInt(transaccion.getValor());
        int nuevoSaldoCorresponsal = actualSaldoCorresponsal - valorDeposito;

        corresponsal.setSaldo(String.valueOf(nuevoSaldoCorresponsal));
        mtd.actualizarSaldoCorresponsal(corresponsal);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuCorresponsal.class);
        startActivity(intent);
        finish();
    }

}