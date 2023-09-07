package com.example.absensi.view.regis;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.absensi.R;
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

import java.io.IOException;

public class RegisActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private String selectedImageFileName;
    private EditText etUsename;
    private EditText etNama;
    private EditText etPass;
    private EditText etNik;
    private ImageView ivUserImage;

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

        btnUpGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

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
                if (username.isEmpty() || namaLengkap.isEmpty() || password.isEmpty() || nikText.isEmpty() || selectedImageUri == null) {
                    // Menampilkan pesan kesalahan jika ada input yang kosong
                    Toast.makeText(RegisActivity.this, "Semua input harus diisi dan gambar harus dipilih", Toast.LENGTH_SHORT).show();
                    return; // Hentikan proses jika ada input yang kosong
                }

                // Parse nik menjadi tipe data long
                long nik = Long.parseLong(nikText);

                // Memeriksa apakah gambar telah dipilih
                if (selectedImageUri != null) {
                    selectedImageFileName = getFileName(selectedImageUri);
                    Log.d("PILIHAN","msg" + selectedImageFileName);
                    // Lanjutkan dengan pengiriman data ke server
                    sendDataToServer(username, namaLengkap, password, nik,selectedImageFileName);
                } else {
                    // Tampilkan pesan jika gambar belum dipilih
                    Toast.makeText(RegisActivity.this, "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            ivUserImage.setImageURI(selectedImageUri);
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    Log.d("DISPLAY GAMABR", "msg : "+displayNameIndex);
                    if (displayNameIndex != -1) {
                        result = cursor.getString(displayNameIndex);
                        Log.d("DISPLAY RESULT", "msg : "+result);

                    }
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void sendDataToServer(String username, String namaLengkap, String password, long nik, String selectedImageFileName) {
        // Inisialisasi Retrofit
        Retrofit retrofit = ApiServer.getClient();
        Iauth iauth = retrofit.create(Iauth.class);

        // Membuat objek RequestBody untuk data String
        RequestBody usernameRequestBody = RequestBody.create(MediaType.parse("text/plain"), username);
        RequestBody namaLengkapRequestBody = RequestBody.create(MediaType.parse("text/plain"), namaLengkap);
        RequestBody passwordRequestBody = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody nikRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(nik));
        RequestBody imageFileNameRequestBody = RequestBody.create(MediaType.parse("text/plain"), selectedImageFileName);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("user_image", selectedImageFileName,imageFileNameRequestBody);

        Log.d("uname","nama" +usernameRequestBody);
        Log.d("GAMBAR","nama" +selectedImageFileName);

        // Mengirim data ke server untuk registrasi
        Call<MRegister> call = iauth.registerUser(usernameRequestBody, namaLengkapRequestBody, passwordRequestBody, nikRequestBody, imagePart);
        call.enqueue(new Callback<MRegister>() {
            @Override
            public void onResponse(Call<MRegister> call, Response<MRegister> response) {
                if (response.isSuccessful()) {
                   // MRegister mRegister = response.body();
                    String message = "Anda Berhasil Mendaftar";
                    Toast.makeText(RegisActivity.this, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Registrasi gagal
                    try {


                        String errorBody = response.errorBody().string();
                        Log.e("RegisActivity", "Error Body: " + errorBody); // Tambahkan ini untuk melihat error body di logcat
                        JSONObject jsonObject = new JSONObject(errorBody);
                        Log.d("JSON","msg " +jsonObject);
                        String errorMessage = jsonObject.getString("error");
                        Toast.makeText(RegisActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MRegister> call, Throwable t) {
                Toast.makeText(RegisActivity.this, "Gagal melakukan registrasi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
