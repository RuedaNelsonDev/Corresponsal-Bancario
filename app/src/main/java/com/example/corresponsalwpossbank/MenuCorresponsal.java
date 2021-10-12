package com.example.corresponsalwpossbank;

import static com.example.corresponsalwpossbank.constantes.Constantes.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.example.corresponsalwpossbank.adaptadores.AdaptadorMenuCorresponsal;
import com.example.corresponsalwpossbank.clasesCorresponsal.ActualizarCorresponsal;
import com.example.corresponsalwpossbank.clasesCorresponsal.ConsultarSaldoCorresponsal;
import com.example.corresponsalwpossbank.clasesCorresponsal.CrearClienteCorresponsal;
import com.example.corresponsalwpossbank.clasesCorresponsal.DepositoCorresponsal;
import com.example.corresponsalwpossbank.clasesCorresponsal.HistorialCorresponsal;
import com.example.corresponsalwpossbank.clasesCorresponsal.PagoTarjetaCorresponsal;
import com.example.corresponsalwpossbank.clasesCorresponsal.RetiroCorresponsal;
import com.example.corresponsalwpossbank.clasesCorresponsal.TransferenciaCorresponsal;
import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Corresponsal;
import com.example.corresponsalwpossbank.modelos.Menu;
import com.example.corresponsalwpossbank.modelos.SharedPreference;

import java.util.ArrayList;

public class MenuCorresponsal extends AppCompatActivity {
    Metodos mtd;
    SharedPreference sp;
    AdaptadorMenuCorresponsal adaptadorMenuCorresponsal;
    Corresponsal corresponsal;


//Datos superior
    TextView tvRango;
    TextView tvNombre;
    TextView tvApellido;
    TextView tvSaldo;


    RecyclerView rvFuncionesMenu;


    ArrayList<Menu> listaMenuCorres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mtd = new Metodos(this);
        sp = new SharedPreference(this);
        corresponsal = new Corresponsal();

        corresponsal.setCorreo(sp.getCorresponsalActivo());
        corresponsal = mtd.leerCorresponsalPorCorreo(corresponsal);

        rvFuncionesMenu = findViewById(R.id.rvMenu);

        tvRango = findViewById(R.id.tvRango);
        tvRango.setText("Corresponsal");
        tvNombre = findViewById(R.id.tvNombre);
        tvNombre.setText(corresponsal.getNombre());
        tvApellido = findViewById(R.id.tvApellido);
        tvApellido.setText(corresponsal.getApellido());
        tvSaldo = findViewById(R.id.tvSaldo);
        tvSaldo.setText("$ " + corresponsal.getSaldo());

        listaMenuCorres = new ArrayList<>();

        listaMenuCorres.add(new Menu(CORRESPONSAL_PAGO_TARJETA, CORRESPONSAL_PAGO_TARJETA_IMG));
        listaMenuCorres.add(new Menu(CORRESPONSAL_RETIRO, CORRESPONSAL_RETIRO_IMG));
        listaMenuCorres.add(new Menu(CORRESPONSAL_DEPOSITO, CORRESPONSAL_DEPOSITO_IMG));
        listaMenuCorres.add(new Menu(CORRESPONSAL_TRANSFERENCIA, CORRESPONSAL_TRANSFERENCIA_IMG));
        listaMenuCorres.add(new Menu(CORRESPONSAL_CONSULTAR_SALDO, CORRESPONSAL_CONSULTAR_SALDO_IMG));
        listaMenuCorres.add(new Menu(CORRESPONSAL_CREAR_CLIENTE, CORRESPONSAL_CREAR_CLIENTE_IMG));
        listaMenuCorres.add(new Menu(CORRESPONSAL_HISTORIAL, CORRESPONSAL_HISTORIAL_IMG));
        listaMenuCorres.add(new Menu(CORRESPONSAL_ACTUALIZAR, CORRESPONSAL_ACTUALIZAR_IMG));

