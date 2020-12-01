package com.example.proyecto_delivery.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.Entidades.Informacion;
import com.example.proyecto_delivery.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdaptadorInformacion extends RecyclerView.Adapter<ViewHolderInformacion> implements View.OnClickListener{
    private List<Informacion> listaInformacion;
    private View.OnClickListener listener;
    public AdaptadorInformacion(List<Informacion> listaInformacion){
        this.listaInformacion=listaInformacion;
    }

    @NonNull
    @Override
    public ViewHolderInformacion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //vista donde se invoca el xml donde tenemos la informacion
        //Creamos la vista que representa el item y se enlaza con la informacion del ViewHolderInformacion
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.plantilla_xml,parent,false);
        vista.setOnClickListener(this);
        ViewHolderInformacion vhInformacion=new ViewHolderInformacion(vista);
        return vhInformacion;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInformacion holder, int position) {
        //aqui se une la informacion del view holder con la data

        holder.getLblTitulo().setText(listaInformacion.get(position).getTitulo());
        holder.getLblSubtitulo().setText(listaInformacion.get(position).getSubTitulo());
        holder.getLblDescripcion().setText(listaInformacion.get(position).getDescripcion());
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
