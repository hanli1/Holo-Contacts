package app.sunstreak.holocontacts;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.RemoteViews;
import android.widget.Toast;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                TelephonyManager.EXTRA_STATE_RINGING)) {

                // Phone number 
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

                // Ringing state
                // This code will execute when the phone has an incoming call
        }
        else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                TelephonyManager.EXTRA_STATE_IDLE)
                || intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                        TelephonyManager.EXTRA_STATE_OFFHOOK)) {

            // This code will execute when the call is answered or disconnected
    		Intent updateWidget = new Intent(context, WidgetProvider4x1.class);
    		updateWidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
    		// Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
    		// since it seems the onUpdate() is only fired on that:
    		int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, WidgetProvider4x1.class));
    		updateWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
    		context.sendBroadcast(updateWidget);
    		
    		Toast.makeText(context, "CALL ENDED YAY!", 
    				   Toast.LENGTH_LONG).show();
        }

    }
}