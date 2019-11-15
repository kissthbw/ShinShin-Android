package com.supermarket.shingshing.crear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityCrearCuentaBinding;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.privacidad.PrivacidadActivity;
import com.supermarket.shingshing.terminos.TerminosActivity;
import com.supermarket.shingshing.util.Utils;
import com.supermarket.shingshing.util.UtilsView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CrearCuentaActivity extends AppCompatActivity implements TextWatcher {
    private static final String TAG = CrearCuentaActivity.class.getSimpleName();
    private ActivityCrearCuentaBinding binding;
    private String fecha;
    private boolean isVisibleContra1, isVisibleContra2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_crear_cuenta);

        iniciarVistas();
    }

    private void iniciarVistas() {
        crearSpinnerSexo();
        binding.btnCrearCuenta.setOnClickListener(v -> obtenerDatos());
        binding.clCrearFecha.setOnClickListener(v -> obtenerFecha());
        binding.tvCrearTerminos.setOnClickListener(v -> startActivity(new Intent(this, TerminosActivity.class)));
        binding.tvCrearPrivacidad.setOnClickListener(v -> startActivity(new Intent(this, PrivacidadActivity.class)));
        binding.cbCrearAceptarTC.setOnCheckedChangeListener((buttonView, isChecked) -> binding.cbCrearAceptarTC.setError(null));
        binding.ivCrearContrasena2.setOnClickListener(v -> mostrarContrasena1());
        binding.ivCrearConfirmar2.setOnClickListener(v -> mostrarContrasena2());

        binding.etCrearContrasena.addTextChangedListener(this);
        binding.etCrearConfirmar.addTextChangedListener(this);
    }

    private void crearSpinnerSexo() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options_sexo, R.layout.item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sCrearSexo.setAdapter(adapter);
    }

    private void obtenerFecha(){
        Calendar c = Calendar.getInstance();
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int anio = c.get(Calendar.YEAR);

        DatePickerDialog recogerFecha = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            binding.tvCrearMes.setError(null);
            binding.tvCrearMes.setText(Utils.getMes(month + 1));
            binding.tvCrearDia.setText(String.format(Locale.US, "%02d", dayOfMonth));
            binding.tvCrearAnio.setText(String.valueOf(year));
            fecha = String.format(Locale.US, "%d-%02d-%02d", year, month + 1, dayOfMonth);
        },anio, mes, dia);
        recogerFecha.getDatePicker().setMaxDate(new Date().getTime());
        recogerFecha.show();
    }

    private void obtenerDatos() {
        binding.btnCrearCuenta.setEnabled(false);
        String nombre = binding.etCrearNombre.getText().toString();
        String correo = binding.etCrearCorreo.getText().toString();
        String contrasena = binding.etCrearContrasena.getText().toString();
        String confirmar = binding.etCrearConfirmar.getText().toString();
        String telefono = binding.etCrearTelefono.getText().toString();
        String cp = binding.etCrearCP.getText().toString();
        String genero = binding.sCrearSexo.getSelectedItem().toString();
        int sexo = genero.equalsIgnoreCase(getString(R.string.msg_femenino)) ? 2 : genero.equalsIgnoreCase(getString(R.string.msg_masculino)) ? 1 : 0;

        if (validarDatos(nombre, correo, contrasena, confirmar, telefono, sexo, cp)) {
            JsonObject json = new JsonObject();
            json.addProperty("nombre", nombre);
            json.addProperty("fechaNac", fecha);
            json.addProperty("telMovil", "+521"+telefono);
            json.addProperty("correoElectronico", correo);
            json.addProperty("usuario", correo);
            json.addProperty("contrasenia", contrasena);
            json.addProperty("codigoPostal", cp);
            json.addProperty("idCatalogoSexo", sexo);

            ejecutarServicio(json, telefono);
        } else {
            binding.btnCrearCuenta.setEnabled(true);
        }
    }

    private boolean validarDatos(String nombre, String email, String contrasena, String conContrasena, String telefono, int sexo, String cp) {
        if (nombre.trim().isEmpty() || nombre.length() <= 2) {
            binding.etCrearNombre.setError(getString(R.string.crear_error_nombre));
            binding.etCrearNombre.requestFocus();
            return false;
        }

        if (email.trim().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etCrearCorreo.setError(getString(R.string.crear_error_correo));
            binding.etCrearCorreo.requestFocus();
            return false;
        }

        if (contrasena.trim().isEmpty() || contrasena.length() < 8) {
            binding.etCrearContrasena.setError(getString(R.string.crear_error_contrasena1));
            binding.etCrearContrasena.requestFocus();
            return false;
        }

        if (conContrasena.trim().isEmpty() || conContrasena.length() < 8) {
            binding.etCrearConfirmar.setError(getString(R.string.crear_error_contrasena2));
            binding.etCrearConfirmar.requestFocus();
            return false;
        }

        if (!contrasena.equalsIgnoreCase(conContrasena)) {
            binding.etCrearConfirmar.setError(getString(R.string.crear_error_contrasena3));
            binding.etCrearConfirmar.requestFocus();
            return false;
        }

        if (telefono.trim().isEmpty() || telefono.length() < 10) {
            binding.etCrearTelefono.setError(getString(R.string.crear_error_telefono));
            binding.etCrearTelefono.requestFocus();
            return false;
        }

        if (fecha == null || fecha.trim().isEmpty()) {
            binding.tvCrearMes.setError(getString(R.string.crear_error_fecha));
            binding.tvCrearMes.requestFocus();
            return false;
        }

        if (sexo == 0) {
            View selectedView = binding.sCrearSexo.getSelectedView();
            if (selectedView instanceof TextView) {
                binding.sCrearSexo.requestFocus();
                TextView selectedTextView = (TextView) selectedView;
                selectedTextView.setError(getString(R.string.msg_selecciona)); // any name of the error will do
                selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
                selectedTextView.setText(getString(R.string.msg_selecciona)); // actual error message
            }
            return false;
        }

        if (cp.trim().isEmpty() || cp.length() < 5) {
            binding.etCrearCP.setError(getString(R.string.crear_error_cp));
            binding.etCrearCP.requestFocus();
            return false;
        }

        if (!binding.cbCrearAceptarTC.isChecked()) {
            binding.cbCrearAceptarTC.setError(getString(R.string.crear_error_tyc));
            return false;
        }

        return true;
    }

    private void ejecutarServicio(JsonObject json, String telefono) {
        UtilsView.mostrarProgress(this, getString(R.string.general_msg_esperar));
        ApiService apiService = ApiClient.getClient(getApplicationContext()).create(ApiService.class);
        Disposable disposable = apiService.registrarCuenta(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnCrearCuenta.setEnabled(true);
                    if (result.getCode() == 200) {
                        Intent intent = new Intent(this, ValidarActivity.class);
                        intent.putExtra("id", result.getId());
                        intent.putExtra("telefono", telefono.substring(6));
                        startActivity(intent);
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnCrearCuenta.setEnabled(true);
                    Log.e(TAG, "Crear cuenta error: " + throwable.getLocalizedMessage());
                });
    }

    private void mostrarContrasena1() {
        if (isVisibleContra1) {
            binding.ivCrearContrasena2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_close_grey));
            binding.etCrearContrasena.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isVisibleContra1 = false;
        } else {
            binding.ivCrearContrasena2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_open_grey));
            binding.etCrearContrasena.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isVisibleContra1 = true;
        }
    }

    private void mostrarContrasena2() {
        if (isVisibleContra2) {
            binding.ivCrearConfirmar2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_close_grey));
            binding.etCrearConfirmar.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isVisibleContra2 = false;
        } else {
            binding.ivCrearConfirmar2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_open_grey));
            binding.etCrearConfirmar.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isVisibleContra2 = true;
        }
    }

    private void validarContrasenas() {
        String contrasena = binding.etCrearContrasena.getText().toString();
        String confirmar = binding.etCrearConfirmar.getText().toString();

        if (contrasena.trim().length() > 0 && confirmar.trim().length() > 0) {
            if (contrasena.trim().equals(confirmar)) {
                binding.ivCrearContrasenaSuccess.setVisibility(View.VISIBLE);
                binding.ivCrearConfirmarSuccess.setVisibility(View.VISIBLE);
            } else {
                binding.ivCrearContrasenaSuccess.setVisibility(View.GONE);
                binding.ivCrearConfirmarSuccess.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        validarContrasenas();
    }

    @Override
    public void afterTextChanged(Editable s) {}
}
