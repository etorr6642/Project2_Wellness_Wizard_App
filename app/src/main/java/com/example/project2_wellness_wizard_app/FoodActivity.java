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
import com.example.project2_wellness_wizard_app.databinding.ActivityFoodBinding;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    private ActivityFoodBinding binding;
    private UserInfoRepository repository;
    public static final String TAG = "WELLNESS_WIZARD";
    String mFood;
    int mCalories;
    private final LocalDate date = LocalDate.now();
    private final LocalTime time = LocalTime.now();
    private int loggedInUserId =-1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserInfoRepository.getRepository(getApplication()); //gives access to our bd

        SharedPreferences sharedPreferences =getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key),-1);

        binding.foodDisplayTextView.setMovementMethod(new ScrollingMovementMethod()); //added to scroll in water display
        updateFoodDisplay();

        binding.addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfoFromDisplay();
                if(checkFood()&checkCalories()){
                    insertFoodRecord();
                    updateFoodDisplay();
                }else{
                    getInfoFromDisplay();
                }

            }
        });


        binding.foodBackButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
               startActivity(intent);
           }
       });
    }

    private void updateFoodDisplay(){
        ArrayList<String> allFoodLogs = repository.getAllFoodLogs(loggedInUserId);
        ArrayList<Integer> allCalorieLogs = repository.getAllCalorieLogs(loggedInUserId);

        if(allFoodLogs.isEmpty() && allCalorieLogs.isEmpty()){
            binding.foodDisplayTextView.setText(R.string.no_food_to_show_strack_tracking);
        }

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < allFoodLogs.size(); i++){
            String food = allFoodLogs.get(i);
            int calories = allCalorieLogs.get(i);

            sb.append("Food Intake: "). append(food).append("\nCalories: ").append(calories).append("\nDate: ").append(date).append("\nTime: ").append(time).append("\n=-=-=-=-=-=-=-=-=-=-=-\n");
        }

        binding.foodDisplayTextView.setText(sb.toString());

    }

    private void insertFoodRecord(){
        UserInfo info = UserInfo.Food(mFood, mCalories, loggedInUserId);
        repository.insertUserInfo(info);
        toastMaker("Food added");
        updateFoodDisplay();
    }

    private void getInfoFromDisplay(){
        mFood = binding.foodInputEditText.getText().toString();

        try{
            mCalories = Integer.parseInt(binding.calorieInputEditText.getText().toString());
        }catch(NumberFormatException e)
        {
            Log.d(TAG, "Error reading value from Calories EditText.");
        }

    }

    private boolean checkFood(){
        String checkTime = binding.foodInputEditText.getText().toString();

        if(checkTime.isEmpty()){
            toastMaker("Enter data for Food");
            return false;
        }
        return true;
    }

    private boolean checkCalories(){
        String checkName = binding.calorieInputEditText.getText().toString();

        if(checkName.isEmpty()){
            toastMaker("Enter data for Calories");
            return false;
        }
        return true;
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    public static Intent FoodActivityIntentFactory(Context context){
        return new Intent(context, FoodActivity.class);
    }
}