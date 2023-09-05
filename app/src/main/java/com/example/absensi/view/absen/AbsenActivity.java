package com.example.absensi.view.absen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.absensi.api.ApiServer;
import com.example.absensi.database.Iabsen;
import com.example.absensi.model.MAbsenku;
import com.example.absensi.view.history.HistoryActivity;
import com.example.absensi.view.main.MainActivity;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.example.absensi.R;
import com.google.zxing.qrcode.encoder.QRCode;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AbsenActivity extends AppCompatActivity {

    private Iabsen iabsen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Arahkan kamera ke QR Code");
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Pemindaian dibatalkan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AbsenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                String qrCodeData = result.getContents();
                sendAbsenDataToServer(qrCodeData);
            }
        }
    }

    private void sendAbsenDataToServer(String idUser) {
        int jenisAbsen= getIntent().getIntExtra("jenis_absen", 1);
        Retrofit retrofit = ApiServer.getClient();
        iabsen = retrofit.create(Iabsen.class);

        MAbsenku absenData = new MAbsenku();
        absenData.setId_user(idUser);
        if(jenisAbsen==1) {
            absenData.setId_jenis_absen(1);
        }else{
            absenData.setId_jenis_absen(2);
        }
        absenData.setId_lokasi(1);

        Gson gson = new Gson();
        String json = gson.toJson(absenData);
        // Atur header Content-Type
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        // Buat permintaan HTTP POST
        Call<Void> call = iabsen.createAbsen(requestBody);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AbsenActivity.this, "Absen berhasil", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AbsenActivity.this, HistoryActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AbsenActivity.this, "Gagal absen", Toast.LENGTH_SHORT).show();
                    Log.e("ABSEN ACTIVITY", "Gagal absen " + response);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AbsenActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


}