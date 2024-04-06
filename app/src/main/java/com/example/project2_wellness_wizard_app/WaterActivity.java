package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_wellness_wizard_app.databinding.ActivityWaterBinding;

public class WaterActivity extends AppCompatActivity {

    private ActivityWaterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWaterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.waterDisplayTextView.setMovementMethod(new ScrollingMovementMethod()); //added to scroll in water display

        binding.waterBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent WaterActivityIntentFactory(Context context){
        return new Intent(context, WaterActivity.class);
    }
}