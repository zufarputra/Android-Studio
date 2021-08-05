package com.komputerkit.intentactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Penjualan extends AppCompatActivity {

    TextView tvPenjualan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan);

        load();
    }

    public void load(){
        tvPenjualan = findViewById(R.id.tvPenjualan);
    }

    public void ambilData(){
        String ambil = getIntent().getStringExtra("pen");
        tvPenjualan.setText(ambil);
    }
}