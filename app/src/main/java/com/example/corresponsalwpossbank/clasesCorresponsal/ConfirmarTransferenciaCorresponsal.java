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

public class ConfirmarTransferenciaCorresponsal extends AppCompatActivity {
//menos 1000 al valor transferencia q se le pasan al corresponsal

    Metodos mtd;
    Cliente cliente;
    Transaccion transaccion;
    Corresponsal corresponsal;
    SharedPreference sp;

    TextView tvCedula;
    TextView tvValorPagar;
    TextView tvValorTransferencia;
    TextView tvTipo;
    Button btnPagar;
    Button btnCancelar;

    TextView txtPagoCedula;
    TextView txtPagoValorTotal;


    String id;
    String id2;
    String valorPagar;
    String valorTransferencia;
    String tipoTransaccion;
    String cedula1;
    String cedula2;
    String saldo1;
    String saldo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pago_corresponsal);
        mtd = new Metodos(this);
        cliente = new Cliente();
        transaccion = new Transaccion();
        corresponsal = new Corresponsal();
        sp = new SharedPreference(this);

        tvCedula = findViewById(R.id.tvPagoCedula);
        tvCedula.setVisibility(View.VISIBLE);
        tvValorPagar = findViewById(R.id.tvPagoConfirmValorTotal);
        tvValorPagar.setVisibility(View.VISIBLE);
        tvValorTransferencia = findViewById(R.id.tvPagoConfirmValor);
        tvValorTransferencia.setVisibility(View.VISIBLE);
        tvTipo = findViewById(R.id.tvPagoTipo);
        btnPagar = findViewById(R.id.btnPagoConfirmPagar);
        btnCancelar = findViewById(R.id.btnPagoConfirmCancelar);

        txtPagoCedula = findViewById(R.id.txtPagoCedula);
        txtPagoCedula.setVisibility(View.VISIBLE);
        txtPagoValorTotal = findViewById(R.id.txtPagoValorTotal);
        txtPagoValorTotal.setVisibility(View.VISIBLE);

        cedula1 = tvCedula.getText().toString().trim();
        transaccion.setCedulaRemitente(cedula1);

        valorTransferencia = tvValorTransferencia.getText().toString().trim();
        transaccion.setValorTotal(valorTransferencia);

        tipoTransaccion = tvTipo.getText().toString().trim();
        transaccion.setTipo(tipoTransaccion);

        getAndSetIntentData();

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                corresponsal.setCorreo(sp.getCorresponsalActivo());
                corresponsal = mtd.leerCorresponsalPorCorreo(corresponsal);

                nuevoSaldoCliente1();
                nuevoSaldoCliente2();
                nuevoSaldoCorresponsal();

                transaccion.setCuentaCorresponsal(corresponsal.getCuenta());
                transaccion.setCedulaRemitente(cedula1);
                transaccion.setCedulaReceptor(cedula2);
                String comision = "1000";
                transaccion.setComision(comision);

                String fecha = String.valueOf(mtd.fechaPrestamo());
                transaccion.setFecha(fecha);
                mtd.hora(transaccion);

                mtd.agregarTransaccion(transaccion);
                Toast.makeText(getApplicationContext(), "Pago exitoso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ConfirmarTransferenciaCorresponsal.this, TransaccionExitosa.class);
                startActivity(intent);
                finish();

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmarTransferenciaCorresponsal.this, TransaccionRechazada.class);
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

        id2 = getIntent().getStringExtra("id2");
        cliente.setId2(id2);

        cedula1 = getIntent().getStringExtra("cedula1");
        cliente.setCedula(cedula1);

        cedula2 = getIntent().getStringExtra("cedula2");
        transaccion.setCedulaReceptor(cedula2);

        saldo1 = getIntent().getStringExtra("saldo1");
        cliente.setSaldo(saldo1);

        saldo2 = getIntent().getStringExtra("saldo2");
        cliente.setSaldo2(saldo2);

        valorPagar = getIntent().getStringExtra("valorPagar");
        transaccion.setValorTotal(valorPagar);

        valorTransferencia = getIntent().getStringExtra("valorTransferencia");
        transaccion.setValor(valorTransferencia);

        tipoTransaccion = "Transferir";
        transaccion.setTipo(tipoTransaccion);
        //inflado de los TextView

        tvCedula.setText(cedula1);
        tvValorPagar.setText(valorPagar);
        tvValorTransferencia.setText(valorTransferencia);
        tvTipo.setText(tipoTransaccion);
    }


    /**
     * nuevoSaldoCliente2 > resta del saldo del cliente el valor de la cuota y lo actualiza en la TABLA CLIENTE
     */
    void nuevoSaldoCliente1() {

        int actualSaldoCliente1 = Integer.parseInt(cliente.getSaldo());
        int valorTransferecia = Integer.parseInt(transaccion.getValorTotal());
        int nuevoSaldoCliente1 = actualSaldoCliente1 - valorTransferecia;

        cliente.setId(id);
        cliente.setSaldo(String.valueOf(nuevoSaldoCliente1));
        mtd.actualizarSaldoCliente(cliente);
    }

    /**
     * nuevoSaldoCliente2 > resta del saldo del cliente el valor de la cuota y lo actualiza en la TABLA CLIENTE
     */
    void nuevoSaldoCliente2() {

        int actualSaldoCliente2 = Integer.parseInt(cliente.getSaldo2());
        int valorTransferencia = Integer.parseInt(transaccion.getValor());
        int nuevoSaldoCliente2 = actualSaldoCliente2 + valorTransferencia;

        cliente.setId(cliente.getId2());
        cliente.setSaldo(String.valueOf(nuevoSaldoCliente2));
        mtd.actualizarSaldoCliente(cliente);
    }

    /**
     * nuevoSaldoCorresponsal > suma el saldo del cliente con el valor total del pago y lo actualiza en la TABLA CORRESPONSAL
     */
    void nuevoSaldoCorresponsal() {

        int actualSaldoCorresponsal = Integer.parseInt(corresponsal.getSaldo());
        int nuevoSaldoCorresponsal = actualSaldoCorresponsal + 1000;

        corresponsal.setSaldo(String.valueOf(nuevoSaldoCorresponsal));
        mtd.actualizarSaldoCorresponsal(corresponsal);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, TransferenciaCorresponsal.class);
        startActivity(intent);
        finish();
    }
}