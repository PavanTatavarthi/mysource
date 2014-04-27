package com.sbk;

import java.util.Calendar;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class UnlockCountService extends Service {
	private final IBinder mBinder = new MyBinder();
	
	Context context;
	public UnlockReceiver receiver;
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		context = getApplicationContext();
		UnlockReceiver receiver = new UnlockReceiver();
		registerReceiver(receiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
		registerReceiver(receiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
		registerReceiver(receiver, new IntentFilter(Intent.ACTION_USER_PRESENT));
		
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO for communication return IBinder implementation
		return mBinder;
	}
		
	public void saveHourToPreferences(int value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putInt("hur", value);
		editor.commit();
	}
	
	public int getCurrentHourOfDay(){
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.MINUTE);
		return hour;
	}
	

	public class MyBinder extends Binder {
		UnlockCountService getService() {
			return UnlockCountService.this;
		}
	}
}