package com.doberan.study.servicesample.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.doberan.study.servicesample.MainActivity;
import com.doberan.study.servicesample.ReceiveActivity;

import java.util.Calendar;

public class SampleService extends Service {
    private String TAG = SampleService.class.getCanonicalName();
    private Intent intent;
    private PendingIntent pendingIntent;
    private Calendar calendar;
    public SampleService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        // 時間をセットする
        Calendar calendar = Calendar.getInstance();
        // Calendarを使って現在の時間をミリ秒で取得
        calendar.setTimeInMillis(System.currentTimeMillis());
        // 5秒後に設定
        calendar.add(Calendar.SECOND, 5);

        //明示的なBroadCast
        Intent newintent = new Intent(getApplicationContext(),
                TestService.class);
        PendingIntent pending = PendingIntent.getBroadcast(
                getApplicationContext(), 0, newintent, 0);

        // アラームをセットする
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        if(am != null) {
            am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
        }




//        createIntent();
//        createPendingIntent();
//        createCalendar();
//        calendar.setTimeInMillis(System.currentTimeMillis()); // 現在時刻を取得
//        calendar.add(Calendar.SECOND, 5); // 現時刻より15秒後を設定
//
//        Intent indent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
//// Broadcast にメッセージを送るための設定
//        PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), 0, indent, 0);
//
//        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE); // AlramManager取得
//        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending); // AlramManagerにPendingIntentを登録

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemove");
    }


    public void createIntent(){
        intent = new Intent(getApplicationContext(), TestService.class); // ReceivedActivityを呼び出すインテントを作成
    }

    public void createPendingIntent(){
        if(intent == null){
            throw new NullPointerException("intentがぬるぽです");
        }
        pendingIntent = PendingIntent.getBroadcast(SampleService.this, 0, intent, 0); // ブロードキャストを投げるPendingIntentの作成

    }
    public void createCalendar(){
        calendar = Calendar.getInstance(); // Calendar取得
    }
}
