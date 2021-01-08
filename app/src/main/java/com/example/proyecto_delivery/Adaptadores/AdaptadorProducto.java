package com.example.proyecto_delivery.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.Entidades.Producto;
import com.example.proyecto_delivery.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AdaptadorProducto extends RecyclerView.Adapter<ViewHolderProducto> implements View.OnClickListener{
    private List<Producto> listaInformacion;
    private View.OnClickListener listener;
        public AdaptadorProducto(List<Producto> listaInformacion){
        this.listaInformacion=listaInformacion;
    }

    @NonNull
    @Override
    public ViewHolderProducto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //vista donde se invoca el xml donde tenemos la informacion
        //Creamos la vista que representa el item y se enlaza con la informacion del ViewHolderInformacion
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.plantilla2_xml,parent,false);
        vista.setOnClickListener(this);
        ViewHolderProducto vhInformacion=new ViewHolderProducto(vista);
        return vhInformacion;
    }
    private String ValidarLongitud(int parametro,String titulo){
        switch(parametro){
            case 1:
                if(titulo.length()>65)
                    return titulo.substring(0,65);
            case 2:
                if(titulo.length()>100)
                    return titulo.substring(0,100);
            default:
                return titulo;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProducto holder, int position) {
        //Convirtiendo el precio a string para poder mostrar dos decimales
        double valor=listaInformacion.get(position).getPrecio();
        DecimalFormat df = new DecimalFormat("0.00");
        String precio = df.format(valor).replace(",", ".");

        holder.getLblIdProducto().setText(Integer.toString(listaInformacion.get(position).getIdProducto()));
        holder.getLblProducto().setText(ValidarLongitud(1,listaInformacion.get(position).getProducto()));
        holder.getLblPrecio().setText("$ "+precio);
        //Esta es la descripcion previa al producto
        holder.getLblDescripcion().setText(ValidarLongitud(2,listaInformacion.get(position).getDescripcion()));
        //holder.getImagen().setImageURI(Uri.parse(listaInformacion.get(position).getImagen()));
        Picasso.get().load(listaInformacion.get(position).getImagen()).error(R.mipmap.ic_launcher_round).fit().centerInside().into((ImageView) holder.getImagen());
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
