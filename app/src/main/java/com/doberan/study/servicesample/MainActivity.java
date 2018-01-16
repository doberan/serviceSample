package com.doberan.study.servicesample;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.doberan.study.servicesample.service.SampleService;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView)findViewById(R.id.hello_text);
        text.setClickable(true);

        if(isActiveService("SampleService")) {
            text.setText("Stop Service");
        }else{
            text.setText("Start Service");
        }
        text.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(text.getText().equals("Start Service")) {
                startService(new Intent(MainActivity.this, SampleService.class));
                text.setText("Stop Service");
            }else{
                stopService(new Intent(MainActivity.this, SampleService.class));
                text.setText("Start Service");
            }
        }
    };

    public boolean isActiveService(String serviceClassName)
    {
        ActivityManager activityManager = (ActivityManager) this.getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServicesInfo = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServicesInfo)
        {
            if (runningServiceInfo.service.getClassName().equals(serviceClassName))
            {
                return true;
            }
        }

        return false;
    }
}
