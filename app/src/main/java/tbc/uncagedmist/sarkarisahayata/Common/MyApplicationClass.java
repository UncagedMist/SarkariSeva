package tbc.uncagedmist.sarkarisahayata.Common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.onesignal.OneSignal;

import tbc.uncagedmist.sarkarisahayata.AdUtility.AppOpenManager;
import tbc.uncagedmist.sarkarisahayata.Service.NetworkStatusReceiver;
import tbc.uncagedmist.sarkarisahayata.Service.SarkariNotificationOpenedHandler;
import tbc.uncagedmist.sarkarisahayata.Service.SarkariNotificationReceivedHandler;

public class MyApplicationClass extends Application {

  private static Context context;

  private static AppOpenManager appOpenManager;

  public static Context getContext() {
    return context;
  }

  public static Activity mActivity;
  NetworkStatusReceiver mNetworkReceiver;

  @Override
  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
    MobileAds.initialize(this, "ca-app-pub-5860770870597755~3016203128");

    MobileAds.initialize(
            this,
            new OnInitializationCompleteListener() {
              @Override
              public void onInitializationComplete(InitializationStatus initializationStatus) {}
            });

    appOpenManager = new AppOpenManager(this);

    OneSignal.startInit(this)
            .setNotificationReceivedHandler(new SarkariNotificationReceivedHandler())
            .setNotificationOpenedHandler(new SarkariNotificationOpenedHandler())
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init();

    registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
      @Override
      public void onActivityCreated(Activity activity, Bundle bundle) {
        mNetworkReceiver = new NetworkStatusReceiver();
      }

      @Override
      public void onActivityStarted(Activity activity) {
        mActivity = activity;
      }

      @Override
      public void onActivityResumed(Activity activity) {
        mActivity = activity;
        registerNetworkBroadcastForLollipop();
      }

      @Override
      public void onActivityPaused(Activity activity) {
        mActivity = null;
        unregisterReceiver(mNetworkReceiver);
      }

      @Override
      public void onActivityStopped(Activity activity) {
      }

      @Override
      public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
      }

      @Override
      public void onActivityDestroyed(Activity activity) {
      }
    });
  }

  private void registerNetworkBroadcastForLollipop() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
  }
}