package com.example.plant_disease_detection;
import android.text.Html;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class PlantDetailsActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        // Retrieve data from the Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("plantDetails")) {
            Plants selectedPlant = intent.getParcelableExtra("plantDetails");

            ImageView image = findViewById(R.id.imageView);
            String imageUrl = selectedPlant.getImage();
            Picasso.get().load(imageUrl).into(image);

            TextView plantName = findViewById(R.id.plantNameTextView);
            plantName.setText(selectedPlant.getPlantName());

            TextView details = findViewById(R.id.detailsTextView);
            details.setText(formatText2("Details", selectedPlant.getDetails()));

            TextView growingConditions = findViewById(R.id.TextviewgrowingConditions);
            growingConditions.setText(formatText2("Growing Conditions", selectedPlant.getGrowingConditions()));

            TextView careTips = findViewById(R.id.Textviewcaretips);
            careTips.setText(formatText2("Care Tips", selectedPlant.getCareTips()));

            TextView category = findViewById(R.id.categoryTextView);
            category.setText(formatText2("Category", selectedPlant.getCategory()));

            TextView scientificName = findViewById(R.id.scientificNameTextView);
            scientificName.setText(formatText2("Scientific Name", selectedPlant.getScientificName()));

            TextView difficultyLevel = findViewById(R.id.difficultyLevelTextView);
            difficultyLevel.setText(formatText2("Difficulty Level", selectedPlant.getDifficultyLevel()));

            TextView tags = findViewById(R.id.tagsTextView);
            tags.setText(formatText2("Tags", String.join(", ", selectedPlant.getTags())));

            TextView source = findViewById(R.id.sourceTextView);
            source.setText(formatText2("Source", selectedPlant.getSource()));

            TextView growingGuideTextView = findViewById(R.id.growingGuideTextView);
            String combinedSteps = String.join("\n", selectedPlant.getGrowingGuide().getSteps());
            growingGuideTextView.setText(formatText("Growing Guide", "&#8226; " + combinedSteps.replace("\n", "<br/>&#8226; ")));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private CharSequence formatText2(String label, String content) {
        return Html.fromHtml("<b>"+label + ": </b><font color='#008000'>" + content + "</font>", Html.FROM_HTML_MODE_COMPACT);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private CharSequence formatText(String label, String content) {
        return Html.fromHtml("<b>" + label + ":</b><font color='#008000'><br/>" + content + "</font>", Html.FROM_HTML_MODE_COMPACT);
    }
}
