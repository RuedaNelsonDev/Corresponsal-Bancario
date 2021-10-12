package com.example.corresponsalwpossbank;

import static com.example.corresponsalwpossbank.constantes.Constantes.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.corresponsalwpossbank.adaptadores.AdaptadorMenuAdmin;
import com.example.corresponsalwpossbank.modelos.Menu;

import java.util.ArrayList;

public class MenuAdministrador extends AppCompatActivity {

    AdaptadorMenuAdmin adaptadorMenuAdmin;

    //Datos superios
    TextView tvRango;
    TextView tvNombre;
    TextView tvApellido;
    TextView tvSaldo;

    RecyclerView rvFuncionesMenu;

    ArrayList<Menu> listaMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //inflar datos superior
        tvRango = findViewById(R.id.tvRango);
        tvRango.setText("Administrador");
        tvNombre = findViewById(R.id.tvNombre);
        tvNombre.setText("Banco");
        tvApellido = findViewById(R.id.tvApellido);
        tvApellido.setText("Wposs");
        tvSaldo = findViewById(R.id.tvSaldo);
        tvSaldo.setText("");


        rvFuncionesMenu = findViewById(R.id.rvMenu);


        listaMenu = new ArrayList<>();

        listaMenu.add(new Menu(REGISTRAR_CORRESPONSAL, REGISTRAR_CORRESPONSAL_IMG));
        listaMenu.add(new Menu(REGISTRAR_CLIENTE, REGISTRAR_CLIENTE_IMG));
        listaMenu.add(new Menu(MOSTRAR_CORRESPONSAL, MOSTAR_CORRESPONSAL_IMG));
        listaMenu.add(new Menu(MOSTRAR_CLIENTE, MOSTRAR_CLIENTE_IMG));

        adaptadorMenuAdmin = new AdaptadorMenuAdmin(MenuAdministrador.this, listaMenu, MenuAdministrador.this::clickListener);
        rvFuncionesMenu.setAdapter(adaptadorMenuAdmin);
        rvFuncionesMenu.setLayoutManager(new GridLayoutManager(MenuAdministrador.this, 2));

    }

    public void clickListener(Menu menu) {
        switch (menu.getFuncionMenu()) {
            case REGISTRAR_CORRESPONSAL:
                Intent intent = new Intent(this, RegistrarCorresponsal.class);
                startActivity(intent);
                break;
            case REGISTRAR_CLIENTE:
                intent = new Intent(this, RegistrarCliente.class);
                startActivity(intent);
                break;

            case MOSTRAR_CORRESPONSAL:
                intent = new Intent(this, MostrarCorresponsal.class);
                startActivity(intent);
                break;
            case MOSTRAR_CLIENTE:
                intent = new Intent(this, MostrarCliente.class);
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