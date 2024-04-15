package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.database.entities.UserInfo;
import com.example.project2_wellness_wizard_app.databinding.ActivityWaterBinding;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class WaterActivity extends AppCompatActivity {

    private ActivityWaterBinding binding;
    private UserInfoRepository repository;
    public static final String TAG = "WELLNESS_WIZARD";
    int mWater = 0;
    private final LocalDate date = LocalDate.now();
    private final LocalTime time = LocalTime.now();
    private int loggedInUserId =-1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWaterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserInfoRepository.getRepository(getApplication()); //gives access to our bd

        binding.waterDisplayTextView.setMovementMethod(new ScrollingMovementMethod()); //added to scroll in water display

        SharedPreferences sharedPreferences =getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key),-1);

        updateDisplay();
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
        ArrayList<Integer> allLogs = repository.getAllWaterLogs(loggedInUserId);

        if(allLogs.isEmpty()){
            binding.waterDisplayTextView.setText(R.string.nothing_to_show_start_tracking_water);
        }

        StringBuilder sb = new StringBuilder();
        for(Integer water: allLogs){
            sb.append("Water Intake: ").append(water).append("mL").append("     \nDate: ").append(date).append("      \nTime: ").append(time).append("\n=-=-=-=-=-=-=-=-=-=-=-\n");
        }
        binding.waterDisplayTextView.setText(sb.toString());
    }

    private void insertWaterRecord() {
        UserInfo info = UserInfo.Water(mWater,loggedInUserId);
        repository.insertUserInfo(info);
        updateDisplay();
    }

    private void getInformationFromDisplay() {
        if(!checkWater()){
            toastMaker("Water should not be blank.");
            return;
        }
        try{
            mWater = Integer.parseInt(binding.waterInputEditText.getText().toString());
            toastMaker("Water added");
        }catch(NumberFormatException e)
        {
            Log.d(TAG, "Error reading value from Water edit text.");
        }
    }

    private boolean checkWater(){
        String water = binding.waterInputEditText.getText().toString();
        return !water.isEmpty();
    }
    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent WaterActivityIntentFactory(Context context){
        return new Intent(context, WaterActivity.class);
    }
}