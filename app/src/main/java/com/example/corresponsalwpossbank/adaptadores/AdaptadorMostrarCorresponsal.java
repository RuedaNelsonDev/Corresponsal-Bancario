package com.example.corresponsalwpossbank.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corresponsalwpossbank.EstadoCorresponsal;
import com.example.corresponsalwpossbank.R;
import com.example.corresponsalwpossbank.modelos.Corresponsal;

import java.util.ArrayList;

public class AdaptadorMostrarCorresponsal extends RecyclerView.Adapter<AdaptadorMostrarCorresponsal.MyViewHolder> {

    private Context context;
    private ArrayList<Corresponsal> listaCorresponsal;



    public AdaptadorMostrarCorresponsal(Context context, ArrayList<Corresponsal> listaCorresponsal) {
        this.context = context;
        this.listaCorresponsal = listaCorresponsal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila_ver_creados, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Corresponsal corresponsal = listaCorresponsal.get(position);

        holder.tvVerCorresId.setText(String.valueOf(corresponsal.getId()));
        holder.tvVerCorresNombre.setText(String.valueOf(corresponsal.getNombre()));
        holder.tvVerCorresCedula.setText(String.valueOf(corresponsal.getCedula()));
        holder.tvVerCorresSaldo.setText(String.valueOf("$ " + corresponsal.getSaldo()));
        holder.tvVerCorresEstado.setText(String.valueOf(corresponsal.getEstado()));
        holder.linearVerCorres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EstadoCorresponsal.class);
                intent.putExtra("id", String.valueOf(corresponsal.getId()));
                intent.putExtra("nombre", String.valueOf(corresponsal.getNombre()));
                intent.putExtra("apellido", String.valueOf(corresponsal.getApellido()));
                intent.putExtra("cedula", String.valueOf(corresponsal.getCedula()));
                intent.putExtra("cuenta", String.valueOf(corresponsal.getCuenta()));
                intent.putExtra("saldo", String.valueOf(corresponsal.getSaldo()));
                intent.putExtra("telefono", String.valueOf(corresponsal.getTelefono()));
                intent.putExtra("direccion", String.valueOf(corresponsal.getDireccion()));
                intent.putExtra("correo", String.valueOf(corresponsal.getCorreo()));
                intent.putExtra("estado", String.valueOf(corresponsal.getEstado()));

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listaCorresponsal.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvVerCorresId;
        TextView tvVerCorresNombre;
        TextView tvVerCorresCedula;
        TextView tvVerCorresSaldo;
        TextView tvVerCorresEstado;

        LinearLayout linearVerCorres;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVerCorresId = itemView.findViewById(R.id.tvVerId);
            tvVerCorresNombre = itemView.findViewById(R.id.tvVerNombre);
            tvVerCorresCedula = itemView.findViewById(R.id.tvVerCedula);
            tvVerCorresSaldo = itemView.findViewById(R.id.tvVerSaldo);
            tvVerCorresEstado = itemView.findViewById(R.id.tvVerCorresEstado);
            linearVerCorres = itemView.findViewById(R.id.linearCuenta);
        }
    }
}
