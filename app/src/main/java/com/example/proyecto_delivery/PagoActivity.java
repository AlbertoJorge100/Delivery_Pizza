package com.example.proyecto_delivery;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_delivery.BaseDatos.RespuestaProductos;
import com.example.proyecto_delivery.Entidades.Carrito;
import com.example.proyecto_delivery.Interfaces.JsonPlaceHolder;
import com.example.proyecto_delivery.Modelos.Existencias;
import com.example.proyecto_delivery.Modelos.FacturaProductos;
import com.example.proyecto_delivery.Modelos.IDFactura;
import com.example.proyecto_delivery.Modelos.Pagos;
import com.example.proyecto_delivery.Modelos.Usuarios;
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
    //Almacenar el mensajeq que retorne la peticion al servidor
    private String MensajeDialog = "";
    //Tag, si el pago fue aceptado
    public static String TAG_MSJ = "MSJ_J";
    //Tag, si existen productos, que fueron modificados en el servidor
    public static String TAG_MOD = "moDF";

    //Toolbar
    //private Toolbar toolbar;

    //Variable que guarda el muncipio seleccionado
    public String ResultadoMunicipio = "";
    //Boton municipio
    public Button btnMunicipio;
    ProgressDialog progressDialog;
    //private TextWatcher KeyPress;
    //Singleton
    private Logger _Logger = Logger.getInstance();
    //TextView que muestra el numero de la orden
    private TextView lblNumeroOrden;
    //EditText Ingresar direccion
    private TextView txbDireccion;
    //private Button btnMunicipo;
    private TextView txbAnio;
    private TextView txbCVV;
    private TextView txbMes;
    private TextView txbTarjeta;
    private TextView txbPropietario;
    //Validar para no recorrer la lista del carrito, otra vez
    private Boolean AddFactProd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);
        //Vertical por defecto
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Conexiones
        this.progressDialog = new ProgressDialog(this);

        this.lblNumeroOrden = findViewById(R.id.lblNumeroOrden);

        //final TextView txbTelefono = findViewById(R.id.RegtxbTelefono);
        TextView lblTotalPago = findViewById(R.id.lblTotalTarjeta);
        //Mostrar el total a pagar
        lblTotalPago.setText("Total a pagar: " + getIntent().getStringExtra(ListaCarrito.ID_PAGO));

        String mensaje = "Posterior al pago de tu orden, podras monitorear el estado de esta, a travez de la opcion: " +
                "Historial de compras; Donde podras ver el estado de tu orden en tiempo real. en caso de que suceda algun " +
                "inconveniente con tu orden, te sera notificado a travez del numero telefonico, que nos proporcionastes." +
                " Si tienes dudas o necesitas asistencia con tu orden llama al 2453-0091, proporcionando el numero de la orden " +
                "que se te ha sido asignado, ten en cuenta que el envio de tu orden puede tardar en aproximadamente 15 minutos en preparacion " +
                "y el tiempo de llegada depende de tu ubicacion, siempre que se encuentre dentro de una zona de cobertura";
        final TextView lblMensaje = findViewById(R.id.lblMensaje);
        lblMensaje.setText(mensaje);
        this.txbDireccion = findViewById(R.id.PagotxbDireccion);
        this.txbPropietario = findViewById(R.id.PagotxbPropietario);
        this.txbTarjeta = findViewById(R.id.PagotxbTarjeta);
        this.txbCVV = findViewById(R.id.PagotxbCVV);
        this.txbAnio = findViewById(R.id.PagotxbAnio);
        this.txbMes = findViewById(R.id.PagotxbMes);
        this.btnMunicipio = findViewById(R.id.PagoidMunicipio);

        //Validacion de gestion
        if (!this._Logger.getGestionado()) {
            //No se ha gestionado la orden
            Gestionar();
        } else {
            this.lblNumeroOrden.setText("Numero de órden: " + _Logger.getNumeroOrden());
            if (this._Logger.getMostrarDatos()) {
                /*Hubieron productos modificados en el servidor, y el usuario ya pudo revisar
                de nuevo su orden, al iniciar esta activity hay que mostrar los datos, en los textviews*/
                Pagos pago = _Logger.getPago();
                this.txbDireccion.setText(pago.getDireccion());
                this.txbPropietario.setText(pago.getPropietario());
                this.txbTarjeta.setText(pago.getTarjeta());
                this.txbCVV.setText(pago.getCVV());
                this.txbMes.setText(pago.getMM());
                this.txbAnio.setText(pago.getYY());
                this.btnMunicipio.setText(this._Logger.getMunicipioLocal());
                this.ResultadoMunicipio = this._Logger.getMunicipioLocal();
            }
        }

        ImageView img = findViewById(R.id.imgPago);
        //Conexion de imagen por Url
        String imagen = "https://www.tiendaidc.es/img/Logos%20de%20Pago.png";
        Picasso.get().load(imagen).error(R.mipmap.ic_launcher_round).fit().centerInside().into((ImageView) img);

        Button btnAceptar = findViewById(R.id.btnPagar);
        //Presionar Aceptar
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ResultadoMunicipio.equals("")) {
                    //Validacion si campos estan vacios
                    if (ValidarCampos(new TextView[]{txbDireccion,
                            txbPropietario, txbTarjeta, txbCVV, txbAnio, txbMes})) {
                        if (txbCVV.length() == 3) {
                            //CVV correcto
                            if (txbAnio.length() == 2) {
                                //Año correcto
                                if (txbMes.length() == 2) {
                                    //Mes correcto, y paso todos los filtros
                                    //Toast.makeText(PagoActivity.this,"Aceptado",Toast.LENGTH_SHORT).show();
                                    Pagar();
                                } else {
                                    txbMes.setError("Longitud invalida! ej: 06, 06: mes");
                                }

                            } else {
                                txbAnio.setError("Longitud invalida! ej: 34, 34: año");
                            }
                        } else {
                            txbCVV.setError("Longitud invalida !");
                        }
                    }
                } else {
                    Toast.makeText(PagoActivity.this, "Debe seleccionar un municipio !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnMunicipio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new FragmentDialog().show(getSupportFragmentManager(),"FragmentDialog");

                final String[] municipios = getResources().getStringArray(R.array.municipios);
                AlertDialog.Builder builder = new AlertDialog.Builder(PagoActivity.this);
                builder.setTitle("Elije un municipio");
                builder.setIcon(R.drawable.delivery);
                builder.setItems(municipios, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ResultadoMunicipio = municipios[i];
                        btnMunicipio.setText(municipios[i]);
                        //Toast.makeText(getApplicationContext(),"Seleccion: "+ResultadoMunicipio,Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                /*dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

                wmlp.gravity = Gravity.TOP | Gravity.LEFT;
                wmlp.x = 100;   //x position
                wmlp.y = 100;   //y position*/

                dialog.show();
            }
        });

    }

    /**
     * Gestionar la orden.
     * Cada vez que se ingrese a esta activity, solo gestionara la orden una sola vez,
     * las siguientes veces, antes de ingresar se validara la existencia de la orden en singleton...
     */
    private void Gestionar() {
        //Mostrando progressdialog
        this.progressDialog.show();
        this.progressDialog.setMessage("Cargando...");
        this.progressDialog.setCancelable(false);

        String url = getResources().getString(R.string.UrlAplicacion_local);
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
        //Id del usuario almacenado en singleton
        int idusuario = _Logger.getUsuario().getIDUsuario();
        Call<List<IDFactura>> call = jsonPlaceHolder.getIDFactura(idusuario);
        call.enqueue(new Callback<List<IDFactura>>() {
            @Override
            public void onResponse(Call<List<IDFactura>> call, Response<List<IDFactura>> response) {
                if (response.isSuccessful()) {
                    //peticion exitosa
                    if (response.body() != null) {
                        //Almacenar la
                        List<IDFactura> lista = response.body();
                        //Toast.makeText(PagoActivity.this,lista.get(0).getIDFactura()+" "+lista.get(0).getNumeroOrden(),Toast.LENGTH_SHORT).show();
                        //Almacenar en singleton el id de la factura, el numero de la orden
                        PagoActivity.this._Logger.setIDFactura(lista.get(0).getIDFactura());
                        PagoActivity.this._Logger.setNumeroOrden(lista.get(0).getNumeroOrden());
                        //Mostrarlo
                        PagoActivity.this.lblNumeroOrden.setText("Numero de órden: " + _Logger.getNumeroOrden());
                    } else {
                        Toast.makeText(PagoActivity.this, "Error al gestionar la orden", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PagoActivity.this, "Error response " + response.message(), Toast.LENGTH_SHORT).show();
                }
                PagoActivity.this.progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<IDFactura>> call, Throwable t) {
                Toast.makeText(PagoActivity.this, "Error response " + t.getMessage(), Toast.LENGTH_SHORT).show();
                PagoActivity.this.progressDialog.dismiss();
            }
        });
    }

    /**
     * Metodo encargado de realizar el pago, y realizar las validaciones en el servidor
     */
    public void Pagar(){
        final List<FacturaProductos> lista=new ArrayList<>();
        //Valida si cerra activity, si retorna codigo 500 no cerrar, no permite dejarla variable normal, obliga a dejarla como vector
        final Boolean[] cerrarAct = {true};
        //Validar si se desea pagar aunque el servidor haya modificado existencias
        //final Boolean[] pagar_siempre = {false};

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
        final Pagos pagos =new Pagos();
        //pagos.setCVV(txbCB)
        String direccion=this.txbDireccion.getText().toString();
        String municipio=this.ResultadoMunicipio;
        pagos.setDireccion(municipio+": "+direccion);
        pagos.setListaProductos(lista);
        pagos.setPropietario(this.txbPropietario.getText().toString());
        pagos.setCVV(this.txbCVV.getText().toString());
        pagos.setMM(this.txbMes.getText().toString());
        pagos.setYY(this.txbAnio.getText().toString());
        pagos.setTarjeta(this.txbTarjeta.getText().toString());

        final Call<RespuestaProductos> call = jsonPlaceHolder.InsertFacturaProductos(pagos);
        call.enqueue(new Callback<RespuestaProductos>() {
            @Override
            public void onResponse(Call<RespuestaProductos> call, Response<RespuestaProductos> response) {
                final RespuestaProductos respuesta=response.body();
                String mensaje="", titulo="Aceptar";//productos modificados, titulo aceptar
                String dialog_titulo="Pago aceptado";//Titulo del dialog

                //Icono a mostrar en el dialog, android obliga usar un arreglo :(
                int icono = R.drawable.ordenado;
                if(response.isSuccessful()){
                    switch(respuesta.getCodigo()){
                        case "200":{
                            //Orden ingresada al sistema
                            PagoActivity.this._Logger.getListaCarrito().clear();
                            PagoActivity.this._Logger.setGestionado(false);
                            _Logger.setMostrarDatos(false);
                            if(_Logger.getMostrarDatos()){
                                //Se estaban mostrando los datos, ya no hay que mostrarlos...
                                _Logger.setMostrarDatos(false);
                            }
                            /*Modificacion de las compras del usuario, Paso de direccion de memoria del usuario
                            * Actualizar de manera local, el numero de compras realizadas por el usuario
                            * */
                            Usuarios user_aux=PagoActivity.this._Logger.getUsuario();
                            user_aux.setCompras(user_aux.getCompras()+1);
                            Intent intn=new Intent();
                            //Pago efectuado, cerrar la activity listacarrito...
                            intn.putExtra(TAG_MSJ,true);
                            setResult(ListaCarrito.ID_PAGO_RESULTADO,intn);
                            break;
                        }
                        case "300":{
                            //Productos modificados en el servidor...
                            /*Almacenamos en memoria los datos, Si el servidor, no nos permite realizar la gestion,
                                entonces nos retornara a realizar algunos cambios*/
                            if(!_Logger.getMostrarDatos()){
                                //Datos no estan almacenados en singleton, hay que almacenarlos
                                _Logger.setMunicipioLocal(ResultadoMunicipio);
                                _Logger.setPago(pagos);
                                _Logger.setMostrarDatos(true);
                            }
                            //Llenar el mensaje con los productos, que han sido modificados en el servidor
                            mensaje=ExistenciasErroneas(respuesta.getData());
                            Intent intn=new Intent();
                            //intn.putExtra(TAG_MSJ,true);//Cerrar activity, listaCarrito
                            intn.putExtra(TAG_MOD,true);//No cerrar activity, solo verificar las modificaciones
                            setResult(ListaCarrito.ID_PAGO_RESULTADO,intn);
                            titulo="Ir a ver";
                            //Setear icono...
                            icono =R.drawable.warning;
                            dialog_titulo="Aviso";
                            break;
                        }
                        case "500":{
                            //Pago no fue aceptado

                            //La activity no debe cerrarse
                            cerrarAct[0] =false;
                            icono=R.drawable.error;
                            dialog_titulo="Error";
                            break;
                        }
                    }
                    //MensajeDialog =respuesta.getMensaje()+mensaje;
                    //Toast.makeText(getApplicationContext(),respuesta.getMensaje()+mensaje,Toast.LENGTH_SHORT).show();
                    //Toast.makeText(PagoActivity.this,respuesta.getMensaje(),Toast.LENGTH_SHORT).show();
                }
                PagoActivity.this.progressDialog.dismiss();
                AlertDialog.Builder builder=new AlertDialog.Builder(PagoActivity.this);
                builder.setTitle(dialog_titulo);
                //Icono
                builder.setIcon(icono);
                builder.setMessage(respuesta.getMensaje()+"\n"+mensaje);
                builder.setPositiveButton(titulo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(cerrarAct[0]){
                            //Cerrar activity
                            PagoActivity.this.finish();
                        }
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
            }

            @Override
            public void onFailure(Call<RespuestaProductos> call, Throwable t) {
                Toast.makeText(PagoActivity.this,"Error response "+t.getMessage(),Toast.LENGTH_SHORT).show();
                PagoActivity.this.progressDialog.dismiss();
            }
        });
    }

    /**
     * Modificar las existencias, en la orden que esta en espera, al hacer la peticion de pago
     * , si algunos productos elegidos ya disminuyeron sus existencias, afectando nuestra orden
     * , entonces el web services retornara una lista de productos, con las existencias actualizadas,
     * las que deberan ser modificadas en la orden de espera, y se le notificara al usuario
     * @param lst_existencias: la lista de productos, que trae el web services
     *
     */
    private String ExistenciasErroneas(List<Existencias> lst_existencias){
        String resultado="";
        int cont=0;
        //Recorrer la lista que retorno el web services
        for(int i=0;i<lst_existencias.size();i++){
            //Recorrer la lista de carrito de singleton
            for(int j=0;j<_Logger.getListaCarrito().size();j++){

                int id=this._Logger.getListaCarrito().get(j).getIdProducto();
                if(lst_existencias.get(i).getId()==id){
                    //id de producto almancenado en singleton es igual a el del web services

                    int existencias=lst_existencias.get(i).getExistencias();
                    Carrito carrito=this._Logger.getListaCarrito().get(j);

                    //Almacenar el contador, nombre de producto, stock.
                    resultado+=(++cont)+" - "+carrito.getProducto()+". Stock: "+existencias+"\n";
                    if(existencias==0){
                        //Las existencias del item seleccionado son 0, hay que eliminar el item
                        _Logger.getListaCarrito().remove(j);
                    }else{
                        //Modificando el total $ del item modificado, en singleton
                        Double precio=carrito.getPrecioUnitario();
                        carrito.setTotal(precio*existencias);
                        carrito.setCantidad(existencias);
                        carrito.setCant_Limite(existencias);
                    }

                    break;
                }
            }
        }
        return resultado;
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

    /**
     * Validacion de los campos: que no esten vacios
     * @param lista lista de campos a validar
     * @return true si pasa los filtros
     */
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
        this.ResultadoMunicipio="";
    }
}