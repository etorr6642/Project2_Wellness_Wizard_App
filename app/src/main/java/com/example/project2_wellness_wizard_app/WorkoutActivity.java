package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.databinding.ActivityWorkoutBinding;

public class WorkoutActivity extends AppCompatActivity {

    private ActivityWorkoutBinding binding;
    private UserInfoRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserInfoRepository.getRepository(getApplication());

        binding.generateWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayWorkout();
            }
        });

        binding.workoutBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void displayWorkout(){
        String workout = repository.getRandomWorkout();

        StringBuilder sb = new StringBuilder();
        sb.append(workout).append("\n");

        binding.workoutDisplayTextView.setText(sb.toString());
    }

    public static Intent WorkoutActivityIntentFactory(Context context){
        return new Intent(context, WorkoutActivity.class);
    }
}