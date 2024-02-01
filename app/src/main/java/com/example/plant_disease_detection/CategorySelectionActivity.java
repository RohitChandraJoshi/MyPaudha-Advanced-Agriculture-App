package com.example.plant_disease_detection;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class CategorySelectionActivity extends AppCompatActivity {

    private Button btnVegetables, btnFruits, btnFlowers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        btnVegetables = findViewById(R.id.btnVegetables);
        btnFruits = findViewById(R.id.btnFruits);
        btnFlowers = findViewById(R.id.btnFlowers);

        btnVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHowToGrow("Vegetable");
            }
        });

        btnFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHowToGrow("Fruit");
            }
        });

        btnFlowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHowToGrow("Flower");
            }
        });
    }

    private void navigateToHowToGrow(String category) {
        Intent intent = new Intent(this, HowtoGrow.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}
