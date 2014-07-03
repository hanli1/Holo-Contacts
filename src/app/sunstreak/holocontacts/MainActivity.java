package app.sunstreak.holocontacts;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		public int NUM_CONTACTS = 100;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
//			String[] data = getCallDetails();
//			TextView x = new TextView(getActivity());
//			for(int i = 0; i< NUM_CONTACTS; i++)
//			{
//				x.append(data[i]);
//			}
//			((ViewGroup) rootView).addView(x);
			return rootView;
		}
//		private String[] getCallDetails() {
//			String[] data = new String[NUM_CONTACTS];
//			String[] strFields = {
//			        android.provider.CallLog.Calls.NUMBER, 
//			        android.provider.CallLog.Calls.TYPE,
//			        android.provider.CallLog.Calls.CACHED_NAME,
//			        android.provider.CallLog.Calls.CACHED_NUMBER_TYPE
//			        };
//			String strOrder = android.provider.CallLog.Calls.DATE + " DESC"; 
//			 
//			Cursor managedCursor = getActivity().getContentResolver().query(
//			        android.provider.CallLog.Calls.CONTENT_URI,
//			        strFields,
//			        null,
//			        null,
//			        strOrder
//			        );
//	        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
//	        int name= managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
//	        int count = 0;
//	        managedCursor.moveToFirst();
//	        
//	        do
//	        {
//	            String phNumber = managedCursor.getString(number);
//	            String contactName = managedCursor.getString(name);
//	            if(contactName== null)
//	            	contactName = "Unknown";
//	            	data[count] = contactName;
//	            	count++;
//	            System.out.println("name: " + contactName + " number: " + phNumber);
//	        }while (count<NUM_CONTACTS && managedCursor.moveToNext());
//	        for(int i = 0; i< NUM_CONTACTS; i++)
//	        {
//	        	System.out.println(data[i]);
//	        }
//	        managedCursor.close();
//	        return data;
//	    }
	}

}
