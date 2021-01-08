package com.example.proyecto_delivery;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_delivery.Entidades.Carrito;
import com.example.proyecto_delivery.Interfaces.CalcularTotales;
import com.example.proyecto_delivery.Utilerias.Logger;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Activity "DetalleActivity" donde mostraremos los detalles de un producto
 * seleccionado en la activity "ListaProducto" para agregarlo al carrito,
 * o en "ListaCarrito" para modificarlo y luego guardar los cambios
 */
public class DetalleActivity extends AppCompatActivity {
     //Singleton
     private Logger logger=Logger.getInstance();

     //Variables para almacenar los datos obtenidos por parametros
     private int IdProducto=0;
     private Double Precio=0.0;
     private Double Total=0.0;
     private int Cantidad=1;
     private String Titulo;
     private String Imagen;
     private String Descripcion;

     //TextViews principales
     private TextView lblCantidadProductos;
     private TextView lblTotal;
     private TextView lblPrecio;
     private String Mensaje;
     //public static Opcion _Opcion=Opcion.AGREGAR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lblCantidadProductos=findViewById(R.id.lblCantidadProductos);
        TextView lblMenos=findViewById(R.id.lblMenos);
        TextView lblMas=findViewById(R.id.lblMas);
        lblTotal=findViewById(R.id.lblTotal);
        lblPrecio=findViewById(R.id.lblPrecio);
        final ImageView imgProducto=findViewById(R.id.imgProducto);
        TextView lbltitulo=findViewById(R.id.lblTitulo);
        TextView lbldescripcion=findViewById(R.id.lblDescripcion);
        Button btnAgregar=findViewById(R.id.btnAgregar);
        //String Opcion=getIntent().getStringExtra(Lista)

        //Validacion a travez de Singleton, para poder realizar las acciones
        if(this.logger.getOpcion()==Logger._Opcion.MODIFICAR){
            //Modificar: traemos los datos desde la listaCarrito
            btnAgregar.setText("Modificar");
            /*Toast.makeText(DetalleActivity.this,
                    Integer.toString(getIntent().getIntExtra(ListaCarrito.ID_PRODUCTO,0))
                    ,Toast.LENGTH_SHORT).show();*/
            this.IdProducto=getIntent().getIntExtra(ListaCarrito.ID_PRODUCTO,0);//El mas importante
            this.Imagen=getIntent().getStringExtra(ListaCarrito.ID_IMAGEN);
            this.Precio=getIntent().getDoubleExtra(ListaCarrito.ID_PRECIO,0);
            this.Descripcion=getIntent().getStringExtra(ListaCarrito.ID_DESCRIPCION);
            this.Titulo=getIntent().getStringExtra(ListaCarrito.ID_TITULO);
            this.Cantidad=getIntent().getIntExtra(ListaCarrito.ID_CANTIDAD,0);
            this.Mensaje="Desea modificar el producto en el carrito de compras?";
        }else{
            //Agregar: traemos los datos desde la listaProducto
            this.Mensaje="Desea agregar el producto al carrito de compras?";
            this.IdProducto=getIntent().getIntExtra(ListaProducto.ID_PRODUCTO,0);//El mas importante
            this.Imagen=getIntent().getStringExtra(ListaProducto.ID_IMAGEN);
            this.Precio=getIntent().getDoubleExtra(ListaProducto.ID_PRECIO,0);
            this.Descripcion=getIntent().getStringExtra(ListaProducto.ID_DESCRIPCION);
            this.Titulo=getIntent().getStringExtra(ListaProducto.ID_TITULO);
        }
        //Calculamos los totales -1: No accionar "Cantidad"
        CalcularTotales(-1);

