package tbc.uncagedmist.sarkarisahayata.Common;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.ads.MobileAds;
import com.onesignal.OneSignal;

import tbc.uncagedmist.sarkarisahayata.Service.SarkariNotificationOpenedHandler;
import tbc.uncagedmist.sarkarisahayata.Service.SarkariNotificationReceivedHandler;

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