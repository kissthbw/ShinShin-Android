package com.supermarket.shingshing.main.ocr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityLenteSucioBinding;

public class LenteSucioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLenteSucioBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_lente_sucio);
        binding.btnLenteSucioVolver.setOnClickListener(v -> finish());
    }
}