        //Evento presionar lblMenos Decrementar en 1 siempre que no sea igual a 1
        lblMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Cantidad>1){
                    CalcularTotales(2);
                }
            }
        });

        //Evento precionar lblMas Incrementar en 1 sin limite de final
        lblMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalcularTotales(1);
            }
        });

        /**
         * Boton Agregar el producto a la lista del carrito, o modificarlo en la lista del carrito
         * segun sea la validacion de Singleton
         */
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Confirmacion
                AlertDialog.Builder builder=new AlertDialog.Builder(DetalleActivity.this);
                builder.setTitle("Confirmacion");
                builder.setMessage(DetalleActivity.this.Mensaje);
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Validar Singleton
                        if(DetalleActivity.this.logger.getOpcion()==Logger._Opcion.AGREGAR){
                            //Producto que sera agregado al carrito de compras
                            Carrito aux=new Carrito();
                            aux.setIdProducto(DetalleActivity.this.IdProducto);
                            aux.setProducto(DetalleActivity.this.Titulo);
                            aux.setCantidad(DetalleActivity.this.Cantidad);
                            aux.setDescripcion(DetalleActivity.this.Descripcion);
                            aux.setPrecioUnitario(DetalleActivity.this.Precio);
                            aux.setImagen(DetalleActivity.this.Imagen);
                            aux.setTotal(DetalleActivity.this.Total);
                            //Seteo a singleton
                            logger.setItemCarrito(aux);
                            Toast.makeText(DetalleActivity.this,"Producto agregado al carrito de compras", Toast.LENGTH_SHORT).show();
                        }else{
                            //Acceder al indice de la listaCarrito para poder, modificarlo posteriormente
                            int indice=getIntent().getIntExtra(ListaCarrito.ID_INDICE,0);
                            //Modificando las cantidades en singleton
                            logger.getListaCarrito().get(indice).setCantidad(DetalleActivity.this.Cantidad);
                            logger.getListaCarrito().get(indice).setTotal(DetalleActivity.this.Total);
                            //Modificando nuevamente la opcion de acceso a esta activity para posteriores accesos
                            DetalleActivity.this.logger.setOpcion(Logger._Opcion.AGREGAR);
                            //Modificando los totales de la activity ListaCarrito a travez de sus TextViews estaticos
                            ListaCarrito.lblProductos.setText(Integer.toString(CalcularTotales.TotalProductos()));
                            ListaCarrito.lblTotal.setText("$ "+CalcularTotales.TotalPagar());
                            Toast.makeText(DetalleActivity.this,"Producto modificado", Toast.LENGTH_SHORT).show();
                            //Modificando el adaptador para actualizar los cambios realizados
                            ListaCarrito.adaptador.notifyDataSetChanged();
                        }
                        finish();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Presiono cancelar
                        }
                    });
                AlertDialog dialog=builder.create();
                dialog.show();
                //Toast.makeText(DetalleActivity.this,pr.getProducto(),Toast.LENGTH_SHORT).show();
            }
        });
        //Seteando los datos de la activity
        lbltitulo.setText(Titulo);
        lbldescripcion.setText(Descripcion);
        /*lblPrecio.setText("$ "+Double.toString(this.Precio));
        lblTotal.setText("$ "+Double.toString(this.Precio));*/
            Picasso.get().load(Imagen).error(R.mipmap.ic_launcher_round).fit().centerInside().into((ImageView) imgProducto);
    }

    /**
     * Evento de tecla "Atrás"
     * donde modificaremos la Opcion en Singleton para acceder a esta activity
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Modificando el singleton
        DetalleActivity.this.logger.setOpcion(Logger._Opcion.AGREGAR);
        //Toast.makeText(DetalleActivity.this,"Atrás <-",Toast.LENGTH_SHORT).show();
    }

    /**
     * Calculamos El total a pagar por el cliente
     * @param i para indicar que accion realizar
     *          1: para incrementar la cantidad en 1
     *          2: para decrementar la cantidad en 1
     */
    private void CalcularTotales(int i){
        switch(i){
            case 1:Cantidad++;break;
            case 2:Cantidad--;break;
        }
        lblCantidadProductos.setText(Integer.toString(Cantidad));
        Total=Precio*Cantidad;
        /*Toast.makeText(DetalleActivity.this,"Total: "+this.Total,Toast.LENGTH_SHORT).show();*/
        //Limitar los decimales a 2
        BigDecimal bd = new BigDecimal(Total);//Redondeamos a dos decimales
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        Total=bd.doubleValue();
        //Seteo de totales


        DecimalFormat df = new DecimalFormat("0.00");//Se usa para mostrar dos decimales incluyendo el 0 al final
        DetalleActivity.this.lblPrecio.setText("$ "+df.format(Precio).replace(",", "."));
        DetalleActivity.this.lblTotal.setText("$ "+df.format(Total).replace(",", "."));
    }
}
