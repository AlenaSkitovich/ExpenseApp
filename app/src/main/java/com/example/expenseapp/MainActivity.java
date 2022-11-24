package com.example.expenseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainActivity.this);
        if (sharedPreferences.getAll().get("login") == null) {
            Intent intent = new Intent(MainActivity.this, ChooseActivity.class);
            startActivity(intent);
        }

        imageView = findViewById(R.id.image);
        textView = findViewById(R.id.name);

        textView.setText(sharedPreferences.getAll().get("name").toString());
        downloadBytes(sharedPreferences.getAll().get("url").toString(), imageView);

    }
    public void downloadBytes(String url, ImageView imageView){
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        StorageReference storageReference1 = storageReference.child(url);

        final long MAXBYTES = 10240*10240;
        storageReference1.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
