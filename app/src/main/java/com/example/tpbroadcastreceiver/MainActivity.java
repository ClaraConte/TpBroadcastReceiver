package com.example.tpbroadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private SmsRecibido sms;
    TextView myTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
    }

    @Override
    protected void onResume() {
       super.onResume();
       sms = new SmsRecibido();
       registerReceiver(sms, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
        if(checkPermission() == 0){
            myTextView = findViewById(R.id.myView);
            myTextView.setText("Bienvenido");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(sms);
        if(checkPermission() == -1){
            myTextView = findViewById(R.id.myView);
            myTextView.setText("No tiene permisos");

        }
    }

    private void getPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},100);
            Toast.makeText(this,"Debe conceder permisos a la aplicación", Toast.LENGTH_LONG).show();
        }
    }

    private int checkPermission(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
    }
}