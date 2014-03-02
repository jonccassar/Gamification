package com.example.gamification_research;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamification_research.CircularSeekBar.*;


/**
 * @author Jonathan Cassar
 * @Class Name: Evaluation_screen
 * @Description: Provides methods for the Evaluations screen
 */
public class Evaluation_screen extends Activity implements OnCircularSeekBarChangeListener, OnClickListener 
{
	//VARIABLES
	CircularSeekBar sb;
	Data d;
	ImageView btn_help;
	TextView sample, comp_surveys, message,pts_day;
	SharedPreferences getData;
	String user, completed, total_points, daily_points;
	int surveys,intro,stop, tot_pts, day_pts, progress, counter;
	int base_pts = 2;
	double percent;

	
	//[1] ONCREATE
	/**
	 * @Method name: onCreate 
	 * @param Bundle
	 * @return null
	 * @Description: Class constructor.
	 */ 
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaluation_screen);
		getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		//References 
		sb = (CircularSeekBar) findViewById(R.id.circularSeekBar1);
		sample= (TextView)findViewById(R.id.txt_svr_evaluation);
		comp_surveys= (TextView)findViewById(R.id.txt_prog_evaluation);
		message =  (TextView)findViewById(R.id.txt_hints_evaluation);
		pts_day = (TextView)findViewById(R.id.txt_tag_pts_eval);
		btn_help = (ImageView)findViewById(R.id.btn_help_eval);
		
		//Listeners
		sb.setOnSeekBarChangeListener(this);
		btn_help.setOnClickListener(this);
		
		//Shared Preferences
		user = getData.getString("user_name", "user");
		
		//Methods
		get_data();
		set_data();
		popup();
	}
	
	//[2] GET DATA
	/**
	 * @Method name: get_data
	 * @param  null
	 * @return null
	 * @Description: Get data from various sources to be used within the class.
	 */ 
	public void get_data()
	{
	  getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	  d= new Data(this);
	  d.open();
	  user = d.read_user(getData.getString("user_name", "User"));
	  total_points = d.read_total_pts(user); 
  	  daily_points = d.read_daily_pts(user);
	  try{progress = Integer.parseInt(d.read_evaluation_state(user));
		  sb.setProgress(progress);}catch(Exception e){} //read data from database 
	  d.close();
	}
	
	//[3] SET DATA
	/**
	 * @Method name: set_data
	 * @param null
	 * @return null
	 * @Description: Set the variables with the retrieved data before.
	 */ 
	public void set_data()
	{
		//set number of surveys to be done
		getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		sample.setText(getData.getString("surveys", "s"));

		//set maximum progress bar
		surveys  = Integer.parseInt(getData.getString("surveys", "s"));
		sb.setMax(surveys);
		
		//Caculate percentage of completeness
		percent = (float)progress/(float)surveys;
		
		//Set initial hint
		message(percent);
		
		//Set daily points earned from surveys only
		pts_day.setText("0");
		
		//set Color
		setColor();
		
		//set progress text
		try{
			d.open();
			comp_surveys.setText(Integer.toString(sb.getProgress()));
			d.close();
		}catch(Exception e){}
			
	}
	
	//[4] ON PROGRESS
	/**
	 * @Method name: onProgressChanged
	 * @param Circular seekbar, Integer, boolean
	 * @return null
	 * @Description: On seek bar progress, update text and save state to database.
	 */ 
	public void onProgressChanged(CircularSeekBar sb, int progress, boolean fromUser) 
	{
		switch(sb.getId())
		{
		case R.id.circularSeekBar1:
			intro = progress;
			setColor();    //set col 
			comp_surveys.setText(""+progress);
			counter+=base_pts; //needed to keep record of daily points earned only from surveys
			pts_day.setText(""+counter);
			save_state(sb.getProgress());
			earn_points();
			break;
		}
	}
	
	//[5] SAVE STATE
	/**
	 * @Method name: save_state
	 * @param Integer
	 * @return null
	 * @Description: Save seekbar state to database. 
	 */ 
	public void save_state(int state)
	{
		try{
			d.open();
			d.write_evaluation_state(user,Integer.toString(state));
			d.close();
		}catch(Exception e){}
	}
	
	//[6] ON STOP
	/**
	 * @Method name: onStop
	 * @param null
	 * @return null
	 * @Description: On activity stop save the seekbar state to the database.
	 */ 
	public void onStop()
	{
		super.onStop();
		earn_points();
		update_points();
		save_points();
		save_state(sb.getProgress());
	}
	
	//[7] POPUP
	/**
	 * Method name: popup
	 * @param null
	 * @return null
	 * @Description: Pop a message to instruct user how to operate.
	 */
	public void popup()
	{
			Context c = getApplicationContext();
			CharSequence text = " Rotate wheel to mark survey progress.";
			int duration = Toast.LENGTH_SHORT;

			Toast t= Toast.makeText(c, text, duration);
			t.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
	        t.show();
	}
	
	//[8] EARN POINTS
	/**
	 * Method name: earn_points
	 * @param null
	 * @return null
	 * @Description: earn points with every survey completed
	 */
	public void earn_points()
	{  
		day_pts+=base_pts;
		tot_pts+=base_pts;
	}
	
	//[9] UPDATE POINTS
	/**
	 * Method name: update_points
	 * @param null
	 * @return null
	 * @Description: update points
	 */
	public void update_points()
	{
		int total = 0;
		int day = 0;

		//convert retrieved data from database to string
		try{total = Integer.parseInt(total_points);
		day = Integer.parseInt(daily_points);
		}catch(Exception e){};

		//Add points
		day+=day_pts;
		total+=tot_pts;

		//convert back to string
		try{
			daily_points= Integer.toString(day);
			total_points = Integer.toString(total);
		}catch(Exception e){}
	}
	
	//[10] SAVE POINTS
	/**
	 * Method name: save_points
	 * @param null
	 * @return null
	 * @Description: save points to database 
	 */
	public void save_points()
	{
		d = new Data(this);
		d.open();
		d.write_daily(user,daily_points);
		d.write_total(user,total_points);
		d.close();
	}
	
	//[11] SET HINT
		/**
		 * Method name: set hint
		 * @param null
		 * @return null
		 * @Description: Dsiplays hint messages to the user
		 */
	public void message (double percent)
	{ 
		if(percent>0.0  & percent<=0.2){message.setText(" Try and ask your peers!");}
		if(percent>=0.2 & percent<=0.4){message.setText(" Why not include your friends?");}
		if(percent>=0.4 & percent<=0.6){message.setText(" Time to do some chasing");}
		if(percent>=0.6 & percent<=0.8){message.setText(" Any luck with the phone?");}
		if(percent>=0.8){message.setText(" Have you considered collecting data from social media?");}
	}
	
	//[12] SET COLOR
		/**
		 * Method name: set_color
		 * @param null
		 * @return null
		 * @Description: sets the color of the progress wheel
		 */
	public void setColor()
	{ 
		percent = (float)intro/(float)surveys;
		if(percent>0.0  & percent<0.2){comp_surveys.setTextColor(Color.parseColor("#330000"));sb.setCircleProgressColor(Color.parseColor("#330000"));}
		if(percent>=0.2 & percent<0.4){comp_surveys.setTextColor(Color.parseColor("#FF0000"));sb.setCircleProgressColor(Color.parseColor("#FF0000"));}
		if(percent>=0.4 & percent<0.6){comp_surveys.setTextColor(Color.parseColor("#FF6600"));sb.setCircleProgressColor(Color.parseColor("#FF6600"));}
		if(percent>=0.6 & percent<0.8){comp_surveys.setTextColor(Color.parseColor("#FFEE00"));sb.setCircleProgressColor(Color.parseColor("#FFEE00"));}
		if(percent>=0.8){comp_surveys.setTextColor(Color.parseColor("#33CC00"));;sb.setCircleProgressColor(Color.parseColor("#33CC00"));}
	}		

	//[13] SET ON CLICK METHOD
	/**
	 * Method name: onClick
	 * @param null
	 * @return null
	 * @Description: set on click methods
	 */
	public void onClick(View arg0) 
	{
		switch(arg0.getId())
		{
		case R.id.btn_help_eval:
			try{message(arg0);}catch(Exception e){e.printStackTrace();};
			break;
		}

	}
	
	//[14] SET HELP
	/**
	 * Method name: message
	 * @param View
	 * @return null
	 * @Description: Set the help 
	 */
	public void message(View view)
	{
		AlertDialog d = new AlertDialog.Builder(this).create();
		d.setTitle("Evaluation Progress");
		d.setMessage(
		            "Rotate the Wheel clockwise to mark the survey progress."+
				    " In the process, earn two points per each survey completed."+
		            " Don't forget to check the hints at the bottom of the screen");    
			         
		d.setButton("OK", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				//REMAIN AT LOGIN SCREEN
			}
		});
		d.setIcon(R.drawable.help);
		d.show();	
	}
}



