package com.example.plant_disease_detection;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HowtoGrow extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PlantAdapter plantAdapter;
    private List<Plants> allPlants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howto_grow);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get all plants
        allPlants = JsonUtils.getPlantsData(this);

        // Get the selected category from the intent
        String selectedCategory = getIntent().getStringExtra("category");

        // Filter plants based on the selected category
        List<Plants> filteredPlants = filterPlantsByCategory(selectedCategory);

        // Set up the RecyclerView with the adapter
        plantAdapter = new PlantAdapter(filteredPlants);
        recyclerView.setAdapter(plantAdapter);
    }

    private List<Plants> filterPlantsByCategory(String category) {
        List<Plants> filteredPlants = new ArrayList<>();
        for (Plants plant : allPlants) {
            if (plant.getCategory().equalsIgnoreCase(category)) {
                filteredPlants.add(plant);
            }
        }
        return filteredPlants;
    }
}

