package com.example.gamification_research;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * @author Jonathan Cassar
 * @Class Name: Progress_screen
 * @Description: This class is accountable for the Progress screen activity. It deals with saving, retrieving and 
 * displaying points earned by the user.
 */
public class Progress_screen extends Activity implements OnSeekBarChangeListener, OnClickListener
{
	//VARIABLES
	TextView cnt_intro, cnt_litreview, cnt_metod, cnt_resul, cnt_concl,
	         wrd_intro, wrd_litreview, wrd_metod, wrd_resul, wrd_concl;
	
	SeekBar bar_intro, bar_litreview, bar_metod, bar_result, bar_concl;
	
	ImageView img_intro_red, img_intro_yel, img_intro_grn;
	ImageView img_lit_red, img_lit_yel, img_lit_grn;
	ImageView img_met_red, img_met_yel, img_met_grn;
	ImageView img_res_red, img_res_yel, img_res_grn;
	ImageView img_con_red, img_con_yel, img_con_grn;
	ImageView btn_help;
	
	SharedPreferences getData;
	Data d;
	String user, total_points, daily_points;
	int words, intro, litreview, metod, result, concl;
	float per_intro, per_lit, per_met, per_res, per_con;
	int start, stop;
	
	
	//[1] ON CREATE METHOD
	/**
	 * @Method name: onCreate
	 * @param Bundle savedInstances
	 * @return null
	 * @Description: On Create method.
	 */
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress_screen);
		getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		//Get References
		
		//references - word counter
		cnt_intro =(TextView)findViewById(R.id.txt_intro_count_progress);
		cnt_litreview =(TextView)findViewById(R.id.txt_lit_count_progress);
		cnt_metod =(TextView)findViewById(R.id.txt_met_count_progress);
		cnt_resul =(TextView)findViewById(R.id.txt_res_count_progress);
		cnt_concl =(TextView)findViewById(R.id.txt_conc_count_progress);
		
		//references - words set by user
		wrd_intro =(TextView)findViewById(R.id.txt_intro_progress);
		wrd_litreview =(TextView)findViewById(R.id.txt_lit_progress);
		wrd_metod =(TextView)findViewById(R.id.txt_met_progress);
		wrd_resul =(TextView)findViewById(R.id.txt_res_progress);
		wrd_concl =(TextView)findViewById(R.id.txt_conc_progress);
		
		//reference - seek bars
		bar_intro = (SeekBar)findViewById(R.id.bar_int_progress);
		bar_litreview = (SeekBar)findViewById(R.id.bar_liter_progress);
		bar_metod = (SeekBar)findViewById(R.id.bar_met_progress);
		bar_result = (SeekBar)findViewById(R.id.bar_res_progress);
	    bar_concl = (SeekBar)findViewById(R.id.bar_conc_progress);
	    
	    //reference - images 
	    img_intro_red =  (ImageView)findViewById(R.id.img_red_intro);
	    img_intro_yel = (ImageView)findViewById(R.id.img_yellow_intro);
	    img_intro_grn = (ImageView)findViewById(R.id.img_green_intro);
	    
	    img_lit_red =  (ImageView)findViewById(R.id.img_red_lit);
	    img_lit_yel = (ImageView)findViewById(R.id.img_yellow_lit);
	    img_lit_grn = (ImageView)findViewById(R.id.img_green_lit);
	    
	    img_met_red =  (ImageView)findViewById(R.id.img_red_met);
	    img_met_yel = (ImageView)findViewById(R.id.img_yellow_met);
	    img_met_grn = (ImageView)findViewById(R.id.img_green_met);
	    
	    img_res_red =  (ImageView)findViewById(R.id.img_red_res);
	    img_res_yel = (ImageView)findViewById(R.id.img_yellow_res);
	    img_res_grn = (ImageView)findViewById(R.id.img_green_res);
	    
	    img_con_red =  (ImageView)findViewById(R.id.img_red_con);
	    img_con_yel = (ImageView)findViewById(R.id.img_yellow_con);
	    img_con_grn = (ImageView)findViewById(R.id.img_green_con);
	    
	    btn_help = (ImageView)findViewById(R.id.btn_help_prog);
	    
	    //Listeners
	    bar_intro.setOnSeekBarChangeListener(this);
	    bar_litreview.setOnSeekBarChangeListener(this);
	    bar_metod.setOnSeekBarChangeListener(this);
	    bar_result.setOnSeekBarChangeListener(this);
	    bar_concl.setOnSeekBarChangeListener(this);
	    btn_help.setOnClickListener(this);
	    
	    
	    //Shared preferences 
	    user = getData.getString("user_name", "user");
	    
	    //methods
	    get_data();
	    set_data();
	    
	}
	
	//[2] GET DATA 
	/**
	 * @Method name: get_data
	 * @param null
	 * @return null
	 * @Description: Get the data which is to be used by the Progress Activity.
	 */
	public void get_data()
	{
		//get data from parameters entered by user
		getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		words = Integer.parseInt(getData.getString("words", "w")); 
		per_intro = (float)Float.parseFloat(getData.getString("intro_length", "i"))/(float)100;    //divide by 100 to get percent
	    per_lit = (float)Float.parseFloat(getData.getString("lit_review_length", "ir"))/(float)100; 
	    per_met = (float)Float.parseFloat(getData.getString("methodology_length", "m"))/(float)100; 
	    per_res = (float)Float.parseFloat(getData.getString("results_length", "rl"))/(float)100; 
	    per_con = (float)Float.parseFloat(getData.getString("conclusion_length", "cl"))/(float)100; 
		
		d = new Data(this);
    	d.open();
    	user = d.read_user(getData.getString("user_name", "User"));
    	total_points = d.read_total_pts(user); 
    	daily_points = d.read_daily_pts(user);
    	d.close();
	}
	
	//[3] SET DATA
	/**
	 * @Method name: set_data
	 * @param null
	 * @return null
	 * @Description: Set the data which is to be used by the Progress Activity.
	 */
	public void set_data()
	{	
		cnt_intro.setText("0");
		cnt_litreview.setText("0");
		cnt_metod.setText("0");
		cnt_resul.setText("0");
		cnt_concl.setText("0");
        
		//distribute the length of the dissertation
		wrd_intro.setText(Integer.toString(Math.round(words*per_intro)));     
		wrd_litreview.setText(Integer.toString(Math.round(words*per_lit)));
		wrd_metod.setText(Integer.toString(Math.round(words*per_met)));
		wrd_resul.setText(Integer.toString(Math.round(words*per_res)));
		wrd_concl.setText(Integer.toString(Math.round(words*per_con)));
		
		//set seek bar max value
		bar_intro.setMax(Math.round(words*per_intro));
	    bar_litreview.setMax(Math.round(words*per_lit));
		bar_metod.setMax(Math.round(words*per_met));
		bar_result.setMax(Math.round(words*per_res));
		bar_concl.setMax(Math.round(words*per_con));
		
		//set progress bars
		d.open();
		try{bar_intro.setProgress(Integer.parseInt(d.read_intro_state(user)));}catch(Exception e){}
		try{bar_litreview.setProgress(Integer.parseInt(d.read_litreview_state(user)));}catch(Exception e){}
		try{bar_metod.setProgress(Integer.parseInt(d.read_metod_state(user)));}catch(Exception e){}
		try{bar_result.setProgress(Integer.parseInt(d.read_result_state(user)));}catch(Exception e){}
		try{bar_concl.setProgress(Integer.parseInt(d.read_concl_state(user)));}catch(Exception e){}
	}
	
	//[4] PROGRESS BAR HANDLER
	/**
	 * @Method name: onProgressChanged
	 * @param seek bar, integer, boolean
	 * @return null
	 * @Description: Handles the Seek Bar changes and updates the number of words 
	 */
	public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
		
		switch(bar.getId())
		{
		case R.id.bar_int_progress:
			cnt_intro.setText(""+progress); //show words 
			intro = progress;
			check_images();
			break;
		case R.id.bar_liter_progress:
			cnt_litreview.setText(""+progress);
			litreview = progress;
			check_images();
			break;
		case R.id.bar_met_progress:
			cnt_metod.setText(""+progress);
			metod = progress;
			check_images();
			break;
		case R.id.bar_res_progress:
			cnt_resul.setText(""+progress);
			result = progress;
			check_images();
			break;
		case R.id.bar_conc_progress:
			cnt_concl.setText(""+progress);
			concl = progress;
			check_images();
			break;
		}
	}

	//[5] ON START HANDLER
	/**
	 * @Method name: onStartTrackingTouch
	 * @param seek bar,
	 * @return null
	 * @Description: On start interacting with the seek button get the data from the database. 
	 */
	public void onStartTrackingTouch(SeekBar bar) 
	{
	}

	//[6] ON STOP HANDLER
	/**
	 * @Method name: onStopTrackingTouch
	 * @param seek bar
	 * @return null
	 * @Description: According to the state of the seek bar earn points and save points. 
	 */
	public void onStopTrackingTouch(SeekBar bar) 
	{
		switch(bar.getId())
		{
		case R.id.bar_int_progress:
			stop = intro;
			earn_points();
			save_pts_database();
			save_intro_sate(stop);
			break;
		case R.id.bar_liter_progress:
			stop = litreview ;
			earn_points();
			save_pts_database();
			save_lit_sate(stop);
			break;
		case R.id.bar_met_progress:
			stop = metod;
			earn_points();
			save_pts_database();
			save_metod_sate(stop);
			break;
		case R.id.bar_res_progress:
			stop = result;
			earn_points();
			save_pts_database();
			save_res_sate(stop);
			break;
		case R.id.bar_conc_progress:
			stop = concl;
			earn_points();
			save_pts_database();
			save_conc_sate(stop);
			break;
		}
	}
	
	//[7] CHECK IMAGES 
	/**
	 * @Method name: check_images
	 * @param null
	 * @return null
	 * @Description: Check if the seek bar has reached max and if so display image.
	 */
	public void check_images()
	{	
		//Image for Introduction
		if(intro>(int)Math.round((words*per_intro)*0.35)){img_intro_red.setVisibility(View.INVISIBLE);}
		if(intro>(int)Math.round((words*per_intro)*0.80)){img_intro_yel.setVisibility(View.INVISIBLE);}
		
		//Image for Lit Review
		if(litreview>Math.round((words*per_lit)*0.35)){img_lit_red.setVisibility(View.INVISIBLE);}
		if(litreview>Math.round((words*per_lit)*0.80)){img_lit_yel.setVisibility(View.INVISIBLE);}
		
		//Image for Methodology
		if(metod>Math.round((words*per_met)*0.35)){img_met_red.setVisibility(View.INVISIBLE);}
		if(metod>Math.round((words*per_met)*0.80)){img_met_yel.setVisibility(View.INVISIBLE);}
		
		//Image for Result
		if(result>Math.round((words*per_res)*0.35)){img_res_red.setVisibility(View.INVISIBLE);}
		if(result>Math.round((words*per_res)*0.80)){img_res_yel.setVisibility(View.INVISIBLE);}
		
		//Image for Conclusion
		if(concl>Math.round((words*per_con)*0.35)){img_con_red.setVisibility(View.INVISIBLE);}
		if(concl>Math.round((words*per_con)*0.80)){img_con_yel.setVisibility(View.INVISIBLE);}
				
	}
	
	//[8] EARN POINTS
	/**
	 * @Method name: earn_points
	 * @param null
	 * @return null
	 * @Description: Earn points. 
	 */
	public void earn_points()
	{   
		//Introduction pts
    	if(intro>Math.round((words*per_intro)*0.35) & intro<Math.round((words*per_intro)*0.80)){update_points(10);
		}else{if (intro>=Math.round((words*per_intro)*0.80) & intro<Math.round((words*per_intro))){update_points(40);
		}else{if (intro == Math.round((words*per_intro))){update_points(150);}}}
		
		//Lit Review pts
		if(litreview>Math.round((words*per_lit)*0.35) & litreview<Math.round((words*per_lit)*0.80)){update_points(10);
		}else{if (litreview>=Math.round((words*per_lit)*0.80) & litreview<Math.round((words*per_lit))){update_points(40);
		}else{if (litreview == Math.round((words*per_lit))){update_points(150);}}}
		
		//Methodology pts
		if(metod>Math.round((words*per_met)*0.35) & metod<Math.round((words*per_met)*0.80)){update_points(10);
		}else{if (metod>=Math.round((words*per_met)*0.80) & metod<Math.round((words*per_met))){update_points(40);
		}else{if (metod == Math.round((words*per_met))){update_points(150);}}}
		
		//Results pts
		if(result>Math.round((words*per_res)*0.35) & result<Math.round((words*per_res)*0.80)){update_points(10);
		}else{if (result>=Math.round((words*per_res)*0.80) & result<Math.round((words*per_res))){update_points(40);
		}else{if (result == Math.round((words*per_res))){update_points(150);}}}
		
		//Conclusion pts
		if(concl>Math.round((words*per_con)*0.35) & concl<Math.round((words*per_con)*0.80)){update_points(10);
		}else{if (concl>=Math.round((words*per_con)*0.80) & concl<Math.round((words*per_con))){update_points(40);
		}else{if (concl == Math.round((words*per_con))){update_points(150);}}}
		
	}
	
	//[9] UPDATE POINTS
	/**
	 * @Method name: update_points
	 * @param points
	 * @return null
	 * @Description: Update accumulated points with earned ones.
	 */
	public void update_points(int points)
	{
		int total = 0;
		int day = 0;

		//convert retrieved data from database to string
		try{total = Integer.parseInt(total_points);
		day = Integer.parseInt(daily_points);
		}catch(Exception e){};

		//Add points
		day+=points;
		total+=points;

		//convert back to string
		try{
			daily_points= Integer.toString(day);
			total_points = Integer.toString(total);
		}catch(Exception e){}

	}
	
	//[10] SAVE POINTS TO DATABASE
	/**
	 * @Method name: save_pts_database 
	 * @param null
	 * @return null
	 * @Description: Save points to database. 
	 */
	public void save_pts_database()
	{
		d = new Data(this);
		d.open();
		d.write_daily(user,daily_points);
		d.write_total(user,total_points);
		d.close();
	}
	
	//[11A] SAVE INTRO STATE 
	/**
	 * @Method name: save_intro_state 
	 * @param null
	 * @return null
	 * @Description: Call database method to save introduction state. 
	 */
	public void save_intro_sate(int state)
	{
		try{
			d.open();
			d.write_intro_state(user,Integer.toString(state));
			d.close();
		}catch(Exception e){}
	}

	//[11B] SAVE LITERATURE REVIEW STATE
	/**
	 * @Method name: save_lit_state 
	 * @param null
	 * @return null
	 * @Description: Call database method to save lit review state. 
	 */
	public void save_lit_sate(int state)
	{
		try{
			d.open();
			d.write_literview_state(user,Integer.toString(state));
			d.close();
		}catch(Exception e){}
	}

	//[11C] SAVE METHODOLOGY STATE
	/**
	 * @Method name: save_metod_state 
	 * @param null
	 * @return null
	 * @Description: Call database method to save methodology state.
	 */
	public void save_metod_sate(int state)
	{
		try{
			d.open();
			d.write_metod_state(user,Integer.toString(state));
			d.close();
		}catch(Exception e){}
	}

	//[11D] SAVE RESULTS STATE
	/**
	 * @Method name: save_res_state 
	 * @param null
	 * @return null
	 * @Description: Call database method to save results state. 
	 */
	public void save_res_sate(int state)
	{
		try{
			d.open();
			d.write_res_state(user,Integer.toString(state));
			d.close();
		}catch(Exception e){}
	}

	//[11E] SAVE CONCLUSION STATE
	/**
	 * @Method name: save_conc_state 
	 * @param null
	 * @return null
	 * @Description: Call database method to save conclusion state. 
	 */
	public void save_conc_sate(int state)
	{
		try{
			d.open();
			d.write_conc_state(user,Integer.toString(state));
			d.close();
		}catch(Exception e){}
	}
	
	//[12] ON CLICK VIEW
	/**
	 * 
	 */
	public void onClick(View arg0) 
	{
		switch(arg0.getId())
		{
		case R.id.btn_help_prog:
			try{message(arg0);}catch(Exception e){e.printStackTrace();};
			break;
		}
		
	}

	//[13] HELP MESSAGE 
	/**
	 * Method name: message
	 * @param View
	 * @return null
	 * @Description: Set the help 
	 */
	public void message(View view)
	{
		AlertDialog d = new AlertDialog.Builder(this).create();
		d.setTitle("Progress");
		d.setMessage(
		            "Slide the sliders to the right."+
				    " In the process, earn 10, 40 and 150 points if you complete more than 35%, 80% and 100% of the chapter respectively."+
		            " Write, and turn all to green.");    
			         
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
