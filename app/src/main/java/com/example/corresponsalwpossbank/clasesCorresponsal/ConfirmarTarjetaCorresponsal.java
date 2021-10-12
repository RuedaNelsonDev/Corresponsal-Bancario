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
import com.google.android.material.textfield.TextInputLayout;

public class ConfirmarTarjetaCorresponsal extends AppCompatActivity {

    Metodos mtd;
    Cliente cliente;
    Transaccion transaccion;
    Corresponsal corresponsal;
    SharedPreference sp;

    TextView tvNombre;
    TextView tvApellido;
    TextView tvValorTotal;
    TextView tvCuota;
    TextView tvValor;
    TextView tvTipo;
    TextView tvPagoConfirmValorTotal;
    TextInputEditText tietPin;
    Button btnPagar;
    Button btnCancelar;

    TextInputLayout tilConfimPin;
    TextView txtPagoCuotas;
    TextView txtPagoCuotasValor;
    TextView txtPagoPin;

    String nombre;
    String apellido;
    String valorTotal;
    String cuota;
    String valor;
    String pin;
    String tipoTransaccion;
    String id;
    String cedula;
    String saldo;
    String vencimiento;
    String cvv;
    String tarjeta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_pago_corresponsal);

        mtd = new Metodos(this);
        cliente = new Cliente();
        transaccion = new Transaccion();
        corresponsal = new Corresponsal();
        sp = new SharedPreference(this);

        tvNombre = findViewById(R.id.tvPagoConfirmNombre);
        tvNombre.setVisibility(View.VISIBLE);
        tvApellido = findViewById(R.id.tvPagoConfirmApellido);
        tvApellido.setVisibility(View.VISIBLE);
        tvValorTotal = findViewById(R.id.tvPagoConfirmValor);
        tvValorTotal.setVisibility(View.VISIBLE);
        tvPagoConfirmValorTotal = findViewById(R.id.tvPagoConfirmValorTotal);
        tvCuota = findViewById(R.id.tvPagoConfirmCuotas);
        tvCuota.setVisibility(View.VISIBLE);
        tvValor = findViewById(R.id.tvPagoConfirmValorCuota);
        tvValor.setVisibility(View.VISIBLE);
        tietPin = findViewById(R.id.tietPagoConfirmPin);
        tietPin.setVisibility(View.VISIBLE);
        tvTipo = findViewById(R.id.tvPagoTipo);
        btnPagar = findViewById(R.id.btnPagoConfirmPagar);
        btnCancelar = findViewById(R.id.btnPagoConfirmCancelar);

        txtPagoCuotas = findViewById(R.id.txtPagoCuotas);
        txtPagoCuotas.setVisibility(View.VISIBLE);
        txtPagoCuotasValor = findViewById(R.id.txtPagoCuotasValor);
        txtPagoCuotasValor.setVisibility(View.VISIBLE);
        txtPagoPin = findViewById(R.id.txtPagoPin);
        txtPagoPin.setVisibility(View.VISIBLE);
        tilConfimPin = findViewById(R.id.tilPagoConfirmPin);
        tilConfimPin.setVisibility(View.VISIBLE);

        nombre = tvNombre.getText().toString().trim();
        cliente.setNombre(nombre);

        apellido = tvApellido.getText().toString().trim();
        cliente.setApellido(apellido);

        valorTotal = tvValorTotal.getText().toString().trim();
        transaccion.setValorTotal(valorTotal);

        cuota = tvCuota.getText().toString().trim();
        transaccion.setCuotas(cuota);

        valor = tvValor.getText().toString().trim();
        transaccion.setValor(valor);

        tipoTransaccion = tvTipo.getText().toString().trim();
        transaccion.setTipo(tipoTransaccion);

        getAndSetIntentData();

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pin = tietPin.getText().toString();
                cliente.setPin(pin);

                if (mtd.validarPin(cliente)) {

                    corresponsal.setCorreo(sp.getCorresponsalActivo());
                    corresponsal = mtd.leerCorresponsalPorCorreo(corresponsal);

                    nuevoSaldoCliente();

                    nuevoSaldoCorresponsal();

                    transaccion.setCuentaCorresponsal(corresponsal.getCuenta());
                    transaccion.setCedulaRemitente(cedula);
                    transaccion.setTarjeta(tarjeta);

                    String fecha = String.valueOf(mtd.fechaPrestamo());
                    transaccion.setFecha(fecha);
                    mtd.hora(transaccion);

                    mtd.agregarTransaccion(transaccion);
                    Toast.makeText(getApplicationContext(), "Pago exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConfirmarTarjetaCorresponsal.this, TransaccionExitosa.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(getApplicationContext(), "El pin ingresado es incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmarTarjetaCorresponsal.this, TransaccionRechazada.class);
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

        tarjeta = getIntent().getStringExtra("tarjeta");
        cliente.setTarjeta(tarjeta);

        nombre = getIntent().getStringExtra("nombre");
        cliente.setNombre(nombre);

        apellido = getIntent().getStringExtra("apellido");
        cliente.setApellido(apellido);

        cedula = getIntent().getStringExtra("cedula");
        cliente.setCedula(cedula);

        saldo = getIntent().getStringExtra("saldo");
        cliente.setSaldo(saldo);

        vencimiento = getIntent().getStringExtra("vencimiento");
        cliente.setVen(vencimiento);

        cvv = getIntent().getStringExtra("cvv");
        cliente.setCvv(cvv);

        cuota = getIntent().getStringExtra("cuotas");
        transaccion.setCuotas(cuota);

        valorTotal = getIntent().getStringExtra("valorTotal");
        transaccion.setValorTotal(valorTotal);

        valor = getIntent().getStringExtra("valor");
        transaccion.setValor(valor);

        tipoTransaccion = "Pagar con tarjeta";
        transaccion.setTipo(tipoTransaccion);
        //inflado de los TextView

        tvNombre.setText(nombre);
        tvApellido.setText(apellido);
        tvValorTotal.setText(valorTotal);
        tvCuota.setText(cuota);
        tvValor.setText(valor);
        tvPagoConfirmValorTotal.setText(valor);
        tvTipo.setText(tipoTransaccion);
    }

    /**
     * nuevoSaldoCliente > resta del saldo del cliente el valor de la cuota y lo actualiza en la TABLA CLIENTE
     */
    void nuevoSaldoCliente() {

        int actualSaldoCliente = Integer.parseInt(cliente.getSaldo());
        int valorCuota = Integer.parseInt(transaccion.getValor());
        int nuevoSaldoCliente = actualSaldoCliente - valorCuota;

        cliente.setSaldo(String.valueOf(nuevoSaldoCliente));
        mtd.actualizarSaldoCliente(cliente);
    }

    /**
     * nuevoSaldoCorresponsal > suma el saldo del cliente con el valor total del pago y lo actualiza en la TABLA CORRESPONSAL
     */
    void nuevoSaldoCorresponsal() {

        int actualSaldoCorresponsal = Integer.parseInt(corresponsal.getSaldo());
        int pagoValorTotal = Integer.parseInt(transaccion.getValorTotal());
        int nuevoSaldoCorresponsal = actualSaldoCorresponsal + pagoValorTotal;

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

