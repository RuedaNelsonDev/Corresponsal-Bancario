package com.example.corresponsalwpossbank.clasesCorresponsal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.corresponsalwpossbank.MenuCorresponsal;
import com.example.corresponsalwpossbank.MostrarTarjeta;
import com.example.corresponsalwpossbank.R;
import com.example.corresponsalwpossbank.RegistrarCliente;
import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Cliente;
import com.example.corresponsalwpossbank.modelos.Corresponsal;
import com.example.corresponsalwpossbank.modelos.Transaccion;
import com.google.android.material.textfield.TextInputEditText;

public class PagoTarjetaCorresponsal extends AppCompatActivity {

    Metodos mtd;
    Cliente cliente;
    Transaccion transaccion;
    Corresponsal corresponsal;


    TextInputEditText pagoTarjeta;
    TextInputEditText pagoCvv;
    TextInputEditText pagoVen;
    TextInputEditText pagoTarjetaNombre;
    TextInputEditText pagoTarjetaApellido;
    TextInputEditText pagoTarjetaValor;

    Spinner spncuotas;

    Button btnConfirmar;
    Button btnCancelar;

    //Datos superior
    TextView tvRango;
    TextView tvNombre;
    TextView tvApellido;
    TextView tvSaldo;
    String rangoCorresponsal;
    String nombreCorresponsal;
    String apellidoCorresponsal;
    String saldoCorresponsal;

    String tarjeta;
    String cvv;
    String ven;
    String nombre;
    String apellido;
    String cuotas;
    String estado;
    String valorTotal;
    String saldo;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_tarjeta_corresponsal);
        mtd = new Metodos(this);
        cliente = new Cliente();
        transaccion = new Transaccion();
        corresponsal = new Corresponsal();

        pagoTarjeta = findViewById(R.id.tietPagoTarjeta);
        pagoCvv = findViewById(R.id.tietPagoCvv);
        pagoVen = findViewById(R.id.tietPagoVen);
        pagoTarjetaNombre = findViewById(R.id.tietPagoTarjetaNombre);
        pagoTarjetaApellido = findViewById(R.id.tietPagoTarjetaApellido);
        pagoTarjetaValor = findViewById(R.id.tietPagoTarjetaValor);
        spncuotas = findViewById(R.id.spnPagoCuotas);


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


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cuotas, android.R.layout.simple_spinner_item);
        spncuotas.setAdapter(adapter);

        btnCancelar = findViewById(R.id.btnPagoTarjetaCancelar);
        btnConfirmar = findViewById(R.id.btnPagoTarjetaConfirmar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PagoTarjetaCorresponsal.this, MenuCorresponsal.class);
                startActivity(intent);
                finish();
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tarjeta = pagoTarjeta.getText().toString();
                cvv = pagoCvv.getText().toString();
                ven = pagoVen.getText().toString();
                nombre = pagoTarjetaNombre.getText().toString();
                apellido = pagoTarjetaApellido.getText().toString();
                cuotas = spncuotas.getSelectedItem().toString();
                valorTotal = pagoTarjetaValor.getText().toString();

                if (pagoTarjeta.getText().toString().equals("") || pagoCvv.getText().toString().equals("")
                        || pagoVen.getText().toString().equals("") || pagoTarjetaNombre.getText().toString().equals("")
                        || pagoTarjetaApellido.getText().toString().equals("") || pagoTarjetaValor.getText().toString().equals("")
                        || spncuotas.getSelectedItem().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();
                }

                cliente.setTarjeta(tarjeta);
                cliente.setCvv(cvv);
                cliente.setVen(ven);
                cliente.setNombre(nombre);
                cliente.setApellido(apellido);
                transaccion.setCuotas(cuotas);
                transaccion.setValorTotal(valorTotal);

                cliente = mtd.leerClientePorTarjeta(cliente);

                estado = cliente.getEstado();
                cliente.setEstado(estado);

                saldo = cliente.getSaldo();
                cliente.setSaldo(saldo);


                if (mtd.validarTarjetaCliente(cliente)) {

                    if (cliente.getEstado().equals("Habilitado")) {

                        int valorTransaccion = Integer.parseInt(transaccion.getValorTotal()) / Integer.parseInt(transaccion.getCuotas());
                        transaccion.setValor(String.valueOf(valorTransaccion));

                        int saldoDisponible = Integer.parseInt(cliente.getSaldo());

                        if (valorTransaccion < 1000000 && valorTransaccion > 10000) {
                            if (saldoDisponible > valorTransaccion) {
                                Intent intent = new Intent(PagoTarjetaCorresponsal.this, ConfirmarTarjetaCorresponsal.class);

                                intent.putExtra("id", String.valueOf(cliente.getId()));
                                intent.putExtra("tarjeta", String.valueOf(cliente.getTarjeta()));
                                intent.putExtra("nombre", String.valueOf(cliente.getNombre()));
                                intent.putExtra("apellido", String.valueOf(cliente.getApellido()));
                                intent.putExtra("cedula", String.valueOf(cliente.getCedula()));
                                intent.putExtra("saldo", String.valueOf(cliente.getSaldo()));
                                intent.putExtra("vencimiento", String.valueOf(cliente.getVen()));
                                intent.putExtra("cvv", String.valueOf(cliente.getCvv()));
                                intent.putExtra("cuotas", String.valueOf(transaccion.getCuotas()));
                                intent.putExtra("valorTotal", String.valueOf(transaccion.getValorTotal()));
                                intent.putExtra("valor", String.valueOf(transaccion.getValor()));

                                startActivity(intent);
                                finish();

                            } else {

                                Toast.makeText(getApplicationContext(), "Saldo del cliente insuficiente", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Toast.makeText(getApplicationContext(), "Valor del pago no permitido por los limites", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        Toast.makeText(getApplicationContext(), "Cliente deshabilitado, comuniquese con el Administrador ", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
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