package com.supermarket.shingshing.main.ocr.ayuda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityAyudaCamaraBinding;

public class AyudaCamaraActivity extends AppCompatActivity {
    private final static float MIN_SCALE = 0.65f;
    private final static float MIN_ALPHA = 0.3f;
    private ActivityAyudaCamaraBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ayuda_camara);

        iniciarVistas();
    }

    private void iniciarVistas() {
        binding.btnAyudaSalir.setOnClickListener(v -> finish());
        binding.btnAyudaSiguiente.setOnClickListener(v -> siguiente());
        binding.btnAyudaListo.setOnClickListener(v -> finish());

        binding.vpAyudaPager.setAdapter(new AyudaCamaraAdapter(this));
        binding.vpAyudaPager.setPageTransformer(this::zoomTransformacion);
        binding.vpAyudaPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                validarUltimo(position);
                listenerDots(position);
            }
        });
    }

    private void siguiente() {
        binding.vpAyudaPager.setCurrentItem(getItem());
    }

    private int getItem() {
        return binding.vpAyudaPager.getCurrentItem() + 1;
    }

    private void zoomTransformacion(View page, Float position) {
        if (position < -1) {
            page.setAlpha(0f);
        }
        else if (position <= 1) {
            page.setScaleX(Math.max(MIN_SCALE, 1 - Math.abs(position)));
            page.setScaleY(Math.max(MIN_SCALE, 1 - Math.abs(position)));
            page.setAlpha(Math.max(MIN_ALPHA, 1 - Math.abs(position)));
        }
        else {
            page.setAlpha(0f);
        }
    }

    private void validarUltimo(int position) {
        if (position == 3) {
            binding.btnAyudaSalir.setVisibility(View.GONE);
            binding.btnAyudaSiguiente.setVisibility(View.GONE);
            binding.btnAyudaListo.setVisibility(View.VISIBLE);
        } else {
            binding.btnAyudaSalir.setVisibility(View.VISIBLE);
            binding.btnAyudaSiguiente.setVisibility(View.VISIBLE);
            binding.btnAyudaListo.setVisibility(View.GONE);
        }
    }

    private void listenerDots(int position) {
        binding.ivAyuda1.setImageDrawable(ContextCompat.getDrawable(binding.ivAyuda1.getContext(), R.drawable.ic_dot_ayuda_disable));
        binding.ivAyuda2.setImageDrawable(ContextCompat.getDrawable(binding.ivAyuda2.getContext(), R.drawable.ic_dot_ayuda_disable));
        binding.ivAyuda3.setImageDrawable(ContextCompat.getDrawable(binding.ivAyuda3.getContext(), R.drawable.ic_dot_ayuda_disable));
        binding.ivAyuda4.setImageDrawable(ContextCompat.getDrawable(binding.ivAyuda4.getContext(), R.drawable.ic_dot_ayuda_disable));

        switch (position) {
            case 0:
                binding.ivAyuda1.setImageDrawable(ContextCompat.getDrawable(binding.ivAyuda1.getContext(), R.drawable.ic_dot_ayuda_enable));
                break;
            case 1:
                binding.ivAyuda2.setImageDrawable(ContextCompat.getDrawable(binding.ivAyuda2.getContext(), R.drawable.ic_dot_ayuda_enable));
                break;
            case 2:
                binding.ivAyuda3.setImageDrawable(ContextCompat.getDrawable(binding.ivAyuda3.getContext(), R.drawable.ic_dot_ayuda_enable));
                break;
            case 3:
                binding.ivAyuda4.setImageDrawable(ContextCompat.getDrawable(binding.ivAyuda4.getContext(), R.drawable.ic_dot_ayuda_enable));
                break;
        }
    }
}
