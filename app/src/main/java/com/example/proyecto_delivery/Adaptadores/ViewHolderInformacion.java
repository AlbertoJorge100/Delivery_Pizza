package com.example.proyecto_delivery.Adaptadores;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.R;

public class ViewHolderInformacion extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView lblIdMenu;
    public TextView lblTitulo;
    private ImageView Imagen;

    public ViewHolderInformacion(@NonNull View itemView) {
        super(itemView);
        this.lblIdMenu=itemView.findViewById(R.id.lblIdItem);
        this.lblTitulo=itemView.findViewById(R.id.lblTitulo);
        this.Imagen=itemView.findViewById(R.id.imgProducto);
    }

    public TextView getLblTitulo() {
        return lblTitulo;
    }

    public ImageView getImagen(){return this.Imagen;}

    public TextView getLblIdMenu(){return this.lblIdMenu;}
    @Override
    public void onClick(View view) {
        //Toast.makeText(ViewHolderInformacion.this,"hola",Toast.LENGTH_SHORT).show();
    }
}
