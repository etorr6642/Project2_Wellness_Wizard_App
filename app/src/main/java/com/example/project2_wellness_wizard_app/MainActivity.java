package com.example.project2_wellness_wizard_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

    private UserInfoRepository userRepo = UserInfoRepository.getRepository(getApplication());

    String mFood = "";
    int mCalories = 0;
    String vitMeds = "";
    String mTimeOfDay = "";
    int mWater = 0;
    double mWeight = 0.0;

    private int loggedInUserId=-1;
    private User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = UserInfoRepository.getRepository(getApplication());
        
        loginUser(savedInstanceState);


        if(loggedInUserId==-1){
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        updateSharedPreference();

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
                showAlertDialog();
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

        binding.trackFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FoodActivity.FoodActivityIntentFactory((getApplicationContext()));
                startActivity(intent);
            }
        });

        binding.trackWeightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WeightActivity.WeightActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });


        binding.adminMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminActivity.AdminActivityIntentFactory(getApplicationContext()); //should i do context: MainActivity.this??
                startActivity(intent);
            }
        });
    }


    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences =getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key),LOGGED_OUT);

        if(loggedInUserId ==LOGGED_OUT & savedInstanceState!= null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY,LOGGED_OUT);
        }
        if(loggedInUserId==LOGGED_OUT){
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if(loggedInUserId==LOGGED_OUT){
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if(this.user!=null){
                invalidateOptionsMenu();
                if(this.user.isAdmin()){
                    binding.adminMenuButton.setVisibility(View.VISIBLE);
                }else{
                    binding.adminMenuButton.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        updateSharedPreference();
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
        if(user ==null){
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showAlertDialog();
                return false;
            }
        });
        return true;
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setTitle("Logout?");

        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
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

    private void logout() {
        loggedInUserId=LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, loggedInUserId);

        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key),loggedInUserId);
        sharedPrefEditor.apply();
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