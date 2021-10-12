package com.example.corresponsalwpossbank;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.corresponsalwpossbank.adaptadores.AdaptadorMostrarCliente;
import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Cliente;

import java.util.ArrayList;

public class MostrarCliente extends AppCompatActivity {
    AdaptadorMostrarCliente adaptadorMostrarCliente;
    Metodos mtd;

    //Datos superios
    TextView tvRango;
    TextView tvNombre;
    TextView tvApellido;
    TextView tvSaldo;

    Toolbar toolCreados;


    private ArrayList<Cliente> listaCliente;

    RecyclerView rvVerCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_creados);
        mtd = new Metodos(this);
        listaCliente = new ArrayList<>();

        toolCreados = findViewById(R.id.toolClientesCreados);
        toolCreados.setVisibility(View.VISIBLE);

        //inflar datos superior
        tvRango = findViewById(R.id.tvRango);
        tvRango.setText("Administrador");
        tvNombre = findViewById(R.id.tvNombre);
        tvNombre.setText("Banco");
        tvApellido = findViewById(R.id.tvApellido);
        tvApellido.setText("Wposs");
        tvSaldo = findViewById(R.id.tvSaldo);
        tvSaldo.setText("");

        rvVerCliente = findViewById(R.id.rvMostrarCreados);

        listaCliente =mtd.leerCliente();

        adaptadorMostrarCliente = new AdaptadorMostrarCliente(MostrarCliente.this, listaCliente);
    rvVerCliente.setAdapter(adaptadorMostrarCliente);
    rvVerCliente.setLayoutManager(new GridLayoutManager(MostrarCliente.this, 1));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuAdministrador.class);
        startActivity(intent);
    }
}