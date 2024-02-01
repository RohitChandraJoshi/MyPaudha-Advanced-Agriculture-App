package com.example.plant_disease_detection;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<Plants> getPlantsData(Context context) {
        List<Plants> plantsList = new ArrayList<>();

        try {
            // Read the JSON file from the resources
            InputStream inputStream = context.getResources().openRawResource(R.raw.plants_data);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            // Parse the JSON data
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String category = jsonObject.getString("category");

                // Extracting additional fields
                String growingConditions = jsonObject.optString("growingConditions");
                String careTips = jsonObject.optString("careTips");
                String scientificName = jsonObject.optString("scientificName");
                String difficultyLevel = jsonObject.optString("difficultyLevel");
                List<String> tags = jsonArrayToList(jsonObject.optJSONArray("tags"));
                String source = jsonObject.optString("source");

                // Extracting growingGuide
                JSONObject growingGuideObject = jsonObject.optJSONObject("growingGuide");
                List<String> steps = jsonArrayToList(growingGuideObject.optJSONArray("steps"));
                String additionalTips = growingGuideObject.optString("additionalTips");
                Plants.GrowingGuide growingGuide = new Plants.GrowingGuide(steps, additionalTips);

                Plants plant = new Plants(
                        jsonObject.getInt("id"),
                        jsonObject.getString("plantName"),
                        jsonObject.getString("image"),
                        jsonObject.getString("details"),
                        category,
                        growingConditions,
                        careTips,
                        scientificName,
                        difficultyLevel,
                        tags,
                        source,
                        growingGuide
                );
                plantsList.add(plant);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return plantsList;
    }

    private static List<String> jsonArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }
}
