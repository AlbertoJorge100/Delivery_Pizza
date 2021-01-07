package com.example.proyecto_delivery.Adaptadores;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.R;

public class ViewHolderCarrito extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView lblIdProducto;
    private TextView lblProducto;
    private TextView lblPrecio;
    private TextView lblCantidad;
    private TextView lblTotal;
    private ImageView ImagenProducto;
    private TextView lblDescripcion;

    public ViewHolderCarrito(@NonNull View itemView) {
        super(itemView);
        this.lblIdProducto=itemView.findViewById(R.id.lblp4_IdProducto);
        this.lblProducto=itemView.findViewById(R.id.lblP4_Producto);
        this.lblPrecio=itemView.findViewById(R.id.lblP4_Unitario);
        this.lblCantidad=itemView.findViewById(R.id.lblP4_Cantidad);
        this.lblTotal=itemView.findViewById(R.id.lblP4_Total);
        this.lblDescripcion=itemView.findViewById(R.id.lblP4_Descripcion);
        this.ImagenProducto=itemView.findViewById(R.id.imgP4);
    }

    public TextView getLblProducto() {
        return lblProducto;
    }

    public TextView getLblPrecio() {
        return lblPrecio;
    }

    public TextView getLblCantidad() {
        return lblCantidad;
    }

    public TextView getLblTotal() {
        return lblTotal;
    }

    public TextView getLblDescripcion(){return this.lblDescripcion;}

    public ImageView getImagenProducto() {
        return ImagenProducto;
    }
    public void setLblIdProducto(TextView lblIdProducto){
        this.lblIdProducto=lblIdProducto;
    }
    public TextView getLblIdProducto(){
        return this.lblIdProducto;
    }

    public void setImagenProducto(ImageView imagenProducto) {
        ImagenProducto = imagenProducto;
    }

    @Override
    public void onClick(View view) {

    }
}
