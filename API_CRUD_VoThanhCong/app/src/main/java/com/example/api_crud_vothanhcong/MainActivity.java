package com.example.api_crud_vothanhcong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private FirebaseAuth firebaseAuth;
    private TextInputLayout edtpassword, edtmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        hideSoftKeyboard();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtmail.getEditText().getText().toString().trim();
                String pw = edtpassword.getEditText().getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    edtmail.setError("Email required !");
                    return;
                }
                if (TextUtils.isEmpty(pw)) {
                    edtpassword.setError("Password required !");
                    return;
                }
                if (pw.length() < 6) {
                    edtpassword.setError("Password must be >= 6 characters !");
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), ListUser.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void init() {
        edtmail = (TextInputLayout) findViewById(R.id.edtmail);
        edtpassword = (TextInputLayout) findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        ///firebase
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}