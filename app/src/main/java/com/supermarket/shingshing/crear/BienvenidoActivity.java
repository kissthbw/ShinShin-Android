package com.supermarket.shingshing.crear;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityBienvenidoBinding;
import com.supermarket.shingshing.login.LoginActivity;

public class BienvenidoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBienvenidoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_bienvenido);

        binding.btnBienvenidoEmpecemos.setOnClickListener(v -> {
            binding.btnBienvenidoEmpecemos.setEnabled(false);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }
}
