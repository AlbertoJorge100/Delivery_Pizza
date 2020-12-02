package com.example.proyecto_delivery.Adaptadores;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.R;

public class ViewHolderProducto extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView lblProducto;
    private TextView lblDescripcion;
    //private Button btnCompartir;
    private TextView lblPrecio;
    private ImageView Imagen;
    public ViewHolderProducto(@NonNull View itemView) {
        super(itemView);
        this.lblProducto=itemView.findViewById(R.id.lblProducto);
        this.lblPrecio=itemView.findViewById(R.id.lblPrecio);
        this.lblDescripcion=itemView.findViewById(R.id.lblDescripcion);
        this.Imagen=itemView.findViewById(R.id.imgProducto);
    }

    public TextView getLblProducto() {
        return lblProducto;
    }

    public TextView getLblPrecio() {
        return lblPrecio;
    }

    public TextView getLblDescripcion() {
        return lblDescripcion;
    }

    //public Button getBtnCompartir(){return this.btnCompartir;}

    public ImageView getImagen(){return this.Imagen;}

    @Override
    public void onClick(View view) {
        //Toast.makeText(ViewHolderInformacion.this,"hola",Toast.LENGTH_SHORT).show();
    }
}
