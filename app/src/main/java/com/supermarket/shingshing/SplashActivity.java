package com.supermarket.shingshing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.gson.JsonObject;
import com.supermarket.shingshing.login.LoginActivity;
import com.supermarket.shingshing.main.MainActivity;
import com.supermarket.shingshing.models.UsuarioModel;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UserPreferences;
import com.supermarket.shingshing.util.UsuarioSingleton;
import com.supermarket.shingshing.util.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            if (UserPreferences.getLoginUser(this) != 0) {
                UsuarioModel usuario = Utils.leerArchivoUsuario(this);
                if (usuario != null) {
                    loginInBack(usuario.getUsuario(), usuario.getHash());
                    UsuarioSingleton.getInstance(usuario);
                    irActivity(MainActivity.class);
                } else {
                    irActivity(LoginActivity.class);
                }
            } else {
                irActivity(LoginActivity.class);
            }
        }, 3000);
    }

    private void loginInBack(String usuario, String hash) {
        JsonObject json = new JsonObject();
        json.addProperty("usuario", usuario);
        json.addProperty("hash", hash);

        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        Disposable disposable = apiService.loginInBack(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> Log.i(TAG, "Servicio login back OK"),
                        throwable -> Log.e(TAG, "Error servicio login back: " + throwable.getLocalizedMessage()));
    }

    private void irActivity(Class clase) {
        Intent mainIntent = new Intent(SplashActivity.this, clase);
        SplashActivity.this.startActivity(mainIntent);
        SplashActivity.this.finish();
    }
}
