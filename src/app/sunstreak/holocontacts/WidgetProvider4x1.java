package app.sunstreak.holocontacts;

import java.io.IOException;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.MediaStore.Images.Media;
import android.widget.RemoteViews;

public class WidgetProvider4x1 extends AppWidgetProvider {
	private Context context;
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		final int N = appWidgetIds.length;
		this.context = context;
		Contact[] data = getCallDetails();
		// Perform this loop procedure for each App Widget that belongs to this provider
		for (int i=0; i<N; i++) {
			int appWidgetId = appWidgetIds[i];
			Intent intent = new Intent(Intent.ACTION_DIAL);   
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// Get the layout for the App Widget and attach an on-click listener to the button
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout_4x1);
			// Arrays for each view to modify on run
			int[] labels = {R.id.textView1,R.id.textView2,R.id.textView3,R.id.textView4};
			int[] pics = {R.id.imageView1,R.id.imageView2,R.id.imageView3,R.id.imageView4};
			
			for(int j = 0; j< data.length; j++)
			{
				if(data[i]!=null)
				{
					// Launch activity on click of contact
					intent.setData(Uri.parse("tel:"+data[j].getPhone())); 
					PendingIntent callIntent = PendingIntent.getActivity(context, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);      
					views.setOnClickPendingIntent(pics[j], callIntent);
					views.setTextViewText(labels[j], data[j].getName());
					views.setImageViewBitmap(pics[j], data[j].getPic());
				}
				else
					break;
			}

			// Tell the AppWidgetManager to perform an update on the current app widget
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
	public void onReceive(Context context, Intent intent)
	{
		super.onReceive(context, intent);
	}
	private Contact[] getCallDetails() {
		final int NUM_CONTACTS = 4;
		Contact[] data = new Contact[NUM_CONTACTS];
		String[] strFields = {
				android.provider.CallLog.Calls.NUMBER, 
				android.provider.CallLog.Calls.CACHED_NAME
		};
		String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
		//Create a database reader based on properties to look for
		ContentResolver contentResolver = context.getContentResolver();
		Cursor managedCursor = contentResolver.query(
				android.provider.CallLog.Calls.CONTENT_URI,strFields,null,null,strOrder);
		int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
		int name= managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
		int count = 0;
		managedCursor.moveToFirst();
		do
		{
			String phNumber = managedCursor.getString(number);
			String contactName = managedCursor.getString(name);
			if(contactName== null)
				contactName = "Unknown";
			data[count] = new Contact(contactName, phNumber);
			count++;
			for(int curr = count-2; curr>= 0; curr--)
			{
				if(data[curr].getName().equals(contactName))
				{
					count--;
					break;
				}
			}
		}
		while (count<NUM_CONTACTS && managedCursor.moveToNext());

		final Resources res = context.getResources();
		final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);
		final LetterTileProvider tileProvider = new LetterTileProvider(context);
		String[] projection = new String[] {PhoneLookup.PHOTO_URI};
		// Goes through and assign images
		for(int i = 0; i< NUM_CONTACTS; i++)
		{
			Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(data[i].getPhone()));
			Cursor cursor =contentResolver.query(uri, projection,null, null, null);
			Bitmap mBitmap = tileProvider.getLetterTile(data[i].getName(), ""+data[i].hashCode(), tileSize, tileSize);
			data[i].setPic(getRoundBitmap(mBitmap));
			if(cursor!=null) {
				while(cursor.moveToNext()){
					String lookFor = cursor.getString(cursor.getColumnIndexOrThrow(PhoneLookup.PHOTO_URI));
					Uri temp = Uri.parse(lookFor);
					try {
						mBitmap = Media.getBitmap(context.getContentResolver(), temp);
						data[i].setPic(getRoundBitmap(mBitmap));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				cursor.close();
			}

		}
		managedCursor.close();
		return data;
	}
	/** round the bitmaps given
	 * @param bitmap bitmap to be rounded
	 * @return rounded bitmap
	 */
	public Bitmap getRoundBitmap(Bitmap bitmap) {
		Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		BitmapShader shader = new BitmapShader (bitmap,  TileMode.CLAMP, TileMode.CLAMP);
		Paint paint = new Paint();
		paint.setShader(shader);
//		paint.setShadowLayer(10.0f, 0.0f, 2.0f, 0xFF000000); 
		Canvas c = new Canvas(circleBitmap);
		c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);
		return circleBitmap;
	}


}
