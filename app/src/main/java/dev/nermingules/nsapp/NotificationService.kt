package dev.nermingules.nsapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationService : FirebaseMessagingService(){

   override fun onMessageReceived(message: RemoteMessage) {
      super.onMessageReceived(message)

      message.notification?.let {
         val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

         if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
              val channel = NotificationChannel(packageName,"KANAL ADI",NotificationManager.IMPORTANCE_DEFAULT).apply {  }
              notificationManager.createNotificationChannel(channel)
         }

         val builder = NotificationCompat.Builder(this,packageName)
              .setSmallIcon(R.drawable.ic_launcher_foreground)
              .setContentTitle(it.title)
              .setContentText(it.body)
              .setPriority(NotificationCompat.PRIORITY_DEFAULT)
         notificationManager.notify(0,builder.build())

          val notificationIntent = Intent(this,MainActivity::class.java)
          notificationIntent.putExtra("deger","Gönderilen değer")

          val pendingIntent = PendingIntent.getActivity(
              this,
              0,
              notificationIntent,
              PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
          )
          builder.setContentIntent(pendingIntent)

          notificationManager.notify(0,builder.build())
      }
   }
}