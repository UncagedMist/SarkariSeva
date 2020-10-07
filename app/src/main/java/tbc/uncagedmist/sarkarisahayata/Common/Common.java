
package tbc.uncagedmist.sarkarisahayata.Common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import tbc.uncagedmist.sarkarisahayata.Model.Detail;
import tbc.uncagedmist.sarkarisahayata.Model.Product;
import tbc.uncagedmist.sarkarisahayata.Model.Service;
import tbc.uncagedmist.sarkarisahayata.R;
import tbc.uncagedmist.sarkarisahayata.Service.MyFCMService;

public class Common {

  public static final String PRIVACY_APP = "https://docs.google.com/document/d/1k69ryqRPhtMfU46oNVqv0aXLw4GZHbbDQkyhJUsQhr4/edit?usp=sharing";
  public static final String TERMS_APP = "https://docs.google.com/document/d/19YZdBTOGUS-TliNEiCbnK_iCPdfDiGGvI0SMTDX94XI/edit?usp=sharing";

  public static final String NOTI_TITLE = "title";
  public static final String NOTI_CONTENT = "content";

  public static Product CurrentProduct;
  public static Service CurrentService;
  public static Detail CurrentDetail;


  public static void showNotification(Context context, int id, String title, String content, Intent intent) {
    PendingIntent pendingIntent = null;

    if (intent != null)
      pendingIntent = PendingIntent.getActivity(context,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);

    String NOTIFICATION_CHANNEL_ID = "sarkari_sahayata";

    NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)   {
      NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
              "Sarkari Sahayata",
              NotificationManager.IMPORTANCE_DEFAULT
      );

      notificationChannel.setDescription("Sarkari Sahayata");
      notificationChannel.enableLights(true);
      notificationChannel.setLightColor(Color.RED);
      notificationChannel.setVibrationPattern(new long[] {0,1000,500,1000});
      notificationChannel.enableVibration(true);

      notificationManager.createNotificationChannel(notificationChannel);

      NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
      builder.setContentTitle(title)
              .setContentText(content)
              .setAutoCancel(true)
              .setSmallIcon(R.drawable.ic_notif)
              .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_notif));

      if (pendingIntent != null)
        builder.setContentIntent(pendingIntent);

      Notification notification = builder.build();
      notificationManager.notify(id,notification);
    }

  }

  public static void updateToken(Context context, String newToken) {
  }
}