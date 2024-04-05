package com.example.project2_wellness_wizard_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.project2_wellness_wizard_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public static final String TAG = "WELLNESS_WIZARD";

    String mFood = "";
    int mCalories = 0;
    String vitMeds = "";
    String mTimeOfDay = "";
    double mWater = 0.0;
    double mWeight = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}