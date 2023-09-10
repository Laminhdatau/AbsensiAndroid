package com.example.absensi.view.main;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.absensi.R;
import com.example.absensi.session.SessionManager;
import com.example.absensi.view.absen.AbsenActivity;
import com.example.absensi.view.history.HistoryActivity;
import com.example.absensi.view.login.LoginActivity;
import com.example.absensi.view.navigasi.BottomNav;
import com.example.absensi.view.profile.ProfileActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private SessionManager sessionManager;
    private String strTitle;

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        if(!sessionManager.isLoggedIn()) {
            Toast.makeText(this, "Anda Tidak Punya Session", Toast.LENGTH_SHORT).show();
        }else{
            setInitLayout();


        }

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

    }



    private void setInitLayout() {

        View cvAbsenMasuk = findViewById(R.id.cvAbsenMasuk);
        View cvAbsenKeluar = findViewById(R.id.cvAbsenKeluar);
        View cvHistory = findViewById(R.id.cvHistory);

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
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Yakin Anda ingin Logout?");
            builder.setCancelable(true);
            builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());
            builder.setPositiveButton("Ya", (dialog, which) -> {
                SessionManager sessionManager = new SessionManager(MainActivity.this);

                sessionManager.logout();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        } else if (itemId == R.id.home) {

            return true;
        } else if (itemId == R.id.profile) {
            finishAffinity();
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yakin Anda ingin Logout?");
        builder.setCancelable(true);
        builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());
        builder.setPositiveButton("Ya", (dialog, which) -> {
            // Inisialisasi SessionManager
            SessionManager sessionManager = new SessionManager(MainActivity.this);

            // Logout pengguna
            sessionManager.logout();

            // Setelah logout, arahkan kembali ke halaman login
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Tutup aktivitas history agar tidak bisa kembali ke sana
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
