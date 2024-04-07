package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.database.entities.User;
import com.example.project2_wellness_wizard_app.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private UserInfoRepository repository;

    private User user =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserInfoRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verifyUser()){
                    Toastmaker("Invalid username or password");

                }else{
                    Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext(), user.getUserID());
                    startActivity(intent);
                }

            }
        });
    }

    private boolean verifyUser() {
        String username =binding.userNameLoginEditText.getText().toString();
        if(username.isEmpty()){
            Toastmaker("Username should not be blank");
            return false;
        }
        user = repository.getUserByUserName(username);
        if(user !=null){
            String password = binding.passwordLoginEditText.getText().toString();
            if(password.equals(user.getPassword())){
                return true;
            }else{
                Toastmaker("Invalid password");
                return false;
            }
        }
        Toastmaker(String.format("No %s found",username));
        return false;
    }

    private void Toastmaker(String message) {
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }


    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }
}