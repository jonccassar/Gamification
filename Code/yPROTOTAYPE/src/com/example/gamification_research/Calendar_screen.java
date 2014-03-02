package com.example.gamification_research;

import java.util.Calendar;
import java.util.TimeZone;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.text.Html;
import android.text.format.Time;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;

/**
 * @author Jonathan Cassar
 * @Class Name: Calendar Screen
 * @Extends Activity
 * @TargetApi(Build.VERSION_CODES.HONEYCOMB)
 * @Description: The calendar screen is responsible for the handling of calendar events. This class connects to the gmail calendar database and extracts
 * user's events keyed by the user. Furthermore, it indicates to the user the free slots in which the user can work on the dissertation.
 */
public class Calendar_screen extends Activity
{
	Data d;
	TextView txt0,txt1,txt2,txt3,txt4,txt5,txt6,txt7, txt8,txt9,txt10,txt11,txt12,txt13,txt14,txt15,txt16,txt17,txt18,txt19,txt20,txt21,txt22,txt23,txt24;
	Time dstart, dend, all_day_start;
	Long start, end, day;
	Context c;
	Toast t;
	Calendar cal;
	int counter =-1;
	int yy,mm,dd,hr,min,sec;
	ContentResolver cr;
	String user,email;
	String[] events;
	int [] hrs;
	SharedPreferences getData;
	String[] evts_chron = {"-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1","-1"};

