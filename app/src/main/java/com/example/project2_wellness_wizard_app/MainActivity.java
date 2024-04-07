package com.example.project2_wellness_wizard_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.project2_wellness_wizard_app.database.UserInfoRepository;
import com.example.project2_wellness_wizard_app.database.entities.User;
import com.example.project2_wellness_wizard_app.databinding.ActivityMainBinding;
import com.example.project2_wellness_wizard_app.databinding.ActivityLoginBinding;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = ".com.example.project2_wellness_wizard_app.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = ".com.example.project2_wellness_wizard_app.SHARED_PREFERENCE_USERID_KEY";
    private static final int LOGGED_OUT =-1;
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = ".com.example.project2_wellness_wizard_app.SAVED_INSTANCE_STATE_USERID_KEY";
    private ActivityMainBinding binding;
    private ActivityLoginBinding loginBinding;

    private UserInfoRepository repository;

    public static final String TAG = "WELLNESS_WIZARD";

    String mFood = "";
    int mCalories = 0;
    String vitMeds = "";
    String mTimeOfDay = "";
    double mWater = 0.0;
    double mWeight = 0.0;
    //TODO: add login information
    private int loggedInUserId=-1;
    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserInfoRepository.getRepository(getApplication());
        
        loginUser();

        if(loggedInUserId==-1){
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        
        invalidateOptionsMenu();

/**

*/
        binding.accountSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = AccountActivity.accountIntentFactory((getApplicationContext()));
                startActivity(intent);
            }
        });

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.addVitMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddVitMedActivity.AddVitMedActivityIntentFactory(getApplicationContext());
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

        binding.getWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WorkoutActivity.WorkoutActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.trackWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WaterActivity.WaterActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void loginUser() {
        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        item.setTitle("Eddie");
        return true;
    }

    static Intent MainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
    public static Intent MainActivityIntentFactory(Context context){
        return new Intent(context, MainActivity.class);
    }

}