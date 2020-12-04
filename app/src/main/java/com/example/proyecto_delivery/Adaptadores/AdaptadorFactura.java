package com.example.proyecto_delivery.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.Entidades.Factura;
import com.example.proyecto_delivery.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorFactura extends RecyclerView.Adapter<ViewHolderFactura> implements View.OnClickListener{
    private List<Factura> listaInformacion;
    private View.OnClickListener listener;
    public AdaptadorFactura(List<Factura> listaInformacion){
        this.listaInformacion=listaInformacion;
    }

    @NonNull
    @Override
    public ViewHolderFactura onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //vista donde se invoca el xml donde tenemos la informacion
        //Creamos la vista que representa el item y se enlaza con la informacion del ViewHolderInformacion
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.plantilla3_xml,parent,false);
        vista.setOnClickListener(this);
        ViewHolderFactura vhInformacion=new ViewHolderFactura(vista);
        return vhInformacion;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFactura holder, int position) {
        holder.getLblIdFactura().setText(Integer.toString(listaInformacion.get(position).getIdFactura()));
        holder.getLblFecha().setText(listaInformacion.get(position).getFecha().substring(0,10));
        holder.getLblCantidad().setText(Integer.toString(listaInformacion.get(position).getCantidad()));
        holder.getLblTotal().setText("$ "+Double.toString(listaInformacion.get(position).getTotal()));
        //holder.getImagen().setImageURI(Uri.parse(listaInformacion.get(position).getImagen()));
        Picasso.get().load(listaInformacion.get(position).getImagen()).error(R.mipmap.ic_launcher_round).fit().centerInside().into((ImageView) holder.getImagenFactura());
    }

    @Override
    public int getItemCount() {
        //retorna el numero de items que tiene el reciclerview
        return listaInformacion.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View view) {
        if(this.listener!=null){
            this.listener.onClick(view);
        }
    }
}
