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

import java.util.ArrayList;

public class AdaptadorMostrarCliente extends RecyclerView.Adapter<AdaptadorMostrarCliente.MyViewHolder> {

    private Context context;
    private ArrayList<Cliente> listaCliente;



    public AdaptadorMostrarCliente(Context context, ArrayList<Cliente> listaCliente) {
        this.context = context;
        this.listaCliente = listaCliente;
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
        Cliente cliente = listaCliente.get(position);

        holder.tvVerClienteId.setText(String.valueOf(cliente.getId()));
        holder.tvVerClienteNombre.setText(String.valueOf(cliente.getNombre()));
        holder.tvVerClienteCedula.setText(String.valueOf(cliente.getCedula()));
        holder.tvVerClienteSaldo.setText(String.valueOf("$" + cliente.getSaldo()));
        holder.tvVerClienteEstado.setText(String.valueOf(cliente.getEstado()));
        holder.linearVerClientes.setOnClickListener(new View.OnClickListener() {
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
        });


    }

    @Override
    public int getItemCount() {
        return listaCliente.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvVerClienteId;
        TextView tvVerClienteNombre;
        TextView tvVerClienteCedula;
        TextView tvVerClienteSaldo;
        TextView tvVerClienteEstado;

        LinearLayout linearVerClientes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVerClienteId = itemView.findViewById(R.id.tvVerId);
            tvVerClienteNombre = itemView.findViewById(R.id.tvVerNombre);
            tvVerClienteCedula = itemView.findViewById(R.id.tvVerCedula);
            tvVerClienteSaldo = itemView.findViewById(R.id.tvVerSaldo);
            tvVerClienteEstado = itemView.findViewById(R.id.tvVerCorresEstado);
            linearVerClientes = itemView.findViewById(R.id.linearCuenta);
        }
    }
}
