package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.project2_wellness_wizard_app.database.UserDAO;
import com.example.project2_wellness_wizard_app.database.UserInfoDAO;
import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.database.entities.User;

import com.example.project2_wellness_wizard_app.databinding.ActivityAccountBinding;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {

    private UserInfoRepository repository;
    public static final String TAG = "WELLNESS_WIZARD";

    private ActivityAccountBinding binding;

    private int loggedInUserId =-1;
    private int LOGGED_OUT =-1;
    private User user;


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


        LiveData<User> username = repository.getUserByUserId(loggedInUserId);



        binding.deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
        binding.AccountBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory((getApplicationContext()));
                startActivity(intent);
            }
        });
    }


    private void showAlertDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AccountActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setTitle("Delete account?");

        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO: delete user information from database
                getUserAndDelete();
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);



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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String username = binding.usernameDisplayTextView.getText().toString();
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if(user ==null){
            return false;
        }
        item.setTitle(username);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showAlertDialog();
                return false;
            }
        });
        return true;
    }

    private void getUserAndDelete(){
        boolean flag = false;
        String username = binding.usernameDisplayTextView.getText().toString();

        ArrayList<String> allUsers = repository.getAllUsers();

        if(username.isEmpty()){
            toastMaker("Username should not be blank.");
            return;
        }else{
            for(String user: allUsers){
                if (user.equals(username)){
                    repository.deleteByUsername(username);
                    toastMaker("Your account was successfully deleted");
                    toastMaker("Create a new account to login");
                    flag = true;
                }
            }
            if(!flag){
                toastMaker("Input Valid Username.");
            }
        }
    }
    private void displayUsername(){
        String username = repository.getUsername(loggedInUserId);
        binding.usernameDisplayTextView.setText(username);
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }

    private void displayPassword(){
        String password = repository.getPassword(loggedInUserId);
        binding.passwordDisplayTextView.setText(password);
    }
    public static Intent AccountIntentFactory(Context context){

        return new Intent(context, AccountActivity.class);
    }
}