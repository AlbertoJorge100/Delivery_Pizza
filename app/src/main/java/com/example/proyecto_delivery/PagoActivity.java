package com.example.proyecto_delivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_delivery.BaseDatos.RespuestaProductos;
import com.example.proyecto_delivery.Entidades.Carrito;
import com.example.proyecto_delivery.Interfaces.JsonPlaceHolder;
import com.example.proyecto_delivery.Modelos.FacturaProductos;
import com.example.proyecto_delivery.Modelos.IDFactura;
import com.example.proyecto_delivery.Modelos.Pagos;
import com.example.proyecto_delivery.Modelos.Usuarios;
import com.example.proyecto_delivery.Utilerias.FragmentDialog;
import com.example.proyecto_delivery.Utilerias.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import android.support.v7.widget.Toolbar;

/**
 * Pagos
 */
public class PagoActivity extends AppCompatActivity {
    //Validar si se ha seleccionado un municipio
    public static boolean ValidarMunicipio=false;

    public static String TAG_MSJ="MSJ_J";
    //Toolbar
    //private Toolbar toolbar;

    //Variable que guarda el muncipio seleccionado
    public static String ResultadoMunicipio="";
    //Boton municipio
    public static Button btnMunicipio;
    ProgressDialog progressDialog;
    //private TextWatcher KeyPress;
    //Singleton
    private Logger _Logger=Logger.getInstance();
    //TextView que muestra el numero de la orden
    private TextView lblNumeroOrden;
    //EditText Ingresar direccion
    private TextView txbDireccion;
    //private Button btnMunicipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);
        //Vertical por defecto
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Conexiones
        this.progressDialog=new ProgressDialog(this);
        this.lblNumeroOrden=findViewById(R.id.lblNumeroOrden);
        final TextView txbTelefono=findViewById(R.id.RegtxbTelefono);
        TextView lblTotalPago=findViewById(R.id.lblTotalTarjeta);
        //Mostrar el total a pagar
        lblTotalPago.setText("Total a pagar: "+getIntent().getStringExtra(ListaCarrito.ID_PAGO));
        //Validacion de gestion
        if(!this._Logger.getGestionado()){
            //No se ha gestionado la orden
            Gestionar();
        }else{
            this.lblNumeroOrden.setText("Numero de órden: "+_Logger.getNumeroOrden());
        }

        String mensaje="Posterior al pago de tu orden, podras monitorear el estado de esta, a travez de la opcion: " +
                "Historial de compras; Donde podras ver el estado de tu orden en tiempo real. en caso de que suceda algun " +
                "inconveniente con tu orden, te sera notificado a travez del numero telefonico, que nos proporcionastes." +
                " Si tienes dudas o necesitas asistencia con tu orden llama al 2453-0091, proporcionando el numero de la orden " +
                "que se te ha sido asignado, ten en cuenta que el envio de tu orden puede tardar en aproximadamente 15 minutos en preparacion " +
                "y el tiempo de llegada depende de tu ubicacion, siempre que se encuentre dentro de una zona de cobertura";
        final TextView lblMensaje=findViewById(R.id.lblMensaje);
        lblMensaje.setText(mensaje);
        this.txbDireccion=findViewById(R.id.PagotxbDireccion);
        final TextView txbPropietario=findViewById(R.id.PagotxbPropietario);
        final TextView txbTarjeta=findViewById(R.id.PagotxbTarjeta);
        final TextView txbCVV=findViewById(R.id.PagotxbCVV);
        final TextView txbAnio=findViewById(R.id.PagotxbAnio);
        final TextView txbMes=findViewById(R.id.PagotxbMes);


