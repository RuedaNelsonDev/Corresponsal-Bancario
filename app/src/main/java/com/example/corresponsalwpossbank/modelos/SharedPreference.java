package com.example.corresponsalwpossbank.modelos;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    Context context;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    public SharedPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("bd_shared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void setCorresponsalActivo(String corresponsal){
        editor.putString("corresponsal", corresponsal);
        editor.apply();
    }

    public String getCorresponsalActivo(){
        return sharedPreferences.getString("corresponsal", "no encontrado");
    }

}
