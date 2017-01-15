package project.recsound.common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import project.recsound.model.StaticData;

/**
 * Created by susy on 4/01/17.
 */

public class RecService extends Service {

    private String TAG="SERVICE";

    boolean recording = false;
    int touchPower;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {

            int TOUCH_POWER = Integer.parseInt(Preferences.getSettingsNumberPower(context));
            int WAIT_TO_RESTART = 2000;

            switch (TOUCH_POWER){
                case 2:
                    WAIT_TO_RESTART = 200;
                    break;
                case 3:
                    WAIT_TO_RESTART = 750;
                    break;
                case 4:
                    WAIT_TO_RESTART = 1200;
                    break;
                case 5:
                    WAIT_TO_RESTART = 1500;
                    break;
            }


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    touchPower = 0;
                }
            },WAIT_TO_RESTART);

            if(intent.getAction().equals(intent.ACTION_SCREEN_OFF)){
                touchPower++;
            }

            if(intent.getAction().equals(intent.ACTION_SCREEN_ON)){
                touchPower++;
            }

            if(touchPower == TOUCH_POWER ){
                if(recording){
                    //Stop
                    stop(context);
                }else{
                    //Start
                    start(context);

                    int SETTINGS_TIME = Integer.parseInt(Preferences.getSettingsNumberTime(context));

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(recording) stop(context);
                        }
                    },SETTINGS_TIME*60000);

                }

                touchPower = 0;
            }

        }
    };

    public void Notification(Context context, String title, String message){
        Notification n  = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(android.support.design.R.drawable.navigation_empty_icon).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);
    }

    MediaRecorder recorder;
    public void start(Context context){

        Vibrator vibrator=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

        if(Preferences.getSettingsVibrate(context))vibrator.vibrate(250);
        if(Preferences.getSettingsNotification(context))Notification(context,"","Grabación iniciada");
        recording = true;

        File folder;
        String file;

        folder = new File(StaticData.getPath_folder());
        Files.getFilesList(folder.getPath());

        String nummbeFile = String.valueOf(folder.list().length);

        Calendar c = Calendar.getInstance();
        Date date = c.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat( "d MMMM yy"  , Locale.getDefault());
        file = "Audio "+nummbeFile+">"+dateFormat.format(date)+">.mp3";

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(folder.getPath()+"/"+file);


        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }

        recorder.start();

    }

    public void stop(Context context){

        Vibrator vibrator=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

        if(Preferences.getSettingsVibrate(context))vibrator.vibrate(700);
        if(Preferences.getSettingsNotification(context))Notification(context,"","Grabación parada");
        recording = false;

        recorder.stop();
        recorder.release();
        recorder = null;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"Service created");

        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(TAG,"Service destroyed");
        unregisterReceiver(broadcastReceiver);

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
