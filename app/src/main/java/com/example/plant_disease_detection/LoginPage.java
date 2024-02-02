package com.example.plant_disease_detection;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.SharedPreferences;
import android.content.Context;
public class LoginPage extends AppCompatActivity {

    private EditText etLoginUsername, etLoginPassword;
    private Button btnLogin;
    private ProgressBar loadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        etLoginUsername = findViewById(R.id.etLoginUsername);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        loadingProgressBar = findViewById(R.id.loadingProgressBar);
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isDarkMode = nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
        if (isDarkMode) {
            findViewById(R.id.layout).setBackgroundResource(R.drawable.frontscreen);
        } else {
            findViewById(R.id.layout).setBackgroundResource(R.drawable.walkthrough);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etLoginUsername.getText().toString();
                String password = etLoginPassword.getText().toString();
                btnLogin.setVisibility(View.GONE);
                // Show loading icon
                loadingProgressBar.setVisibility(View.VISIBLE);

                new LoginTask().execute(username, password);
            }
        });
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            String apiUrl = "https://android-backend-jnec.onrender.com/login";  // Replace with your login API endpoint

            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("username", username);
                jsonParam.put("password", password);

                OutputStream os = connection.getOutputStream();
                os.write(jsonParam.toString().getBytes("UTF-8"));
                os.close();

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    return "Login successful";
                } else {
                    return "Error: " + responseCode;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Hide loading icon
            loadingProgressBar.setVisibility(View.GONE);

            Toast.makeText(LoginPage.this, result, Toast.LENGTH_SHORT).show();
            if (result.equals("Login successful")) {
                SharedPreferences.Editor editor = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE).edit();
                editor.putString("username", etLoginUsername.getText().toString());
                editor.putString("email", etLoginUsername.getText().toString());
                editor.putString("password", etLoginPassword.getText().toString());
                editor.putBoolean("isLoggedIn", true);
                editor.apply();
                Intent intent = new Intent(LoginPage.this, FrontPage.class);
                intent.putExtra("username", etLoginUsername.getText().toString());
                intent.putExtra("email", etLoginUsername.getText().toString());
                intent.putExtra("password", etLoginPassword.getText().toString());
                startActivity(new Intent(getApplicationContext(), FrontPage.class));
            }
        }
    }

    public void btn_signup_module(View view){
        startActivity(new Intent(getApplicationContext(), SignupPage.class));
    }
}
