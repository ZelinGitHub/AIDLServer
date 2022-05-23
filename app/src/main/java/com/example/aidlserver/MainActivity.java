package com.example.aidlserver;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;

import com.example.aidlserver.broadcastreceiver.MyBroadcastReceiver;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.aidlserver.cons.Const.SERVER_BROADCAST_ACTION;

public class MainActivity extends AppCompatActivity {

    private final MyBroadcastReceiver mBroadcastReceiver = new MyBroadcastReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerBroadcastReceiver(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceiver(this);
    }

    private void registerBroadcastReceiver(Context pContext) {
        System.out.println("服务端 注册广播接收者");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SERVER_BROADCAST_ACTION);
        pContext.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private void unregisterBroadcastReceiver(Context pContext) {
        System.out.println("服务端 注销广播接收者");
        pContext.unregisterReceiver(mBroadcastReceiver);
    }
}