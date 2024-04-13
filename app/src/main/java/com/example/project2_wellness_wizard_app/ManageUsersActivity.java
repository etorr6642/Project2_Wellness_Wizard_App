package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.databinding.ActivityManageUsersBinding;

import java.util.ArrayList;

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

        displayUsers();

        binding.deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: create method to get information from display
                //TODO: create a method delete user
                displayUsers();
            }
        });

        binding.manageUsersBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminActivity.AdminActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void displayUsers(){
        ArrayList<String> allUsers = repository.getAllUsers();

        if(allUsers.isEmpty()){
            binding.usernamesDisplayTextView.setText(R.string.no_users);
        }

        StringBuilder sb = new StringBuilder();
        for(String user: allUsers){
            sb.append(user).append("\n=-=-=-=-=-=-=-=-=-=-=-\n");
        }

        binding.usernamesDisplayTextView.setText(sb.toString());

    }
    public static Intent ManageUsersActivityIntentFactory(Context context){
        return new Intent(context, ManageUsersActivity.class);
    }
}