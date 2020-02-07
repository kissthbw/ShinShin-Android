package com.supermarket.shingshing.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityIntroBinding;
import com.supermarket.shingshing.main.MainActivity;

public class IntroActivity extends AppCompatActivity {
    private final static float MIN_SCALE = 0.65f;
    private final static float MIN_ALPHA = 0.3f;
    private ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro);

        iniciarVistas();
    }

    private void iniciarVistas() {
        binding.btnIntroOmitir.setOnClickListener(v -> terminar());
        binding.btnIntroSiguiente.setOnClickListener(v -> siguiente());
        binding.btnIntroListo.setOnClickListener(v -> terminar());

        binding.vpIntroPager.setAdapter(new IntroAdapter(this));
        binding.vpIntroPager.setPageTransformer(this::zoomTransformacion);
        binding.vpIntroPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                validarUltimo(position);
                listenerDots(position);
            }
        });
    }

    private void terminar() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void siguiente() {
        binding.vpIntroPager.setCurrentItem(getItem());
    }

    private int getItem() {
        return binding.vpIntroPager.getCurrentItem() + 1;
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
            binding.btnIntroOmitir.setVisibility(View.GONE);
            binding.btnIntroSiguiente.setVisibility(View.GONE);
            binding.btnIntroListo.setVisibility(View.VISIBLE);
        } else {
            binding.btnIntroOmitir.setVisibility(View.VISIBLE);
            binding.btnIntroSiguiente.setVisibility(View.VISIBLE);
            binding.btnIntroListo.setVisibility(View.GONE);
        }
    }

    private void listenerDots(int position) {
        binding.ivIntro1.setImageDrawable(ContextCompat.getDrawable(binding.ivIntro1.getContext(), R.drawable.ic_dot_disable));
        binding.ivIntro2.setImageDrawable(ContextCompat.getDrawable(binding.ivIntro2.getContext(), R.drawable.ic_dot_disable));
        binding.ivIntro3.setImageDrawable(ContextCompat.getDrawable(binding.ivIntro3.getContext(), R.drawable.ic_dot_disable));
        binding.ivIntro4.setImageDrawable(ContextCompat.getDrawable(binding.ivIntro4.getContext(), R.drawable.ic_dot_disable));

        switch (position) {
            case 0:
                binding.ivIntro1.setImageDrawable(ContextCompat.getDrawable(binding.ivIntro1.getContext(), R.drawable.ic_dot_enable));
                break;
            case 1:
                binding.ivIntro2.setImageDrawable(ContextCompat.getDrawable(binding.ivIntro2.getContext(), R.drawable.ic_dot_enable));
                break;
            case 2:
                binding.ivIntro3.setImageDrawable(ContextCompat.getDrawable(binding.ivIntro3.getContext(), R.drawable.ic_dot_enable));
                break;
            case 3:
                binding.ivIntro4.setImageDrawable(ContextCompat.getDrawable(binding.ivIntro4.getContext(), R.drawable.ic_dot_enable));
                break;
        }
    }
}
