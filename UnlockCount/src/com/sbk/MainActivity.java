package com.sbk;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	private UnlockCountService s;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// use this to start and trigger a service
		Intent i= new Intent(this, UnlockCountService.class);
		this.startService(i); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	  @Override
	  protected void onResume() {
	    super.onResume();
	/*    Intent intent= new Intent(this, UnlockCountService.class);
	    bindService(intent, mConnection,
	        Context.BIND_AUTO_CREATE);*/
	  }
	  
/*	  private ServiceConnection mConnection = new ServiceConnection() {

	    public void onServiceConnected(ComponentName className, 
	        IBinder binder) {
	      UnlockCountService.MyBinder b = (UnlockCountService.MyBinder) binder;
	      s = b.getService();
	      s.saveHourToPreferences(s.getCurrentHourOfDay());
	      Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT)
	          .show();
	    }

	    public void onServiceDisconnected(ComponentName className) {
	      s = null;
	    }
	  };*/
}