	//[1] ON CREATE 
	/**
	 * @Method name: onCreate
	 * @param Bundle savedInstances
	 * @return null
	 * @Description: On Create method
	 */
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_screen);
		
		//Get References 
		txt0 = (TextView)findViewById(R.id.txt0_schedule_srn);
		txt1 = (TextView)findViewById(R.id.txt1_schedule_srn);
		txt2 = (TextView)findViewById(R.id.txt2_schedule_srn);
		txt3 = (TextView)findViewById(R.id.txt3_schedule_srn);
		txt4 = (TextView)findViewById(R.id.txt4_schedule_srn);
		txt5 = (TextView)findViewById(R.id.txt5_schedule_srn);
		txt6 = (TextView)findViewById(R.id.txt6_schedule_srn);
		txt7 = (TextView)findViewById(R.id.txt7_schedule_srn);
		txt8 = (TextView)findViewById(R.id.txt8_schedule_srn);
		txt9 = (TextView)findViewById(R.id.txt9_schedule_srn);
		txt10 = (TextView)findViewById(R.id.txt10_schedule_srn);
		txt11 = (TextView)findViewById(R.id.txt11_schedule_srn);
		txt12 = (TextView)findViewById(R.id.txt12_schedule_srn);
		txt13 = (TextView)findViewById(R.id.txt13_schedule_srn);
		txt14 = (TextView)findViewById(R.id.txt14_schedule_srn);
		txt15 = (TextView)findViewById(R.id.txt15_schedule_srn);
		txt16 = (TextView)findViewById(R.id.txt16_schedule_srn);
		txt17 = (TextView)findViewById(R.id.txt17_schedule_srn);
		txt18 = (TextView)findViewById(R.id.txt18_schedule_srn);
		txt19 = (TextView)findViewById(R.id.txt19_schedule_srn);
		txt20 = (TextView)findViewById(R.id.txt20_schedule_srn);
		txt21 = (TextView)findViewById(R.id.txt21_schedule_srn);
		txt22 = (TextView)findViewById(R.id.txt22_schedule_srn);
		txt23 = (TextView)findViewById(R.id.txt23_schedule_srn);
		txt24 = (TextView)findViewById(R.id.txt24_schedule_srn);
		
		//Methods
		get_data();
		convert();
		read_calendar();
		arrange_data();
		show_data();
		notification();
	}
	
	//[2] GET DATA
	/**
	 * @Method name: get_data
	 * @param null
	 * @return null
	 * @Description: Get the required data to be used by methods within the class
	 */
	public void get_data()
	{
		getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		user = getData.getString("user_name", "User");
		email = getData.getString("email", "email");
	}

	//[3] CONVERT TIME TO MILLISECONDS 
	/**
	 * @Method name: convert
	 * @param null
	 * @return null
	 * @Description: Extracts the system date and convert it to milliseconds 
	 */
    public void convert()
    {
		dstart = new Time();
		dend = new Time();
		all_day_start = new Time();
		
		dstart.setToNow();
		
		dstart.hour=0;
		dstart.minute=0;
		dstart.second=0;

		dend.set(dstart);
		dend.hour=dstart.hour+24;
		dend.minute=dstart.minute+59;
		dend.second=dstart.second+59;
		
		all_day_start.timezone=TimeZone.getDefault().toString();
		all_day_start.set(dstart.monthDay, dstart.month, dstart.year);

		start = dstart.toMillis(false);
		end = dend.toMillis(false) + 999;
		day = all_day_start.toMillis(false);
    }
	
    //[4] READ DATA FROM GMAIL CALENDAR
    /**
	 * @Method name: read_calendar
	 * @param null
	 * @return null
	 * @SuppressLint("InlinedApi")
	 * @Description: Reads data from the gmail calendar through the use of an SQL query.
	 */
	public void read_calendar()
    {
    	c = getApplicationContext();    
    	cr = c.getContentResolver(); 
    
    	//Columns to query
    	String [] COLS = new String[]{"calendar_id", "title","dtstart", "dtend"};  

    	//Select statement
    	String select = 
    			"((" + Calendars.ACCOUNT_NAME + " = ?) " +
    					"AND (" + Calendars.OWNER_ACCOUNT + "= ?) " +
    					"AND (" +
    					"((" + Events.DTSTART + ">= ?) " +
    					"AND (" + Events.DTSTART + "<= ?) " +
    					"AND (" + Events.ALL_DAY + "= ?) " +
    					") " +
    					"OR ((" + Events.DTSTART + "= ?) " +
    					"AND (" + Events.ALL_DAY + "= ?)" +
    					")" +
    					")" +
    					")"; 

    	//Where clause
    	String[] where = new String[] {email,email,start.toString(), end.toString(), "0",day.toString(), "1" };

    	//Query
    	Cursor crsr = cr.query(Uri.parse("content://com.android.calendar/events"),COLS,select,where,Events.DTSTART);       

    	//Get data
    	crsr.moveToFirst();
    	events = new String[crsr.getCount()];    //size of cursor i.e database
    	hrs =  new int[crsr.getCount()];
    	
    	for (int i = 0; i < events.length; i++)
		{ 
			//Convert milliseconds date to dd/mm/yy hr:mm:ss
			cal = Calendar.getInstance(); 
			cal.setTimeInMillis(Long.parseLong(crsr.getString(2)));
			yy = cal.get(Calendar.YEAR);
			mm = cal.get(Calendar.MONTH)+1; 
			dd = cal.get(Calendar.DAY_OF_MONTH);
			hr = cal.get(Calendar.HOUR_OF_DAY);
			min = cal.get(Calendar.MINUTE);
			sec = cal.get(Calendar.SECOND);
			
			String d = dd+"/"+mm+"/"+yy;
		    String t = hr+":"+min+":"+sec;
			hrs[i]=hr;
			
			events[i] = crsr.getString(1)+" @ "+d+" @ "+t;  //array with events
			
			crsr.moveToNext(); 
		}  
		crsr.close();
    }
    
    //[5] ARRANGE DATA
	/**
	 * @Method name: arrange_data
	 * @param null
	 * @return null
	 * @Description: Arranges the retrieved data in a chronological order.
	 */   
    public void arrange_data()
    {
    	
    	for (int i=0; i<hrs.length;i++)
    	{	
    		if(hrs[i]==0){
    			evts_chron[0] = events[i];
    		}else if(hrs[i]==1){
    			evts_chron[1] = events[i];
    		}else if(hrs[i]==2){
    			evts_chron[2] = events[i];
    		}else if(hrs[i]==3){
    			evts_chron[3] = events[i];
    		}else if(hrs[i]==4){
    			evts_chron[4] = events[i];
    		}else if(hrs[i]==5){
    			evts_chron[5] = events[i];
    		}else if(hrs[i]==6){
    			evts_chron[6] = events[i];
    		}else if(hrs[i]==7){
    			evts_chron[7] = events[i];
    		}else if(hrs[i]==8){
    			evts_chron[8] = events[i];
    		}else if(hrs[i]==9){
    			evts_chron[9] = events[i];
    		}else if(hrs[i]==10){
    			evts_chron[10] = events[i];
    		}else if(hrs[i]==11){
    			evts_chron[11] = events[i];
    		}else if(hrs[i]==12){
    			evts_chron[12] = events[i];
    		}else if(hrs[i]==13){
    			evts_chron[13] = events[i];
    		}else if(hrs[i]==14){
    			evts_chron[14] = events[i];
    		}else if(hrs[i]==15){
    			evts_chron[15] = events[i];
    		}else if(hrs[i]==16){
    			evts_chron[16] = events[i];
    		}else if(hrs[i]==17){
    			evts_chron[17] = events[i];
    		}else if(hrs[i]==18){
    			evts_chron[18] = events[i];
    		}else if(hrs[i]==19){
    			evts_chron[19] = events[i];
    		}else if(hrs[i]==20){
    			evts_chron[20] = events[i];
    		}else if(hrs[i]==21){
    			evts_chron[21] = events[i];
    		}else if(hrs[i]==22){
    			evts_chron[22] = events[i];
    		}else if(hrs[i]==23){
    			evts_chron[23] = events[i];
    		}else if(hrs[i]==24){
    			evts_chron[24] = events[i];
    		}
    	}

    	//replace -1 with text and change colour
    	for(int i=0;i<evts_chron.length; i++)
    	{
    		if(evts_chron[i]=="-1"){
    			evts_chron[i]="<font color=\"green\">* You can work on the dissertation.</font>";
    			counter+=1;
    		}
    	}
    }
 
    //[6] SHOW DATA
	/**
	 * @Method name: show_data
	 * @param null
	 * @return null
	 * @Description: Display to screen the retrieved data. In case the slot is free, display text in green otherwise display text in white.
	 */ 
    public void show_data()
    {
    	txt0.setText(Html.fromHtml(evts_chron[0]), BufferType.SPANNABLE);
    	txt1.setText(Html.fromHtml(evts_chron[1]), BufferType.SPANNABLE);
    	txt2.setText(Html.fromHtml(evts_chron[2]), BufferType.SPANNABLE);
    	txt3.setText(Html.fromHtml(evts_chron[3]), BufferType.SPANNABLE);
    	txt4.setText(Html.fromHtml(evts_chron[4]), BufferType.SPANNABLE);
    	txt5.setText(Html.fromHtml(evts_chron[5]), BufferType.SPANNABLE);
    	txt6.setText(Html.fromHtml(evts_chron[6]), BufferType.SPANNABLE);
    	txt7.setText(Html.fromHtml(evts_chron[7]), BufferType.SPANNABLE);
    	txt8.setText(Html.fromHtml(evts_chron[8]), BufferType.SPANNABLE);
    	txt9.setText(Html.fromHtml(evts_chron[9]), BufferType.SPANNABLE);
    	txt10.setText(Html.fromHtml(evts_chron[10]), BufferType.SPANNABLE);
    	txt11.setText(Html.fromHtml(evts_chron[11]), BufferType.SPANNABLE);
    	
    	txt12.setText(Html.fromHtml(evts_chron[12]), BufferType.SPANNABLE);
    	txt13.setText(Html.fromHtml(evts_chron[13]), BufferType.SPANNABLE);
    	txt14.setText(Html.fromHtml(evts_chron[14]), BufferType.SPANNABLE);
    	txt15.setText(Html.fromHtml(evts_chron[15]), BufferType.SPANNABLE);
    	txt16.setText(Html.fromHtml(evts_chron[16]), BufferType.SPANNABLE);
    	txt17.setText(Html.fromHtml(evts_chron[17]), BufferType.SPANNABLE);
    	txt18.setText(Html.fromHtml(evts_chron[18]), BufferType.SPANNABLE);
    	txt19.setText(Html.fromHtml(evts_chron[19]), BufferType.SPANNABLE);
    	txt20.setText(Html.fromHtml(evts_chron[20]), BufferType.SPANNABLE);
    	txt21.setText(Html.fromHtml(evts_chron[21]), BufferType.SPANNABLE);
    	txt22.setText(Html.fromHtml(evts_chron[22]), BufferType.SPANNABLE);
    	txt23.setText(Html.fromHtml(evts_chron[23]), BufferType.SPANNABLE);
    	txt24.setText(Html.fromHtml(evts_chron[24]), BufferType.SPANNABLE);
    	
    }
   
   //[7] NOTIFICATION
	/**
	 * @Method name: notification
	 * @param null
	 * @return null
	 * @Description: Notify the user about the free time which can be used to work on the dissertation.
	 */ 
    public void notification()
    {
    	int net =  counter -8; // considers 8 hrs of sleep
    	c = getApplicationContext();
    	CharSequence text = "Considering 8 hours of sleep, you have "+ net + "hrs to work on the dissertation.";
    	int duration = Toast.LENGTH_LONG;

    	t= Toast.makeText(c, text, duration);
    	t.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
    	t.show();
    }
}
