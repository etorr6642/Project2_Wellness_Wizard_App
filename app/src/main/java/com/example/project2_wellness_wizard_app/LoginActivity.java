package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;


import androidx.appcompat.app.AppCompatActivity;


import com.example.project2_wellness_wizard_app.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }
}