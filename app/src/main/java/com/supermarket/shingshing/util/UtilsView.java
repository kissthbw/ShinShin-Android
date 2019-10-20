package com.supermarket.shingshing.util;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class UtilsView {
    private static ProgressDialog progressDialog;

    public static void mostrarAlerta(Context context, String title, String mensaje, String textoPositivo) {
        if (title == null) {
            title = "";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(mensaje)
                .setCancelable(false)
                .setPositiveButton(textoPositivo, (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void mostrarProgress(Context context, String message) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void esconderProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
