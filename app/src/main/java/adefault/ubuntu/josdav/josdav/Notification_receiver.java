package adefault.ubuntu.josdav.josdav;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

/**
 * Created by android on 6/24/2017.
 */

public class Notification_receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent repeating_intent=new Intent(context,GoogleSignInActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder= (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent).
                setSmallIcon(android.R.drawable.ic_menu_agenda)
                .setContentTitle("Daily Data Input Reminder")
                .setContentText("Have you put in your data today?")
                .setAutoCancel(true);
        builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        notificationManager.notify(100,builder.build());
    }
}
