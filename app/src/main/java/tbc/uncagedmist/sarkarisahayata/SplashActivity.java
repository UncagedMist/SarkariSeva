package tbc.uncagedmist.sarkarisahayata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;

import am.appwise.components.ni.NoInternetDialog;
import tbc.uncagedmist.sarkarisahayata.Adapter.ScreenSliderPagerAdapter;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView animationView;

    ScreenSliderPagerAdapter pagerAdapter;
    ViewPager viewPager;

    Animation anim;

    NoInternetDialog noInternetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        noInternetDialog = new NoInternetDialog.Builder(SplashActivity.this).build();

        animationView = findViewById(R.id.lottie);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSliderPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerAdapter);

        setAdapter();
        setupAnimation();
    }

    private void setAdapter() {
        anim = AnimationUtils.loadAnimation(this,R.anim.o_b_anim);
        viewPager.startAnimation(anim);
    }

    private void setupAnimation() {
        animationView.animate()
                .translationY(1600)
                .setDuration(1000)
                .setStartDelay(4000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        noInternetDialog.onDestroy();
    }
}