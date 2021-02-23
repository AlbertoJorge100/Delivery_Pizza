package com.example.proyecto_delivery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_delivery.Adaptadores.AdaptadorCarrito;
import com.example.proyecto_delivery.Entidades.Carrito;
import com.example.proyecto_delivery.Interfaces.JsonPlaceHolder;
import com.example.proyecto_delivery.Modelos.OrdenProductos;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaFacturaProductos extends AppCompatActivity {
    private AdaptadorCarrito adaptador=new AdaptadorCarrito();
    private Toolbar toolbar;
    private LinearLayoutManager manager;
    private RecyclerView listaInformacion;
    //ProgressDialog que se mostrara hasta que la peticion sea procesada con exito
    private ProgressDialog progressDialog;
    private List<Carrito> ListaCarrito=new ArrayList<>();
    //Contador de items
    private TextView LFItems;
    //Contador de productos
    private TextView LFProductos;
    //Total
    private TextView LFTotal;
    //Estado de la Orden
    private TextView LFEstado;
    //Numero de la Orden
    private TextView LFNumero;
    //Fecha de la Orden
    private TextView LFFecha;
    //Direccion de envio
    private TextView LFDireccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_factura_productos);
        this.listaInformacion=findViewById(R.id.ListaFacturaProd);
        this.progressDialog=new ProgressDialog(this);
        this.LFItems=findViewById(R.id.LFItems);
        this.LFProductos=findViewById(R.id.LFProductos);
        this.LFTotal=findViewById(R.id.LFTotal);
        this.LFEstado=findViewById(R.id.LFEstado);
        this.LFNumero=findViewById(R.id.LFNumeroOrden);
        this.LFFecha=findViewById(R.id.LFFecha);
        this.LFDireccion=findViewById(R.id.LFDireccion);

        /*toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detalle de factura");*/

        //Cargando las facturas
        CargarProductos(getIntent().getIntExtra(ListaFactura.ID_FACTURA,0));
    }

    /**
     * Cargar los productos desde el servidor web
     * @param idfactura: El id de la factura que se buscara dentro del
     *                 servidor web
     */
    private void CargarProductos(int idfactura){
        //Mostrar el progressdialog
        this.progressDialog.show();
        this.progressDialog.setMessage("Cargando...");
        this.progressDialog.setCancelable(false);
        //Direccion base
        String url=getResources().getString(R.string.UrlAplicacion_local);
        Retrofit retrofit=new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolder jsonPlaceHolder=retrofit.create(JsonPlaceHolder.class);

        Call<List<OrdenProductos>> call=jsonPlaceHolder.getFacturaProductos(idfactura);
        call.enqueue(new Callback<List<OrdenProductos>>() {
            @Override
            public void onResponse(Call<List<OrdenProductos>> call, Response<List<OrdenProductos>> response) {
                if(response.isSuccessful()){
                    //Peticion exitosa
                    List<OrdenProductos> lista=response.body();
                    for(OrdenProductos orden:lista){
                        /*Traspasar de la clase OrdenProductos hacia Carrito para luego
                                mostrarlo en el reciclerview*/
                        Carrito carrito=new Carrito();
                        carrito.setCantidad(orden.getCantidad());
                        carrito.setPrecioUnitario(orden.getPrecio());
                        carrito.setProducto(orden.getProducto());
                        carrito.setTotal(orden.getSubTotal());
                        carrito.setImagen(orden.getImagen());
                        //Agregando el producto a la lista
                        ListaFacturaProductos.this.ListaCarrito.add(carrito);
                    }
                    //Llenado de los TextView donde se mostraran los detalles
                    LFItems.setText(Integer.toString(lista.size()));
                    //getIntent(): Obtener el valor de la activity anterior
                    LFProductos.setText(Integer.toString(getIntent().getIntExtra(ListaFactura.ID_PRODUCTOS,0)));
                    LFTotal.setText("$ "+Double.toString(getIntent().getDoubleExtra(ListaFactura.ID_TOTAL,0)));
                    LFNumero.setText(getIntent().getStringExtra(ListaFactura.ID_NUMERO_ORDEN));
                    LFEstado.setText(getIntent().getStringExtra(ListaFactura.ID_ESTADO_ENVIO));
                    LFFecha.setText(getIntent().getStringExtra(ListaFactura.ID_FECHA));
                    LFDireccion.setText(getIntent().getStringExtra(ListaFactura.ID_DIRECCION));

                    //RecyclerView
                    ListaFacturaProductos.this.adaptador.setListaCarrito(ListaFacturaProductos.this.ListaCarrito);
                    ListaFacturaProductos.this.manager=new LinearLayoutManager(ListaFacturaProductos.this);
                    ListaFacturaProductos.this.listaInformacion.setHasFixedSize(true);
                    ListaFacturaProductos.this.listaInformacion.setLayoutManager(ListaFacturaProductos.this.manager);
                    ListaFacturaProductos.this.listaInformacion.setAdapter(ListaFacturaProductos.this.adaptador);
                }else{
                    //Error response
                    Toast.makeText(ListaFacturaProductos.this, "onResponse error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
                ListaFacturaProductos.this.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<OrdenProductos>> call, Throwable t) {
                //Falló
                ListaFacturaProductos.this.progressDialog.dismiss();
                Toast.makeText(ListaFacturaProductos.this, "onFailure error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}