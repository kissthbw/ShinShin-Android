package com.supermarket.shingshing.main.ocr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityPreviewBinding;
import com.supermarket.shingshing.models.ResponseAnalizarTicket;
import com.supermarket.shingshing.network.ApiClient;
import com.supermarket.shingshing.network.ApiService;
import com.supermarket.shingshing.resultado.ResultadoActivity;
import com.supermarket.shingshing.util.UtilsView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PreviewActivity extends AppCompatActivity {
    private static final String TAG = PreviewActivity.class.getSimpleName();
    private ActivityPreviewBinding binding;
    private ResponseAnalizarTicket responseTicket;
    private JsonArray lineas;
    private int imagenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preview);

        if (getIntent() != null && getIntent().getExtras() != null) {
            imagenes = getIntent().getIntExtra("imagenes", 0);
            iniciarVistas();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if(result != null) {
                if(result.getContents() == null) {
                    verVistaError();
                } else {
                    verProductos(responseTicket, result.getContents());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    
    private void iniciarVistas() {
        lineas = new JsonArray();
        binding.vp2Preview.setAdapter(new PreviewAdapter(this, imagenes));

        binding.btnPreviewAgregar.setOnClickListener(v -> finish());
        binding.btnPreviewListo.setOnClickListener(v -> leerImagenes(1));
        binding.ivEliminar.setOnClickListener(view -> borrar());
    }

    private void leerImagenes(int contador) {
        try {
            ContextWrapper cw = new ContextWrapper(this);
            File directorio = cw.getDir("imagenes", Context.MODE_PRIVATE);
            String nombre = String.format(Locale.US, "img_%d.png", contador);

            File f = new File(directorio, nombre);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));
            reconocerTexto(bitmap, contador);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Error al leer archivo: " + e.getLocalizedMessage());
        }
    }

    private void reconocerTexto(Bitmap imageBitmap, int contador) {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        textRecognizer.processImage(image)
                .addOnSuccessListener(firebaseVisionText -> procesarTexto(firebaseVisionText, contador))
                .addOnFailureListener(e -> Log.e(TAG, "Error al procesar la imagen"));
    }

    private void procesarTexto(FirebaseVisionText text, int contador) {
        for (FirebaseVisionText.TextBlock block : text.getTextBlocks()) {
            for (FirebaseVisionText.Line line : block.getLines()) {
                lineas.add(line.getText());
            }
        }

        if (contador == imagenes) {
            consultarAnalzarTicket();
        } else {
            leerImagenes(contador + 1);
        }
    }

    private void consultarAnalzarTicket() {
        UtilsView.mostrarProgress(this, getString(R.string.general_msg_esperar));
        binding.btnPreviewListo.setEnabled(false);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("lineas", lineas);

        ApiService apiService = ApiClient.getClient(this).create(ApiService.class);
        Disposable disposable = apiService.getAnalizarTicket(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    UtilsView.esconderProgress();
                    binding.btnPreviewListo.setEnabled(true);
                    if (result.getCode() == 200) {
                        if (result.getTieneCB()) {
                            responseTicket = result;
                            leerCodigoBarras();
                        } else {
                            verProductos(result, null);
                        }
                    } else {
                        verVistaError();
                    }
                }, throwable -> {
                    UtilsView.esconderProgress();
                    Log.e("RESULT preview", "error: " + throwable.getLocalizedMessage());
                    binding.btnPreviewListo.setEnabled(true);
                    verVistaError();
                });
    }

    private void leerCodigoBarras() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setOrientationLocked(true);
        integrator.setBeepEnabled(true);
        integrator.setCaptureActivity(CaptureBarrasActivity.class);
        integrator.initiateScan();
    }

    private void verProductos(ResponseAnalizarTicket response, String codigoBarras) {
        Intent intent = new Intent(this, ResultadoActivity.class);
        intent.putExtra("ticket", response);
        if (codigoBarras != null) {
            intent.putExtra("codigoBarras", codigoBarras);
        }
        startActivity(intent);
    }

    private void verVistaError() {
        startActivity(new Intent(this, LenteSucioActivity.class));
    }

    private void borrar() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imagenes", Context.MODE_PRIVATE);
        String[] entries = directory.list();
        for (String s: entries) {
            File currentFile = new File(directory.getPath(), s);
            currentFile.delete();
        }
        finish();
    }
}
