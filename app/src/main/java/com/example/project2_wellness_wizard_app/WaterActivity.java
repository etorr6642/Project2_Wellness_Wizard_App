package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.database.entities.User;
import com.example.project2_wellness_wizard_app.database.entities.UserInfo;
import com.example.project2_wellness_wizard_app.databinding.ActivityWaterBinding;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class WaterActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = ".com.example.project2_wellness_wizard_app.MAIN_ACTIVITY_USER_ID";
    private ActivityWaterBinding binding;
    private UserInfoRepository repository;
    public static final String TAG = "WELLNESS_WIZARD";
    int mWater = 0;
    private final LocalDate date = LocalDate.now();
    private final LocalTime time = LocalTime.now();
    private int loggedInUserId=-1;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWaterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserInfoRepository.getRepository(getApplication()); //gives access to our bd

        binding.waterDisplayTextView.setMovementMethod(new ScrollingMovementMethod()); //added to scroll in water display

        binding.addWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertWaterRecord();
                updateDisplay();
            }
        });

        binding.waterBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }


    private void updateDisplay() {
        ArrayList<Integer> allLogs = repository.getAllWaterLogs();

        if(allLogs.isEmpty()){
            binding.waterDisplayTextView.setText(R.string.nothing_to_show_start_tracking_water);
        }

        StringBuilder sb = new StringBuilder();
        for(Integer water: allLogs){
            sb.append("Water Intake: ").append(water).append("     Date: ").append(date).append("      Time: ").append(time).append("\n");
        }

        binding.waterDisplayTextView.setText(sb.toString());
    }

    //TODO: FIX ERROR, grab userId from current user
    private void insertWaterRecord() {
        UserInfo info = new UserInfo(mWater, loggedInUserId);
        repository.insertUserInfo(info);
    }

    private void getInformationFromDisplay() {
        try{
            mWater = Integer.parseInt(binding.waterInputEditText.getText().toString());
        }catch(NumberFormatException e)
        {
            Log.d(TAG, "Error reading value from Water edit text.");
        }
    }

    public static Intent WaterActivityIntentFactory(Context context){
        return new Intent(context, WaterActivity.class);
    }
}