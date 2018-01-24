package com.doberan.study.servicesample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

/*
* This software is contributed or developed by KYOCERA Corporation.
* (C) 2018 KYOCERA Corporation
*/
public class NotificationUtil extends BroadcastReceiver {
    private static NotificationManager notificationManager = null;
    private static NotificationChannel notificationChannel = null;
    private static Notification notification = null;

    public static void updateSendNotification(){
        notificationManager = (NotificationManager) ApplicationController.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        setNotificationChannel(
                NotificationConstants.UPDATE_CHANNEL_ID,
                NotificationConstants.UPDATE_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
        );
        setLightColor(Color.BLUE);
        String[] message = {NotificationConstants.NOTIFICATION_MESSAGE_DUMMY1, NotificationConstants.NOTIFICATION_MESSAGE_DUMMY2};
        setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        boolean flag = createNotificationChannel();
        if(!flag){
            Toast.makeText(ApplicationController.getInstance().getApplicationContext(),"NotificationManager is null",Toast.LENGTH_SHORT).show();
        }
        setNotification(
                ApplicationController.getInstance(),
                NotificationConstants.NOTIFICATION_TITLE,
                message,
                R.drawable.ic_launcher_background
        );
        send();
    }

    /**
     * チャンネルを設定する
     * @param channelId
     * @param channelName
     * @param importance
     */
    private static void setNotificationChannel(String channelId, String channelName, int importance){
        notificationChannel = new NotificationChannel(
                "CHANNEL_ID",
                "CHANNEL_NAME",
                NotificationManager.IMPORTANCE_DEFAULT
        );
    }

    /**
     * 通知が来た際のLEDのカラー設定
     * @param color
     */
    private static void setLightColor(int color){
        notificationChannel.setLightColor(color);
    }

    /**
     * ロック画面でも通知を送るかの設定
     * @param lockscreenVisibility
     */
    private static void setLockscreenVisibility(int lockscreenVisibility){
        notificationChannel.setLockscreenVisibility(lockscreenVisibility);
    }

    /**
     * チャンネルを作成する
     * NotificationManagerかNotificationChannelがnullだった場合 falseを返す
     * @return
     */
    private static boolean createNotificationChannel(){
        if(notificationManager == null || notificationChannel == null){
            return false;
        }
        notificationManager.createNotificationChannel(notificationChannel);
        return true;
    }

    /**
     * 通知を設定する
     * @param context
     * @param title
     * @param message
     * @param icon
     * @return
     */
    private static boolean setNotification(Context context, String title, String[] message, int icon) {
        if(notificationChannel == null){
            return false;
        }
        try {
            notification = new Notification.Builder(context.getApplicationContext(), notificationChannel.getId())
                    .setContentTitle(title)
                    .setContentText(message[0])
                    .setSubText(message[1])
                    .setSmallIcon(icon)
                    .setChannelId(notificationChannel.getId())
                    .build();
        }catch (NullPointerException e){
            e.getStackTrace();
            return false;
        }catch (Exception e){
            e.getStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 通知を送信する
     * @return
     */
    private static boolean send(){
        if(notificationManager == null || notification == null){
            return false;
        }
        notificationManager.notify(1,notification);
        return true;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("NotificationUtil", "onreceive!");
        Toast.makeText(context.getApplicationContext(),"onReceiveです", Toast.LENGTH_SHORT).show();
        updateSendNotification();
    }
}
