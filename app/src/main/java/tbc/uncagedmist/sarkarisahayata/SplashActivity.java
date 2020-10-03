package tbc.uncagedmist.sarkarisahayata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;

import tbc.uncagedmist.sarkarisahayata.Adapter.ScreenSliderPagerAdapter;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView animationView;

    ScreenSliderPagerAdapter pagerAdapter;
    ViewPager viewPager;

    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        animationView = findViewById(R.id.lottie);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSliderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        anim = AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        viewPager.startAnimation(anim);

        setupAnimation();
    }

    private void setupAnimation() {
        animationView.animate()
                .translationY(1600)
                .setDuration(2500)
                .setStartDelay(4000);
    }
}