package com.doberan.study.servicesample;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.doberan.study.servicesample.service.SampleService;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    private Messenger messenger;
    private TextView text;
    private TextView sendText;
    private TextView sendNotice;
    private SampleService sampleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.hello_text);
        sendText = (TextView) findViewById(R.id.sendText);
        sendText.setText("send message");
        sendText.setVisibility(View.INVISIBLE);
        sendText.setClickable(true);
        sendText.setOnClickListener(onSendClickListener);
        sendNotice = (TextView) findViewById(R.id.send_notification);
        sendNotice.setText("send notification");
        sendNotice.setVisibility(View.INVISIBLE);
        sendNotice.setClickable(true);
        sendNotice.setOnClickListener(onSendClickListener);
        text.setClickable(true);
        text.setText("Start Service");
        text.setOnClickListener(onBindClickListener);
        sampleService = new SampleService();
    }

    View.OnClickListener onBindClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "ボタンが押下されました", Toast.LENGTH_SHORT).show();
            if (text.getText().equals("Start Service")) {
                sendText.setVisibility(View.VISIBLE);
                sendNotice.setVisibility(View.VISIBLE);
                connect();
            } else {
                disConnected();
            }
        }
    };

    View.OnClickListener onSendClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sendText:
                    sendText();
                    break;
                case R.id.send_notification:
                    sendNotification();
                    break;
            }
        }
    };

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Toast.makeText(getApplicationContext(), "アクティビティがサービスに接続しました", Toast.LENGTH_SHORT).show();
        messenger = new Messenger(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Toast.makeText(getApplicationContext(), "アクティビティがサービスを切断しました", Toast.LENGTH_SHORT).show();
        messenger = null;
    }

    public void connect() {
        bindService(new Intent(getApplicationContext(), sampleService.getClass()), this, Context.BIND_AUTO_CREATE);
        text.setText("Stop Service");
    }

    public void disConnected() {
        unbindService(this);
        text.setText("Start Service");
        sendNotice.setVisibility(View.INVISIBLE);
        sendText.setVisibility(View.INVISIBLE);
    }

    /**
     * メッセージの送信
     *
     * @param
     */
    public void sendText() {
        try {
            // メッセージの送信
            Toast.makeText(getApplicationContext(), "MainのsendText()。", Toast.LENGTH_SHORT).show();
            messenger.send(Message.obtain(null, SampleService.SEND_TEST, "message test"));
        } catch (RemoteException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "メッセージの送信に失敗しました。", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * メッセージの送信
     *
     * @param
     */
    public void sendNotification() {
        try {
            Toast.makeText(getApplicationContext(), "MainのsendNotification()。", Toast.LENGTH_SHORT).show();
            // メッセージの送信
            messenger.send(Message.obtain(null, SampleService.SEND_NOTIFICATION));
        } catch (RemoteException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "メッセージの送信に失敗しました。", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "MainのonDestroyです", Toast.LENGTH_SHORT).show();
        disConnected();
    }
}
