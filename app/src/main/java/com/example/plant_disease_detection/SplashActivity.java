package com.example.plant_disease_detection;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 3000; // 5 seconds
    private ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        logoImageView = findViewById(R.id.logoImageView);

        // Animation for zooming in and glowing effect
        Animation zoomAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        zoomAnimation.setDuration(1500);
        zoomAnimation.setRepeatCount(Animation.INFINITE);
        zoomAnimation.setRepeatMode(Animation.REVERSE);

        // Fade-in animation for the logo
        Animation fadeInAnimation = new AlphaAnimation(0, 1);
        fadeInAnimation.setDuration(1000);

        // Set up the animation sequence
        zoomAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                logoImageView.setVisibility(ImageView.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Start the fade-in animation after the zooming
                logoImageView.startAnimation(fadeInAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        // Start the zooming animation
        logoImageView.startAnimation(zoomAnimation);

        // Use a Handler to delay the opening of the next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the NextActivity
                Intent intent = new Intent(SplashActivity.this, WalkThrough.class);
                startActivity(intent);
                finish(); // Close the current activity
            }
        }, SPLASH_DELAY);
    }
}
