package com.example.plant_disease_detection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextView  txtwelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        // Initialize TextViews for user details

        txtwelcome = findViewById(R.id.txtwelcome);
        // Load user details
        loadUserDetails();
    }

    private void loadUserDetails() {
        // Get user details from SharedPreferences
        String username = sharedPreferences.getString("username", "");


        txtwelcome.setText(username);



    }

    // Implement the logout method
    public void btn_logout(View view) {
        logout();
    }

    private void logout() {
        // Update SharedPreferences to mark the user as logged out
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        // Redirect to the login page
        startActivity(new Intent(getApplicationContext(), LoginPage.class));
        finish();  // Finish the current activity to prevent going back to it using the back button
    }
}
