package com.example.absensi.view.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.absensi.R;
import com.example.absensi.view.history.HistoryActivity;
import com.example.absensi.view.main.MainActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView usernameText;
    private TextView informasiProfileText;
    private TextView ubahText;
    private TextView identitasPenggunaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inisialisasi komponen tampilan
        usernameText = findViewById(R.id.username_text);
        informasiProfileText = findViewById(R.id.informasi_profile_text);
        ubahText = findViewById(R.id.ubah_text);
        identitasPenggunaText = findViewById(R.id.identitas_pengguna_text);


        usernameText.setText("Nama Pengguna Anda");

        informasiProfileText.setText("Informasi Profile");


        ubahText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tambahkan aksi saat tombol Ubah ditekan
            }
        });

        // Mengatur teks data identitas pengguna
        identitasPenggunaText.setText("Data Identitas Pengguna Anda");

        // Mengatur Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Mengatur judul Toolbar
        getSupportActionBar().setTitle("Informasi Akun");

        // Mengatur tombol kembali pada Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
