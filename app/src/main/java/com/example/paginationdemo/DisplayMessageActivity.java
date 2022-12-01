package com.example.paginationdemo;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DisplayMessageActivity extends AppCompatActivity {
    ImageView imageView;
   View view;

   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();

        ImageView imageView = new ImageView(this);
        String url =  intent.getStringExtra("pox");

       Glide.with(this).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
       imageView.setClickable(true);
      imageView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
              saveReceivedImage(bitmap, "lox");
          }
      });


       setContentView(imageView);

    }

    private void saveReceivedImage(Bitmap image, String imageName){
        try {
            File path = new File(DisplayMessageActivity.this.getFilesDir(), "MyAppName" + File.separator + "Images");
            if(!path.exists()){
                path.mkdirs();
            }
            File outFile = new File(path, imageName + ".jpeg");
            FileOutputStream outputStream = new FileOutputStream(outFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Saving received message failed with", e);
        } catch (IOException e) {
            Log.e(TAG, "Saving received message failed with", e);
        }
    }

}