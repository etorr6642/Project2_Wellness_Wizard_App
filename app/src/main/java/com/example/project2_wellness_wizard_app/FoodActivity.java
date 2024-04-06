package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_wellness_wizard_app.databinding.ActivityFoodBinding;

public class FoodActivity extends AppCompatActivity {

    private ActivityFoodBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       binding.foodBackButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
               startActivity(intent);
           }
       });
    }

    public static Intent FoodActivityIntentFactory(Context context){
        return new Intent(context, FoodActivity.class);
    }
}