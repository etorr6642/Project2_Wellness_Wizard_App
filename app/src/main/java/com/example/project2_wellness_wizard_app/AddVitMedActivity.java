package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.database.entities.UserInfo;
import com.example.project2_wellness_wizard_app.databinding.ActivityAddVitMedBinding;


public class AddVitMedActivity extends AppCompatActivity {

    String vitMeds = "";
    String mTimeOfDay = "";
    private ActivityAddVitMedBinding binding;
    private UserInfoRepository repository;
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
                if(checkName()&checkTime()){
                    insertVitMedRecord();
                }else{
                    getInformationFromDisplay();
                }
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
        toastMaker("Vitamin/Medication added!");
    }

    private boolean checkTime(){
        String checkTime = binding.timeEditText.getText().toString();

        if(checkTime.isEmpty()){
            toastMaker("Enter data for Time");
            return false;
        }
        return true;
    }

    private boolean checkName(){
        String checkName = binding.nameEditText.getText().toString();

        if(checkName.isEmpty()){
            toastMaker("Enter data for Name");
            return false;
        }
       return true;
    }

    private void getInformationFromDisplay() {
        vitMeds = binding.nameEditText.getText().toString();
        mTimeOfDay=binding.timeEditText.getText().toString();

    }

    private void toastMaker(String message) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    public static Intent AddVitMedActivityIntentFactory(Context context){
        return new Intent(context, AddVitMedActivity.class);
    }
}