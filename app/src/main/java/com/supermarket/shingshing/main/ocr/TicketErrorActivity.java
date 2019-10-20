package com.supermarket.shingshing.main.ocr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.supermarket.shingshing.R;
import com.supermarket.shingshing.databinding.ActivityTicketErrorBinding;

public class TicketErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTicketErrorBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_ticket_error);
        binding.btnTicketErrorVolver.setOnClickListener(v -> finish());
    }
}
