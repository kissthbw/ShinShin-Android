package com.supermarket.shingshing.main.menu.opciones;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.common.EliminarDialogListener;
import com.supermarket.shingshing.databinding.FragmentPerfilBinding;
import com.supermarket.shingshing.common.EliminarDialogFragment;
import com.supermarket.shingshing.main.menu.opciones.eliminar.EliminarCuentaActivity;
import com.supermarket.shingshing.models.UsuarioModel;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.util.UsuarioSingleton;
import com.supermarket.shingshing.util.Utils;
import com.supermarket.shingshing.util.UtilsView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PerfilFragment extends Fragment implements EliminarDialogListener, TextWatcher {
    private static final String TAG = PerfilFragment.class.getSimpleName();
    private static final int CON_ACTUAL = 1;
    private static final int CON_NUEVA = 2;
    private static final int CON_CONFIRMAR = 3;
    private static final int REQUEST_GALLERY = 101;
    private FragmentPerfilBinding binding;
    private UsuarioModel usuario;
    private boolean isVisibleContra1, isVisibleContra2, isVisibleContra3;
    private String fecha, imagen64;

    public PerfilFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_perfil, container, false);

        iniciarVistas();

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK)
            if (requestCode == REQUEST_GALLERY) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap = BitmapFactory.decodeFile(imgDecodableString);
                binding.ivPerfilImagen.setImageBitmap(bitmap);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos); //bm is the bitmap object
                byte[] byteArray = baos.toByteArray();
                imagen64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
            }
    }

    private void iniciarVistas() {
        usuario = UsuarioSingleton.getUsuario();

        crearSpinnerSexo();
        binding.clPerfilFecha.setOnClickListener(v -> obtenerFecha());
        binding.ivPerfilContrasenaActualVer.setOnClickListener(v -> mostrarContrasena(CON_ACTUAL));
        binding.ivPerfilContrasenaNuevaVer.setOnClickListener(v -> mostrarContrasena(CON_NUEVA));
        binding.ivPerfilConfirmarVer.setOnClickListener(v -> mostrarContrasena(CON_CONFIRMAR));
        binding.btnPerfilGuardar.setOnClickListener(v -> obtenerDatos());

        binding.ivPerfilFoto.setOnClickListener(v -> iniciarGaleria());

        binding.etPerfilContrasenaNueva.addTextChangedListener(this);
        binding.etPerfilConfirmar.addTextChangedListener(this);

        binding.tvPerfilEliminar.setOnClickListener(v -> {
            EliminarDialogFragment dialogFragment = new EliminarDialogFragment(this);
            dialogFragment.show(getFragmentManager().beginTransaction(), EliminarDialogFragment.class.getSimpleName());
        });

        llenarVistas();
    }

    private void iniciarGaleria() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    private void llenarVistas() {
        if (usuario.getNombre() != null && !usuario.getNombre().trim().isEmpty()) {
            binding.etPerfilNombre.setText(usuario.getNombre());
        }

        if (usuario.getCorreoElectronico() != null && !usuario.getCorreoElectronico().trim().isEmpty()) {
            binding.etPerfilEmail.setText(usuario.getCorreoElectronico());
        }

        if (usuario.getTelMovil() != null && !usuario.getTelMovil().trim().isEmpty()) {
            String telefono = usuario.getTelMovil().substring(4);
            binding.etPerfilNumero.setText(telefono);
        }

        if (usuario.getFechaNac() != null && !usuario.getFechaNac().trim().isEmpty()) {
            fecha = usuario.getFechaNac();
            String[] nac = fecha.split("-");
            binding.tvPerfilMes.setText(Utils.getMes(Integer.parseInt(nac[1])));
            binding.tvPerfilDia.setText(nac[2]);
            binding.tvPerfilAnio.setText(nac[0]);
        }

        if (usuario.getIdCatalogoSexo() != 0) {
            if (usuario.getIdCatalogoSexo() == 1) {
                binding.sPerfilSexo.setSelection(2);
            } else {
                binding.sPerfilSexo.setSelection(1);
            }
        }

        if (usuario.getCodigoPostal() != null && !usuario.getCodigoPostal().trim().isEmpty()) {
            binding.etPerfilCP.setText(usuario.getCodigoPostal());
        }

        if (usuario.getImgUrl() != null && !usuario.getImgUrl().trim().isEmpty()) {
            binding.ivPerfilImagen.setPadding(0, 0, 0, 0);
            Picasso.get().load(usuario.getImgUrl()).into(binding.ivPerfilImagen);
        }
    }

    @Override
    public void onClickEliminar() {
        startActivity(new Intent(getContext(), EliminarCuentaActivity.class));
    }

    private void crearSpinnerSexo() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.options_sexo, R.layout.item_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.sPerfilSexo.setAdapter(adapter);
    }

    private void obtenerFecha(){
        Calendar c = Calendar.getInstance();
        int mes = c.get(Calendar.MONTH);
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int anio = c.get(Calendar.YEAR);

        DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            binding.tvPerfilMes.setError(null);
            binding.tvPerfilMes.setText(Utils.getMes(month + 1));
            binding.tvPerfilDia.setText(String.format(Locale.US, "%02d", dayOfMonth));
            binding.tvPerfilAnio.setText(String.valueOf(year));
            fecha = String.format(Locale.US, "%d-%02d-%02d", year, month + 1, dayOfMonth);
        },anio, mes, dia);
        recogerFecha.getDatePicker().setMaxDate(new Date().getTime());
        recogerFecha.show();
    }

    private void contrasenaNueva() {
        String nueva = binding.etPerfilContrasenaNueva.getText().toString();
        String confirmar = binding.etPerfilConfirmar.getText().toString();

        if (nueva.trim().length() > 0 && confirmar.trim().length() > 0) {
            if (nueva.trim().equals(confirmar)) {
                binding.ivPerfilContrasenaNuevaSuccess.setVisibility(View.VISIBLE);
                binding.ivPerfilConfirmarSuccess.setVisibility(View.VISIBLE);
            } else {
                binding.ivPerfilContrasenaNuevaSuccess.setVisibility(View.GONE);
                binding.ivPerfilConfirmarSuccess.setVisibility(View.GONE);
            }
        }
    }

    private void mostrarContrasena(int tipo) {
        switch (tipo) {
            case CON_ACTUAL:
                if (isVisibleContra1) {
                    binding.ivPerfilContrasenaActualVer.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_eye_close_grey));
                    binding.etPerfilContrasenaActual.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isVisibleContra1 = false;
                } else {
                    binding.ivPerfilContrasenaActualVer.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_eye_open_grey));
                    binding.etPerfilContrasenaActual.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isVisibleContra1 = true;
                }
                break;
            case CON_NUEVA:
                if (isVisibleContra2) {
                    binding.ivPerfilContrasenaNuevaVer.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_eye_close_grey));
                    binding.etPerfilContrasenaNueva.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isVisibleContra2 = false;
                } else {
                    binding.ivPerfilContrasenaNuevaVer.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_eye_open_grey));
                    binding.etPerfilContrasenaNueva.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isVisibleContra2 = true;
                }
                break;
            case CON_CONFIRMAR:
                if (isVisibleContra3) {
                    binding.ivPerfilConfirmarVer.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_eye_close_grey));
                    binding.etPerfilConfirmar.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isVisibleContra3 = false;
                } else {
                    binding.ivPerfilConfirmarVer.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_eye_open_grey));
                    binding.etPerfilConfirmar.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isVisibleContra3 = true;
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        contrasenaNueva();
    }

    @Override
    public void afterTextChanged(Editable s) {}

    private void obtenerDatos() {
        binding.btnPerfilGuardar.setEnabled(false);
        String nombre = binding.etPerfilNombre.getText().toString();
        String correo = binding.etPerfilEmail.getText().toString();
        String telefono = binding.etPerfilNumero.getText().toString();
        String cp = binding.etPerfilCP.getText().toString();
        String genero = binding.sPerfilSexo.getSelectedItem().toString();
        int sexo = genero.equalsIgnoreCase(getString(R.string.msg_femenino)) ? 2 : genero.equalsIgnoreCase(getString(R.string.msg_masculino)) ? 1 : 0;
        String actual = binding.etPerfilContrasenaActual.getText().toString();
        String contrasena = binding.etPerfilContrasenaNueva.getText().toString();
        String confirmar = binding.etPerfilConfirmar.getText().toString();

        if (validarDatos(nombre, correo, telefono, cp, actual, contrasena, confirmar, fecha, sexo)) {
            JsonObject json = new JsonObject();
            json.addProperty("idUsuario", usuario.getIdUsuario());
            if (!nombre.isEmpty()) {
                json.addProperty("nombre", nombre);
            }
            if (!correo.isEmpty()) {
                json.addProperty("correoElectronico", correo);
            }
            if (!telefono.isEmpty()) {
                json.addProperty("telMovil", "+521"+telefono);
            }
            if (!cp.isEmpty()) {
                json.addProperty("codigoPostal", cp);
            }
            if (sexo != 0) {
                json.addProperty("idCatalogoSexo", sexo);
            }
            if (fecha != null) {
                json.addProperty("fechaNac", fecha);
            }
            if (!actual.isEmpty() && !contrasena.isEmpty()) {
                json.addProperty("contraseniaActual", actual);
                json.addProperty("contrasenia", contrasena);
            }
            if (imagen64 != null) {
                json.addProperty("imageData", imagen64);
            }

            ejecutarServicio(json);
        } else {
            binding.btnPerfilGuardar.setEnabled(true);
        }
    }

    private boolean validarDatos(String nombre, String email, String telefono, String cp, String actual, String contrasena, String confirmar, String fecha, int sexo) {
        if (!nombre.trim().isEmpty() && nombre.length() <= 2) {
            binding.etPerfilNombre.setError(getString(R.string.crear_error_nombre));
            binding.etPerfilNombre.requestFocus();
            return false;
        }

        if (!email.trim().isEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etPerfilEmail.setError(getString(R.string.crear_error_correo));
            binding.etPerfilEmail.requestFocus();
            return false;
        }

        if (!telefono.trim().isEmpty() && telefono.length() < 10) {
            binding.etPerfilNumero.setError(getString(R.string.crear_error_telefono));
            binding.etPerfilNumero.requestFocus();
            return false;
        }

        if(fecha == null || fecha.isEmpty()) {
            UtilsView.mostrarAlerta(getContext(),"", getString(R.string.crear_error_fecha), getString(R.string.general_button_aceptar));
            return false;
        }

        if(sexo == 0) {
            UtilsView.mostrarAlerta(getContext(),"", getString(R.string.crear_error_sexo), getString(R.string.general_button_aceptar));
            return false;
        }

        if (!cp.trim().isEmpty() && cp.length() < 5) {
            binding.etPerfilCP.setError(getString(R.string.crear_error_cp));
            binding.etPerfilCP.requestFocus();
            return false;
        }

        if (!actual.trim().isEmpty() && actual.length() < 8) {
            binding.etPerfilContrasenaActual.setError(getString(R.string.crear_error_contrasena1));
            binding.etPerfilContrasenaActual.requestFocus();
            return false;
        }

        if (!contrasena.trim().isEmpty() && contrasena.length() < 8) {
            binding.etPerfilContrasenaNueva.setError(getString(R.string.crear_error_contrasena1));
            binding.etPerfilContrasenaNueva.requestFocus();
            return false;
        }

        if (!confirmar.trim().isEmpty() && confirmar.length() < 8) {
            binding.etPerfilConfirmar.setError(getString(R.string.crear_error_contrasena1));
            binding.etPerfilConfirmar.requestFocus();
            return false;
        }

        if ((!actual.trim().isEmpty() || !contrasena.trim().isEmpty() || !confirmar.trim().isEmpty()) && (actual.trim().isEmpty() || contrasena.trim().isEmpty() || confirmar.trim().isEmpty())) {
            binding.etPerfilContrasenaActual.setError(getString(R.string.crear_error_contrasena4));
            binding.etPerfilContrasenaActual.requestFocus();
            return false;
        }

        if (!contrasena.equalsIgnoreCase(confirmar)) {
            binding.etPerfilConfirmar.setError(getString(R.string.crear_error_contrasena3));
            binding.etPerfilConfirmar.requestFocus();
            return false;
        }

        return true;
    }

    private void ejecutarServicio(JsonObject json) {
        UtilsView.mostrarProgress(getContext(), getString(R.string.general_msg_esperar));
        ApiService apiService = ApiClient.getClient(getContext()).create(ApiService.class);
        Disposable disposable = apiService.actualizarPerfil(json)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnPerfilGuardar.setEnabled(true);
                    if (result.getCode() == 200) {
                        mostrarMensaje();
                        UsuarioSingleton.getInstance(result.getUsuario());
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    binding.btnPerfilGuardar.setEnabled(true);
                    Log.e(TAG, "Actualizar perfil error: " + throwable.getLocalizedMessage());
                });
    }

    private void mostrarMensaje() {
        Snackbar snackbar = Snackbar.make(binding.nsvPerfilContainer, getString(R.string.nuevo_msg_actualizar), Snackbar.LENGTH_LONG);
        View snackbarLayout = snackbar.getView();
        TextView textView = snackbarLayout.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
        textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.dimen_icon));
        snackbar.setBackgroundTint(getResources().getColor(R.color.verde_mensaje));
        snackbar.show();
    }
}
