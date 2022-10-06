package com.example.tayapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.example.tayapp.R
import com.example.tayapp.presentation.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseCloudMessage : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        //fcm내부 data layer확인
        if (message.data.isNotEmpty()) {
            Log.d("ㅁㄴㅇㄹ", "Message data payload: ${message.data}")
        }

        //안에 notification(notification, data할 때 notification)있냐?
        message.notification?.let {
            Log.d("ㅁㄴㅇㄹ", "Message Notification Body: ${it.body}")
        }

        //알림 생성
        sendNotification(message.data)
    }

    override fun onNewToken(token: String) {
        Log.d("ㅁㄴㅇㄹ","token : ${token}")
        createChannel()
    }

    private fun sendNotification(messageBody: Map<String, String>) {


        //딥링크 연결해줘용
        val taskDetailIntent = Intent(
            Intent.ACTION_VIEW,
            ("https://todayeouido/" + messageBody["id"]).toUri(),
            this,
            MainActivity::class.java
        )

        val requestCode = System.currentTimeMillis().toInt()
        val pendingIntent: PendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(taskDetailIntent)
                getPendingIntent(requestCode, PendingIntent.FLAG_MUTABLE)
            }
        } else {
            TaskStackBuilder.create(this).run {
                addNextIntentWithParentStack(taskDetailIntent)
                getPendingIntent(requestCode, PendingIntent.FLAG_CANCEL_CURRENT)
            }
        }


        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //여기서 채널이랑 연결한다 서버에서 messageBody["channel"]에 넣어주면 됨
        val notificationBuilder = NotificationCompat.Builder(this, messageBody["channel"]!!)
            .setSmallIcon(R.drawable.ic_tay_ch_emoji_gray_sleep)
            .setContentTitle(messageBody["channel"])
            .setContentText(messageBody["body"])
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        //알람 보내기
        notificationManager.notify(0, notificationBuilder.build())
    }
    fun createChannel() {

        //안드로이드 오레오 알림채널이 필요하기 때문에 넣음.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(
                "channel1",
                "channel1",
                NotificationManager.IMPORTANCE_HIGH)
            val channel2 = NotificationChannel(
                "channel2",
                "channel2",
                NotificationManager.IMPORTANCE_HIGH)

            // 초기 알림 셋팅은 여기서 하면 되는듯
            //만약 셋팅을 변경했으면 앱 지우고 다시 깔아야함
            channel1.setShowBadge(false)
            channel1.enableVibration(true)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)
        }

    }
}