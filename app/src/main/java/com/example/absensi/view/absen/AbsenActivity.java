package com.example.absensi.view.absen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.absensi.api.ApiServer;
import com.example.absensi.database.Iabsen;
import com.example.absensi.model.MAbsen;
import com.example.absensi.model.MAbsenku;
import com.example.absensi.session.SessionManager;
import com.example.absensi.view.history.HistoryActivity;
import com.example.absensi.view.main.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AbsenActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 123;
    private SessionManager sessionManager;
    private Iabsen iabsen;

    private FusedLocationProviderClient locationProviderClient;
    private double latitude = 0.0;
    private double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        super.onCreate(savedInstanceState);
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Arahkan kamera ke QR Code");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
            integrator.initiateScan();
        }
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
                String idLokasi = result.getContents();
                sendAbsenDataToServer(idLokasi);
            }
        }
    }

    private void sendAbsenDataToServer(String idLokasi) {
        int jenisAbsen = getIntent().getIntExtra("jenis_absen", 1);
        Retrofit retrofit = ApiServer.getClient();
        iabsen = retrofit.create(Iabsen.class);
        sessionManager = new SessionManager(this);
        MAbsenku absenData = new MAbsenku();
        MAbsen abs = new MAbsen();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        location.getAccuracy();
                        sendAbsenToServer(idLokasi, jenisAbsen);
                    } else {
                        Toast.makeText(getApplicationContext(), "Lokasi tidak aktif!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AbsenActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    private void sendAbsenToServer(String idLokasi, int jenisAbsen) {
        // Pastikan Anda sudah memiliki lokasi yang valid di sini
        if (latitude != 0.0 && longitude != 0.0) {
            Gson gson = new Gson();
            JsonObject absenJson = new JsonObject();
            absenJson.addProperty("id_user", sessionManager.ambilSesIdu());
            if (jenisAbsen == 1) {
                absenJson.addProperty("id_jenis_absen", 1);
            } else {
                absenJson.addProperty("id_jenis_absen", 2);
            }
            absenJson.addProperty("id_lokasi", idLokasi);
            absenJson.addProperty("latitude", latitude);
            absenJson.addProperty("longitude", longitude);
            String json = gson.toJson(absenJson);
            Log.d("JSON", "msg " + json);

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);
MAbsenku mAbsenku=new MAbsenku();
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
                        Log.e("ABSEN ACTIVITY", "Gagal absen " + response.message()+ response.body());
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(AbsenActivity.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            Toast.makeText(AbsenActivity.this, "Tidak dapat mendapatkan lokasi", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AbsenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Terima Kasih..!");
                builder.setCancelable(true);
                builder.setPositiveButton("Sama-sama", (dialog, which) -> {
                    IntentIntegrator integrator = new IntentIntegrator(this);
                    integrator.setPrompt("Arahkan kamera ke QR Code");
                    integrator.initiateScan();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Anda harus mengizinkan lokasi");
                builder.setCancelable(true);
                builder.setPositiveButton("Faham", (dialog, which) -> {
                    Intent intent = new Intent(AbsenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
    }
}
