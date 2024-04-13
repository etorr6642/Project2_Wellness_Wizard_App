package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.database.entities.Workout;
import com.example.project2_wellness_wizard_app.databinding.ActivityAddWorkoutBinding;

public class AddWorkoutActivity extends AppCompatActivity {
    private ActivityAddWorkoutBinding binding;
    private UserInfoRepository repository;
    private String mWorkout;
    public static final String TAG = "WELLNESS_WIZARD";
    private int loggedInUserId =-1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddWorkoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserInfoRepository.getRepository(getApplication()); //gives access to our bd
        SharedPreferences sharedPreferences =getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key),-1);

        binding.addWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWorkoutFromDisplay();
                addWorkout();
            }
        });

        binding.addWorkoutBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminActivity.AdminActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void addWorkout(){
        Workout workout = new Workout(mWorkout);
        repository.addWorkout(workout);
        toastMaker("Workout was successfully Added!");
    }
    private void getWorkoutFromDisplay(){
        mWorkout = binding.addWorkoutInputEditText.getText().toString();
    }
    private void toastMaker(String message) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    public static Intent AddWorkoutActivityIntentFactory(Context context){
        return new Intent(context, AddWorkoutActivity.class);
    }
}