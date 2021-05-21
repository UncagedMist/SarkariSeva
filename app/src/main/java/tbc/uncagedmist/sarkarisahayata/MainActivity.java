package tbc.uncagedmist.sarkarisahayata;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.Icon;

import tbc.uncagedmist.sarkarisahayata.Fragments.AboutFragment;
import tbc.uncagedmist.sarkarisahayata.Fragments.HomeFragment;
import tbc.uncagedmist.sarkarisahayata.Fragments.PrivacyFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   ReviewManager manager;
   ReviewInfo reviewInfo;

   private static final int PERMISSION_REQUEST_CODE = 31;

   AdView aboveBanner, bottomBanner;

   Toolbar toolbar;

   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                          @NonNull  int[] grantResults) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);

      switch (requestCode)
      {
         case PERMISSION_REQUEST_CODE:
         {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)    {
               Toast.makeText(this, "PERMISSION GRANTED..", Toast.LENGTH_SHORT).show();
            }
            else    {
               Toast.makeText(this, "PERMISSION DENIED...", Toast.LENGTH_SHORT).show();
            }
         }
         break;
      }
   }

   @RequiresApi(api = Build.VERSION_CODES.M)
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
         Window window = getWindow();
         window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                 WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
      }

      setContentView(R.layout.activity_main);

      manager = ReviewManagerFactory.create(MainActivity.this);

      toolbar = findViewById(R.id.toolbar);
      toolbar.setTitle(R.string.app_name);
      setSupportActionBar(toolbar);

      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
              != PackageManager.PERMISSION_GRANTED)   {
         requestPermissions(new String[]{
                 Manifest.permission.WRITE_EXTERNAL_STORAGE
         }, PERMISSION_REQUEST_CODE);
      }

      aboveBanner = findViewById(R.id.aboveBanner);
      bottomBanner = findViewById(R.id.bottomBanner);

      AdRequest adRequest = new AdRequest.Builder().build();

      aboveBanner.loadAd(adRequest);
      bottomBanner.loadAd(adRequest);

      DrawerLayout drawer = findViewById(R.id.drawer_layout);
      ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
              this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      drawer.addDrawerListener(toggle);
      toggle.syncState();

      NavigationView navigationView =  findViewById(R.id.nav_view);
      navigationView.setNavigationItemSelectedListener(this);

      loadFragment(HomeFragment.getInstance());

      adMethod();
   }

   @Override
   public void onBackPressed() {

      new FancyAlertDialog.Builder(MainActivity.this)
              .setTitle("One Nation One Ration Card")
              .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
              .setMessage("Support us by downloading our other apps!")
              .setNegativeBtnText("Don't")
              .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
              .setPositiveBtnText("Support")
              .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
              .setAnimation(Animation.POP)
              .isCancellable(true)
              .setIcon(R.drawable.ic_star_border_black_24dp, Icon.Visible)
              .OnPositiveClicked(() ->
                      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=tbc.uncagedmist.rationpro"))))
              .OnNegativeClicked(() -> {
              })
              .build();
   }

   private void adMethod() {
      aboveBanner.setAdListener(new AdListener() {
         @Override
         public void onAdLoaded() {
            // Code to be executed when an ad finishes loading.
         }

         @Override
         public void onAdFailedToLoad(LoadAdError adError) {
            // Code to be executed when an ad request fails.
         }

         @Override
         public void onAdOpened() {
            // Code to be executed when an ad opens an overlay that
            // covers the screen.
         }

         @Override
         public void onAdClicked() {
            // Code to be executed when the user clicks on an ad.
         }

         @Override
         public void onAdClosed() {
            // Code to be executed when the user is about to return
            // to the app after tapping on an ad.
         }
      });

      bottomBanner.setAdListener(new AdListener() {
         @Override
         public void onAdLoaded() {
            // Code to be executed when an ad finishes loading.
         }

         @Override
         public void onAdFailedToLoad(LoadAdError adError) {
            // Code to be executed when an ad request fails.
         }

         @Override
         public void onAdOpened() {
            // Code to be executed when an ad opens an overlay that
            // covers the screen.
         }

         @Override
         public void onAdClicked() {
            // Code to be executed when the user clicks on an ad.
         }

         @Override
         public void onAdClosed() {
            // Code to be executed when the user is about to return
            // to the app after tapping on an ad.
         }
      });
   }

   @RequiresApi(api = Build.VERSION_CODES.M)
   @SuppressLint("ResourceAsColor")
   @Override
   public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      int id = item.getItemId();

      if (id == R.id.nav_home) {
         toolbar.setBackgroundColor(getColor(R.color.blue));
         HomeFragment homeFragment = new HomeFragment();
         transaction.replace(R.id.main_frame,homeFragment);
         toolbar.setTitle(R.string.app_name);
      }
      else if (id == R.id.nav_about)   {
         toolbar.setBackgroundColor(getColor(R.color.orange));
         AboutFragment aboutFragment = new AboutFragment();
         transaction.replace(R.id.main_frame,aboutFragment);
         toolbar.setTitle("Developer Story");
      }
      else if (id == R.id.nav_privacy) {
         toolbar.setBackgroundColor(getColor(R.color.red));
         PrivacyFragment privacyFragment = new PrivacyFragment();
         transaction.replace(R.id.main_frame,privacyFragment);
         toolbar.setTitle("Privacy & Policy");
      }
      else if (id == R.id.nav_setting)   {
         startActivity(new Intent(MainActivity.this,SettingActivity.class));
      }
      else if (id == R.id.nav_feed) {
         feedback();
      }
      else if (id == R.id.nav_exit) {
         exit();
      }
      transaction.addToBackStack(null);
      transaction.commit();

      DrawerLayout drawer = findViewById(R.id.drawer_layout);
      drawer.closeDrawer(GravityCompat.START);
      return true;
   }

   private void feedback() {
      Task<ReviewInfo> request = manager.requestReviewFlow();

      request.addOnCompleteListener(task -> {
         if (task.isSuccessful())    {
            reviewInfo = task.getResult();

            Task<Void> flow = manager.launchReviewFlow(MainActivity.this,reviewInfo);

            flow.addOnSuccessListener(result -> {
            });
         }
         else {
            Toast.makeText(MainActivity.this, "ERROR...", Toast.LENGTH_SHORT).show();
         }
      });
   }

   private void exit() {
      new FancyAlertDialog.Builder(MainActivity.this)
              .setTitle("Good-Bye")
              .setBackgroundColor(Color.parseColor("#303F9F"))  //Don't pass R.color.colorvalue
              .setMessage("Do You Want to Step Out?")
              .setNegativeBtnText("Exit")
              .setPositiveBtnBackground(Color.parseColor("#FF4081"))  //Don't pass R.color.colorvalue
              .setPositiveBtnText("Rate US")
              .setNegativeBtnBackground(Color.parseColor("#FFA9A7A8"))  //Don't pass R.color.colorvalue
              .setAnimation(Animation.POP)
              .isCancellable(true)
              .setIcon(R.drawable.ic_star_border_black_24dp, Icon.Visible)
              .OnPositiveClicked(() ->
                      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=tbc.uncagedmist.allgameswallpapers"))))
              .OnNegativeClicked(() -> {
                 moveTaskToBack(true);
                 android.os.Process.killProcess(android.os.Process.myPid());
                 System.exit(1);
              })
              .build();
   }

   private void loadFragment(Fragment fragment) {
      if (fragment != null) {
         getSupportFragmentManager().beginTransaction()
                 .replace(R.id.main_frame, fragment)
                 .commit();
      }
   }
}