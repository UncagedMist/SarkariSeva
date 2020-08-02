package tbc.uncagedmist.sarkariseva.Common;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import tbc.uncagedmist.sarkariseva.MainActivity;
import tbc.uncagedmist.sarkariseva.Service.SarkariNotificationOpenedHandler;
import tbc.uncagedmist.sarkariseva.Service.SarkariNotificationReceivedHandler;

public class MyApplicationClass extends Application {

  private static Context context;

  public static Context getContext() {
    return context;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
    MobileAds.initialize(this, "ca-app-pub-7920815986886474~5642992812");

    OneSignal.startInit(this)
            .setNotificationReceivedHandler(new SarkariNotificationReceivedHandler())
            .setNotificationOpenedHandler(new SarkariNotificationOpenedHandler())
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init();
  }
}