package com.supermarket.shingshing.main.ocr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityCameraBinding;
import com.wonderkiln.camerakit.CameraKit;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {
    private static final String TAG = CameraActivity.class.getSimpleName();
    private ActivityCameraBinding binding;
    private CameraView cameraView;
    private ImageView preview;
    private View view;
    private int contador;
    private boolean flash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera);
        iniciarVistas();
    }

    private void iniciarVistas() {
        contador = 1;
        flash = false;
        cameraView = binding.cvCamera;
        preview = binding.ivCameraPreview;
        view = binding.vCameraOverlay;

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {}

            @Override
            public void onError(CameraKitError cameraKitError) {}

            @Override
            public void onImage(CameraKitImage cameraKitImage) {
                Bitmap bitmap = cameraKitImage.getBitmap();
                bitmap = Bitmap.createScaledBitmap(bitmap, cameraView.getWidth(), cameraView.getHeight(), false);
                Bitmap scale = Bitmap.createBitmap(bitmap, view.getLeft(), view.getTop(), view.getWidth(), view.getHeight());
                mostrarCaptura(scale);
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {}
        });

        binding.ivCameraTomar.setOnClickListener(v -> {
            cameraView.start();
            cameraView.captureImage();
        });

        binding.ivCameraFlash.setOnClickListener(v -> cambiarFlash());

        binding.tvCameraOk.setOnClickListener(v -> mostrarPreview());

        binding.tvCameraBorrar.setOnClickListener(v -> borrar());
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    public void onPause() {
        cameraView.stop();
        super.onPause();
    }

    private void mostrarCaptura(Bitmap bitmap) {
        guardarImagen(bitmap);
        setPreview(bitmap);
        binding.tvCameraOk.setVisibility(View.VISIBLE);
        binding.tvCameraBorrar.setVisibility(View.VISIBLE);
    }

    private void guardarImagen(Bitmap bitmap) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imagenes", Context.MODE_PRIVATE);
        File mypath = new File(directory, String.format(Locale.US, "img_%d.png", contador));

        try (FileOutputStream fos = new FileOutputStream(mypath)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            Log.e(TAG, "Error al guardar la imagen: " + e.getLocalizedMessage());
        }

        contador++;
    }

    private void setPreview(Bitmap bitmap) {
        binding.ivCameraPreview.setVisibility(View.VISIBLE);

        preview.setImageBitmap(bitmap);
    }

    private void mostrarPreview() {
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra("imagenes", contador - 1);
        startActivity(intent);
    }

    private void borrar() {
        contador = 1;
        binding.tvCameraOk.setVisibility(View.INVISIBLE);
        binding.tvCameraBorrar.setVisibility(View.INVISIBLE);
        binding.ivCameraPreview.setVisibility(View.INVISIBLE);

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imagenes", Context.MODE_PRIVATE);
        String[] entries = directory.list();
        for (String s: entries) {
            File currentFile = new File(directory.getPath(), s);
            currentFile.delete();
        }
    }

    private void cambiarFlash() {
        if (flash) {
            cameraView.setFlash(CameraKit.Constants.FLASH_OFF);
            binding.ivCameraFlash.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_flash_white));
            flash = false;
        } else {
            cameraView.setFlash(CameraKit.Constants.FLASH_ON);
            binding.ivCameraFlash.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_flash_bold_white));
            flash = true;
        }
    }
}
