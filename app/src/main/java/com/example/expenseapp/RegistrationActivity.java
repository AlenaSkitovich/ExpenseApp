package com.example.expenseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.expenseapp.helpers.LoginThread;
import com.example.expenseapp.helpers.RegisterThread;
import com.example.expenseapp.helpers.RegistrationBody;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name, lastName, login, password, passwordAgain;
    private Button register;
    private TextView notCorrect, loginExists;
    private ImageView imageView;
    private static final int PICK_IMAGE = 100;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        imageView = findViewById(R.id.image);
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastname);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        passwordAgain = findViewById(R.id.password_again);

        register = findViewById(R.id.register);
        notCorrect = findViewById(R.id.not_correct);
        loginExists = findViewById(R.id.login_exists);

        notCorrect.setVisibility(View.INVISIBLE);
        loginExists.setVisibility(View.INVISIBLE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password.getText().toString().
                        equals(passwordAgain.getText().toString())) {
                    notCorrect.setVisibility(View.INVISIBLE);
                    RegistrationBody body = new RegistrationBody(login.getText().toString(),
                            password.getText().toString(),
                            name.getText().toString(),
                            lastName.getText().toString(), url);

                    Log.e("body", body.toString());
                    RegisterThread registerThread = new RegisterThread(body);
                    registerThread.start();
                    while (registerThread.isAlive()) ;
                    String str = registerThread.getAuth();

                    SharedPreferences sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("login", body.getLogin());
                    editor.putString("name", body.getName() + " " + body.getLastName());
                    editor.putString("url", body.getUrl());
                    editor.putString("id", registerThread.getAuth());
                    editor.apply();
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    notCorrect.setVisibility(View.VISIBLE);
                }
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
}
