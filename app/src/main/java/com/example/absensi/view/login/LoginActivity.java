package com.example.absensi.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.absensi.R;
import com.example.absensi.api.ApiServer;
import com.example.absensi.database.Iauth;
import com.example.absensi.model.auth.MRegister;
import com.example.absensi.session.SessionManager;
import com.example.absensi.view.login.LoginActivity;
import com.example.absensi.view.main.MainActivity;
import com.example.absensi.view.navigasi.BottomNav;
import com.example.absensi.view.regis.RegisActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private EditText etUname;
    private EditText etPass;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUname = findViewById(R.id.etUnamel);
        etPass = findViewById(R.id.etPassl);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView btnReg = findViewById(R.id.btReg);

        sessionManager = new SessionManager(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUname.getText().toString();
                String password = etPass.getText().toString();

                // Pastikan kedua field tidak kosong
                if (!username.isEmpty() && !password.isEmpty()) {
                    loginUser(username, password);
                } else {
                    Toast.makeText(LoginActivity.this, "Isi username dan password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void loginUser(String username, String password) {
        // Inisialisasi Retrofit
        Retrofit retrofit = ApiServer.getClient();
        Iauth iauth = retrofit.create(Iauth.class);
        Call<MRegister> call = iauth.loginUser(username, password);
        call.enqueue(new Callback<MRegister>() {
            @Override
            public void onResponse(Call<MRegister> call, Response<MRegister> response) {
                if (response.isSuccessful()) {
                    sessionManager.createLoginSession(username);
                    Toast.makeText(LoginActivity.this, "Selamat datang, "+ username, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Login gagal
                    Toast.makeText(LoginActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MRegister> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal melakukan login", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
