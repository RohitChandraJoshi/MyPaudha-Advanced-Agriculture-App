package com.example.plant_disease_detection;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughActivity;
import com.shashank.sony.fancywalkthroughlib.FancyWalkthroughCard;

import java.util.ArrayList;
import java.util.List;

public class WalkThrough extends FancyWalkthroughActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FancyWalkthroughCard fancywalkthroughCard1 = new FancyWalkthroughCard("Detect Plant Disease", "Explore our advanced disease detection technology for plants. Identify diseases early to ensure the health of your plants.", R.drawable.disease_logo);

        fancywalkthroughCard1.setBackgroundColor(R.color.white);
        fancywalkthroughCard1.setTitleColor(R.color.black);
        fancywalkthroughCard1.setDescriptionColor(R.color.black);

        FancyWalkthroughCard fancywalkthroughCard2 = new FancyWalkthroughCard("Detect Medicinal Plants", "Discover medicinal plants with our app. Learn about their properties and uses for holistic health and wellness.", R.drawable.medicinal_logo);
        fancywalkthroughCard2.setBackgroundColor(R.color.white);
        fancywalkthroughCard2.setTitleColor(R.color.black);
        fancywalkthroughCard2.setDescriptionColor(R.color.black);

        FancyWalkthroughCard fancywalkthroughCard3 = new FancyWalkthroughCard("Detect Flowers", "Identify various flowers in your garden. Get information on their species, care tips, and create a vibrant floral space.", R.drawable.flower_logo);
        fancywalkthroughCard3.setBackgroundColor(R.color.white);
        fancywalkthroughCard3.setTitleColor(R.color.black);
        fancywalkthroughCard3.setDescriptionColor(R.color.black);

        FancyWalkthroughCard fancywalkthroughCard4 = new FancyWalkthroughCard("Detect Harmful Pests", "Guard your plants against pests. Recognize harmful insects and take preventive measures for a pest-free garden.", R.drawable.pest_logo);
        fancywalkthroughCard4.setBackgroundColor(R.color.white);
        fancywalkthroughCard4.setTitleColor(R.color.black);
        fancywalkthroughCard4.setDescriptionColor(R.color.black);

        FancyWalkthroughCard fancywalkthroughCard5 = new FancyWalkthroughCard("Get Detailed Information", "Access comprehensive information on various plants. Learn about their scientific names, growing conditions, and more.", R.drawable.info_logo);
        fancywalkthroughCard5.setBackgroundColor(R.color.white);
        fancywalkthroughCard5.setTitleColor(R.color.black);
        fancywalkthroughCard5.setDescriptionColor(R.color.black);

        List<FancyWalkthroughCard> pages = new ArrayList<>();
        pages.add(fancywalkthroughCard1);
        pages.add(fancywalkthroughCard2);
        pages.add(fancywalkthroughCard3);
        pages.add(fancywalkthroughCard4);
        pages.add(fancywalkthroughCard5);

        setOnboardPages(pages);
        setImageBackground(R.drawable.walkthrough);
    }

    @Override
    public void onFinishButtonPressed() {
        startActivity(new Intent(getApplicationContext(), SignupPage.class));
    }
}
