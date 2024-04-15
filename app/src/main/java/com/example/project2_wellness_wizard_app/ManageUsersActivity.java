package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.databinding.ActivityManageUsersBinding;

import java.util.ArrayList;

public class ManageUsersActivity extends AppCompatActivity {
    private ActivityManageUsersBinding binding;
    private UserInfoRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserInfoRepository.getRepository(getApplication()); //gives access to our bd

        binding.usernamesDisplayTextView.setMovementMethod(new ScrollingMovementMethod()); //added to scroll in water display

        displayUsers();

        binding.deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
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

        binding.userMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
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

    private void getUserAndDelete(){
        boolean flag = false;
        String username = binding.usernameInputEditText.getText().toString();

        ArrayList<String> allUsers = repository.getAllUsers();

        if(username.isEmpty()){
            toastMaker("Username should not be blank.");
            return;
        }else{
            for(String user: allUsers){
                if (user.equals(username)){
                    repository.deleteByUsername(username);
                    toastMaker("User was successfully deleted");
                    displayUsers();
                    flag = true;
                }
            }
            if(!flag){
                toastMaker("Input Valid Username.");
            }
        }
    }

    private void showDeleteDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ManageUsersActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Are you sure you want to delete this user?\n");

        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getUserAndDelete();
            }
        });

        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.create().show();
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    public static Intent ManageUsersActivityIntentFactory(Context context){
        return new Intent(context, ManageUsersActivity.class);
    }
}