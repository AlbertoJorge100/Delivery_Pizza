package com.example.proyecto_delivery;

import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.Adaptadores.AdaptadorProducto;
import com.example.proyecto_delivery.Clases.classProducto;
import com.example.proyecto_delivery.Entidades.Producto;

import java.util.ArrayList;
import java.util.List;

public class ListaCompra extends AppCompatActivity {
    private AdaptadorProducto adaptador;
    private List<Producto> lista=new ArrayList<Producto>();
    private List<classProducto> Producto=new ArrayList<>();
    private LinearLayoutManager manager;
    private RecyclerView listaInformacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compra);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.listaInformacion=findViewById(R.id.lista_compra);
        manager=new LinearLayoutManager(this);
        CargarProductos();
        adaptador=new AdaptadorProducto(this.lista);//Accediendo a la lista de productos

        this.listaInformacion.setHasFixedSize(true);
        this.listaInformacion.setLayoutManager(manager);
        this.listaInformacion.setAdapter(adaptador);
        TextView lblCantidad=findViewById(R.id.lblCantidad);
        TextView lblTotal=findViewById(R.id.lblTotal);
        TextView lblFecha=findViewById(R.id.lblFecha);

        lblCantidad.setText(Integer.toString(lista.size()));
        lblTotal.setText("$ "+getIntent().getDoubleExtra(ListaFactura.ID_TOTAL,-1));
        lblFecha.setText(getIntent().getStringExtra(ListaFactura.ID_FECHA));
    }
    private Double CalcularTotal(){
        Double total=0.0;
        for(int i=0;i<this.lista.size();i++){
            total+=lista.get(i).getPrecio();
        }
        return total;
    }
    private void CargarProductos(){
        try{
            int i=0;
            classProducto prod=new classProducto(this);
            prod.setIdFacturaReferencia(getIntent().getIntExtra(ListaFactura.ID_FACTURA,-1));
            Producto = prod.GetAllProducto();
            for(classProducto producto : Producto){
                Producto pro=new Producto();
                pro.setImagen(producto.getImagen());
                pro.setDescripcion(producto.getDescripcion());
                pro.setProducto(producto.getProducto());
                pro.setPrecio(producto.getPrecio());
                lista.add(pro);
                //Toast.makeText(this, pro.getProducto()+" "+(++i), Toast.LENGTH_SHORT).show();
            }
        }catch (SQLiteException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}