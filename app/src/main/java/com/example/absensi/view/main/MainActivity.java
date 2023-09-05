package com.example.absensi.view.main;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.absensi.R;
import com.example.absensi.view.absen.AbsenActivity;
import com.example.absensi.view.history.HistoryActivity;

public class MainActivity extends AppCompatActivity {

    private String strTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInitLayout();
    }


    private void setInitLayout() {

        View cvAbsenMasuk = findViewById(R.id.cvAbsenMasuk);
        View cvAbsenKeluar = findViewById(R.id.cvAbsenKeluar);
        View cvHistory = findViewById(R.id.cvHistory);
        ImageView imageLogout = findViewById(R.id.imageLogout);

        cvAbsenMasuk.setOnClickListener(view -> {
            strTitle = "Absen Masuk";
            Intent intent = new Intent(MainActivity.this, AbsenActivity.class);
            intent.putExtra("jenis_absen", 1);
            startActivity(intent);
        });

        cvAbsenKeluar.setOnClickListener(view -> {
            strTitle = "Absen Keluar";
            Intent intent = new Intent(MainActivity.this, AbsenActivity.class);
            intent.putExtra("jenis_absen", 2);
            startActivity(intent);
        });



        cvHistory.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        imageLogout.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Yakin Anda ingin Logout?");
            builder.setCancelable(true);
            builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());
            builder.setPositiveButton("Ya", (dialog, which) -> {
              //  session.logoutUser();
                finishAffinity();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }
}
