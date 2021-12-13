
package tbc.uncagedmist.sarkarisahayata.Common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import tbc.uncagedmist.sarkarisahayata.R;

public class Common {

  public static String CurrentStateId;
  public static String CurrentStateName;
  public static String CurrentStateUrl;

  public static boolean isConnectedToInternet(Context context)    {

    ConnectivityManager connectivityManager = (
            ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

    if (connectivityManager != null)    {

      NetworkInfo[] info = connectivityManager.getAllNetworkInfo();

      if (info != null)   {

        for (int i = 0; i <info.length;i++)   {

          if (info[i].getState() == NetworkInfo.State.CONNECTED)  {
            return true;
          }
        }
      }
    }
    return false;
  }
}