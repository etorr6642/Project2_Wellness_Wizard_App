package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;


import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.database.entities.User;
import com.example.project2_wellness_wizard_app.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private UserInfoRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserInfoRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });
    }

    private void verifyUser() {
        String username =binding.userNameLoginEditText.getText().toString();

        if(username.isEmpty()){
            toastMaker("Username should not be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this,user -> {
            if(user!=null) {
                String password = binding.passwordLoginEditText.getText().toString();
                if (password.equals(user.getPassword())) {
                    startActivity(MainActivity.MainActivityIntentFactory(getApplicationContext(), user.getUserID()));
                } else {
                    toastMaker("Invalid password");
                    binding.passwordLoginEditText.setSelection(0);
                }
            }else{
                toastMaker(String.format("%s is not a valid username", username));
                binding.userNameLoginEditText.setSelection(0);
            }
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }


    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }
}