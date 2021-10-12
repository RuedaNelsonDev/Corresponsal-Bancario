package com.example.corresponsalwpossbank.clasesCorresponsal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.corresponsalwpossbank.MenuCorresponsal;
import com.example.corresponsalwpossbank.R;

public class TransaccionRechazada extends AppCompatActivity {

    ImageView ivRechazada;
    TextView tvRechazada;

    Button btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion_resultado);
        ivRechazada = findViewById(R.id.ivRechazada);
        ivRechazada.setVisibility(View.VISIBLE);
        tvRechazada = findViewById(R.id. tvRechazada);
        tvRechazada.setVisibility(View.VISIBLE);
        btnSalir = findViewById(R.id.btnResultadoSalir);
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransaccionRechazada.this, MenuCorresponsal.class);
                startActivity(intent);
                finish();
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