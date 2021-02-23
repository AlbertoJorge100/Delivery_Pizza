package com.example.proyecto_delivery;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_delivery.BaseDatos.RespuestaUsuarios;
import com.example.proyecto_delivery.Interfaces.JsonPlaceHolder;
import com.example.proyecto_delivery.Modelos.Usuarios;
import com.example.proyecto_delivery.Utilerias.Hash;
import com.example.proyecto_delivery.Utilerias.Logger;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilActivity extends AppCompatActivity {
    //progressdialog para ver el progreso de la peticion
    private ProgressDialog progressDialog;
    //Singleton
    private Logger logger;

    //Switch
    private Switch SwitchNombres;
    private Switch SwitchUsuario;
    private Switch SwitchTelefono;
    private Switch SwitchCorreo;
    private Switch SwitchPassword;

    //EditTexts para ingresar los datos a modificar
    private TextView txbNombres;
    private TextView txbUsuario;
    private TextView txbTelefono;
    private TextView txbCorreo;
    private TextView txbPassword;
    private TextView txbPasswordN;
    private TextView txbPasswordNR;
    //Linearlayout para mostrar y ocultar el area de confirmacion
    private LinearLayout LayoutConfirmacion;
    //Linearlayout para mostrar y ocultar el area del password
    private LinearLayout LayoutPassword;
    //Singleton
    private Logger _Logger=Logger.getInstance();
    //Usuario global para mostrar los datos del usuario
    private Usuarios UsuarioSingleton=_Logger.getUsuario();
    //Boton de modificar los datos
    private Button btnAceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        this.progressDialog=new ProgressDialog(this);

        //Orientacion por defecto..
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.logger=Logger.getInstance();

        //Conexiones
        ImageView imgPerfilImagen=findViewById(R.id.imgPerfilImagen);
        final TextView lblNombre=findViewById(R.id.lblPerfilNombre);
        final TextView lblUsuario=findViewById(R.id.lblPerfilUsuario);
        final TextView lblTelefono=findViewById(R.id.lblPerfilTelefono);
        final TextView lblCorreo=findViewById(R.id.lblPerfilCorreo);
        final TextView lblPassword=findViewById(R.id.lblPerfilPassword);
        final TextView lblTituloPassword=findViewById(R.id.lblTituloPassword);
        final TextView lblPerfilCompras=findViewById(R.id.lblPerfilCompras);
        lblPerfilCompras.setText(Integer.toString(this.UsuarioSingleton.getCompras()));

        this.btnAceptar=findViewById(R.id.PerfilbtnAceptar);
        this.txbNombres=findViewById(R.id.PerfiltxbNombres);
        this.txbUsuario=findViewById(R.id.PerfiltxbUsuario);
        this.txbTelefono=findViewById(R.id.PerfiltxbTelefono);
        this.txbCorreo=findViewById(R.id.PerfiltxbCorreo);
        this.txbPassword=findViewById(R.id.txbPassword);
        this.txbPasswordN=findViewById(R.id.txbPasswordN);
        this.txbPasswordNR=findViewById(R.id.txbPasswordNR);
        this.LayoutConfirmacion=findViewById(R.id.LayoutConfirmacion);
        this.LayoutPassword=findViewById(R.id.LayoutPassword);


        this.SwitchNombres=findViewById(R.id.SwitchNombres);
        this.SwitchUsuario=findViewById(R.id.SwitchUsuario);
        this.SwitchTelefono=findViewById(R.id.SwitchTelefono);
        this.SwitchCorreo=findViewById(R.id.SwitchCorreo);
        this.SwitchPassword=findViewById(R.id.SwitchPassword);
        Boolean a=SwitchNombres.isChecked();
        //Switch Nombres
        SwitchNombres.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //Encendido mostrar los datos en los edittexts
                    txbNombres.setVisibility(View.VISIBLE);
                    txbNombres.setText(PerfilActivity.this.UsuarioSingleton.getNombres());
                    lblNombre.setVisibility(View.GONE);
                }else{
                    txbNombres.setVisibility(View.GONE);
                    lblNombre.setVisibility(View.VISIBLE);
                    //Apagado
                }
                EstadoBoton(b);
            }
        });

        //Switch Usuario
        SwitchUsuario.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //Encendido mostrar los datos en los edittexts
                    txbUsuario.setVisibility(View.VISIBLE);
                    txbUsuario.setText(PerfilActivity.this.UsuarioSingleton.getUsuario());
                    lblUsuario.setVisibility(View.GONE);
                }else{
                    txbUsuario.setVisibility(View.GONE);
                    lblUsuario.setVisibility(View.VISIBLE);
                }
                EstadoBoton(b);
            }
        });

        //Switch Telefono
        SwitchTelefono.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //Encendido mostrar los datos en los edittexts
                    txbTelefono.setVisibility(View.VISIBLE);
                    txbTelefono.setText(PerfilActivity.this.UsuarioSingleton.getTelefono());
                    lblTelefono.setVisibility(View.GONE);
                }else{
                    txbTelefono.setVisibility(View.GONE);
                    lblTelefono.setVisibility(View.VISIBLE);
                }
                EstadoBoton(b);
            }
        });

        //Switch Correo
        SwitchCorreo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //Encendido mostrar los datos en los edittexts
                    txbCorreo.setVisibility(View.VISIBLE);
                    txbCorreo.setText(PerfilActivity.this.UsuarioSingleton.getCorreo());
                    lblCorreo.setVisibility(View.GONE);
                }else{
                    txbCorreo.setVisibility(View.GONE);
                    lblCorreo.setVisibility(View.VISIBLE);
                }
                EstadoBoton(b);
            }
        });

        //Switch Password
        SwitchPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //Encendido mostrar los datos en los edittexts
                    //Mostrando el linearlayout de la contraseña
                    PerfilActivity.this.LayoutPassword.setVisibility(View.VISIBLE);
                    lblPassword.setVisibility(View.GONE);
                    lblTituloPassword.setVisibility(View.INVISIBLE);//quien eres?
                }else{
                    PerfilActivity.this.LayoutPassword.setVisibility(View.GONE);
                    lblPassword.setVisibility(View.VISIBLE);
                    lblTituloPassword.setVisibility(View.VISIBLE);
                    txbPasswordN.setText("");
                    txbPasswordNR.setText("");
                }
                EstadoBoton(b);
            }
        });

        //Presionar boton aceptar
        this.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuarios usuario=ValidarCampos();
                if(usuario!=null){
                    //Toast.makeText(PerfilActivity.this,"Campos aceptados "+usuario.getNombres(),Toast.LENGTH_SHORT).show();
                    UpdateUsuario(txbPasswordN.getText().toString(), usuario);
                }
            }
        });

        //Imagen quemada
        String imagen="https://i.pinimg.com/originals/64/3e/fe/643efe51394d635cbf544a25088ee269.png";
        Picasso.get().load(imagen).error(R.mipmap.ic_launcher_round).fit().centerInside().into((ImageView) imgPerfilImagen);

        //Datos del usuario amostrar, cargadas desde singleton
        Usuarios usuario=this.logger.getUsuario();
        lblNombre.setText(usuario.getNombres());
        lblUsuario.setText(usuario.getUsuario());
        lblTelefono.setText(usuario.getTelefono());
        lblCorreo.setText(usuario.getCorreo());

        //Caracteres y longitud del password
        lblPassword.setText(this.logger.getPassword2());
    }

    /**
     * El boton estara visible siempre y cuando este uno o mas
     * switchs encendidos.
     * @param estado: estado del switch actual en el que se esta
     *              invocando el metodo
     */
    private void EstadoBoton(Boolean estado){
        //Es mejor aguardarlos en arreglo para poder procesar
        Switch switches[]={SwitchNombres,SwitchUsuario,SwitchPassword,SwitchTelefono,SwitchCorreo};
        int cont=0;
        for(int i=0;i<switches.length;i++){
            if(switches[i].isChecked()){
                //contar cuantos switchs estan encendidos
                cont++;
            }
        }
        if(estado){
            //Switch encendido
            if(cont==1){
                /*Solamente esta en cendido el switch que actualmente estamos
                        presionando*/
                this.LayoutConfirmacion.setVisibility(View.VISIBLE);
            }
        }else{
            //Switch Apagado
            if(cont==0){
                //El ultimo switch es el que estamos presionando, hay que ocultar el layout
                this.LayoutConfirmacion.setVisibility(View.GONE);
                this.txbPassword.setText("");
            }
        }
    }

    /**
     * Validacion de los campos para poder enviar los datos unicamente
     * necesarios, hacia el web services
     * @return
     */
    private Usuarios ValidarCampos(){
        //Variable de desicion
        Boolean resultado=true;
        //Usuario a llenar con los datos
        Usuarios Usuario=new Usuarios();

        //Datos que se usaran mas de una vez
        String passwordActual=txbPassword.getText().toString();
        String usuario=this.txbUsuario.getText().toString();
        String passwordN=this.txbPasswordN.getText().toString();
        String telefono=this.txbTelefono.getText().toString();
        String correo=this.txbCorreo.getText().toString();

        if(this.SwitchNombres.isChecked()){
            //Nombres activado
            String nombres=this.txbNombres.getText().toString();
            if(nombres.equals("")){
                //Esta vacio el nombre
                this.txbNombres.setError("Campo obligatorio !");
                resultado=false;
            }else{
                Usuario.setNombres(nombres);
            }
        }

        //Usuario activado
        if(this.SwitchUsuario.isChecked()){
            if(usuario.equals("")){
                //Usuario vacio
                this.txbUsuario.setError("Campo obligatorio !");
                resultado=false;
            }else if(usuario.length()<6){
                this.txbUsuario.setError("Usuario muy corto !");
                resultado=false;
            }
            else{Usuario.setUsuario(usuario);}
        }
        //Password activado
        if(this.SwitchPassword.isChecked()){
            String passwordNR=this.txbPasswordNR.getText().toString();
            if(passwordN.equals("")){
                //Password vacio
                this.txbPasswordN.setError("Campo obligatorio !");
                resultado=false;
            }
            if(passwordNR.equals("")){
                //Repetir password vacio
                this.txbPasswordNR.setError("Campo obligatorio !");
            }else if(!passwordN.equals(passwordNR)){
                //Ambos campos no estan vacios pero, no coinciden las contraseñas
                this.txbPasswordNR.setError("Contraseñas no coinciden!");
                resultado=false;
            }else if(passwordN.length()<6){
                //La longitud del password es inferior a 6
                this.txbPasswordN.setError("Contraseña muy corta!");
                resultado=false;
            }
            else{
                //Ninguna de las anteriores, cumple los requisitos
                Usuario.setPassword(Hash.generarHash(passwordN,Hash.SHA256));
                //Toast.makeText(this,"acceso contra",Toast.LENGTH_SHORT).show();
            }
        }

        if(this.SwitchCorreo.isChecked()){
            //Correo activado
            if(correo.equals("")){
                //correo vacio
                this.txbCorreo.setError("Campo obligatorio !");
                resultado=false;
            }else if(!ValidarCorreo()){
                //Correo no vacio pero no lleva @, o '.'
                this.txbCorreo.setError("Correo invalido !");
                resultado=false;
            }
            else{
                //Todo bien
                Usuario.setCorreo(correo);
            }
        }

        if(this.SwitchTelefono.isChecked()){
            //Telefono activado
            if(telefono.equals("")){
                //telefono vacio
                this.txbTelefono.setError("Campo obligatorio !");
                resultado=false;
            }else if(telefono.length()<8){
                //Telefono invalido
                this.txbTelefono.setError("Numero de telefono invalido !");
                resultado=false;
            }else{
                //Cumple los requisitos
                Usuario.setTelefono(telefono);
            }
        }
        //Validacion de la contraseña actual, para proceder a enviar la peticion al web services
        if(passwordActual.equals("")){
            //Contraseña actual vacia
            this.txbPassword.setError("Campo obligatorio !");
            resultado=false;
            //Utilizando la clase hash para encriptar la contraseña que escribamos y copararla con la desingleton
        }else if(!Hash.generarHash(passwordActual,Hash.SHA256).equals(this.UsuarioSingleton.getPassword())){
            //No vacia, pero no coincide con la contraseña, almacenada en singleton
            this.txbPassword.setError("Contraseña incorrecta !");
            resultado=false;
        }

        //Validamos si cumplio con los filtros
        if(resultado){
            //Todo correcto
            Usuario.setIDUsuario(this.UsuarioSingleton.getIDUsuario());
            return Usuario;
        }
        return null;
    }

    /**
     * Validacion del correo electronico
     * @return: true: correo correcto
     */
    private Boolean ValidarCorreo(){
        String correo=txbCorreo.getText().toString();
        int i=0, cont=0;
        while(i<correo.length()){
            if(correo.charAt(i)=='@'){
                cont++;
            }
            if(correo.charAt(i)=='.'){
                cont++;
            }
            i++;
        }
        return (cont>=2);
    }


    /**
     * Cargar los datos en el web services
     * @param password Se validara si ubo un cambio del password para asi modificar el singleton
     * @param usuario
     */
    private void UpdateUsuario(final String password, Usuarios usuario){
        this.progressDialog.show();
        this.progressDialog.setMessage("Cargando...");
        this.progressDialog.setCancelable(false);
        //String values
        String url=getResources().getString(R.string.UrlAplicacion_local);
        Retrofit retrofit=new Retrofit
                .Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolder jsonPlaceHolder=retrofit.create(JsonPlaceHolder.class);
        Call<RespuestaUsuarios> call=jsonPlaceHolder.UpdateUsuarios(usuario);
        call.enqueue(new Callback<RespuestaUsuarios>() {
            @Override
            public void onResponse(Call<RespuestaUsuarios> call, Response<RespuestaUsuarios> response) {
                RespuestaUsuarios respuesta=response.body();
                if(response.isSuccessful()){
                    //Peticion exitosa

                    if(respuesta.getUsuario()!=null && respuesta.getCodigo().equals("200")){
                        //Seteo, actualizacion del usuario en singleton
                        PerfilActivity.this._Logger.setUsuario(respuesta.getUsuario());
                        //Seteo de numero de caracteres que tiene la contraseña
                        if(password.length()>1 && password.length()!=_Logger.getPassword2().length()){
                            /*Hubo un cambio de contraseña, entonces hay que modificar la longitud
                            de caracteres en singleton*/
                            _Logger.setPassword2(password);
                        }
                        //Cerrar la activity
                        PerfilActivity.this.finish();
                    }
                    //Mensaje del servidor
                    Toast.makeText(PerfilActivity.this,respuesta.getMensaje(),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(PerfilActivity.this,"Error response "+response.message(),Toast.LENGTH_SHORT).show();
                }
                PerfilActivity.this.progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<RespuestaUsuarios> call, Throwable t) {
                //Error al conectar al web services
                PerfilActivity.this.progressDialog.dismiss();
                Toast.makeText(PerfilActivity.this,"Error onFailure: "+t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}