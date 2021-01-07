package com.example.proyecto_delivery.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.Entidades.Carrito;
import com.example.proyecto_delivery.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorCarrito  extends RecyclerView.Adapter<ViewHolderCarrito> implements View.OnClickListener, View.OnLongClickListener{
    private List<Carrito> listaCarrito;
    private View.OnClickListener listener;
    private View.OnLongClickListener longListener;
    public AdaptadorCarrito(List<Carrito> listaCarrito, Opcion opc){
        this.listaCarrito=listaCarrito;
        this.opcion=opc;
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
    public void setOnLongClickListener(View.OnLongClickListener listener){
        this.longListener=listener;
    }

    public boolean onLongClick(View view){
        if(this.longListener!=null){
            this.longListener.onLongClick(view);
            return true;
        }else{
            return false;
        }

    }

    public enum Opcion {INSERTAR, ELIMINAR};
    private Opcion opcion=Opcion.INSERTAR;

    @NonNull
    @Override
    public ViewHolderCarrito onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.plantilla4_xml,parent,false);
        //Evento on click
        vista.setOnClickListener(this);
        vista.setOnLongClickListener(this);
        ViewHolderCarrito vhCarrito=new ViewHolderCarrito(vista);
        return vhCarrito;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCarrito holder, int position) {
        holder.getLblIdProducto().setText(Integer.toString(listaCarrito.get(position).getIdProducto()));
        holder.getLblProducto().setText(ValidarTitulo(listaCarrito.get(position).getProducto()));
        holder.getLblPrecio().setText("$ "+Double.toString(listaCarrito.get(position).getPrecioUnitario()));
        holder.getLblCantidad().setText(Integer.toString(listaCarrito.get(position).getCantidad()));
        holder.getLblTotal().setText("$ "+Double.toString(listaCarrito.get(position).getTotal()));
        holder.getLblDescripcion().setText(listaCarrito.get(position).getDescripcion());
        Picasso.get().load(listaCarrito.get(position).getImagen()).error(R.mipmap.ic_launcher_round)
                .fit().centerInside().into((ImageView)holder.getImagenProducto());

        if(this.opcion==Opcion.ELIMINAR){
            //Code make here
        }
    }
    private String ValidarTitulo(String titulo){
        if(titulo.length()>40){
            return titulo.substring(0,40);
        }else {
            return titulo;
        }
    }
    @Override
    public int getItemCount() {
        return this.listaCarrito.size();
    }


}
