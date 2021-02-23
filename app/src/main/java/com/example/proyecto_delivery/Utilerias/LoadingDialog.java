package com.example.proyecto_delivery.Utilerias;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.proyecto_delivery.R;

public class LoadingDialog {
    private Activity _Activity;
    private AlertDialog Dialog;
    public LoadingDialog(Activity myActivity){
        this._Activity=myActivity;
    }
    public void StartLoadingDialog(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this._Activity);
        LayoutInflater inflater=_Activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.customdialog_xml,null));
        builder.setCancelable(true);
        Dialog=builder.create();
        Dialog.show();
    }
    public void DissmissDialog(){
        this.Dialog.dismiss();
    }
}
