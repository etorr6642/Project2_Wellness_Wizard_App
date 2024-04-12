package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.databinding.ActivityManageUsersBinding;

public class ManageUsersActivity extends AppCompatActivity {
    private ActivityManageUsersBinding binding;
    private UserInfoRepository repository;
    public static final String TAG = "WELLNESS_WIZARD";
    private int loggedInUserId =-1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserInfoRepository.getRepository(getApplication()); //gives access to our bd
        SharedPreferences sharedPreferences =getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key),-1);

    }

    public static Intent ManageUsersActivityIntentFactory(Context context){
        return new Intent(context, ManageUsersActivity.class);
    }
}