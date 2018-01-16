package com.doberan.study.servicesample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by doberanmac on 2018/01/17.
 */

public class ReceiveActivity extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "called ReceivedActivity", Toast.LENGTH_SHORT).show();
    }
}
