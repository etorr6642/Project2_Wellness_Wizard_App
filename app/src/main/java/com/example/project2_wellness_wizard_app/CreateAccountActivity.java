package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.database.entities.User;
import com.example.project2_wellness_wizard_app.databinding.ActivityCreateAccountBinding;

import java.util.ArrayList;

public class CreateAccountActivity extends AppCompatActivity {

    private ActivityCreateAccountBinding binding;
    private UserInfoRepository repository;
    String username ="";
    String password ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TODO: binding.createAccountButton
        repository=UserInfoRepository.getRepository(getApplication());
        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getInformationFromDisplay();
               if(checkUsername()&checkPassword()){
                   addUserToDatabase();
                   Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                   startActivity(intent);

               }
               else{
                   getInformationFromDisplay();
               }
            }
        });
        binding.createAccountBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private boolean checkPassword() {
        String confirmPassword = binding.confirmPasswordInputEditText.getText().toString();
        if(!password.equals(confirmPassword)){
            Toast.makeText(this,"Passwords do not match",Toast.LENGTH_SHORT).show();
            getInformationFromDisplay();
            return false;
        }else
            return true;
    }

    private boolean checkUsername() {
        boolean checkUserName =true;
        ArrayList<String> allUserNames = repository.getAllUsers();
        for(int i = 0; i<allUserNames.size();i++){
            if(username.equals(allUserNames.get(i))){
                Toast.makeText(this, "Username is already taken", Toast.LENGTH_SHORT).show();
                checkUserName = false;
                getInformationFromDisplay();
            }
        }
        return checkUserName;
    }

    private void addUserToDatabase() {
        User user = new User(username,password);
        repository.insertUser(user);
        Toast.makeText(this, "Account successfully created! ", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Log in with your new username and password",Toast.LENGTH_SHORT).show();
    }

    private void getInformationFromDisplay() {
        username = binding.createUsernameInputEditText.getText().toString();
        password = binding.createPasswordInputEditText.getText().toString();
    }

    public static Intent CreateAccountIntentFactory(Context context){
        return new Intent(context, CreateAccountActivity.class);
    }
}