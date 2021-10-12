package com.example.corresponsalwpossbank.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.corresponsalwpossbank.MenuCorresponsal;
import com.example.corresponsalwpossbank.R;
import com.example.corresponsalwpossbank.modelos.Menu;

import java.util.ArrayList;

public class AdaptadorMenuCorresponsal extends RecyclerView.Adapter<AdaptadorMenuCorresponsal.MyViewHolder> {

    MenuClickListener menuClickListener;
    private Context context;
    ArrayList<Menu> listaMenuCorres;

    public AdaptadorMenuCorresponsal(Context context, ArrayList<Menu> listaMenuCorres, MenuClickListener menuClickListener){
        this.context = context;
        this.listaMenuCorres= listaMenuCorres;
        this.menuClickListener = menuClickListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.fila_menu_funcion, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Menu menu = listaMenuCorres.get(position);
        holder.tvMenuFuncion.setText(menu.getFuncionMenu());
        Glide.with(context)
                .load(String.valueOf(menu.getImagenMenu()))
                .into(holder.ivMenuFuncion);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuClickListener.clickListener(menu);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaMenuCorres.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView ivMenuFuncion;
        TextView tvMenuFuncion;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            ivMenuFuncion = itemView.findViewById(R.id.ivMenuFuncion);
            tvMenuFuncion = itemView.findViewById(R.id.tvMenuFuncion);
            linearLayout = itemView.findViewById(R.id.linearLayoutMenu);

        }
    }


}
