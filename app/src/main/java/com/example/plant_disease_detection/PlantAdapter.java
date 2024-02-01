package com.example.plant_disease_detection;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {
    private List<Plants> plantList;
    private List<Plants> originalPlantList; // Added to store the original list

    public PlantAdapter(List<Plants> plantList) {
        this.plantList = plantList;
        this.originalPlantList = new ArrayList<>(plantList); // Initialize the original list
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_plant_card, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plants plant = plantList.get(position);

        // Load plant data into the ViewHolder
        holder.tvPlantName.setText(plant.getPlantName());

        // Load plant image using Picasso (or your preferred image loading library)
        Picasso.get().load(plant.getImage()).into(holder.imgPlant);

        // Set an OnClickListener for the card
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch PlantDetailsActivity and pass data
                Intent intent = new Intent(view.getContext(), PlantDetailsActivity.class);
                intent.putExtra("plantDetails", plant); // Pass the entire Plant object
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    // ViewHolder class
    public static class PlantViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPlant;
        TextView tvPlantName;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPlant = itemView.findViewById(R.id.imgPlant1);  // Assuming imgPlant1 is the correct ImageView ID
            tvPlantName = itemView.findViewById(R.id.tvPlantName1);  // Assuming tvPlantName1 is the correct TextView ID
        }
    }

    // Filter method to update the plantList based on category
    public void filterByCategory(String category) {
        List<Plants> filteredList = new ArrayList<>();

        for (Plants plant : originalPlantList) {
            if (plant.getCategory().equals(category)) {
                filteredList.add(plant);
            }
        }

        plantList = filteredList;
        notifyDataSetChanged();
    }

    // Reset the plantList to the original list
    public void resetList() {
        plantList = new ArrayList<>(originalPlantList);
        notifyDataSetChanged();
    }

    public void updateData(List<Plants> newPlantList) {
        plantList.clear();
        plantList.addAll(newPlantList);
        notifyDataSetChanged();
    }
}
