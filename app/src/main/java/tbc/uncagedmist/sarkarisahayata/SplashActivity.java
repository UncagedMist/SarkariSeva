package tbc.uncagedmist.sarkarisahayata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.ncorti.slidetoact.SlideToActView;

import java.util.ArrayList;
import java.util.List;

import tbc.uncagedmist.sarkarisahayata.Adapter.MyViewPagerAdapter;
import tbc.uncagedmist.sarkarisahayata.Model.ImageItem;

public class SplashActivity extends AppCompatActivity {

    RelativeLayout parentView;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    SlideToActView slideToActView;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        parentView = findViewById(R.id.parentView);
        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.dotTabs);
        slideToActView = findViewById(R.id.btnContinue);

        slideToActView.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        });

        List<ImageItem> imageItemList = new ArrayList<>();
        imageItemList.add(new ImageItem(R.drawable.cover));
        imageItemList.add(new ImageItem(R.drawable.services));
        imageItemList.add(new ImageItem(R.drawable.id));
        imageItemList.add(new ImageItem(R.drawable.social));
        imageItemList.add(new ImageItem(R.drawable.update));

        viewPager2.setAdapter(new MyViewPagerAdapter(imageItemList,viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer transformer = new CompositePageTransformer();
        transformer.addTransformer(new MarginPageTransformer(40));

        transformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r + 0.15f);
            }
        });
        viewPager2.setPageTransformer(transformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(slideRunnable);
                handler.postDelayed(slideRunnable,3000);
            }
        });
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideToActView.resetSlider();
        handler.postDelayed(slideRunnable,3000);
    }
}