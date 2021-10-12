package com.example.corresponsalwpossbank.clasesCorresponsal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.corresponsalwpossbank.MenuCorresponsal;
import com.example.corresponsalwpossbank.MostrarCorresponsal;
import com.example.corresponsalwpossbank.R;
import com.example.corresponsalwpossbank.adaptadores.AdaptadorHistorialCorresponsal;
import com.example.corresponsalwpossbank.adaptadores.AdaptadorMostrarCorresponsal;
import com.example.corresponsalwpossbank.metodos.Metodos;
import com.example.corresponsalwpossbank.modelos.Corresponsal;
import com.example.corresponsalwpossbank.modelos.Transaccion;

import java.util.ArrayList;

public class HistorialCorresponsal extends AppCompatActivity {

    AdaptadorHistorialCorresponsal adaptadorHistorialCorresponsal;
    Metodos mtd;


    private ArrayList<Transaccion> listaHistorial;

    TextView tvRango;
    TextView tvNombre;
    TextView tvApellido;
    TextView tvSaldo;

    RecyclerView rvHistorialTransaccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_corresponsal);
        mtd = new Metodos(this);
        listaHistorial = new ArrayList<>();
        rvHistorialTransaccion = findViewById(R.id.rvHistorialTransaccion);

        listaHistorial = mtd.leerTransaccion();

        adaptadorHistorialCorresponsal = new AdaptadorHistorialCorresponsal(HistorialCorresponsal.this, listaHistorial);
        rvHistorialTransaccion.setAdapter(adaptadorHistorialCorresponsal);
        rvHistorialTransaccion.setLayoutManager(new GridLayoutManager(HistorialCorresponsal.this, 1));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MenuCorresponsal.class);
        startActivity(intent);
        finish();
    }
}