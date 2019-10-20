package com.supermarket.shingshing.main.menu.opciones.tour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityAyudaTourBinding;

public class AyudaTourActivity extends AppCompatActivity {
    private final static float MIN_SCALE = 0.65f;
    private final static float MIN_ALPHA = 0.3f;
    private ActivityAyudaTourBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ayuda_tour);

        iniciarVistas();
    }

    private void iniciarVistas() {
        binding.btnAyudaTourSalir.setOnClickListener(v -> finish());
        binding.btnAyudaTourSiguiente.setOnClickListener(v -> siguiente());
        binding.btnAyudaTourVale.setOnClickListener(v -> finish());

        binding.vpAyudaTourPager.setAdapter(new AyudaTourAdapter(this));
        binding.vpAyudaTourPager.setPageTransformer(this::zoomTransformacion);
        binding.vpAyudaTourPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                validarUltimo(position);
                listenerDots(position);
            }
        });
    }

    private void siguiente() {
        binding.vpAyudaTourPager.setCurrentItem(getItem());
    }

    private int getItem() {
        return binding.vpAyudaTourPager.getCurrentItem() + 1;
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
            binding.btnAyudaTourSalir.setVisibility(View.GONE);
            binding.btnAyudaTourSiguiente.setVisibility(View.GONE);
            binding.btnAyudaTourVale.setVisibility(View.VISIBLE);
        } else {
            binding.btnAyudaTourSalir.setVisibility(View.VISIBLE);
            binding.btnAyudaTourSiguiente.setVisibility(View.VISIBLE);
            binding.btnAyudaTourVale.setVisibility(View.GONE);
        }
    }

    private void listenerDots(int position) {
        binding.ivAyudaTour1.setImageDrawable(ContextCompat.getDrawable(binding.ivAyudaTour1.getContext(), R.drawable.ic_dot_tour_disable));
        binding.ivAyudaTour2.setImageDrawable(ContextCompat.getDrawable(binding.ivAyudaTour2.getContext(), R.drawable.ic_dot_tour_disable));
        binding.ivAyudaTour3.setImageDrawable(ContextCompat.getDrawable(binding.ivAyudaTour3.getContext(), R.drawable.ic_dot_tour_disable));
        binding.ivAyudaTour4.setImageDrawable(ContextCompat.getDrawable(binding.ivAyudaTour4.getContext(), R.drawable.ic_dot_tour_disable));

        switch (position) {
            case 0:
                binding.ivAyudaTour1.setImageDrawable(ContextCompat.getDrawable(binding.ivAyudaTour1.getContext(), R.drawable.ic_dot_tour_enable));
                break;
            case 1:
                binding.ivAyudaTour2.setImageDrawable(ContextCompat.getDrawable(binding.ivAyudaTour2.getContext(), R.drawable.ic_dot_tour_enable));
                break;
            case 2:
                binding.ivAyudaTour3.setImageDrawable(ContextCompat.getDrawable(binding.ivAyudaTour3.getContext(), R.drawable.ic_dot_tour_enable));
                break;
            case 3:
                binding.ivAyudaTour4.setImageDrawable(ContextCompat.getDrawable(binding.ivAyudaTour4.getContext(), R.drawable.ic_dot_tour_enable));
                break;
        }
    }
}
