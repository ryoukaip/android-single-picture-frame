package com.ryoukaip.pictureframe;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;

import android.media.ExifInterface;

public class MainActivity extends AppCompatActivity {

    Button buttonSelectPicture, buttonChangeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        setListeners();
    }
    private void init(){
        buttonSelectPicture = findViewById(R.id.buttonSelectPicture);
        buttonChangeView = findViewById(R.id.buttonChangeView);
    }
    private void setListeners(){
        buttonChangeView.setOnClickListener(v -> {
            // intent activity_picture_frame
            Intent intent = new Intent(this, PictureFrameActivity.class);
            startActivity(intent);
        });
        buttonSelectPicture.setOnClickListener(v -> {
            // intent activity_select_picture
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            //buttonChangeView.callOnClick();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "Please wait...", Toast.LENGTH_LONG).show();
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                saveImageToInternalStorage(bitmap);
            }
            catch (Exception e) {
                Log.e(TAG, "Error loading image. onActivityResult: ", e);
                e.printStackTrace();
            }
        }
    }
    private void saveImageToInternalStorage(Bitmap bitmap) {
        // TODO: Implement saving image to internal storage
        String fileName = "image.jpg";
        File file = new File(getFilesDir(), fileName);
        try(FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Toast.makeText(this, "Image saved to internal storage", Toast.LENGTH_SHORT).show();
            //buttonChangeView.callOnClick();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
