package com.example.proyecto_delivery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Clase Splash
 *
 */

public class Cargando extends AppCompatActivity {
    ProgressBar progressbar;
    private int count=0;
    Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargando);
        this.progressbar=findViewById(R.id.pb_Cargando);
        this.timer=new Timer();
        TimerTask timertask=new TimerTask() {
            @Override
            public void run() {
                Cargando.this.count++;
                progressbar.setProgress(Cargando.this.count);
                if(Cargando.this.count==50){
                    Cargando.this.timer.cancel();
                    finish();
                    Intent intn=new Intent(Cargando.this,ListaInformacion.class);
                    startActivity(intn);
                }
            }
        };
        this.timer.schedule(timertask,0,50);
    }
}