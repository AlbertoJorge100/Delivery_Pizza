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

import com.example.proyecto_delivery.Adaptadores.AdaptadorProducto;
import com.example.proyecto_delivery.Entidades.Producto;
import com.example.proyecto_delivery.Utilerias.Logger;

import java.util.ArrayList;
import java.util.List;

//Toolbar Librerias

public class ListaProducto extends AppCompatActivity {
    //Ids para enviar los datos a "DetalleActivity"
    public static final String ID_PRODUCTO="idproducto";
    public static final String ID_IMAGEN="imagen";
    public static final String ID_TITULO="titulo";
    public static final String ID_DESCRIPCION="descripcion";
    public static final String ID_PRECIO="precio";
    public static final String ID_CANTIDAD="cantss";

    private AdaptadorProducto adaptador;

    private List<Producto>lista=new ArrayList<Producto>();
    private RecyclerView listaProducto;
    LinearLayoutManager manager;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        manager= new LinearLayoutManager(this);
        listaProducto=findViewById(R.id.listInformacion);
        adaptador=new AdaptadorProducto(lista);
        this.listaProducto.setHasFixedSize(true);
        this.listaProducto.setLayoutManager(manager);
        this.listaProducto.setAdapter(adaptador);

         String titulo="";
         switch(getIntent().getStringExtra("ID")){
             case ListaInformacion.ID_PROMOCION:
                 CargarInformacionPromocion();
                 titulo=ListaInformacion.ID_PROMOCION;
                 break;
             case ListaInformacion.ID_PIZZA:
                 CargarInformacionPizza();
                 titulo=ListaInformacion.ID_PIZZA;
                 break;
             case ListaInformacion.ID_POLLO:
                 CargarInformacionPollo();
                 titulo=ListaInformacion.ID_POLLO;
                 break;
             case ListaInformacion.ID_HAMBURGUESA:
                 CargarInformacionHamburguesa();
                 titulo=ListaInformacion.ID_HAMBURGUESA;
                 break;
         }

        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(titulo);

        //DetalleActivity para ver el detalle del producto especifico
        this.adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            /*    Toast.makeText(ListaProducto.this, lista.get(listaProducto.getChildAdapterPosition(view)).getIdProducto(),
                        Toast.LENGTH_SHORT).show();*/

