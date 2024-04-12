package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.databinding.ActivityAccountBinding;

public class AccountActivity extends AppCompatActivity {

    private UserInfoRepository repository;
    public static final String TAG = "WELLNESS_WIZARD";

    private ActivityAccountBinding binding;
    private int loggedInUserId =-1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserInfoRepository.getRepository(getApplication()); //gives access to our bd
        SharedPreferences sharedPreferences =getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key),-1);

        displayUsername();
        displayPassword();

        binding.AccountBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory((getApplicationContext()));
                startActivity(intent);
            }
        });
    }

    private void displayUsername(){
        String username = repository.getUsername(loggedInUserId);
        binding.usernameDisplayTextView.setText(username);
    }

    private void displayPassword(){
        String password = repository.getPassword(loggedInUserId);
        binding.passwordDisplayTextView.setText(password);
    }
    public static Intent accountIntentFactory(Context context){
        return new Intent(context, AccountActivity.class);
    }
}