package com.example.project2_wellness_wizard_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2_wellness_wizard_app.databinding.ActivityViewVitMedBinding;

public class ViewVitMedActivity extends AppCompatActivity {

    private ActivityViewVitMedBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewVitMedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.vitMedDisplayTextView.setMovementMethod(new ScrollingMovementMethod()); //added to scroll in display of view vit/med
        binding.viewVitMedBackButton.setOnClickListener(new View.OnClickListener() { //back button in this activity
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.MainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }
    public static Intent ViewVitMedActivityIntentFactory(Context context){
        return new Intent(context, ViewVitMedActivity.class);
    }
}