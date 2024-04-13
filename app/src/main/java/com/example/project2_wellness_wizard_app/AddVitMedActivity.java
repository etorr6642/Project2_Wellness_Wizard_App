package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.database.entities.User;
import com.example.project2_wellness_wizard_app.database.entities.UserInfo;
import com.example.project2_wellness_wizard_app.databinding.ActivityAddVitMedBinding;
import com.example.project2_wellness_wizard_app.databinding.ActivityMainBinding;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

public class AddVitMedActivity extends AppCompatActivity {

    String vitMeds = "";
    String mTimeOfDay = "";

    private ActivityAddVitMedBinding binding;

    private UserInfoRepository repository;

    final LocalDate date = LocalDate.now();
    final LocalTime time = LocalTime.now();
    UserInfo userInfo;

    int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddVitMedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        repository = UserInfoRepository.getRepository(getApplication());

        SharedPreferences sharedPreferences =getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), -1);

        binding.addVitMedButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertVitMedRecord();

            }
        });
        binding.addVitMedBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
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

    }

    private void insertVitMedRecord() {
        UserInfo info = UserInfo.vitMed(vitMeds, mTimeOfDay, loggedInUserId);
        repository.insertUserInfo(info);
        Toast.makeText(this, "Vitamin/Medication added!", Toast.LENGTH_SHORT).show();

    }

    private void getInformationFromDisplay() {
        vitMeds = binding.nameEditText.getText().toString();
        mTimeOfDay=binding.timeEditText.getText().toString();

    }
    public static Intent AddVitMedActivityIntentFactory(Context context){
        return new Intent(context, AddVitMedActivity.class);
    }
}