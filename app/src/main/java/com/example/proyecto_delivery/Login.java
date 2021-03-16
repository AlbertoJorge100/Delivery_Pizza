package com.example.proyecto_delivery;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_delivery.BaseDatos.RespuestaUsuarios;
import com.example.proyecto_delivery.Interfaces.JsonPlaceHolder;
import com.example.proyecto_delivery.Modelos.Categorias;
import com.example.proyecto_delivery.Modelos.Usuarios;
import com.example.proyecto_delivery.Utilerias.Hash;
import com.example.proyecto_delivery.Utilerias.Logger;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity implements Serializable {
    private ProgressDialog progress;
    private Boolean Acceso = false;
    private ProgressDialog progressDialog;
    TextView txbContrasena;
    public static final String LISTA_CATEGORIAS="lstCat";
    private Usuarios UsuarioTemp;
    private List<Categorias> CategoriasTemp;
    private int Contador=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TextView txbRegistro = findViewById(R.id.txbRegistro);
        final TextView txbUsuario = findViewById(R.id.RegtxbUsuario);
        txbContrasena = findViewById(R.id.txbContrasena);
        this.progressDialog = new ProgressDialog(this);

        Button btnSesion = findViewById(R.id.btnSesion);
        //final LoadingDialog loadingDialog = new LoadingDialog(Login.this);
        txbUsuario.setText("adminh");
        txbContrasena.setText("adminh");
        //Presionar inicio de sesion
        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validar que los campos no esten vacios
                if (ValidarCampos(new TextView[]{txbUsuario, txbContrasena})) {
                    String user = txbUsuario.getText().toString();
                    String pass = txbContrasena.getText().toString();
                    if(Contador>=1){
                        //El usuario ingreso una contraseña incorrecta
                        ValidarUsuario(UsuarioTemp,CategoriasTemp);
                    }else{
                        IniciarSesion(user, pass);
                    }
                }
            }
        });
        txbRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intn = new Intent(Login.this, RegistroActivity.class);
                startActivity(intn);

            }
        });
    }

    private Boolean ValidarCampos(TextView[] datos) {
        Boolean resultado = true;
        for (TextView aux : datos) {
            if (aux.getText().toString().equals("")) {
                resultado = false;
                aux.setError("Campo obligatorio !");
            }
        }
        return resultado;
    }

    public void download(View view) {
        progress = new ProgressDialog(this);
        progress.setMessage("Downloading Music");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();

        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while (jumpTime < totalProgressTime) {
                    try {
                        sleep(200);
                        jumpTime += 5;
                        progress.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    /**
     * Cargar la informacion del web services
     * @param usuario
     * @param password
     */
    private void IniciarSesion(String usuario, final String password) {
        try {
            //Mostrando el progressdialog
            this.progressDialog.show();
            this.progressDialog.setMessage("Cargando...");
            this.progressDialog.setCancelable(true);
            //Direccion del servidor
            String url = getResources().getString(R.string.UrlAplicacion_local);
            Retrofit retrofit = new Retrofit
                    .Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JsonPlaceHolder jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
            Call<RespuestaUsuarios> call = jsonPlaceHolder.Login(usuario);
            call.enqueue(new Callback<RespuestaUsuarios>() {
                @Override
                public void onResponse(Call<RespuestaUsuarios> call, Response<RespuestaUsuarios> response) {
                    if (response.isSuccessful()) {
                        //peticion exitosa
                        RespuestaUsuarios aux = response.body();
                        if (aux.getUsuario() != null && aux.getCodigo().equals("200")) {
                            //Usuario encontrado, retorna codigo 200
                            ValidarUsuario(aux.getUsuario(),aux.getCategorias());
                        } else {
                            //Usuario no existe
                            Toast.makeText(Login.this, aux.getMensaje(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Login.this,  response.message() + " " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                    Login.this.progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<RespuestaUsuarios> call, Throwable t) {
                    //Fallo la peticion
                    Login.this.progressDialog.dismiss();
                    Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            this.progressDialog.dismiss();
            Toast.makeText(Login.this, "Excepcion: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Validacion de la contraseña del usuario, y posterior almacenamiento en Singleton
     *, en caso que ambas contraseñas coincidan (La que trae el servidor, y la que escribe el usuario).
     * En caso que el usuario ingrese su contraseña erronea, mayor o igual a 1 vez, no se consultara
     * el servidor web, se validara de manera local.
     * @param usuario usuario a evaluar
     * @param lista lista de categorias a almacenar en
     *              una lista temporal.
     */
    private void ValidarUsuario(Usuarios usuario, List<Categorias> lista){
        String password=this.txbContrasena.getText().toString();
        String contra=Hash.generarHash(password,Hash.SHA256);
        if (contra.equals(usuario.getPassword())) {
            //Contraseña correcta, paso todos los filtros

            if(this.Contador>=1){
                this.Contador=0;
            }
            Logger logger = Logger.getInstance();
            //Singleton fill, llenar la longitud de la contraseña, para mostrarla en el perfil.
            logger.setPassword2(password);
            //Seteo completo del usuario a singleton
            logger.setUsuario(usuario);
            //Envio de la lista de categorias a la siguiente activity
            Bundle extra = new Bundle();
            extra.putSerializable("lista", (Serializable) lista);
            Intent intn = new Intent(Login.this, ListaInformacion.class);
            intn.putExtra(Login.this.LISTA_CATEGORIAS, extra);
            startActivity(intn);
        } else {
            //El usuario ingreso una contraseña incorrecta, Almacenamiento temporal de datos...

            if(this.Contador==0){
                /*Almacenamos los datos, del web services, en caso que el usuario, ingrese muchas
                veces una contraseña erronea*/
                this.UsuarioTemp=usuario;
                this.CategoriasTemp=lista;
            }
            this.Contador++;
            Toast.makeText(Login.this, calcularNombre(usuario.getNombres()) + " tu contraseña es incorrecta", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Calculamos el nombre para mostrar un toast en caso
     * que el usuario haya ingresado mal su contraseña
     * @param nombres
     * @return
     */
    private String calcularNombre(String nombres){
        int i=0;
        while(i<nombres.length()){
            if(nombres.charAt(i)==' '){
                return nombres.substring(0,i);
            }
            i++;
        }
        return nombres;
    }

}
 /*
 Login Anterior
 public void onClick(View view) {
                if(ValidarCampos(new TextView[]{txbUsuario,txbContrasena})){
                    classUsuario usuario=new classUsuario(Login.this);
                    if(usuario.GetUsuario(txbUsuario.getText().toString())){
                        //Encriptacion de contraseña ingresada x el usuario
                        String pass=txbContrasena.getText().toString();
                        String password= Hash.generarHash(pass,Hash.SHA256);
                        if(password.equals(usuario.getPassword())){
                            Intent intsn=new Intent(Login.this,ListaInformacion.class);
                            startActivity(intsn);
                            //Singleton fill
                            Logger logger=Logger.getInstance();
                            logger.setIdUsuario(usuario.getIdUsuario());
                            logger.setNombres(usuario.getNombres());
                            logger.setUsuario(usuario.getUsuario());
                            logger.setPassword(password);
                            logger.setPassword2(pass);
                            logger.setCorreo(usuario.getCorreo());
                            logger.setDireccion(usuario.getDireccion());
                            logger.setTelefono(usuario.getTelefono());

                        }else{
                            Toast.makeText(Login.this,usuario.getNombres()+" tu contraseña es incorrecta !",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Login.this,"El usuario no existe !",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


 Libreria similar a retrofit
 AsyncHttpClient client=new AsyncHttpClient();
    client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
    RequestHandle requestHandler=client.get(getResources().getString(R.string.UrlAplicacion_local) + "/api/Login/" + usuario, null
        , new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                //Toast.makeText(Login.this,new String(responseBody),Toast.LENGTH_SHORT).show();

                Usuarios usuario=null;
                try {
                    String json=new String(responseBody);
                    JSONObject jsonObject=new JSONObject(json);
                    String jsonVisualizer="";
                    jsonVisualizer=jsonObject.getJSONObject("data").toString();
                    JSONObject jsonUser=jsonObject.getJSONObject("data");
                    if(jsonVisualizer.equals("")){
                        Toast.makeText(Login.this,"El usuario no existe",Toast.LENGTH_SHORT).show();
                    }
                    Gson gson=new Gson();
                    Type collectionTypes=new TypeToken<Usuarios>(){}.getType();
                    usuario=gson.fromJson(jsonVisualizer,collectionTypes);

                    Toast.makeText(Login.this,usuario.getNombres(),Toast.LENGTH_SHORT).show();
                    *//*Type collectionTypes2=new TypeToken<List<Usuarios>>(){}.getType();
                    List<Usuarios> lista=new ArrayList<>();
                    lista=gson.fromJson(jsonVisualizer,collectionTypes2);*//*


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Usuarios usuario=responseBody
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(Login.this,new String(error.getMessage()),Toast.LENGTH_SHORT).show();
            }
        });*/