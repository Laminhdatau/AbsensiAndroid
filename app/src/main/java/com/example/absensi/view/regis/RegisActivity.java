package com.example.absensi.view.regis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.absensi.R;
import com.example.absensi.utils.SelectGambar;
import com.example.absensi.view.login.LoginActivity;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.example.absensi.api.ApiServer;
import com.example.absensi.database.Iauth;
import com.example.absensi.model.auth.MRegister;
import java.io.File;
import java.io.IOException;

public class RegisActivity extends AppCompatActivity {

 
    private EditText etUsename;
    private EditText etNama;
    private EditText etPass;
    private EditText etNik;
    private ImageView ivUserImage;

 //   private static final int PICK_IMAGE_REQUEST = 1;
 private static final int REQUEST_STORAGE_PERMISSION = 1;

    private String imageFileName;

    private Uri selectedImageUri;
    private SelectGambar selectGambar; // Deklarasi objek SelectGambar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        etUsename = findViewById(R.id.etUsername);
        etNama = findViewById(R.id.etNama);
        etPass = findViewById(R.id.etPass);
        etNik = findViewById(R.id.etNik);
        ivUserImage = findViewById(R.id.ivUserImage);

        TextView btnLogin = findViewById(R.id.btLog);

        Button btnUpGambar = findViewById(R.id.btnUpGambar);

        // Inisialisasi objek SelectGambar
        selectGambar = new SelectGambar(this);

        btnUpGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Panggil metode ambilGambarDariKamera atau ambilGambarDariGaleri sesuai kebutuhan
                // untuk mengambil gambar
                selectGambar.ambilGambarDariGaleri(); // atau selectGambar.ambilGambarDariGaleri();

            }
        });

        // Memeriksa dan meminta izin penyimpanan jika belum disetujui
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
        }





        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button btnDaftar = findViewById(R.id.btnDaftar);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mendapatkan nilai dari EditText
                String username = etUsename.getText().toString();
                String namaLengkap = etNama.getText().toString();
                String password = etPass.getText().toString();
                String nikText = etNik.getText().toString();

                // Memeriksa apakah semua input yang diperlukan telah diisi
                if (username.isEmpty() || namaLengkap.isEmpty() || password.isEmpty() || nikText.isEmpty() || selectGambar == null) {
                    // Menampilkan pesan kesalahan jika ada input yang kosong
                    Toast.makeText(RegisActivity.this, "Semua input harus diisi dan gambar harus dipilih", Toast.LENGTH_SHORT).show();
                    return; // Hentikan proses jika ada input yang kosong
                }

                // Parse nik menjadi tipe data long
                long nik = Long.parseLong(nikText);

                // Memeriksa apakah gambar telah dipilih
                if (selectGambar != null) {
                    Log.d("PILIHAN","msg" + selectedImageUri);
                    sendDataToServer(username, namaLengkap, password, nik);
                } else {
                    // Tampilkan pesan jika gambar belum dipilih
                    Toast.makeText(RegisActivity.this, "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan, Anda dapat melanjutkan akses ke penyimpanan lokal
            } else {
                // Izin ditolak, Anda perlu memberi tahu pengguna mengapa izin ini diperlukan
                Toast.makeText(this, "Izin akses penyimpanan ditolak.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Panggil metode handleGambarResult dari objek SelectGambar untuk menangani hasil pemilihan gambar
        selectedImageUri = selectGambar.handleGambarResult(requestCode, resultCode, data);

        if (selectedImageUri != null) {
            ivUserImage.setImageURI(selectedImageUri);
            Log.d("IVGAMBAR","MSG " +selectedImageUri);
        }
    }

    private void sendDataToServer(String username, String namaLengkap, String password, long nik) {
        // Inisialisasi Retrofit
        Retrofit retrofit = ApiServer.getClient();
        Iauth iauth = retrofit.create(Iauth.class);

        // Membuat objek RequestBody untuk data String
        RequestBody usernameRequestBody = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody namaLengkapRequestBody = RequestBody.create(MediaType.parse("text/plain"), namaLengkap);
        RequestBody passwordRequestBody = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody nikRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nik));

        File file = new File(getRealPathFromURI(selectedImageUri));
        RequestBody imageRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("user_image", file.getName(), imageRequestBody);

        Log.d("uname","nama" +usernameRequestBody);
        Log.d("name","nama" +namaLengkapRequestBody);
        Log.d("GAMBAR body","nama" +imageRequestBody);
        Log.d("GAMBAR","nama" +imageFileName);
        Log.d("FILE","nama" +file.getName());

        // Mengirim data ke server untuk registrasi
        Call<MRegister> call = iauth.registerUser(usernameRequestBody, namaLengkapRequestBody, passwordRequestBody, nikRequestBody, imagePart);
        call.enqueue(new Callback<MRegister>() {
            @Override
            public void onResponse(Call<MRegister> call, Response<MRegister> response) {
                if (response.isSuccessful()) {
                    // Tanggapan berhasil (status kode 2xx)
                    MRegister mRegister = response.body();
                    Log.d("RESPONSE", "Status Kode: " + response.code()); // Menampilkan kode status HTTP
                    Log.d("RESPONSE", "Message: " + response.message()); // Menampilkan pesan respons HTTP
                    Log.d("RESPONSE", "Message from Server: " + mRegister.getMessage());
                    Log.d("RESPONSE", "Status from Server: " + mRegister.getStatus());
                    Log.d("RESPONSE", "Nama Lengkap: " + mRegister.getNamaLengkap());
                    Log.d("RESPONSE", "URL Gambar: " + mRegister.getUserImage());

                    Toast.makeText(RegisActivity.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();

                    // Pindah ke aktivitas login
                    Intent intent = new Intent(RegisActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Registrasi gagal (status kode selain 2xx)
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("RegisActivity", "Error Body: " + errorBody); // Menampilkan tubuh respons error
                        JSONObject jsonObject = new JSONObject(errorBody);
                        Log.d("JSON", "Error Message: " + jsonObject.getString("error"));

                        Toast.makeText(RegisActivity.this, "Registrasi gagal: " + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(Call<MRegister> call, Throwable t) {
                Toast.makeText(RegisActivity.this, "Gagal melakukan registrasi" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getRealPathFromURI(Uri selectedImageUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null, null);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }
}
