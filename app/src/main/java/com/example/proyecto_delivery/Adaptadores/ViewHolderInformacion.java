package com.example.proyecto_delivery.Adaptadores;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.R;

public class ViewHolderInformacion extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView lblTitulo;
    private TextView lblSubtitulo;
    private TextView lblDescripcion;
    //private Button btnCompartir;
    private ImageView Imagen;
    public ViewHolderInformacion(@NonNull View itemView) {
        super(itemView);
        this.lblTitulo=itemView.findViewById(R.id.lblTitulo);
        this.lblSubtitulo=itemView.findViewById(R.id.lblSubtitulo);
        this.lblDescripcion=itemView.findViewById(R.id.lblDescripcion);
        //this.btnCompartir=itemView.findViewById(R.id.btnCompartir);
        this.Imagen=itemView.findViewById(R.id.imgProducto);
    }

    public TextView getLblTitulo() {
        return lblTitulo;
    }

    public TextView getLblSubtitulo() {
        return lblSubtitulo;
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
