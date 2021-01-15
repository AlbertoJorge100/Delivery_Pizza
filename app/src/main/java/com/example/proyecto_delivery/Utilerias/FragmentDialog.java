package com.example.proyecto_delivery.Utilerias;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.proyecto_delivery.PagoActivity;
import com.example.proyecto_delivery.R;

public class FragmentDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final String [] municipios=getActivity().getResources().getStringArray(R.array.municipios);
        AlertDialog.Builder builder=new AlertDialog.Builder((getActivity()));
        builder.setTitle("Elije un municipio");
        builder.setItems(municipios, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PagoActivity.ResultadoMunicipio=municipios[i];
                PagoActivity.ValidarMunicipio=true;
                PagoActivity.btnMunicipio.setText(municipios[i]);
            }
        });
        return builder.create();
    }
}
