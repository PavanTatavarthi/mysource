package com.sbk;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class UnlockReceiver extends BroadcastReceiver {
	public final String LOGTAG = "UnlockReceiver";
	public static int count = 0;
	private boolean screenOff;
	private Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction();
		mContext = context;
		if (action.equals(Intent.ACTION_SCREEN_ON)) {
			screenOff = false;
			logd("Intent.ACTION_SCREEN_ON");
		} else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
			screenOff = true;
			logd("Intent.ACTION_SCREEN_OFF");
			killbackGroundApplications();
		} else if (action.equals(Intent.ACTION_USER_PRESENT)) {
			logd("Intent.ACTION_USER_PRESENT" + ++count);
			//resetCountHourly();
			setmywall();
			saveCounterToPreferences(count);
		}
		Toast.makeText(context,
				"Unlock count :" + loadCounterPreferences(),
				Toast.LENGTH_SHORT).show();
	}

	private void resetCountHourly(){
		if(getCurrentHourOfDay() > loadHourlyPreferences()){
			count = 0;
		}
	}
	
	private void killbackGroundApplications() {
		List<ApplicationInfo> packages;
		PackageManager pm;
		pm = mContext.getPackageManager();
		// get a list of installed apps.
		packages = pm.getInstalledApplications(0);

		ActivityManager mActivityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);

		for (ApplicationInfo packageInfo : packages) {
			if (packageInfo.packageName.equals("com.example.test2"))
			{
				logd("killing com.example.test2 ..");
			mActivityManager.killBackgroundProcesses("com.example.test2");
			
			//mActivityManager.restartPackage ("com.example.test2");
			logd("killed com.example.test2");}
		}}
	
	private void setmywall(){
		WallpaperManager m= WallpaperManager.getInstance(mContext);

		//String s=Environment.getExternalStorageDirectory().getAbsolutePath()+"/1.jpg";
		AssetManager am=mContext.getAssets();
		//File f=new File("file://android_asset/bhuvan.jpg");
		//logd("bhuvan file"+String.valueOf(f.exists()));
		try {
		        //InputStream is=new BufferedInputStream(new FileInputStream(f));
		        //m.setBitmap(BitmapFactory.decodeStream(am.open("/bhuvan.jpg")));
				int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
				if(1 >= hour && hour<= 12){
					m.setResource(R.drawable.bhuvan);
				}
				logd("wall set");
		    } catch (FileNotFoundException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		        Log.e("File", e.getMessage());
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		        Log.e("IO", e.getMessage());
		    }
	}
	private void saveCounterToPreferences(int value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		Editor editor = sharedPreferences.edit();
		editor.putInt("cnt", value);
		editor.commit();
	}
	
	private int loadCounterPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		int count = sharedPreferences.getInt("cnt", -1);
		return count;
	}
	
	private int loadHourlyPreferences() {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(mContext);
		int count = sharedPreferences.getInt("hur", -1);
		return count;
	}
	
	public int getCurrentHourOfDay(){
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.MINUTE);
		return hour;
	}
	private void logd(String message) {
		Log.d(LOGTAG, message);
	}


	public int getCount() {
		return count;
	}
}