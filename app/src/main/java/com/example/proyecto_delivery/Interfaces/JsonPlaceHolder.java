package com.example.proyecto_delivery.Interfaces;


import com.example.proyecto_delivery.BaseDatos.RespuestaProductos;
import com.example.proyecto_delivery.BaseDatos.RespuestaUsuarios;
import com.example.proyecto_delivery.Modelos.Categorias;
import com.example.proyecto_delivery.Modelos.Facturas;
import com.example.proyecto_delivery.Modelos.IDFactura;
import com.example.proyecto_delivery.Modelos.OrdenProductos;
import com.example.proyecto_delivery.Modelos.Pagos;
import com.example.proyecto_delivery.Modelos.Productos;
import com.example.proyecto_delivery.Modelos.Usuarios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Clase encargada de conectar nuestra APP, a la API REST en laravel
 */
public interface JsonPlaceHolder {
    //Login, retorna los datos del usuario.
    @GET("Login/{usuario}")
    Call<RespuestaUsuarios> Login(@Path("usuario") String usuario);

    //Obtener todas las Categorias
    @GET("Categorias")
    Call<List<Categorias>> getCategorias();

    //Añadir productos... temporal, ya que todo se hara desde el sitio web
    @POST("AddProductos")
    Call<Productos> AddProductos(@Body Productos producto);

    //Obtener lista de productos de acuerdo al id de la categoria
    @GET("Productos/{id}")
    Call<List<Productos>> getProductos(@Path("id") int id);

    /**
     * Gestionar una nueva orden y una nueva factura, a traves del id del usuario
     * Observacion: retorna una lista, porque hago uso de un procedimiento almacenado,
     * y no puedo obtener los ids, sin que sea dentro de una lista
     * @param IDUsuario id del usuario quien gestiona la orden y la factura
     * @return lista IDFactura: nada mas retorna el id de la orden y factura. 
     */
    @GET("GetIdFactura/{IDUsuario}")
    Call<List<IDFactura>> getIDFactura(@Path("IDUsuario") int IDUsuario);

    //Obtener la lista de ordenes "Aunque le he llamado getFacturas".. que ha realizado el usuario...
    //Enviamos el id del usuario
    @GET("GetFacturas/{id}")
    Call<List<Facturas>> getFacturas(@Path("id") int id);

    //Obtenemos la lista de productos de una factura || orden
    @GET("GetFacturaProductos/{id}")
    Call<List<OrdenProductos>> getFacturaProductos(@Path("id") int id);

    //Añadir un usuario; validaciones a nivel de servidor, para ver si no existe ese usuario.
    @POST("AddUsuarios")
    Call<RespuestaUsuarios> AddUsuarios(@Body Usuarios usuario);

    //Actualizar datos especificos del usuario, validaciones a nivel de servidor
    @PUT("UpdateUsuarios")
    Call<RespuestaUsuarios> UpdateUsuarios(@Body Usuarios usuario);

    //Enviamos la lista de productos, que deseamos facturar, y tambien la direccion del envio
    @POST("AddFacturaProductos")
    Call<RespuestaProductos> InsertFacturaProductos(@Body Pagos facturaProductos);

/*  Call<Productos> AddProductos(@Field("Producto") String Producto,
                                 @Field("Precio") double Precio,
                                 @Field("Existencias") int Existencias,
                                 @Field("Imagen") String Imagen,
                                 @Field("Descripcion") String Descripcion,
                                 @Field("IDCategoria") int IDCategoria
                                 );*/
}
