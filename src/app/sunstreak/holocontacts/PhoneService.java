package app.sunstreak.holocontacts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PhoneService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		return Service.START_STICKY;
	}

}
