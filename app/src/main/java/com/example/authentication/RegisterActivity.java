package com.example.authentication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText etUsername;
    @BindView(R.id.password)
    EditText etPassword;
    @BindView(R.id.registerButton)
    Button registerButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

       registerUser();
    }

    private boolean isStringEmpty(String inputString) {
        if (inputString.isEmpty() || inputString == null) {
            return true;
        } else {
            return false;
        }
    }


    private void registerUser(){

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if(!isStringEmpty(username) && !isStringEmpty(password)){

                    SharedPreferences sharedPrefs = getSharedPreferences("userData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPrefs.edit();

                    editor.putString( "username", username);
                    editor.putString( "password", password);

                    editor.apply();

                    Toast.makeText(RegisterActivity.this, "User has been successfully registered!", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(RegisterActivity.this, "You can´t leave any blank spaces", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
