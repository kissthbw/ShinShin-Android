package com.supermarket.shingshing.recuperar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityRecuperarBinding;

public class RecuperarActivity extends AppCompatActivity {
    private ActivityRecuperarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recuperar);

        binding.btnRecuperarEnviar.setOnClickListener(v -> startActivity(new Intent(this, EnviadoActivity.class)));
    }
}
