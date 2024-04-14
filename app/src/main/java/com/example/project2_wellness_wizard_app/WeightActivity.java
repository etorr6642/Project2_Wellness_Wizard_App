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
import com.example.project2_wellness_wizard_app.databinding.ActivityWeightBinding;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class WeightActivity extends AppCompatActivity {

    private ActivityWeightBinding binding;
    private UserInfoRepository repository;
    public static final String TAG = "WELLNESS_WIZARD";
    double mWeight = 0.0;
    private final LocalDate date = LocalDate.now();
    private final LocalTime time = LocalTime.now();


    private int loggedInUserId =-1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserInfoRepository.getRepository(getApplication()); //gives access to our bd

        binding.weightDisplayTextView.setMovementMethod(new ScrollingMovementMethod()); //added to scroll in water display

        SharedPreferences sharedPreferences =getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key),-1);

        updateWeightDisplay();
        binding.addWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfoFromDisplay();
                insertWeightRecord();
                updateWeightDisplay();
            }
        });

        binding.weightBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void updateWeightDisplay(){
        ArrayList<Double> allWeightLogs = repository.getAllWeightLogs(loggedInUserId);

        if(allWeightLogs.isEmpty()){
            binding.weightDisplayTextView.setText(R.string.nothing_to_show_start_tracking_weight);
        }

        StringBuilder sb = new StringBuilder();
        for(Double weight: allWeightLogs){
            sb.append("Weight: ").append(weight).append("lbs.").append("     \nDate: ").append(date).append("      \nTime: ").append(time).append("\n=-=-=-=-=-=-=-=-=-=-=-\n");
        }

        binding.weightDisplayTextView.setText(sb.toString());
    }

    private void insertWeightRecord(){
        UserInfo info = UserInfo.Weight(mWeight, loggedInUserId);
        repository.insertUserInfo(info);
        updateWeightDisplay();
    }

    private void getInfoFromDisplay(){
        if(!checkWeight()){
            toastMaker("Weight should not be blank.");
            return;
        }

        try{
            mWeight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        }catch(NumberFormatException e)
        {
            Log.d(TAG,"Error reading value from Weight EditText.");
        }
    }

    private boolean checkWeight(){
        String weight = binding.weightInputEditText.getText().toString();
        if(!weight.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    private void toastMaker(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public static Intent WeightActivityIntentFactory(Context context){
        return new Intent(context, WeightActivity.class);
    }
}