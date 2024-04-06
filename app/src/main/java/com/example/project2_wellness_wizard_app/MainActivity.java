package com.example.project2_wellness_wizard_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private UserInfoRepository repository;

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

        repository = UserInfoRepository.getRepository(getApplication());

       //Intent intent = LoginActivity.loginIntentFactory((getApplicationContext()));
       //startActivity(intent);

        binding.accountSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                Intent intent = AccountActivity.accountIntentFactory((getApplicationContext()));
                startActivity(intent);
            }
        });

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.addVitMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddVitMedActivity.AddVitMedActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.viewVitMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ViewVitMedActivity.ViewVitMedActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.getWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WorkoutActivity.WorkoutActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.trackWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WaterActivity.WaterActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.trackFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FoodActivity.FoodActivityIntentFactory((getApplicationContext()));
                startActivity(intent);
            }
        });

        binding.trackWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WeightActivity.WeightActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent MainActivityIntentFactory(Context context){
        return new Intent(context, MainActivity.class);
    }

}