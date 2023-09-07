package com.example.absensi.view.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absensi.R;
import com.example.absensi.adapter.AAbsen;
import com.example.absensi.api.ApiServer;
import com.example.absensi.database.Iabsen;
import com.example.absensi.model.MAbsen;
import com.example.absensi.view.absen.AbsenActivity;
import com.example.absensi.view.main.MainActivity;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvNotFound;

private Toolbar kembali;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.rvHistory);
        tvNotFound = findViewById(R.id.tvNotFound);

        // Inisialisasi Retrofit dan memanggil API
        Retrofit retrofit = ApiServer.getClient();
        Iabsen iabsen = retrofit.create(Iabsen.class);

        Call<List<MAbsen>> call = iabsen.getAbsen();

        call.enqueue(new Callback<List<MAbsen>>() {
            @Override
            public void onResponse(Call<List<MAbsen>> call, Response<List<MAbsen>> response) {
                if (response.isSuccessful()) {
                    List<MAbsen> absenList = response.body();
                    if (absenList != null && !absenList.isEmpty()) {
                        // Tampilkan RecyclerView jika ada data absen
                        recyclerView.setVisibility(View.VISIBLE);
                        tvNotFound.setVisibility(View.GONE);

                        // Buat adapter RecyclerView dan set ke RecyclerView
                        AAbsen adapter = new AAbsen(absenList);
                        recyclerView.setAdapter(adapter);

                        // Set layout manager (LinearLayoutManager)
                        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                    } else {
                        // Tampilkan pesan jika tidak ada data absen
                        recyclerView.setVisibility(View.GONE);
                        tvNotFound.setVisibility(View.VISIBLE);
                    }
                } else {
                    // Handle ketika respon tidak berhasil
                    Toast.makeText(HistoryActivity.this, "Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MAbsen>> call, Throwable t) {
                String errorMessage = "Gagal Mengambil Data: " + t.getMessage();
                Toast.makeText(HistoryActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                Log.e("HistoryActivity", errorMessage);
            }
        });

kembali =findViewById(R.id.kembali);
        kembali.setOnClickListener(view -> {
            Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
