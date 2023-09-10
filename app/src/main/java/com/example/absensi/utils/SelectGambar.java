package com.example.absensi.utils;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SelectGambar {
    private Activity activity;
    private String currentPhotoPath;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    public SelectGambar(Activity activity) {
        this.activity = activity;
    }

    // Fungsi untuk mengambil gambar dari kamera
    public void ambilGambarDariKamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity, "com.example.absensi.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    // Fungsi untuk membuat file gambar
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // Fungsi untuk mengambil gambar dari galeri
    public void ambilGambarDariGaleri() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
    }

    // Fungsi untuk menangani hasil pemilihan gambar
    public Uri handleGambarResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Gambar berhasil diambil dari kamera
            return Uri.parse(currentPhotoPath);
        } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            // Gambar berhasil dipilih dari galeri
            return data.getData();
        } else {
            Toast.makeText(activity, "Tidak ada gambar yang dipilih", Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}

