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

public class ConfirmarRetiroCorresponsal extends AppCompatActivity {
    Metodos mtd;
    Cliente cliente;
    Transaccion transaccion;
    Corresponsal corresponsal;
    SharedPreference sp;

    TextView tvCedula;
    TextView tvValor;
    TextView tvValorTotal;
    TextView tvValorComision;
    TextView tvTipo;
    Button btnPagar;
    Button btnCancelar;

    TextView txtPagoCedula;
    TextView txtPagoValorTotal;
    TextView txtPagoComision;
    String id;
    String valor;
    String valorTotal;
    String tipoTransaccion;
    String cedula;
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

        comision = "2000";
        transaccion.setComision(comision);


        tvCedula = findViewById(R.id.tvPagoCedula);
        tvCedula.setVisibility(View.VISIBLE);
        tvValor = findViewById(R.id.tvPagoConfirmValor);
        tvValor.setVisibility(View.VISIBLE);
        tvValorTotal = findViewById(R.id.tvPagoConfirmValorTotal);
        tvValorTotal.setVisibility(View.VISIBLE);
        tvTipo = findViewById(R.id.tvPagoTipo);
        txtPagoComision = findViewById(R.id.txtPagoComision);
        txtPagoComision.setVisibility(View.VISIBLE);
        tvValorComision = findViewById(R.id.tvPagoValorComision);
        tvValorComision.setVisibility(View.VISIBLE);
        btnPagar = findViewById(R.id.btnPagoConfirmPagar);
        btnCancelar = findViewById(R.id.btnPagoConfirmCancelar);

        txtPagoCedula = findViewById(R.id.txtPagoCedula);
        txtPagoCedula.setVisibility(View.VISIBLE);
        txtPagoValorTotal = findViewById(R.id.txtPagoValorTotal);
        txtPagoValorTotal.setVisibility(View.VISIBLE);

        cedula = tvCedula.getText().toString().trim();
        transaccion.setCedulaRemitente(cedula);

        valorTotal = tvValorTotal.getText().toString().trim();
        transaccion.setValorTotal(valorTotal);

        tipoTransaccion = tvTipo.getText().toString().trim();
        transaccion.setTipo(tipoTransaccion);


        getAndSetIntentData();

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                corresponsal.setCorreo(sp.getCorresponsalActivo());
                corresponsal = mtd.leerCorresponsalPorCorreo(corresponsal);

                nuevoSaldoCliente();
                nuevoSaldoCorresponsal();

                transaccion.setCuentaCorresponsal(corresponsal.getCuenta());
                transaccion.setCedulaRemitente(cedula);

                String fecha = String.valueOf(mtd.fechaPrestamo());
                transaccion.setFecha(fecha);
                mtd.hora(transaccion);

                mtd.agregarTransaccion(transaccion);
                Toast.makeText(getApplicationContext(), "Retiro exitoso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ConfirmarRetiroCorresponsal.this, TransaccionExitosa.class);
                startActivity(intent);
                finish();

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmarRetiroCorresponsal.this, TransaccionRechazada.class);
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

        cedula = getIntent().getStringExtra("cedula");
        cliente.setCedula(cedula);

        saldo = getIntent().getStringExtra("saldo");
        cliente.setSaldo(saldo);

        valorTotal = getIntent().getStringExtra("valorTotal");
        transaccion.setValorTotal(valorTotal);

        valor = getIntent().getStringExtra("valor");
        transaccion.setValor(valor);

        tipoTransaccion = "Retirar";
        transaccion.setTipo(tipoTransaccion);
        //inflado de los TextView

        tvCedula.setText(cedula);
        tvValor.setText(valor);
        tvValorTotal.setText(valorTotal);
        tvTipo.setText(tipoTransaccion);
        tvValorComision.setText(comision);
    }

    /**
     * nuevoSaldoCliente > resta del saldo del cliente el valor de la cuota y lo actualiza en la TABLA CLIENTE
     */
    void nuevoSaldoCliente() {

        int actualSaldoCliente = Integer.parseInt(cliente.getSaldo());
        int valorRetiro = Integer.parseInt(transaccion.getValor());
        int valorRetiroTotal = valorRetiro + 2000;
        int nuevoSaldoCliente = actualSaldoCliente - valorRetiroTotal;

        cliente.setSaldo(String.valueOf(nuevoSaldoCliente));
        mtd.actualizarSaldoCliente(cliente);
    }

    /**
     * nuevoSaldoCorresponsal > suma el saldo del cliente con el valor total del pago y lo actualiza en la TABLA CORRESPONSAL
     */
    void nuevoSaldoCorresponsal() {

        int actualSaldoCorresponsal = Integer.parseInt(corresponsal.getSaldo());
        int valorRetiro = Integer.parseInt(transaccion.getValor());
        int valorRetiroTotal = valorRetiro + 2000;
        int nuevoSaldoCorresponsal = actualSaldoCorresponsal + valorRetiroTotal;

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