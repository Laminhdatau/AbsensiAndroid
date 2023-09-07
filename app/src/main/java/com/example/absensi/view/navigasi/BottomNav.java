package com.example.absensi.view.navigasi;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.absensi.R;
import com.example.absensi.fragment.HomeFragment;
import com.example.absensi.fragment.UserFragment;
import com.example.absensi.session.SessionManager;
import com.example.absensi.view.history.HistoryActivity;
import com.example.absensi.view.login.LoginActivity;
import com.example.absensi.view.main.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNav extends AppCompatActivity {
    private SessionManager sessionManager;

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BottomNav.this);
                    builder.setMessage("Yakin Anda ingin Logout?");
                    builder.setCancelable(true);
                    builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());
                    builder.setPositiveButton("Ya", (dialog, which) -> {
                        finishAffinity();
                        Intent intent = new Intent(BottomNav.this, LoginActivity.class);
                        startActivity(intent);
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    return true;
                } else if (itemId == R.id.home) {
                    Intent intent = new Intent(BottomNav.this, MainActivity.class);
                    startActivity(intent);// Gantilah dengan fragment yang sesuai
                    return true;
                } else if (itemId == R.id.profile) {
                    Intent intent = new Intent(BottomNav.this, HistoryActivity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }
        });


    }


}
