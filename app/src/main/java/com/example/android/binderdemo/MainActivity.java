package com.example.android.binderdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    MyService myService;
    boolean isBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding TextView to TextView object
        mTextView = findViewById(R.id.tv_message);

        //Intent retrieving MyService activity from this context (MainActivity.class)
        Intent intent = new Intent(this, MyService.class);

        //binds service to this activity/component
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    public void getFirstServiceMessage(View view) {

        String message;
        message = myService.getFirstMessage();
        mTextView.setText(message);

    }

    public void getSecondServiceMessage(View view) {

        String message;
        message = myService.getSecondMessage();
        mTextView.setText(message);

    }

    //ServiceConnection retrieves Ibinder object
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            //linking LocalService class in MyService.class to MainActivity
            MyService.LocalService localService = (MyService.LocalService)service;
            myService = localService.getService();
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            isBind = false;

        }
    };

    //unbind after use
    @Override
    protected void onStop() {
        super.onStop();

        if (isBind){

            unbindService(serviceConnection);
            isBind = false;

        }

    }
}
