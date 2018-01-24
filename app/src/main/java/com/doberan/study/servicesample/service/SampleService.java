package com.doberan.study.servicesample.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import com.doberan.study.servicesample.ApplicationController;
import com.doberan.study.servicesample.NotificationUtil;

import java.util.Calendar;

public class SampleService extends Service{
    private String TAG = SampleService.class.getCanonicalName();
    private Messenger messenger;

    public static final int SEND_TEST = 0;
    public static final int SEND_NOTIFICATION = 1;


    static class TestHandler extends Handler{
        private Context context;
        public TestHandler(Context context){
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SEND_TEST:
                    Toast.makeText(context,(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
                case SEND_NOTIFICATION:
                    Toast.makeText(context,"send_notification handle",Toast.LENGTH_SHORT).show();
                    sendNotification();
                    break;
                default:
                    Toast.makeText(this.context, "サービス側でメッセージを受信しました", Toast.LENGTH_SHORT).show();
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "サービスのonCreateです", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate");
        messenger = new Messenger(new TestHandler(getApplicationContext()));
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "サービスのonBindです", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onBind");
        return messenger.getBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "サービスのonDestroyです", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Toast.makeText(getApplicationContext(), "サービスのonRebindです", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onRebind");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Toast.makeText(getApplicationContext(), "サービスのonTaskRemovedです", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onTaskRemove");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(getApplicationContext(), "サービスのonUnBindです", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    public static void sendNotification(){
        Toast.makeText(ApplicationController.getInstance().getApplicationContext(), "サービスのonStartCommandです", Toast.LENGTH_SHORT).show();
        Log.d("SampleService", "sneNotification");
        // 時間をセットする
        Calendar calendar = Calendar.getInstance();
        // Calendarを使って現在の時間をミリ秒で取得
        calendar.setTimeInMillis(System.currentTimeMillis());
        // 5秒後に設定
        calendar.add(Calendar.SECOND, 5);
        //明示的なBroadCast
        Intent newintent = new Intent(ApplicationController.getInstance().getApplicationContext(),
                NotificationUtil.class);
        PendingIntent pending = PendingIntent.getBroadcast(
                ApplicationController.getInstance().getApplicationContext(), 0, newintent, 0);

        // アラームをセットする
        AlarmManager am = (AlarmManager) ApplicationController.getInstance().getSystemService(ALARM_SERVICE);
        if(am != null) {
            am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
        }
    }
}
