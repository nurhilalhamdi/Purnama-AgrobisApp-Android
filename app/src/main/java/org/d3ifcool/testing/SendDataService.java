package org.d3ifcool.testing;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SendDataService extends Service {

    private final static String TAG = "BroadcastService";

    public static final String COUNTDOWN_BR = "org.d3ifcool.testing.countdown_br";
    Intent bi = new Intent(COUNTDOWN_BR);

//    CountDownTimer cdt = null;

    private final LocalBinder mBinder = new LocalBinder();
    protected Handler handler;
    protected Toast mToast;

        public class LocalBinder extends Binder {
        public SendDataService getService() {
            return SendDataService .this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        Log.i(TAG, "Starting timer...");
//
//        cdt = new CountDownTimer(30000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//
//                Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
//                bi.putExtra("countdown", millisUntilFinished);
//                sendBroadcast(bi);
//            }
//
//            @Override
//            public void onFinish() {
//                Log.i(TAG, "Timer finished");
//            }
//        };
//
//        cdt.start();
    }

    @Override
    public void onDestroy() {
//
//        cdt.cancel();
//        Log.i(TAG, "Timer cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
                handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                Log.d("bbb", "run: " + new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date()));

                bi.putExtra("timer", new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                sendBroadcast(bi);
                handler.postDelayed(this, 1000);
            }
        }, 10);

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

//    private final LocalBinder mBinder = new LocalBinder();
//    protected Handler handler;
//    protected Toast mToast;
//
//
//    public static final String COUNTDOWN_BR = "org.d3ifcool.testing.countdown_br";
//    Intent bi = new Intent(COUNTDOWN_BR);
//    public class LocalBinder extends Binder {
//        public SendDataService getService() {
//            return SendDataService .this;
//        }
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return mBinder;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        handler = new Handler(Looper.getMainLooper());
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//                Log.d("bbb", "run: " + new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date()));
//
//                bi.putExtra("timer", new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date()));
//                sendBroadcast(bi);
//                handler.postDelayed(this, 1000);
//            }
//        }, 10);
//
//        return android.app.Service.START_STICKY;
//    }
}
