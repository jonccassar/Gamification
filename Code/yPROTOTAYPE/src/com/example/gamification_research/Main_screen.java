package com.example.gamification_research;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.bostonandroid.datepreference.DatePreference;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

/**
 * @author Jonathan Cassar
 * @Class Name: Main_screen
 * @Description: The main activity of the application which is loaded first when the user clicks the icon
 * @TargetApi(Build.VERSION_CODES.HONEYCOMB)
 */
public class Main_screen extends Activity implements OnClickListener
{
	//VARIABLES 
	Button btn_papers, btn_progress, btn_evaluation, btn_calendar, btn_next;
	ImageSwitcher is;
	TextView usr, tot_pts, dly_pts, elpsd_dys, lvl, perCent;
	ProgressBar pb;
	Data d;
	SharedPreferences getData;

	String user, total_points, daily_points, elapsed_days, per_cent; 
	int lvl_counter = 0;
	int index = -1; //Image slider - keep current index of image id array  
	int array_length = 6;


	//[1] ON CREATE METHOD
	/**
	 * @Method name: onCreate
	 * @param Bundle savedInstances
	 * @return null
	 * @Description: On Create method.
	 */
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
	
		//Get Reference
		btn_papers = (Button)findViewById(R.id.btn_papers_main);
		btn_progress = (Button)findViewById(R.id.btn_progress_main);
		btn_evaluation = (Button)findViewById(R.id.btn_evaluation_main);
		btn_calendar = (Button)findViewById(R.id.btn_calendar_main);
		btn_next = (Button)findViewById(R.id.btn_next_main);
		is = (ImageSwitcher)findViewById(R.id.imgSwitcher_main);
		usr = (TextView)findViewById(R.id.txt_user_main);
		tot_pts = (TextView)findViewById(R.id.txt_tpts_main);
		dly_pts = (TextView)findViewById(R.id.txt_dpts_main);
		elpsd_dys = (TextView)findViewById(R.id.txt_eps_main);
		lvl =  (TextView)findViewById(R.id.txt_lvl_main);
		pb = (ProgressBar)findViewById(R.id.prog_bar_progress_screen);
		perCent = (TextView)findViewById(R.id.txt_percebt_main);

		//Listeners
		btn_papers.setOnClickListener(this);
		btn_progress.setOnClickListener(this);
		btn_evaluation.setOnClickListener(this);
		btn_calendar.setOnClickListener(this);
		btn_next.setOnClickListener(this);

		//Methods
		get_data();
		set_data();
		determine_level();
		image_slider();
		popup();
		progress_bar();

