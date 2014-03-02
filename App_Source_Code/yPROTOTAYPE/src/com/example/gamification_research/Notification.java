package com.example.gamification_research;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

/**
 * @author Jonathan Cassar
 * @Class Name: Notifications
 * @extends: Services
 * @Description: Uses the service interface to send notifications at various intervals. This class also caters 
 * for mobile vibration and light beeping
 * @TargetApi(Build.VERSION_CODES.HONEYCOMB)
 */
public class Notification extends Service 
{
	//VARIABLES 
	Intent i,j;
	PendingIntent pi;
	NotificationManager nm;
	NotificationCompat.Builder nbldr;
	Uri s;	

	//[1] ON BIND
	/**
	 * @Method name: onBind
	 * @param Integer
	 * @return null
	 */
	public IBinder onBind(Intent arg0) {

		return null;
	}

	//[2]ON CREATE
	/**
	 * @Method name: onCreate
	 * @param null
	 * @return null
	 * @Override: Constructor override 
	 */
	public void onCreate()  {

		super.onCreate();
		displayNotification();
	}

	//[3]ON START
	/**
	 * @Method name: onStart
	 * @param Intent
	 * @param int 
	 * @return null
	 * @SuppressWarnings("deprecation")
	 * @Description: On Start call the notifications manager
	 */

	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		displayNotification();
	}    

	//[4] ON DESTROY
	/**
	 * @Method name: onDestroy
	 * @param null
	 * @return null
	 */
	public void onDestroy()  {

		super.onDestroy();
	}

	//[5] NOTIFICATION 
	/**
	 * @Method name: onDisplay
	 * @param null
	 * @return null
	 * @Description:  Notify the user through a notification every 6 hours. With every notification, the
	 * mobile is allowed to beep, flash light and vibration.
	 */
	public void displayNotification() 
	{


		long [] vibrate = {500,500,500,500,500,500,500,500,500};
		i = new Intent(this, Main_screen.class);
		pi = PendingIntent.getActivity(this, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);      
		nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		s = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		nbldr = new NotificationCompat.Builder(this);
		nbldr.setContentIntent(pi);
		nbldr.setAutoCancel(true);
		nbldr.setSmallIcon(R.drawable.star);
		nbldr.setContentTitle("GResearch");
		nbldr.setContentText("Stay Focused. Work on dissertation.");
		nbldr.setLights(Color.MAGENTA, 500, 500);     //beep light
		nbldr.setVibrate(vibrate);   //vibrate device
		nbldr.setSound(s);           //play sound
		nm.notify(1, nbldr.build());

	} 

}
