package com.doberan.study.servicesample.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.doberan.study.servicesample.MainActivity;

import java.util.Calendar;

public class SampleService extends Service {
    private String TAG = SampleService.class.getCanonicalName();
    public SampleService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onBind");

        Intent i = new Intent(getApplicationContext(), MainActivity.class); // ReceivedActivityを呼び出すインテントを作成
        PendingIntent sender = PendingIntent.getBroadcast(SampleService.this, 0, i, 0); // ブロードキャストを投げるPendingIntentの作成

        Calendar calendar = Calendar.getInstance(); // Calendar取得
        calendar.setTimeInMillis(System.currentTimeMillis()); // 現在時刻を取得
        calendar.add(Calendar.SECOND, 15); // 現時刻より15秒後を設定

        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE); // AlramManager取得
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender); // AlramManagerにPendingIntentを登録
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG, "onRebind");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.i(TAG, "onTaskRemove");
    }
}
