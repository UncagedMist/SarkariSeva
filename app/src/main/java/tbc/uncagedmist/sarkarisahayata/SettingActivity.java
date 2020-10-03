package tbc.uncagedmist.sarkarisahayata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import am.appwise.components.ni.NoInternetDialog;

public class SettingActivity extends AppCompatActivity {

  NoInternetDialog noInternetDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);

    noInternetDialog = new NoInternetDialog.Builder(SettingActivity.this).build();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    noInternetDialog.onDestroy();
  }
}