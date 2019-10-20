package com.supermarket.shingshing.terminos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityTerminosBinding;

public class TerminosActivity extends AppCompatActivity {
    private ActivityTerminosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terminos);
    }
}
