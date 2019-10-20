package com.supermarket.shingshing.util;

import android.content.Context;

import com.supermarket.shingshing.models.UsuarioModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Utils {
    private static final String archivo = "usuario.txt";

    public static void guardarArchivoUsuario(Context context, UsuarioModel usuario) {
        try {
            FileOutputStream f = new FileOutputStream(new File(context.getFilesDir(), archivo));
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(usuario);

            o.close();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UsuarioModel leerArchivoUsuario(Context context) {
        try {
            FileInputStream fi = new FileInputStream(new File(context.getFilesDir(), archivo));
            ObjectInputStream oi = new ObjectInputStream(fi);

            UsuarioModel usuario = (UsuarioModel) oi.readObject();

            oi.close();
            fi.close();

            return usuario;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void eliminarArchivoUsuario(Context context) {
        context.deleteFile(archivo);
    }

    public static String getMes(int mesId) {
        String mes;
        switch (mesId) {
            case 1:
                mes = "Ene.";
                break;
            case 2:
                mes = "Feb.";
                break;
            case 3:
                mes = "Mar.";
                break;
            case 4:
                mes = "Abr.";
                break;
            case 5:
                mes = "May.";
                break;
            case 6:
                mes = "Jun.";
                break;
            case 7:
                mes = "Jul.";
                break;
            case 8:
                mes = "Ago.";
                break;
            case 9:
                mes = "Sept.";
                break;
            case 10:
                mes = "Oct.";
                break;
            case 11:
                mes = "Nov.";
                break;
            default:
                mes = "Dic.";
                break;
        }

        return mes;
    }
}
