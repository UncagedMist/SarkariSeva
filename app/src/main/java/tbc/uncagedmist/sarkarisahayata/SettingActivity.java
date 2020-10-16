package tbc.uncagedmist.sarkarisahayata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

import am.appwise.components.ni.NoInternetDialog;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

  NoInternetDialog noInternetDialog;

  private ResideMenu resideMenu;
  private ResideMenuItem itemHome;
  private ResideMenuItem itemAbout;
  private ResideMenuItem itemPrivacy;
  private ResideMenuItem itemSettings;

  FloatingActionButton fabSetting;

  AdView settingAboveBanner, settingBottomBanner;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);

    noInternetDialog = new NoInternetDialog.Builder(SettingActivity.this).build();

    fabSetting = findViewById(R.id.settingShare);
    TextView txtTitle = findViewById(R.id.txtTitle);

    settingAboveBanner = findViewById(R.id.settingAboveBanner);
    settingBottomBanner = findViewById(R.id.settingBottomBanner);

    txtTitle.setText("Setting");

    AdRequest adRequest = new AdRequest.Builder().build();

    settingAboveBanner.loadAd(adRequest);
    settingBottomBanner.loadAd(adRequest);

    setUpResideMenu();

    fabSetting.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String message = "Never Miss A Sarkari Update. Install Sarkari Sahayata and Stay Updated! \n https://play.google.com/store/apps/details?id=tbc.uncagedmist.sarkarisahayata";
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "Share Sarkari Sahayata Using"));
      }
    });

    settingAboveBanner.setAdListener(new AdListener() {
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
      public void onAdLeftApplication() {
        // Code to be executed when the user has left the app.
      }

      @Override
      public void onAdClosed() {
        // Code to be executed when the user is about to return
        // to the app after tapping on an ad.
      }
    });

    settingBottomBanner.setAdListener(new AdListener() {
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
      public void onAdLeftApplication() {
        // Code to be executed when the user has left the app.
      }

      @Override
      public void onAdClosed() {
        // Code to be executed when the user is about to return
        // to the app after tapping on an ad.
      }
    });
  }

  private void setUpResideMenu() {

    resideMenu = new ResideMenu(this);
//        resideMenu.setUse3D(true);
    resideMenu.setBackground(R.drawable.menu_background);
    resideMenu.attachToActivity(this);
    resideMenu.setMenuListener(menuListener);

    resideMenu.setScaleValue(0.6f);

    itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "Home");
    itemAbout  = new ResideMenuItem(this, R.drawable.icon_profile,  "About");
    itemPrivacy = new ResideMenuItem(this, R.drawable.icon_profile, "Privacy");
    itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "Settings");

    itemHome.setOnClickListener(this);
    itemAbout.setOnClickListener(this);
    itemPrivacy.setOnClickListener(this);
    itemSettings.setOnClickListener(this);

    resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
    resideMenu.addMenuItem(itemAbout, ResideMenu.DIRECTION_LEFT);
    resideMenu.addMenuItem(itemPrivacy, ResideMenu.DIRECTION_RIGHT);
    resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);

    findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
      }
    });
    findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
      }
    });
  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    return resideMenu.dispatchTouchEvent(ev);
  }

  @Override
  public void onClick(View view) {

    if (view == itemHome){
    }
    else if (view == itemAbout){
      startActivity(new Intent(SettingActivity.this,AboutActivity.class));
    }
    else if (view == itemPrivacy){
      startActivity(new Intent(SettingActivity.this,PrivacyActivity.class));

    }
    else if (view == itemSettings){
    }

    resideMenu.closeMenu();
  }

  private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
    @Override
    public void openMenu() {
    }

    @Override
    public void closeMenu() {
    }
  };

  @Override
  public void onDestroy() {
    super.onDestroy();
    noInternetDialog.onDestroy();
  }
}