		//reset data 
		reset_data();
		reset_daily_pts();
		notification();
	}
	
	//[2] MENU INFLATOR
	/**
	 * @Method name: onCreateOptionsMenue
	 * @param menue
	 * @return boolean 
	 * @Description: Create a menu for the activity. 
	 */
	public boolean onCreateOptionsMenu(android.view.Menu menu) 
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.main_screen,menu);
		progress_bar();
		return true;
	}

	//[3] MENU SELECTED OPTIONS
	/**
	 * @Method name: onOptionsItemSelect
	 * @param Meneu Item
	 * @return boolean 
	 * @Description: Loads the Menu.
	 */
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.action_settings:
			startActivity(new Intent(this,Settings_screen.class));
		}
		return false;
	}

	//[4] CLICK HANDLER
	/**
	 * @Method name: onClick
	 * @param View
	 * @return null
	 * @Description: Handles the buttons click events. 
	 */
	public void onClick(View arg0) 
	{
		switch(arg0.getId())
		{
		case R.id.btn_papers_main:
			save_data_papers();
			startActivity(new Intent(this,Papers_screen.class));
			break;
		case R.id.btn_progress_main:
			startActivity(new Intent(this,Progress_screen.class));
			break;
		case R.id.btn_evaluation_main:
			startActivity(new Intent(this,Evaluation_screen.class));
			break;
		case R.id.btn_calendar_main:
			startActivity(new Intent(this,Calendar_screen.class));
			break;
		case R.id.btn_next_main:
			index++;
			lvl_counter = index+1;
			try {lvl.setText(""+lvl_counter);}catch(Exception e){}

			if(index==array_length) //reset when index reaches max
				index=0;
			lvl_counter=0;	
			is.setImageResource(determine_badge(index));
			break;
		}
	}

	//[5] IMAGE SLIDER
	/**
	 * Method name: image_slider
	 * @param null
	 * @return null
	 * @Description: Create an image slider through the use of Image View and Animation methods. The 
	 * slider can be loaded with images and slide from left to right.
	 */
	public void image_slider()
	{
		is.setFactory(new ViewFactory() 
		{
			public View makeView() 
			{
				// Create a new ImageView set it's properties 
				ImageView iv = new ImageView(getApplicationContext());
				iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
				iv.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
				return iv;
			}
		});

		//Declare animations
		Animation in = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left);
		Animation out = AnimationUtils.loadAnimation(this,android.R.anim.slide_out_right);

		//Set Animations
		is.setInAnimation(in);
		is.setOutAnimation(out);

	}

	//[6] POPUP
	/**
	 * Method name: popup
	 * @param null
	 * @return null
	 * @Description: Pop a message when prompted. The message can be set to have a short or long duration.
	 */
	public void popup()
	{
		if(user=="User"){
			Context c = getApplicationContext();
			CharSequence text = "Go to settings and enter details.";
			int duration = Toast.LENGTH_SHORT;

			Toast t= Toast.makeText(c, text, duration);
			t.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
			t.show();
		}
	}

	//[7] GET DATA
	/**
	 * @Method name: get_data
	 * @param null
	 * @return null
	 * @Description: Loads the required data from the SQLite database and from the Shared Preferences.
	 */
	public void get_data()
	{
		getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		user = get_user();
		d = new Data(this);
		d.open();
		total_points = d.read_total_pts(user); 
		daily_points = d.read_daily_pts(user);
		d.close();
		elapsed_days = days_elapsed(); //determine elapsed days
		per_cent = percent();
	}

	//[8] SET DATA 
	/**
	 * @Method name: set_data
	 * @param null
	 * @return null
	 * @Description: Sets the data so that is can be used by methods within the Main activity.
	 */
	public void set_data()
	{
		usr.setText(user);
		tot_pts.setText(total_points);
		dly_pts.setText(daily_points);
		elpsd_dys.setText(elapsed_days);
		perCent.setText(per_cent);
	}
	
	//[9] GET USER NAME 
	/**
	 * @Method name: get_user
	 * @param null
	 * @return user
	 * @Description: Loads the user name from the Shared Preferences and writes the user to the SQLITE database.
	 */
	public String get_user()
	{
		String user="";
		getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		user = getData.getString("user_name", "User");
		d =  new Data(this);
		d.open();
		d.write_user(user);
		d.close();
		return user;
	}
	
	//[10] GET DAYS ELAPSED
	/**
	 * @Method name: get_elapsed
	 * @param null
	 * @return elapsed days 
	 * @Description: Calculates the days elapsed from a particular date chosen and inputed by the user in the application.
	 */
	@SuppressLint("SimpleDateFormat")
	public String days_elapsed()
	{
		getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		String elapsed="";
		int diff =0;

		SimpleDateFormat dfDate  = new SimpleDateFormat("dd/MM/yyyy");

		java.util.Date start = null;
		java.util.Date today = null;
		Calendar cal_today = Calendar.getInstance();
		Calendar cal_start = DatePreference.getDateFor(PreferenceManager.getDefaultSharedPreferences(this),"dob");
		try {    
			start = dfDate.parse(dfDate.format(cal_start.getTime()));
			today = dfDate.parse(dfDate.format(cal_today.getTime()));
			diff = (int) ((start.getTime() - today.getTime())/ (1000 * 60 * 60 * 24))*-1; //-1 to eliminate -ve
			elapsed = Integer.toString(diff);

		} catch (java.text.ParseException e){
			e.printStackTrace();
		}
		return elapsed;
	}

	//[11] DETERMINE LEVEL
	/**
	 * @Method name: determine_level
	 * @param View
	 * @return level
	 * @Description: Determine the level reached by the user according to the total points earned. 
	 */
	public int determine_level()
	{
		int level = 0;
		int pts = -1;
		try{pts = Integer.parseInt(total_points);}catch(Exception e){};

		if(pts>100 & pts<=200){
			level = 1; try{d.open();d.write_level(user,Integer.toString(level));d.close();}catch(Exception e){}
		}else if(pts>200 & pts<=300){
			level = 2; try{d.open();d.write_level(user,Integer.toString(level));d.close();}catch(Exception e){}
		}else if(pts>300 & pts<=400){
			level =3; try{d.open();d.write_level(user,Integer.toString(level));d.close();}catch(Exception e){}
		}else if(pts>400 & pts<=500){
			level = 4; try{d.open();d.write_level(user,Integer.toString(level));d.close();}catch(Exception e){}
		}else if(pts>500 & pts<=600){
			level = 5; try{d.open();d.write_level(user,Integer.toString(level));d.close();}catch(Exception e){}
		}else if(pts>600){
			level = 6; try{d.open();d.write_level(user,Integer.toString(level));d.close();}catch(Exception e){}
		}
		return level;
	}

	//[12] DETERMINE BADGE
	/**
	 * @Method name: determine_badge
	 * @param int 
	 * @return array
	 * @Description: Determine the badges earned by the user according to the level reached and total points earned. 
	 */
	public int determine_badge(int index)
	{
		int image[] = {R.drawable.unknown,R.drawable.unknown,R.drawable.unknown,R.drawable.unknown,R.drawable.unknown,R.drawable.unknown};

		switch(determine_level()){
		case 1: 
			image[0] = R.drawable.novice;
			break;
		case 2:
			image[0] = R.drawable.novice; image[1] = R.drawable.bookworm;
			break;
		case 3:
			image[0] = R.drawable.novice; image[1] = R.drawable.bookworm; image[2] = R.drawable.junior_researcher; 
			break;
		case 4:
			image[0] = R.drawable.novice; image[1] = R.drawable.bookworm; image[2] = R.drawable.junior_researcher; image[3] = R.drawable.master_researcher;
			break;
		case 5:
			image[0] = R.drawable.novice; image[1] = R.drawable.bookworm; image[2] = R.drawable.junior_researcher; image[3] = R.drawable.master_researcher; image[4] = R.drawable.achademic;
			break;
		case 6:
			image[0] = R.drawable.novice; image[1] = R.drawable.bookworm; image[2] = R.drawable.junior_researcher; image[3] = R.drawable.master_researcher; image[4] = R.drawable.achademic; image[5] = R.drawable.guru;
			break;
		}
		return image[index]; 
	}

	//[13] DETERMINE PERCENTAGE
	/**
	 * @Method name: percentage
	 * @param null
	 * @return null
	 * @Description: Determine the dissertation percentage completed according to the total points earned. 
	 */
	public String percent()
	{
		//Set percentage 
		float percent = (float)Math.round((determine_level()*100)/6f);//calculate progress according to the level reached
		String per_cent = Float.toString(percent);
		return per_cent;
	}

	//[14] DETERMINE PROGRESS BAR
	/**
	 * @Method name: progress_bar
	 * @param null
	 * @return null
	 * @Description: Determine the level of the progress bar according to the total points earned. 
	 */
	public void progress_bar()
	{     try{
		d.open();
		pb.setProgress(Integer.parseInt(d.read_level(user))*100);
		d.close();}catch(Exception e){};     
	}

	//[15] RESET DATA 
	/**
	 * @Method name: reset_data
	 * @param null
	 * @return null
	 * @Description: Reset all data saved by the user to default. 
	 */
	public void reset_data()
	{
		getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean reset = getData.getBoolean("reset", false);

		if(reset){
			getData.edit().clear().commit();
			d.open();
			d.delete_all();
			d.close();
		}
	}

	//[16] RESET DAILY POINTS
	/**
	 * @Method name: reset_daily_pts
	 * @param null
	 * @return null
	 * @Description: Reset daily points to zero.
	 */
	public void reset_daily_pts()
	{
		getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean reset_daily = getData.getBoolean("reset_daily", false);

		if(reset_daily)
		{
			try{
				d.open();
				d.reset_daily();
				d.close();
			}catch(Exception e){}
		}
	}
	
	//[17] SEND NOTIFICATIONS
	/**
	 * @Method name: notification
	 * @param null
	 * @return null
	 * @Description: Send notifications to user every 6 hours. 
	 */
	public void notification()
	{
		getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean notification = getData.getBoolean("Notifications", false);
		
		if(notification){
			Calendar cal = Calendar.getInstance();

			cal.set(Calendar.HOUR_OF_DAY,8);
			cal.set(Calendar.MINUTE,0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND,0);
			long start = cal.getTimeInMillis();
			
			PendingIntent pi = PendingIntent.getService(this, 0 , new Intent(this,Notification.class),PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
			am.setRepeating(AlarmManager.RTC_WAKEUP,start,21600000, pi);

		}
	}
	
	//[18] SAVE DATA FOR PAPERS
	/**
	 * @Method name: save_data_papers
	 * @param null
	 * @return null
	 * @Description: Save paper's button original state. This saved data will be accessed by the Papers Activity.
	 */
	public void save_data_papers()
	{
		String data ="0000000000000000000000000";
		d.open();
		d.btn_write_state(data, user);
		d.close();
	}
}
