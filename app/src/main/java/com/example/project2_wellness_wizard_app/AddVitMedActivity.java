package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2_wellness_wizard_app.databinding.ActivityAddVitMedBinding;
import com.example.project2_wellness_wizard_app.databinding.ActivityMainBinding;

public class AddVitMedActivity extends AppCompatActivity {

    private ActivityAddVitMedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVitMedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addVitMedBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    public static Intent AddVitMedActivityIntentFactory(Context context){
        return new Intent(context, AddVitMedActivity.class);
    }
}