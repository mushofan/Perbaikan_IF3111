package com.example.musho.runtimereqtutorial;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    private static int REQ_CAMERA = 0;

    private Button cameraButton;
    private Button alarmButton;
    private Button periodicAlarm;
    private ImageView capturedImage;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraButton = (Button) findViewById(R.id.cameraButton);
        alarmButton = (Button) findViewById(R.id.alarmButton);

        capturedImage = (ImageView) findViewById(R.id.imageView);

        cameraButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                checkCameraPermission();
            }
        });

        alarmButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                alarmActivity();
            }
        });

        Intent periodicIntent = new Intent(this, PeriodicAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, periodicIntent, 0);
        periodicAlarm = (Button) findViewById(R.id.PeriodicAlarm);
        periodicAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAlarm();
            }
        });
    }

    private void startAlarm() {
        Toast.makeText(this, "Periodic Alarm has been set", Toast.LENGTH_SHORT).show();

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 10000;   //10 detik interval

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
    }

    //Check permission
    void checkCameraPermission(){
        //Permission has not been granted
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            //Request permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQ_CAMERA);
        }
        else{
            //Permission has been granted
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permission[], int[] grantResults){
        if(requestCode == REQ_CAMERA){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //Permission granted
                openCamera();
            }
            else{
                //Permission denied

            }
        }
    }

    void openCamera(){
        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(cameraIntent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 0 && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            capturedImage.setImageBitmap(imgBitmap);
        }
    }

    void alarmActivity(){
        Intent alarmIntent = new Intent(this, AlarmActivity.class);
        startActivity(alarmIntent);
    }
}
