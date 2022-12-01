package com.example.expenseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expenseapp.helpers.EditDataThread;
import com.example.expenseapp.helpers.EditPhotoThread;
import com.example.expenseapp.helpers.RegistrationBody;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class EditProfileActivity extends AppCompatActivity {

    private Button change, savePhoto, save;
    private ImageView imageView;
    private TextView textView;
    private EditText name, lastName;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private static final int PICK_IMAGE = 100;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        change = findViewById(R.id.change_photo);
        savePhoto = findViewById(R.id.save_photo);
        save = findViewById(R.id.save);

        imageView = findViewById(R.id.image);
        textView = findViewById(R.id.name);

        name = findViewById(R.id.u_name);
        lastName = findViewById(R.id.u_lastname);



        SharedPreferences sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE);
        textView.setText(sharedPreferences.getAll().get("name").toString());
        downloadBytes(sharedPreferences.getAll().get("url").toString(), imageView);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE);
            }
        });

        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE);
                EditPhotoThread editPhotoThread = new EditPhotoThread(url,
                        sharedPreferences.getAll().get("login").toString());
                editPhotoThread.start();
                while (editPhotoThread.isAlive());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("url", url);
                editor.apply();
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE);
                RegistrationBody body = new RegistrationBody(name.getText().toString(),
                        lastName.getText().toString(),
                        sharedPreferences.getAll().get("login").toString());
                EditDataThread editDataThread = new EditDataThread(body);
                editDataThread.start();
                while (editDataThread.isAlive()) ;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", body.getName() + " " + body.getLastName());
                editor.apply();
                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void downloadBytes(String url, ImageView imageView) {
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        StorageReference storageReference1 = storageReference.child(url);

        final long MAXBYTES = 10240 * 10240;
        storageReference1.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && data != null) {
            Uri selectImageUri = data.getData();
            if (selectImageUri != null) {
                imageView.setImageURI(selectImageUri);
                Bitmap bitmap = ((BitmapDrawable) (imageView.getDrawable())).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                byte[] bytes = baos.toByteArray();
                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                StorageReference storageReference = firebaseStorage.getReference();
                StorageReference storageReference1 = storageReference
                        .child(System.currentTimeMillis() + "");
                UploadTask up = storageReference1.putBytes(bytes);
                Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        return storageReference1.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        url = task.getResult().toString().substring(76, 89);
                    }
                });
            }
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}