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

import com.example.corresponsalwpossbank.adaptadores.AdaptadorMostrarCorresponsal;
import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Corresponsal;

import java.util.ArrayList;

public class MostrarCorresponsal extends AppCompatActivity {
    AdaptadorMostrarCorresponsal adaptadorMostrarCorresponsal;
    Metodos mtd;


    private ArrayList<Corresponsal> listaCorresponsal;

    TextView tvRango;
    TextView tvNombre;
    TextView tvApellido;
    TextView tvSaldo;

    Toolbar toolCreados;

    RecyclerView rvVerCorresponsal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_creados);
        mtd = new Metodos(this);
        listaCorresponsal = new ArrayList<>();
        rvVerCorresponsal = findViewById(R.id.rvMostrarCreados);

        toolCreados = findViewById(R.id.toolCorresCreados);
        toolCreados.setVisibility(View.VISIBLE);

        tvRango = findViewById(R.id.tvRango);
        tvRango.setText("Administrador");
        tvNombre = findViewById(R.id.tvNombre);
        tvNombre.setText("Banco");
        tvApellido = findViewById(R.id.tvApellido);
        tvApellido.setText("Wposs");
        tvSaldo = findViewById(R.id.tvSaldo);
        tvSaldo.setText("");

        listaCorresponsal = mtd.leerCorresponsal();

        adaptadorMostrarCorresponsal = new AdaptadorMostrarCorresponsal(MostrarCorresponsal.this, listaCorresponsal);
        rvVerCorresponsal.setAdapter(adaptadorMostrarCorresponsal);
        rvVerCorresponsal.setLayoutManager(new GridLayoutManager(MostrarCorresponsal.this, 1));
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