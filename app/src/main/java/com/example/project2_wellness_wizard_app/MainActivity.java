package com.example.project2_wellness_wizard_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.project2_wellness_wizard_app.databinding.ActivityLoginBinding;
import com.example.project2_wellness_wizard_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
        startActivity(intent);


    }
}