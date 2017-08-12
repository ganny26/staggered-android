package com.bacon.demo.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.bacon.demo.R;
import com.bacon.demo.utils.FontIconDrawable;
import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AhoyOnboarderActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Ingredient search", "Got only tomatoes and potatoes at home? Or allergic to peanuts? Search for multi-cuisine recipes that includes only the available ingredient at home or exclude allergic ingredients.", R.drawable.ic_search_black_24dp);
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Semantic search", "Need to cut down some calories? Craving for a dish with your favourite item? No problem. We've got an ear for your needs. Use our auto-complete semantic query.",R.drawable.ic_action_voice_search);
        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Recipe Reader", "Enjoy cooking while we read out the recipe for you. Just 'pause' if you want more time and we'll cover up for you.",R.drawable.ic_favorite_black_24dp);

        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);

        List<Integer> colorList = new ArrayList<>();
        colorList.add(R.color.intro1);
        colorList.add(R.color.intro2);
        colorList.add(R.color.intro3);
        setColorBackground(colorList);

        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.black);
            page.setDescriptionColor(R.color.black);
        }

        setFinishButtonTitle("Get Started");
        setInactiveIndicatorColor(R.color.cardview_shadow_end_color);
        setActiveIndicatorColor(R.color.white);
        //setFinishButtonDrawableStyle(new FontIconDrawable(this,R.string.fa_cutlery,ContextCompat.getColor(this,R.color.black)));

        setOnboardPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {
        Toast.makeText(this, "Finish Pressed", Toast.LENGTH_SHORT).show();
    }
}