        adaptadorMenuCorresponsal = new AdaptadorMenuCorresponsal(MenuCorresponsal.this, listaMenuCorres, MenuCorresponsal.this::clickListener);
        rvFuncionesMenu.setAdapter(adaptadorMenuCorresponsal);
        rvFuncionesMenu.setLayoutManager(new GridLayoutManager(MenuCorresponsal.this, 2));

    }

    public void clickListener(Menu menu) {

        switch (menu.getFuncionMenu()) {
            case CORRESPONSAL_PAGO_TARJETA:
                Intent intent = new Intent(this, PagoTarjetaCorresponsal.class);

                intent.putExtra("nombreCorresponsal", String.valueOf(corresponsal.getNombre()));
                intent.putExtra("apellidoCorresponsal", String.valueOf(corresponsal.getApellido()));
                intent.putExtra("rangoCorresponsal", String.valueOf("Corresponsal"));
                intent.putExtra("saldoCorresponsal", String.valueOf(corresponsal.getSaldo()));

                startActivity(intent);
                break;
            case CORRESPONSAL_RETIRO:
                intent = new Intent(this, RetiroCorresponsal.class);

                intent.putExtra("nombreCorresponsal", String.valueOf(corresponsal.getNombre()));
                intent.putExtra("apellidoCorresponsal", String.valueOf(corresponsal.getApellido()));
                intent.putExtra("rangoCorresponsal", String.valueOf("Corresponsal"));
                intent.putExtra("saldoCorresponsal", String.valueOf(corresponsal.getSaldo()));

                startActivity(intent);
                break;

            case CORRESPONSAL_DEPOSITO:
                intent = new Intent(this, DepositoCorresponsal.class);

                intent.putExtra("nombreCorresponsal", String.valueOf(corresponsal.getNombre()));
                intent.putExtra("apellidoCorresponsal", String.valueOf(corresponsal.getApellido()));
                intent.putExtra("rangoCorresponsal", String.valueOf("Corresponsal"));
                intent.putExtra("saldoCorresponsal", String.valueOf(corresponsal.getSaldo()));

                startActivity(intent);
                break;
            case CORRESPONSAL_TRANSFERENCIA:
                intent = new Intent(this, TransferenciaCorresponsal.class);

                intent.putExtra("nombreCorresponsal", String.valueOf(corresponsal.getNombre()));
                intent.putExtra("apellidoCorresponsal", String.valueOf(corresponsal.getApellido()));
                intent.putExtra("rangoCorresponsal", String.valueOf("Corresponsal"));
                intent.putExtra("saldoCorresponsal", String.valueOf(corresponsal.getSaldo()));

                startActivity(intent);
                break;
            case CORRESPONSAL_CONSULTAR_SALDO:
                intent = new Intent(this, ConsultarSaldoCorresponsal.class);
                startActivity(intent);
                break;

            case CORRESPONSAL_CREAR_CLIENTE:
                intent = new Intent(this, CrearClienteCorresponsal.class);
                startActivity(intent);

                break;

            case CORRESPONSAL_HISTORIAL:
                intent = new Intent(this, HistorialCorresponsal.class);
                startActivity(intent);
                break;

            case CORRESPONSAL_ACTUALIZAR:
                intent = new Intent(this, ActualizarCorresponsal.class);

                intent.putExtra("nombreCorresponsal", String.valueOf(corresponsal.getNombre()));
                intent.putExtra("apellidoCorresponsal", String.valueOf(corresponsal.getApellido()));
                intent.putExtra("rangoCorresponsal", String.valueOf("Corresponsal"));
                intent.putExtra("saldoCorresponsal", String.valueOf(corresponsal.getSaldo()));
                intent.putExtra("correoCorresponsal", String.valueOf(corresponsal.getCorreo()));
                intent.putExtra("contrasenaCorresponsal", String.valueOf(corresponsal.getContrasena()));
                startActivity(intent);
                break;

            default:

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}