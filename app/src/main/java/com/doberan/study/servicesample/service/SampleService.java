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
        Log.i(TAG, "onBind");

        createIntent();
        createPendingIntent();
        createCalendar();
        calendar.setTimeInMillis(System.currentTimeMillis()); // 現在時刻を取得
        calendar.add(Calendar.SECOND, 15); // 現時刻より15秒後を設定

        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE); // AlramManager取得
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent); // AlramManagerにPendingIntentを登録
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
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
        intent = new Intent(getApplicationContext(), ReceiveActivity.class); // ReceivedActivityを呼び出すインテントを作成
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