                Producto producto=ListaProducto.this.lista.get(listaProducto.getChildAdapterPosition(view));
                Intent intn=new Intent(ListaProducto.this, DetalleActivity.class);
                //Seteo de datos hacia "DetallActivity"
                intn.putExtra(ID_PRODUCTO,producto.getIdProducto());
                intn.putExtra(ID_IMAGEN,producto.getImagen());
                intn.putExtra(ID_TITULO,producto.getProducto());
                intn.putExtra(ID_DESCRIPCION,producto.getDescripcion());
                intn.putExtra(ID_PRECIO,producto.getPrecio());
                intn.putExtra(ID_CANTIDAD,producto.getCantidad());
                startActivity(intn);
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Menu del Toolbar
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.carrito:
                //Presiono carrito de compras
                Logger logger=Logger.getInstance();
                if(logger.getListaCarrito().size()>0){
                    Intent intn=new Intent(ListaProducto.this,ListaCarrito.class);
                    startActivity(intn);
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(ListaProducto.this);
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
                Intent intn=new Intent(ListaProducto.this,PerfilActivity.class);
                startActivity(intn);
                break;
        }
        return true;
    }
    private void CargarInformacionPromocion() {
        /*List<Producto> listaSesion=ListaInformacion.ListaProductos;
        for(Producto prod:listaSesion){
            lista.add(prod);
        }*/
        lista.clear();
        Producto info1 = new Producto();
        info1.setIdProducto(1);
        info1.setCantidad(5);
        info1.setProducto("Banquetazo Familiar");
        info1.setPrecio(19.50);
        info1.setDescripcion("6 alitas empanizadas, 9 alitas fritas, 9 camperitos, 1 orden de trocitos");
        info1.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Web58141.png");


        Producto info2 = new Producto();
        info2.setIdProducto(2);
        info2.setCantidad(6);
        info2.setProducto("Combo Luces 10 Piezas");
        info2.setPrecio((double) 21);
        info2.setDescripcion("Combo de 10 piezas de pollo combinadas, 5 acompañamientos, 5 ensaladas de repollo");
        info2.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/58178WAP.png");

        Producto info3 = new Producto();
        info3.setIdProducto(3);
        info3.setCantidad(8);
        info3.setProducto("Pizza Super Personal + Soda 12oz");
        info3.setPrecio(4.99);
        info3.setDescripcion("Pizza Super Personal 4 porciones + Soda 12oz");
        info3.setImagen("https://www.pizzahut.com.sv/static/media/images/products/webp/CMB_4908_MD.webp?2.0.22");

        Producto info4 = new Producto();
        info4.setIdProducto(4);
        info4.setCantidad(9);
        info4.setProducto("Banquete de 20 Finger Foods + Pizza Gigante 1 Ingrediente");
        info4.setPrecio((double) 17);
        info4.setDescripcion("20 Alitas o camperitos, 3 acompañamientos, 2 aderezos y una pizza gigante de un ingrediente");
        info4.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Wb53909.png");

        Producto info5 = new Producto();
        info5.setIdProducto(5);
        info5.setProducto("3/4 lb triple");
        info5.setCantidad(5);
        info5.setPrecio(6.25);
        info5.setDescripcion("aborea esta hamburguesa de tres jugosas carnes de res, envueltas en queso americano derretido");
        info5.setImagen("https://www.wendys.com.sv/static/media/images/products/webp/CMB_593_MD.webp?2.0.7");

        Producto info6 = new Producto();
        info6.setIdProducto(6);
        info6.setProducto("baconator doble");
        info6.setCantidad(5);
        info6.setPrecio(6.05);
        info6.setDescripcion("Tocino ahumado sobre carne fresca, nunca congelada y cocida al momento.");
        info6.setImagen("https://www.wendys.com.sv/static/media/images/products/webp/CMB_599_MD.webp?2.0.7");

        lista.add(info1);
        lista.add(info2);
        lista.add(info3);
        lista.add(info4);
        lista.add(info5);
        lista.add(info6);
    }

    private void CargarInformacionPollo() {
        lista.clear();
        Producto info1 = new Producto();
        info1.setIdProducto(1);
        info1.setProducto("Banquetazo Familiar");
        info1.setPrecio(19.50);
        info1.setCantidad(12);
        info1.setDescripcion("6 alitas empanizadas, 9 alitas fritas, 9 camperitos, 1 orden de trocitos" +
                " Combo de 10 piezas de pollo combinadas, 5 acompañamientos, 5 ensaladas de repollo" +
                " Banquete 30 alitas, con 4 acompañamientos, 3 aderezos y una pizza grande de un ingrediente " +
                "0 Piezas de pollo tradicional, 5 acompañamientos, 5 panes, pastel de tres leches familiar" +
                " 0 Piezas de pollo tradicional, 5 acompañamientos, 5 panes, pastel de tres leches familiar");
        info1.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Web58141.png");

        String descripcion="Combo de 10 piezas de pollo combinadas, 5 acompañamientos, 5 ensaladas de repollo\n\n" +
                                "Incluye: \n\n" +
                                "- 12 Panes \n"+
                                "- 1  Ensalada Familiar \n"+
                                "- 3  Flanes personales \n"+
                                "- 10 Piezas de pollo \n"+
                                "- 1  Soda grande";


        Producto info2 = new Producto();
        info2.setIdProducto(2);
        info2.setProducto("Combo Luces 10 Piezas");
        info2.setPrecio((double) 21);
        info2.setDescripcion(descripcion);
        info2.setCantidad(5);
        info2.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/58178WAP.png");

        Producto info3 = new Producto();
        info3.setIdProducto(3);
        info3.setProducto("Combo Fiesta 10 Piezas Bañadas");
        info3.setPrecio(22.50);
        info3.setCantidad(15);
        info3.setDescripcion("Combo de 10 piezas de pollo bañadas, 5 acompañamientos, 5 ensaladas de repollo");
        info3.setImagen("https://i.pinimg.com/originals/20/87/bc/2087bc5431a009b4f55120d34ac06743.jpg");

        Producto info4 = new Producto();
        info4.setIdProducto(4);
        info4.setCantidad(25);
        info4.setProducto("Banquete de 20 Finger Foods + Pizza Gigante 1 Ingrediente");
        info4.setPrecio((double) 17);
        info4.setDescripcion("20 Alitas o camperitos, 3 acompañamientos, 2 aderezos y una pizza gigante de un ingrediente");
        info4.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Wb53909.png");

        Producto info5 = new Producto();
        info5.setIdProducto(5);
        info5.setProducto("Banquete 30 Alitas + Pizza Gigante 1 Ingrediente");
        info5.setPrecio((double) 20);
        info5.setCantidad(65);
        info5.setDescripcion("Banquete 30 alitas, con 4 acompañamientos, 3 aderezos y una pizza grande de un ingrediente.");
        info5.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/51027.png");

        Producto info6 = new Producto();
        info6.setIdProducto(6);
        info6.setProducto("Banquete 30 Alitas + Pizza Gigante De Especialidad");
        info6.setPrecio((double) 20);
        info6.setCantidad(10);
        info6.setDescripcion("Banquete 30 alitas, con 4 acompañamientos, 3 aderezos y una pizza gigante de especialidad");
        info6.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Wb58013A.png");

        Producto info7 = new Producto();
        info7.setIdProducto(7);
        info7.setProducto("Banquete 30 Camperitos + Pizza Gigante De Especialidad ");
        info7.setPrecio((double) 22);
        info7.setCantidad(50);
        info7.setDescripcion("Banquete 30 Camperitos, con 4 acompañamientos, 3 aderezos y una pizza gigante de especialidad");
        info7.setImagen("https://www.saborusa.com/wp-content/uploads/2019/10/Celebremos-juntos-el-Dia-Nacional-del-pollo-frito-Foto-destacada.png");

        Producto info8 = new Producto();
        info8.setIdProducto(8);
        info8.setProducto("Combo 10 Piezas Cumpleañero");
        info8.setPrecio(25.95);
        info8.setCantidad(45);
        info8.setDescripcion("0 Piezas de pollo tradicional, 5 acompañamientos, 5 panes, pastel de tres leches familiar");
        info8.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Wb53489.png");

        Producto info9 = new Producto();
        info9.setIdProducto(9);
        info9.setProducto("Combo 6 Piezas + Pizza Gigante Cumpleañero");
        info9.setPrecio(24.95);
        info9.setCantidad(1);
        info9.setDescripcion("6 Piezas de pollo tradicional, 3 acompañamientos, 3 panes, pastel de tres leches familiar");
        info9.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Wb53490.png");

        Producto info10 = new Producto();
        info10.setIdProducto(10);
        info10.setProducto("Combo 6 Piezas cumpleañero");
        info10.setPrecio((double) 18);
        info10.setCantidad(25);
        info10.setDescripcion("6 Piezas de pollo tradicional, 3 acompañamientos, 3 panes, 2 pastel chocolate ");
        info10.setImagen("https://www.campero.com/iCadImagesMNCSV/Productos/Wb53491.png");
        lista.add(info1);
        lista.add(info2);
        lista.add(info3);
        lista.add(info4);
        lista.add(info5);
        lista.add(info6);
        lista.add(info7);
        lista.add(info8);
        lista.add(info9);
        lista.add(info10);

    }

    private void CargarInformacionPizza(){
        lista.clear();
        Producto info1 = new Producto();
        info1.setIdProducto(11);
        info1.setProducto("Duo Mega Combos");
        info1.setPrecio(9.99);
        info1.setCantidad(1);
        info1.setDescripcion("Cada combo: Pizza Personal de Jamón, Pepperoni o Suprema más 4 palitroques personales");
        info1.setImagen("https://www.pizzahut.com.sv/static/media/images/products/webp/CMB_4917_MD.webp?2.0.22");

        Producto info2 = new Producto();
        info2.setCantidad(1);
        info2.setIdProducto(12);
        info2.setProducto("Pizza Personal 1 Ingrediente + Soda 12 oz");
        info2.setPrecio(2.99);
        info2.setDescripcion("Pizza Personal 4 porciones + Soda 12oz");
        info2.setImagen("https://www.pizzahut.com.sv/static/media/images/products/webp/CMB_4910_MD.webp?2.0.22");

        Producto info3 = new Producto();
        info3.setIdProducto(13);
        info3.setCantidad(35);
        info3.setProducto("Pizza Super Personal + Soda 12oz");
        info3.setPrecio(4.99);
        info3.setDescripcion("Pizza Super Personal 4 porciones + Soda 12oz");
        info3.setImagen("https://www.pizzahut.com.sv/static/media/images/products/webp/CMB_4908_MD.webp?2.0.22");

        Producto info4 = new Producto();
        info4.setIdProducto(14);
        info4.setProducto("2 Pizzas Personales + Palitroques");
        info4.setPrecio(5.99);
        info4.setCantidad(55);
        info4.setDescripcion("Dos deliciosas Pizzas Personales de 4 porciones + 1 orden de Palitroques");
        info4.setImagen("https://www.pizzahut.com.sv/static/media/images/products/webp/CMB_4905_MD.webp?2.0.22");

        Producto info5 = new Producto();
        info5.setIdProducto(15);
        info5.setProducto("Hut chesse  Grande de un Ingrediente + Palitroques");
        info5.setPrecio(9.99);
        info5.setCantidad(52);
        info5.setDescripcion("Pizza Hut Cheese (orilla rellena de queso mozzarella) más una orden de cinco palitroques");
        info5.setImagen("https://www.pizzahut.com.sv/static/media/images/products/webp/CMB_4912_MD.webp?2.0.22");

        Producto info6 = new Producto();
        info6.setIdProducto(16);
        info6.setProducto("Pan Pizza Gigante 1 Ingrediente +Palitroques + Soda 1.5L");
        info6.setPrecio(12.99);
        info6.setCantidad(35);
        info6.setDescripcion("Una Pan Pizza Gigante (12 pociones) cinco deliciosos Palitroques");
        info6.setImagen("https://www.pizzahut.com.sv/static/media/images/products/webp/CMB_4914_MD.webp?2.0.22");

        Producto info7 = new Producto();
        info7.setIdProducto(17);
        info7.setProducto("Pizza 4 Estaciones + Palitroques Gratis");
        info7.setPrecio(15.99);
        info7.setCantidad(15);
        info7.setDescripcion("Deliciosa Pizza 4 (16 porciones) dividida en 4 sabores: Jamón, Peperoni, Hawaiana y Suprema");
        info7.setImagen("https://www.pizzahut.com.sv/static/media/images/products/webp/CMB_4915_MD.webp?2.0.22");

        Producto info8 = new Producto();
        info8.setIdProducto(18);
        info8.setProducto("Duo Hot Combos");
        info8.setPrecio(7.99);
        info8.setCantidad(15);
        info8.setDescripcion("2 pizzas personales + 2 vasos de soda 12oz + 8 palitroques personales con salsa boloñesa");
        info8.setImagen("https://www.pizzahut.com.sv/static/media/images/products/webp/CMB_5218_MD.webp?2.0.22");

        Producto info9 = new Producto();
        info9.setIdProducto(19);
        info9.setProducto("Triple Hut Box Masa Tradicional");
        info9.setPrecio(17.99);
        info9.setCantidad(5);
        info9.setDescripcion("2 Pizzas Tradicionales (8 porciones cada una) + 4 Panes con Ajo Supremo + 6 Palitroques");
        info9.setImagen("https://www.pizzahut.com.sv/static/media/menu/webp/Banquetes/Bodegones-500x500-THB.webp?2.0.22");

        Producto info10 = new Producto();
        info10.setIdProducto(20);
        info10.setProducto("Big Party Box Alitas");
        info10.setPrecio(18.99);
        info10.setCantidad(5);
        info10.setDescripcion("Big Party Box Alitas que incluye: Pizza Gigante (12 porciones) + 6 Alitas + 6 Cheese Sticks Jr ");
        info10.setImagen("https://www.pizzahut.com.sv/static/media/images/products/webp/CMB_69_MD.webp?2.0.22");
        lista.add(info1);
        lista.add(info2);
        lista.add(info3);
        lista.add(info4);
        lista.add(info5);
        lista.add(info6);
        lista.add(info7);
        lista.add(info8);
        lista.add(info9);
        lista.add(info10);
    }

    private void CargarInformacionHamburguesa(){
        lista.clear();
        Producto info1 = new Producto();
        info1.setIdProducto(21);
        info1.setProducto("Hamburguesa con queso");
        info1.setPrecio(4.00);
        info1.setCantidad(5);
        info1.setDescripcion("Hamburguesa con queso cheddar vegetales, tocino");
        info1.setImagen("https://cocina-casera.com/wp-content/uploads/2016/11/hamburguesa-queso-receta.jpg");

        Producto info2 = new Producto();
        info2.setIdProducto(22);
        info2.setProducto("cheddar chistorria");
        info2.setCantidad(15);
        info2.setPrecio(5.90);
        info2.setDescripcion("Llena de sabor tu paladar con esta nueva hamburguesa hecha exclusivamente para la época");
        info2.setImagen("https://www.wendys.com.sv/static/media/images/products/webp/CMB_6096_MD.webp?2.0.7");

        Producto info3 = new Producto();
        info3.setIdProducto(23);
        info3.setProducto("1/4 lb single");
        info3.setCantidad(25);
        info3.setPrecio(3.99);
        info3.setDescripcion("Llena de sabor tu paladar con esta hamburguesa que trae un cuarto de libra de carne jugosa");
        info3.setImagen("https://www.wendys.com.sv/static/media/images/products/webp/CMB_569_MD.webp?2.0.7");

        Producto info4 = new Producto();
        info4.setIdProducto(24);
        info4.setProducto("1/2 lb double");
        info4.setPrecio(5.25);
        info4.setCantidad(35);
        info4.setDescripcion("Prueba media libra de dos carnes jugosas y calientitas, con queso americano derretido");
        info4.setImagen("https://www.wendys.com.sv/static/media/images/products/webp/CMB_589_MD.webp?2.0.7");

        Producto info5 = new Producto();
        info5.setIdProducto(25);
        info5.setProducto("3/4 lb triple");
        info5.setPrecio(6.25);
        info5.setCantidad(35);
        info5.setDescripcion("aborea esta hamburguesa de tres jugosas carnes de res, envueltas en queso americano derretido");
        info5.setImagen("https://www.wendys.com.sv/static/media/images/products/webp/CMB_593_MD.webp?2.0.7");

        Producto info6 = new Producto();
        info6.setIdProducto(26);
        info6.setProducto("baconator doble");
        info6.setCantidad(15);
        info6.setPrecio(6.05);
        info6.setDescripcion("Tocino ahumado sobre carne fresca, nunca congelada y cocida al momento.");
        info6.setImagen("https://www.wendys.com.sv/static/media/images/products/webp/CMB_599_MD.webp?2.0.7");

        Producto info7 = new Producto();
        info7.setIdProducto(27);
        info7.setProducto("big bacon");
        info7.setPrecio(5.30);
        info7.setCantidad(7);
        info7.setDescripcion("Cuatro lascas de crujiente tocino ahumado, sobre una jugosa carne");
        info7.setImagen("https://www.wendys.com.sv/static/media/images/products/webp/CMB_597_MD.webp?2.0.7");

        Producto info8 = new Producto();
        info8.setIdProducto(28);
        info8.setProducto("la melt");
        info8.setPrecio(5.05);
        info8.setCantidad(8);
        info8.setDescripcion("Una de las favoritas. Deliciosa mezcla de carne fresca nunca congelada, queso americano");
        info8.setImagen("https://www.wendys.com.sv/static/media/images/products/webp/CMB_261_MD.webp?2.0.7");

        Producto info9 = new Producto();
        info9.setIdProducto(29);
        info9.setProducto("chili burguer");
        info9.setPrecio(5.05);
        info9.setCantidad(9);
        info9.setDescripcion("no dejes pasar la oportunidad de probarla dentro de nuestras famosas hamburguesas.");
        info9.setImagen("https://www.wendys.com.sv/static/media/images/products/webp/CMB_595_MD.webp?2.0.7");

        Producto info10 = new Producto();
        info10.setIdProducto(30);
        info10.setProducto("bacon parmesan");
        info10.setPrecio(5.60);
        info10.setCantidad(4);
        info10.setDescripcion("Aderezo Parmesano, tocino ahumado, cebolla fresca y queso");
        info10.setImagen("https://www.wendys.com.sv/static/media/images/products/webp/CMB_601_MD.webp?2.0.7");
        lista.add(info1);
        lista.add(info2);
        lista.add(info3);
        lista.add(info4);
        lista.add(info5);
        lista.add(info6);
        lista.add(info7);
        lista.add(info8);
        lista.add(info9);
        lista.add(info10);
    }
}