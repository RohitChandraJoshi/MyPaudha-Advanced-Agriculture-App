package com.example.plant_disease_detection;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FrontPage extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextView txtWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        // Check if the user is not logged in, redirect to the login page
        if (!isLoggedIn()) {
            startActivity(new Intent(getApplicationContext(), SplashActivity.class));
            finish();  // Finish the current activity to prevent going back to it using the back button
        }

        // Initialize TextView for welcome message
        txtWelcome = findViewById(R.id.txtWelcome);

        // Get the username from SharedPreferences and set it in the TextView
        String username = sharedPreferences.getString("username", "");
        txtWelcome.setText("Welcome, " + username + "!");
    }

    // Helper method to check if the user is logged in
    private boolean isLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    // Helper method to handle logout
    private void logout() {
        // Update SharedPreferences to mark the user as logged out
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.apply();

        // Redirect to the login page
        startActivity(new Intent(getApplicationContext(), LoginPage.class));
        finish();  // Finish the current activity to prevent going back to it using the back button
    }

    // Implementing logout in the button click event
    public void btn_logout(View view) {
        logout();
    }

    // Rest of your code for other button clicks
    public void btn_disease_module(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void btn_flowers_module(View view){
        startActivity(new Intent(getApplicationContext(), Flower_Detection_Activity.class));
    }
    public void btn_pest_module(View view){
        startActivity(new Intent(getApplicationContext(),Pest_Detection_Activity.class));
    }
    public void btn_medicinal_module(View view){
        startActivity(new Intent(getApplicationContext(), MedicinalPlants_Activity.class));
    }
    public void btn_HowToGrow(View view){
        startActivity(new Intent(getApplicationContext(),CategorySelectionActivity.class));
    }
    public void openProfilePage(View view) {
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");

        Intent intent = new Intent(FrontPage.this, ProfileActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }
    // ... (rest of your methods)
}
