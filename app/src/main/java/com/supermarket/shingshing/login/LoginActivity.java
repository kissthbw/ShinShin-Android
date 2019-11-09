package com.supermarket.shingshing.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonObject;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.crear.CrearCuentaActivity;
import com.supermarket.shingshing.databinding.ActivityLoginBinding;
import com.supermarket.shingshing.main.MainActivity;
import com.supermarket.shingshing.models.LoginModel;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.privacidad.PrivacidadActivity;
import com.supermarket.shingshing.recuperar.RecuperarActivity;
import com.supermarket.shingshing.util.UserPreferences;
import com.supermarket.shingshing.util.UsuarioSingleton;
import com.supermarket.shingshing.util.Utils;
import com.supermarket.shingshing.util.UtilsView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 10;
    private ActivityLoginBinding binding;
    private ApiService apiService;
    private CallbackManager callbackManager;
    private boolean isVisibleContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        iniciarVistas();
        checarPermisos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.etLoginCorreo.clearFocus();
        binding.etLoginContrasena.clearFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            getDatosGoogle(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 10 && grantResults.length > 0) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    finish();
                }
            }
        }
    }

    private void checarPermisos() {
        ArrayList<String> permisosFaltantes = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permisosFaltantes.add(Manifest.permission.CAMERA);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permisosFaltantes.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (permisosFaltantes.size() > 0) {
            String[] permisosFaltantesArray = new String[permisosFaltantes.size()];
            permisosFaltantesArray = permisosFaltantes.toArray(permisosFaltantesArray);
            ActivityCompat.requestPermissions(this, permisosFaltantesArray, 10);
        }
    }

    private void iniciarVistas() {
        binding.btnLoginIniciar.setOnClickListener(v -> login());
        binding.tvLoginOlvidar.setOnClickListener(v -> irActivity(RecuperarActivity.class));
        binding.ibLoginCrearCorreo.setOnClickListener(v -> irActivity(CrearCuentaActivity.class));
        binding.ivLoginContrasena2.setOnClickListener(v -> mostrarContrasena());
        binding.tvLoginTituloPrivacidad.setOnClickListener(v -> irActivity(PrivacidadActivity.class));

        //binding.etLoginCorreo.setText("kissthbw@gmail.com");
        //binding.etLoginContrasena.setText("kiss2101");
        binding.etLoginCorreo.setText("roberto.htamayo@gmail.com");
        binding.etLoginContrasena.setText("robe2019");

        apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        iniciarLoginGoogle();
        iniciarLoginFacebook();
    }

    private void login() {
        //irActivity(AyudaTourActivity.class);
        binding.btnLoginIniciar.setEnabled(false);
        String correo = binding.etLoginCorreo.getText().toString();
        String contrasena = binding.etLoginContrasena.getText().toString();

        if (validarDatos(correo, contrasena)) {
            //kissthbw@gmail.com
            //kiss2101
            //roberto.htamayo@gmail.com
            //robe2019
            JsonObject json = new JsonObject();
            json.addProperty("usuario", correo);
            json.addProperty("contrasenia", contrasena);

            ejecutarServicio(json);
        } else {
            binding.btnLoginIniciar.setEnabled(true);
        }
    }

    private boolean validarDatos(String correo, String contrasena) {
        if (correo.trim().isEmpty() || (!correo.matches("\\d+") && !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches())) {
            binding.etLoginCorreo.setError(getString(R.string.login_error_correo));
            binding.etLoginCorreo.requestFocus();
            return false;
        } else if (correo.matches("\\d+") && !correo.matches("\\d{2,10}")) {
            binding.etLoginCorreo.setError(getString(R.string.login_error_correo));
            binding.etLoginCorreo.requestFocus();
            return false;
        }

        if (contrasena.trim().isEmpty()) {
            binding.etLoginContrasena.setError(getString(R.string.login_error_contrasena));
            binding.etLoginContrasena.requestFocus();
            return false;
        }

        return true;
    }

    private void ejecutarServicio(JsonObject json) {
        UtilsView.mostrarProgress(this, getString(R.string.general_msg_esperar));
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        Disposable disposable = apiService.login(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnLoginIniciar.setEnabled(true);
                    if (result.getCode() == 200) {
                        result.getUsuario().setIdRedSocial(0);
                        result.getUsuario().setBonificacion(result.getBonificacion());
                        UsuarioSingleton.getInstance(result.getUsuario());
                        UserPreferences.setLoginUser(this, new LoginModel(LoginModel.TIPO_CORREO));
                        Utils.guardarArchivoUsuario(this, result.getUsuario());
                        irActivity(MainActivity.class);
                        finish();
                    } else {
                        UtilsView.mostrarAlerta(this, null, getString(R.string.login_error_servicio), getString(R.string.general_button_aceptar));
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnLoginIniciar.setEnabled(true);
                    Log.e(TAG, "Login error: " + throwable.getLocalizedMessage());
                });
    }

    private void irActivity(Class clase) {
        startActivity(new Intent(this, clase));
    }

    private void mostrarContrasena() {
        if (isVisibleContra) {
            binding.ivLoginContrasena2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_open_grey));
            binding.etLoginContrasena.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isVisibleContra = false;
        } else {
            binding.ivLoginContrasena2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_close_grey));
            binding.etLoginContrasena.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isVisibleContra = true;
        }
    }

    private void iniciarLoginGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.ibLoginCrearGoogle.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    private void getDatosGoogle(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if (account != null) {
                String email = account.getEmail();
                String nombre = account.getGivenName();
                String apellidos = account.getFamilyName();

                registrarCuentaSocial(nombre + " " + apellidos, email, 1);
            }
        } catch (ApiException e) {
            Log.e(TAG, "Login getDatosFacebook");
            e.printStackTrace();
        }
    }

    private void iniciarLoginFacebook() {
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        getDatosFacebook(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "Login Facebook cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e(TAG, "Login Facebook error: " + exception.toString());
                    }
                });

        binding.ibLoginCrearFacebook.setOnClickListener(view -> LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email","public_profile")));
    }

    private void getDatosFacebook(AccessToken accessToken) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, (object, response) -> {
            try {
                String email = object.getString("email");
                String nombre = object.getString("first_name");
                String apellidos = object.getString("last_name");

                registrarCuentaSocial(nombre + " " + apellidos, email, 2);
            } catch (JSONException e) {
                Log.e(TAG, "Login getDatosFacebook");
                e.printStackTrace();
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields","email,first_name,last_name,gender,picture");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    private void registrarCuentaSocial(String nombre, String email, int tipoSocial) {
        UtilsView.mostrarProgress(this, getString(R.string.general_msg_esperar));
        JsonObject json = new JsonObject();
        json.addProperty("nombre", nombre);
        json.addProperty("fechaNac", "1970-01-01");
        json.addProperty("telMovil", "+5215555555555");
        json.addProperty("correoElectronico", email);
        json.addProperty("usuario", email);
        json.addProperty("contrasenia", email);
        json.addProperty("codigoPostal", "00000");
        json.addProperty("idCatalogoSexo", 3);
        json.addProperty("idRedSocial", tipoSocial);

        Disposable disposable = apiService.registrarSocial(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    if (result.getCode() == 200) {
                        result.getUsuario().setIdRedSocial(tipoSocial);
                        result.getUsuario().setBonificacion(result.getBonificacion());
                        UsuarioSingleton.getInstance(result.getUsuario());
                        UserPreferences.setLoginUser(this, new LoginModel(tipoSocial == 1 ? LoginModel.TIPO_GOOGLE : LoginModel.TIPO_FACEBOOK));
                        Utils.guardarArchivoUsuario(this, result.getUsuario());
                        irActivity(MainActivity.class);
                        finish();
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    Log.e(TAG, "Error registrar cuenta social: " + throwable.getLocalizedMessage());
                });
    }
}
