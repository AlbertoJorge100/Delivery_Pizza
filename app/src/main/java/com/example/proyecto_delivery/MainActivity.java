package com.example.proyecto_delivery;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ImageView imgContenedor=findViewById(R.id.imgContenedor);
        String imagen="https://image.freepik.com/vector-gratis/pizza-delivery-service-flat-vector-illustration_82574-2658.jpg";
        Picasso.get().load(imagen).error(R.mipmap.ic_launcher_round).fit().centerInside().into((ImageView) imgContenedor);


        EnviarData();

    }
    private void EnviarData(){
        final ProgressBar progressBar=findViewById(R.id.pbCargando);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int progreso = 0;
                while (progreso < 100){
                    //simular proceso
                    try{
                        Thread.sleep(5);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    progreso++;

                    //actualizar progresbar
                    final int finalProgreso = progreso;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(finalProgreso);
                        }
                    });

                }
                MainActivity.this.finish();
                Intent intn=new Intent(MainActivity.this,Login.class);
                startActivity(intn);
            }
        }).start();
        //Toast.makeText(MainActivity.this,"finalizada",Toast.LENGTH_SHORT).show();

        /**/

    }
}