package com.example.authentication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.email)
    EditText etEmail;

    @BindView(R.id.password)
    EditText etPassword;

    @BindView(R.id.registerButton)
    Button registerButton;

    private FirebaseAuth mAuth;

    private static final String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Hawk.init(this).build();

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        registerUser();
    }

    private boolean isStringEmpty(String inputString) {
        if (inputString.isEmpty() || inputString == null) {
            return true;
        } else {
            return false;
        }
    }


    private void registerUser() {

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!isStringEmpty(email) && !isStringEmpty(password)) {

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){

                                        FirebaseUser user = mAuth.getCurrentUser();
                                        user.sendEmailVerification();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);

                                        Toast.makeText(RegisterActivity.this, "Verify your email to continue", Toast.LENGTH_LONG).show();
                                    }else {

                                        Log.d(TAG, "onComplete: error:" + task.getException().getMessage());
                                        Toast.makeText(RegisterActivity.this, "Couldn´t create an account with these credentials!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                } else {
                    Toast.makeText(RegisterActivity.this, "You can´t leave any blank spaces", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
