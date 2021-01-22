package com.example.proyecto_delivery.Adaptadores;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.R;

public class ViewHolderProducto extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView lblIdProducto;
    public TextView lblProducto;
    private TextView lblDescripcion;
    private TextView lblUnidades;
    private TextView lblPrecio;
    private ImageView Imagen;
    public ViewHolderProducto(@NonNull View itemView) {
        super(itemView);
        this.lblIdProducto=itemView.findViewById(R.id.P2_IdProducto);
        this.lblProducto=itemView.findViewById(R.id.lblProducto);
        this.lblPrecio=itemView.findViewById(R.id.lblPrecio);
        this.lblDescripcion=itemView.findViewById(R.id.lblDescripcion);
        this.Imagen=itemView.findViewById(R.id.imgProducto);
        this.lblUnidades=itemView.findViewById(R.id.lblUnidades);
    }
    public TextView getLblIdProducto(){return this.lblIdProducto;}
    public TextView getLblProducto() {
        return lblProducto;
    }

    public TextView getLblPrecio() {
        return lblPrecio;
    }

    public TextView getLblDescripcion() {
        return lblDescripcion;
    }

    public ImageView getImagen(){return this.Imagen;}

    public TextView getLblUnidades(){return this.lblUnidades;}

    @Override
    public void onClick(View view) {
        //Toast.makeText(ViewHolderInformacion.this,"hola",Toast.LENGTH_SHORT).show();
    }
}
