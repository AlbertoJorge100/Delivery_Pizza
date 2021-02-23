package com.example.proyecto_delivery;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.Adaptadores.AdaptadorFactura;
import com.example.proyecto_delivery.Clases.classFactura;
import com.example.proyecto_delivery.Modelos.Facturas;
import com.example.proyecto_delivery.Utilerias.Logger;

import java.util.ArrayList;
import java.util.List;

public class ListaFactura extends AppCompatActivity {
    Toolbar toolbar;
    private AdaptadorFactura adaptador;
    private LinearLayoutManager manager;
    private RecyclerView listaInformacion;
    public static final String ID_FACTURA="IdFCt";
    public static final String ID_DIRECCION="IdDrcn";
    public static final String ID_NUMERO_ORDEN="IdNmOd";
    public static final String ID_PRODUCTOS="IdPrCt";
    public static final String ID_FECHA="IdfE";
    public static final String ID_TOTAL="IdTL";
    public static final String ID_ESTADO_ENVIO="IdESteNV";
    //Lista de facturas
    private List<Facturas> ListaFacturas=new ArrayList<Facturas>();

    //lista para SQLite
    private List<classFactura> listaFactura=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_factura);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Accedemos a la lista de facturas cargadas previamente desde el web services
        Bundle extra = getIntent().getBundleExtra(ListaInformacion.ID_LISTA_FACTURAS);
        this.ListaFacturas = (ArrayList<Facturas>) extra.getSerializable("lista");

        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Historial de compras");

        this.listaInformacion=findViewById(R.id.ListaFacturas);
        manager=new LinearLayoutManager(this);
        //this.lista=ListaInformacion.ListaFactura;
        adaptador=new AdaptadorFactura(ListaFacturas);
        this.listaInformacion.setHasFixedSize(true);
        this.listaInformacion.setLayoutManager(manager);
        this.listaInformacion.setAdapter(adaptador);
        //Evento onclick; presionar un item del reciclerview
        this.adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redireccionar a la activity "ListaFacturaProductos", para poder visualizar la lista de productos
                Facturas factura=ListaFacturas.get(listaInformacion.getChildAdapterPosition(view));
                Intent intn=new Intent(ListaFactura.this, ListaFacturaProductos.class);
                intn.putExtra(ID_FACTURA,factura.getIDFactura());
                intn.putExtra(ID_FECHA,factura.getFecha());
                intn.putExtra(ID_PRODUCTOS,factura.getProductos());
                intn.putExtra(ID_TOTAL,factura.getTotal());
                intn.putExtra(ID_ESTADO_ENVIO,factura.getEstadoEnvio());
                intn.putExtra(ID_DIRECCION, factura.getDireccion());
                intn.putExtra(ID_NUMERO_ORDEN,factura.getNumeroOrden());
                startActivity(intn);
            }
        });

    }
    /*
    private void CargarFacturas(){
        try{
            int i=0;
            classFactura factura=new classFactura(this);
            factura.setIdCliente(IdCliente);
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
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //COntrolar los elementos del toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            //Presionar carrito de compras
            case R.id.carrito:
                Logger logger=Logger.getInstance();
                //Validacion de lista de carritos desde singleton
                if(logger.getListaCarrito().size()>0){
                    //Existen productos en el carrito de compras; Mostrarlos
                    Intent intn=new Intent(ListaFactura.this,ListaCarrito.class);
                    startActivity(intn);
                }else{
                    //No existen productos en el carrito de compras
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
                //Presionar perfil de usuario
                Intent intn=new Intent(ListaFactura.this,PerfilActivity.class);
                startActivity(intn);
                break;
        }
        return true;
    }
}