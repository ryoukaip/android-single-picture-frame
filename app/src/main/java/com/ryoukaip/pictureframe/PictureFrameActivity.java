package com.ryoukaip.pictureframe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;

public class PictureFrameActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_frame);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        EdgeToEdge.enable(this);
        init();
        displaySavedImage();
    }
    private void init(){
        imageView = findViewById(R.id.imageView);
    }
    private Bitmap loadImageFromInternalStorage() {
        File file = new File(getFilesDir(), "image.jpg");
        Bitmap bitmap = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (Exception e) {
            Toast.makeText(this, "Error when load image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return bitmap;
    }
    private void displaySavedImage() {
        Bitmap bitmap = loadImageFromInternalStorage();
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
        }
    }
}