        /*txbDireccion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                *//*if(txbDireccion.length()>=100){
                    txbDireccion.setError("Limite de escritura alcanzado");
                }*//*
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

        //Switch on/off
        Switch switchTelefono=findViewById(R.id.switchTelefono);
        //Boolean estadoSwitch=switchTelefono.isChecked();  Verificar si esta encendido o apagado
        txbTelefono.setText("7003-2797");
        txbTelefono.setEnabled(false);
        //Evento On/Off Switch
        switchTelefono.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //Switch telefono encendido
                    txbTelefono.setEnabled(true);
                }else{
                    txbTelefono.setEnabled(false);
                }
            }
        });


        ImageView img=findViewById(R.id.imgPago);
        //Conexion de imagen por Url
        String imagen="https://www.tiendaidc.es/img/Logos%20de%20Pago.png";
        Picasso.get().load(imagen).error(R.mipmap.ic_launcher_round).fit().centerInside().into((ImageView) img);

        Button btnAceptar=findViewById(R.id.btnPagar);
        //Presionar Aceptar
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PagoActivity.this.ValidarMunicipio && PagoActivity.this.ResultadoMunicipio.length()>0){
                    //Validacion si campos estan vacios
                    if(ValidarCampos(new TextView[]{txbDireccion,txbTelefono,
                            txbPropietario,txbTarjeta,txbCVV,txbAnio,txbMes})){
                        if(txbCVV.length()==3){
                            //CVV correcto
                            if(txbAnio.length()==2){
                                //Año correcto
                                if(txbMes.length()==2){
                                    //Mes correcto, y paso todos los filtros
                                    //Toast.makeText(PagoActivity.this,"Aceptado",Toast.LENGTH_SHORT).show();
                                    Pagar();
                                }else{
                                    txbMes.setError("Longitud invalida! ej: 06, 06: mes");
                                }

                            }else{
                                txbAnio.setError("Longitud invalida! ej: 34, 34: año");
                            }
                        }else{
                            txbCVV.setError("Longitud invalida !");
                        }
                    }
                }else{
                    Toast.makeText(PagoActivity.this,"Debe seleccionar un municipio !",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMunicipio=findViewById(R.id.PagoidMunicipio);
        btnMunicipio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 new FragmentDialog().show(getSupportFragmentManager(),"FragmentDialog");
            }
        });

    }

    /**
     * Gestionar la orden.
     */
    private void Gestionar(){
        //Mostrando progressdialog
        this.progressDialog.show();
        this.progressDialog.setMessage("Cargando...");
        this.progressDialog.setCancelable(false);

        String url=getResources().getString(R.string.UrlAplicacion_local);
        Retrofit retrofit=new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolder jsonPlaceHolder=retrofit.create(JsonPlaceHolder.class);
        //Id del usuario almacenado en singleton
        int idusuario=_Logger.getUsuario().getIDUsuario();
        Call<List<IDFactura>> call=jsonPlaceHolder.getIDFactura(idusuario);
        call.enqueue(new Callback<List<IDFactura>>() {
            @Override
            public void onResponse(Call<List<IDFactura>> call, Response<List<IDFactura>> response) {
                if(response.isSuccessful()){
                    //peticion exitosa
                    if(response.body()!=null){
                        //Almacenar la
                        List<IDFactura>lista=response.body();
                        //Toast.makeText(PagoActivity.this,lista.get(0).getIDFactura()+" "+lista.get(0).getNumeroOrden(),Toast.LENGTH_SHORT).show();
                        //Almacenar en singleton el id de la factura, el numero de la orden
                        PagoActivity.this._Logger.setIDFactura(lista.get(0).getIDFactura());
                        PagoActivity.this._Logger.setNumeroOrden(lista.get(0).getNumeroOrden());
                        //Mostrarlo
                        PagoActivity.this.lblNumeroOrden.setText("Numero de órden: "+_Logger.getNumeroOrden());
                    }else{
                        Toast.makeText(PagoActivity.this,"Error al gestionar la orden",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(PagoActivity.this,"Error response "+response.message(),Toast.LENGTH_SHORT).show();
                }
                PagoActivity.this.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<IDFactura>> call, Throwable t) {
                Toast.makeText(PagoActivity.this,"Error response "+t.getMessage(),Toast.LENGTH_SHORT).show();
                PagoActivity.this.progressDialog.dismiss();
            }
        });
    }


    private void Pagar(){
        List<FacturaProductos> lista=new ArrayList<>();
        //Traspaso de clase Carrito a FacturaProductos...
        for(Carrito carrito:this._Logger.getListaCarrito()){
            FacturaProductos fp=new FacturaProductos();
            fp.setIDFactura(_Logger.getIDFactura());
            fp.setIDProducto(carrito.getIdProducto());
            fp.setCantidad(carrito.getCantidad());
            fp.setDescuento(0.0);
            fp.setSubTotal(carrito.getTotal());
            lista.add(fp);
        }
        //Mostrando el progreso de la peticion
        this.progressDialog.show();
        this.progressDialog.setMessage("Cargando...");
        this.progressDialog.setCancelable(false);

        String url=getResources().getString(R.string.UrlAplicacion_local);
        Retrofit retrofit=new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolder jsonPlaceHolder=retrofit.create(JsonPlaceHolder.class);
        //Paso del idusuario
        int idusuario=_Logger.getUsuario().getIDUsuario();
        Pagos pagos =new Pagos();
        String direccion=PagoActivity.this.txbDireccion.getText().toString();
        String municipio=PagoActivity.ResultadoMunicipio;
        pagos.setDireccion(municipio+": "+direccion);
        pagos.setListaProductos(lista);
        Call<RespuestaProductos> call=jsonPlaceHolder.InsertFacturaProductos(pagos);
        call.enqueue(new Callback<RespuestaProductos>() {
            @Override
            public void onResponse(Call<RespuestaProductos> call, Response<RespuestaProductos> response) {
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        RespuestaProductos respuesta=response.body();
                        if(respuesta.getData()){
                            Toast.makeText(PagoActivity.this,respuesta.getMensaje(),Toast.LENGTH_SHORT).show();
                            PagoActivity.this._Logger.getListaCarrito().clear();
                            PagoActivity.this._Logger.setGestionado(false);

                            //Modificacion de las compras del usuario, Paso de direccion de memoria del usuario
                            Usuarios user_aux=PagoActivity.this._Logger.getUsuario();
                            user_aux.setCompras(user_aux.getCompras()+1);

                            //ListaCarrito.finish();
                            Intent intn=new Intent();
                            intn.putExtra(TAG_MSJ,true);
                            setResult(ListaCarrito.ID_PAGO_RESULTADO,intn);
                            PagoActivity.this.finish();
                        }else{
                            Toast.makeText(PagoActivity.this, "Error response" + response.message() + " " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        //Toast.makeText(PerfilActivity.this,"El usuario elegido ya esta en uso",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(PagoActivity.this,"Error response "+response.message(),Toast.LENGTH_SHORT).show();
                }
                PagoActivity.this.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<RespuestaProductos> call, Throwable t) {
                Toast.makeText(PagoActivity.this,"Error response "+t.getMessage(),Toast.LENGTH_SHORT).show();
                PagoActivity.this.progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         switch(item.getItemId()){
             case R.id.carrito:
                 //Toast.makeText(this,this.Reesultado,Toast.LENGTH_SHORT);
                 break;
         }
         return true;
    }

    private boolean ValidarCampos(TextView [] lista){
        boolean resultado=true;
        for(TextView aux:lista){
            if(aux.getText().toString().equals("")){
                aux.setError("Campo obligatorio !");
                resultado=false;
            }
        }
        return resultado;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.ValidarMunicipio=false;
        this.ResultadoMunicipio="";
    }
}