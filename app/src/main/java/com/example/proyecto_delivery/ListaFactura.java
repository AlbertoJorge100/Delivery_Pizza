package com.example.proyecto_delivery;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.Adaptadores.AdaptadorFactura;
import com.example.proyecto_delivery.Clases.classFactura;
import com.example.proyecto_delivery.Entidades.Factura;

import java.util.ArrayList;
import java.util.List;

public class ListaFactura extends AppCompatActivity {
    Toolbar toolbar;
    private AdaptadorFactura adaptador;
    private LinearLayoutManager manager;
    private RecyclerView listaInformacion;
    public static final String ID_FACTURA="Id";
    public static final String ID_FECHA="IdfE";
    public static final String ID_TOTAL="IdTL";
    private List<Factura> lista=new ArrayList<Factura>();
    private List<classFactura> listaFactura=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_factura);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Historial de compras");

        this.listaInformacion=findViewById(R.id.ListaFacturas);
        manager=new LinearLayoutManager(this);
        this.lista=ListaInformacion.ListaFactura;
        adaptador=new AdaptadorFactura(lista);
        this.listaInformacion.setHasFixedSize(true);
        this.listaInformacion.setLayoutManager(manager);
        this.listaInformacion.setAdapter(adaptador);
        this.adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn=new Intent(ListaFactura.this, ListaCompra.class);
                intn.putExtra(ID_FACTURA,lista.get(listaInformacion.getChildAdapterPosition(view)).getIdFactura());
                intn.putExtra(ID_FECHA,lista.get(listaInformacion.getChildAdapterPosition(view)).getFecha());
                intn.putExtra(ID_TOTAL,lista.get(listaInformacion.getChildAdapterPosition(view)).getTotal());
                startActivity(intn);
            }
        });

    }

    private void CargarFacturas(){
        try{
            int i=0;
            classFactura factura=new classFactura(this);
            factura.setIdCliente(ListaInformacion.IdCliente);
            this.listaFactura = factura.GetAllFacturas();
            for(classFactura fct : this.listaFactura ){
                Factura fct2=new Factura();
                fct2.setIdFactura(fct.getIdFactura());
                fct2.setFecha(fct.getFecha());
                fct2.setCantidad(fct.getCantidadProductos());
                fct2.setTotal(fct.getTotal());
                lista.add(fct2);
                //Toast.makeText(this, pro.getProducto()+" "+(++i), Toast.LENGTH_SHORT).show();
            }
        }catch (SQLiteException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.carrito:
                if(ListaInformacion.ListaCarrito.size()>0){
                    Intent intn=new Intent(ListaFactura.this,ListaCarrito.class);
                    startActivity(intn);
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(ListaFactura.this);
                    builder.setTitle("Mensaje");
                    builder.setMessage("No hay productos agregados al carrito");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
                break;
            case R.id.persona:
                Intent intn=new Intent(ListaFactura.this,PerfilActivity.class);
                startActivity(intn);
                break;
        }
        return true;
    }
}