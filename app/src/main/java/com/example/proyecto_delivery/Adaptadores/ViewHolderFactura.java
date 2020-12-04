package com.example.proyecto_delivery.Adaptadores;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.R;

public class ViewHolderFactura extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView lblFecha;
    private TextView lblIdFactura;
    private TextView lblCantidad;
    private TextView lblTotal;
    private ImageView ImagenFactura;
    public ViewHolderFactura(@NonNull View itemView) {
        super(itemView);
        this.lblIdFactura=itemView.findViewById(R.id.lblIdFactura);
        this.lblFecha=itemView.findViewById(R.id.lblFechaCompra);
        this.lblCantidad=itemView.findViewById(R.id.lblCantidadProductos);
        this.lblTotal=itemView.findViewById(R.id.lblTotalPagar);
        this.ImagenFactura=itemView.findViewById(R.id.imgCompra);
    }

    @Override
    public void onClick(View view) {
        //Toast.makeText(ViewHolderInformacion.this,"hola",Toast.LENGTH_SHORT).show();
    }

    public TextView getLblFecha() {
        return lblFecha;
    }

    public void setLblFecha(TextView lblFecha) {
        this.lblFecha = lblFecha;
    }

    public TextView getLblCantidad() {
        return lblCantidad;
    }

    public void setLblCantidad(TextView lblCantidad) {
        this.lblCantidad = lblCantidad;
    }

    public TextView getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(TextView lblTotal) {
        this.lblTotal = lblTotal;
    }

    public ImageView getImagenFactura() {
        return ImagenFactura;
    }

    public void setImagenFactura(ImageView imagen) {
        ImagenFactura = imagen;
    }

    public TextView getLblIdFactura() {
        return lblIdFactura;
    }

    public void setLblIdFactura(TextView lblIdFactura) {
        this.lblIdFactura = lblIdFactura;
    }
}
