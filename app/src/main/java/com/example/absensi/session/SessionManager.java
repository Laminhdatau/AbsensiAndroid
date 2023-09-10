package com.example.absensi.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "LoginSession";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_ID = "id_user";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String username,String idUser) {
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_USER_ID, idUser);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String ambilSesIdu() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }

    public boolean isLoggedIn() {
        return getUsername() != null;
    }
}
