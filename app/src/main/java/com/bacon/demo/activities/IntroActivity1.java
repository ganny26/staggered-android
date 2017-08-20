package com.bacon.demo.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.bacon.demo.R;
import com.bacon.demo.fragments.SampleSlide;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntro2Fragment;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.IndicatorController;

/**
 * Created by serajam on 8/12/2017.
 */

public class IntroActivity1 extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setFlowAnimation();
        //setSlideOverAnimation();
        //setDepthAnimation();
        // Note here that we DO NOT use setContentView();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(SampleSlide.newInstance(R.layout.intro_page_1));
        addSlide(SampleSlide.newInstance(R.layout.intro_page_2));
        addSlide(SampleSlide.newInstance(R.layout.intro_page_3));
//        addSlide((AppIntro2Fragment.newInstance("Ingredient search", "Got only tomatoes and potatoes at home? Or allergic to peanuts? Search for multi-cuisine recipes that includes only the available ingredient at home or exclude allergic ingredients.",
//                R.drawable.ic_mic_black_24dp, ContextCompat.getColor(this,R.color.intro1))));
//
//        addSlide((AppIntro2Fragment.newInstance("Semantic search", "Need to cut down some calories? Craving for a dish with your favourite item? No problem. We've got an ear for your needs. Use our auto-complete semantic query.",
//                R.drawable.ic_favorite_black_24dp, ContextCompat.getColor(this,R.color.intro2))));
//

//        addSlide((AppIntro2Fragment.newInstance("Recipe Reader", "Enjoy cooking while we read out the recipe for you. Just 'pause' if you want more time and we'll cover up for you",
//                R.drawable.ic_search_black_24dp, ContextCompat.getColor(this,R.color.intro3))));
//




        // OPTIONAL METHODS
        // Override bar/separator color.

        showStatusBar(false);
        showSeparator(false);

        showPagerIndicator(true);

       // setIndicatorColor(R.color.white,R.color.primary_light);
       // setCustomIndicator();

        showSkipButton(false);
        setProgressButtonEnabled(false);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.goToNextSlide();
            }
        });

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }



    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }


}
