package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.databinding.ActivityViewVitMedBinding;

import java.util.ArrayList;

public class ViewVitMedActivity extends AppCompatActivity {

    String vitMeds = "";
    String mTimeOfDay = "";

    UserInfoRepository repository;

    private ActivityViewVitMedBinding binding;
    int loggedInUserId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewVitMedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.vitMedDisplayTextView.setMovementMethod(new ScrollingMovementMethod()); //added to scroll in display of view vit/med

        repository =UserInfoRepository.getRepository(getApplication());

        SharedPreferences sharedPreferences =getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key),-1);

        updateDisplay();

        binding.viewVitMedBackButton.setOnClickListener(new View.OnClickListener() { //back button in this activity
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void updateDisplay() {
        ArrayList<String> allVitMedLogs = repository.getAllVitMedLogs(loggedInUserId);
        ArrayList<String> allTimeOfDayLogs = repository.getallTimeOfDayLogs(loggedInUserId);

        if(allVitMedLogs.isEmpty()){
            binding.vitMedDisplayTextView.setText(R.string.nothing_to_show_start_tracking_water);
        }
        if(allTimeOfDayLogs.isEmpty()){
            binding.vitMedDisplayTextView.setText(R.string.nothing_to_show_start_tracking_water);
        }

        StringBuilder sb = new StringBuilder();
        for(int i =0;i<allVitMedLogs.size();i++){
            String vitMed = allVitMedLogs.get(i);
            String timeOfDay = allTimeOfDayLogs.get(i);

            sb.append("Vitamin/Meds: ").append(vitMed).append("\nTime of Day: ").append(timeOfDay).append("\n=-=-=-=-=-=-=-=-=-=-=-\n");
        }

        binding.vitMedDisplayTextView.setText(sb.toString());
    }
    public static Intent ViewVitMedActivityIntentFactory(Context context){
        return new Intent(context, ViewVitMedActivity.class);
    }
}