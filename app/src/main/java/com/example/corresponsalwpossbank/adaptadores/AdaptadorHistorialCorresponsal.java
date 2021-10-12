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

import com.example.corresponsalwpossbank.EstadoCliente;
import com.example.corresponsalwpossbank.R;
import com.example.corresponsalwpossbank.modelos.Cliente;
import com.example.corresponsalwpossbank.modelos.Transaccion;

import java.util.ArrayList;

public class AdaptadorHistorialCorresponsal extends RecyclerView.Adapter<AdaptadorHistorialCorresponsal.MyViewHolder> {
    private Context context;
    private ArrayList<Transaccion> listaHistorial;


    public AdaptadorHistorialCorresponsal(Context context, ArrayList<Transaccion> listaHistorial) {
        this.context = context;
        this.listaHistorial = listaHistorial;
    }

    @NonNull
    @Override
    public AdaptadorHistorialCorresponsal.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila_historial, parent, false);
        return new AdaptadorHistorialCorresponsal.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdaptadorHistorialCorresponsal.MyViewHolder holder, int position) {
        Transaccion transaccion = listaHistorial.get(position);

        holder.tvHistorialId.setText(String.valueOf(transaccion.getId()));
        holder.tvHistorialTipo.setText(String.valueOf(transaccion.getTipo()));
        holder.tvHistorialValorTotal.setText(String.valueOf("$" + transaccion.getValorTotal()));
        holder.tvHistorialFecha.setText(String.valueOf(transaccion.getFecha()));
        holder.tvHistorialHora.setText(String.valueOf(transaccion.getHora()));

        if (transaccion.getTarjeta() == null) {
            holder.tvHistorialCedulaRemitente.setText(String.valueOf(transaccion.getCedulaRemitente()));
            holder.tvHistorialCedulaRemitente.setVisibility(View.VISIBLE);
        } else {
            holder.tvHistorialTarjeta.setText(String.valueOf(transaccion.getTarjeta()));
            holder.tvHistorialTarjeta.setVisibility(View.VISIBLE);
        }
        /*holder.lnHistorial.setOnClickListener(new View.OnClickListener() {

            //TextView tvHistorialId;
            //        TextView tvHistorialTipo;
            //        TextView tvHistorialValorTotal;
            //        TextView tvHistorialTarjeta;
            //        TextView tvHistorialCedulaRemitente;
            //        TextView tvHistorialFecha;
            //        TextView tvHistorialHora;
            //
            //        LinearLayout lnHistorial;
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EstadoCliente.class);
                intent.putExtra("id", String.valueOf(cliente.getId()));
                intent.putExtra("nombre", String.valueOf(cliente.getNombre()));
                intent.putExtra("apellido", String.valueOf(cliente.getApellido()));
                intent.putExtra("cedula", String.valueOf(cliente.getCedula()));
                intent.putExtra("saldo", String.valueOf(cliente.getSaldo()));
                intent.putExtra("tarjeta", String.valueOf(cliente.getTarjeta()));
                intent.putExtra("tipo", String.valueOf(cliente.getTarjetaTipo()));
                intent.putExtra("vencimiento", String.valueOf(cliente.getVen()));
                intent.putExtra("cvv", String.valueOf(cliente.getCvv()));
                intent.putExtra("estado", String.valueOf(cliente.getEstado()));

                context.startActivity(intent);
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return listaHistorial.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvHistorialId;
        TextView tvHistorialTipo;
        TextView tvHistorialValorTotal;
        TextView tvHistorialTarjeta;
        TextView tvHistorialCedulaRemitente;
        TextView tvHistorialFecha;
        TextView tvHistorialHora;

        LinearLayout lnHistorial;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHistorialId = itemView.findViewById(R.id.tvHistorialId);
            tvHistorialTipo = itemView.findViewById(R.id.tvHistorialTipo);
            tvHistorialValorTotal = itemView.findViewById(R.id.tvHistorialValorTotal);
            tvHistorialTarjeta = itemView.findViewById(R.id.tvHistorialTarjeta);
            tvHistorialCedulaRemitente = itemView.findViewById(R.id.tvHistorialCedulaRemitente);
            tvHistorialFecha = itemView.findViewById(R.id.tvHistorialFecha);
            tvHistorialHora = itemView.findViewById(R.id.tvHistorialHora);
            lnHistorial = itemView.findViewById(R.id.lnHistorial);
        }
    }
}
