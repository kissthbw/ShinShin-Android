package com.supermarket.shingshing.recuperar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityEnviadoBinding;

public class EnviadoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEnviadoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_enviado);
        binding.btnEnviadoVale.setOnClickListener(v -> finish());
    }
}
