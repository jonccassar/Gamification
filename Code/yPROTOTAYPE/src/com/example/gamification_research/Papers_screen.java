package com.example.gamification_research;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author Jonathan Cassar
 * @Class Name: Papers Screen
 * @Description: This class is accountable for the papers screen, handling of buttons, remembering of button state and progression
 * @TargetApi(Build.VERSION_CODES.HONEYCOMB)
 */
public class Papers_screen extends Activity implements OnClickListener
{
	//VARIABLES 
	Button btn01,btn02,btn03,btn04,
	       btn05,btn06,btn07,btn08,
	       btn09,btn10,btn11,btn12,
	       btn13,btn14,btn15,btn16,
	       btn17,btn18,btn19,btn20,
	       btn21,btn22,btn23,btn24,
	       btn25;
	
	Data d;
	InputStream is;
	SharedPreferences getData;
	String user, total_points, daily_points, state, ppr_abstract;
	int points = 15;
	int btnId;
	ImageView btn_help;
	boolean btn01_clicked,btn02_clicked,btn03_clicked,btn04_clicked,
	        btn05_clicked,btn06_clicked,btn07_clicked,btn08_clicked,
	        btn09_clicked,btn10_clicked,btn11_clicked,btn12_clicked,
	        btn13_clicked,btn14_clicked,btn15_clicked,btn16_clicked,
	        btn17_clicked,btn18_clicked,btn19_clicked,btn20_clicked,
	        btn21_clicked,btn22_clicked,btn23_clicked,btn24_clicked,
	        btn25_clicked;
	  
	
	
	//[1] ON CREATE METHOD
	/**
	 * @Method name: onCreate 
	 * @param Bundle savedInstanceState
	 * @return null
	 * @Description: On create method.
	 */
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.papers_screen);
		
		//Get Reference
		btn01 = (Button)findViewById(R.id.btn01_paper);
		btn02 = (Button)findViewById(R.id.btn02_paper);
		btn03 = (Button)findViewById(R.id.btn03_paper);
		btn04 = (Button)findViewById(R.id.btn04_paper);
		
		btn05 = (Button)findViewById(R.id.btn05_paper);
		btn06 = (Button)findViewById(R.id.btn06_paper);
		btn07 = (Button)findViewById(R.id.btn07_paper);
		btn08 = (Button)findViewById(R.id.btn08_paper);
		
		btn09 = (Button)findViewById(R.id.btn09_paper);
		btn10 = (Button)findViewById(R.id.btn10_paper);
		btn11 = (Button)findViewById(R.id.btn11_paper);
		btn12 = (Button)findViewById(R.id.btn12_paper);
		
		btn13 = (Button)findViewById(R.id.btn13_paper);
		btn14 = (Button)findViewById(R.id.btn14_paper);
		btn15 = (Button)findViewById(R.id.btn15_paper);
		btn16 = (Button)findViewById(R.id.btn16_paper);
		
		btn17 = (Button)findViewById(R.id.btn17_paper);
		btn18 = (Button)findViewById(R.id.btn18_paper);
		btn19 = (Button)findViewById(R.id.btn19_paper);
		btn20 = (Button)findViewById(R.id.btn20_paper);
		
		btn21 = (Button)findViewById(R.id.btn21_paper);
		btn22 = (Button)findViewById(R.id.btn22_paper);
		btn23 = (Button)findViewById(R.id.btn23_paper);
		btn24 = (Button)findViewById(R.id.btn24_paper);
		
		btn25 = (Button)findViewById(R.id.btn25_paper);
		
		btn_help = (ImageView)findViewById(R.id.btn_help_papers);
		
		//Listeners
	    btn01.setOnClickListener(this);
	    btn02.setOnClickListener(this);
	    btn03.setOnClickListener(this);
	    btn04.setOnClickListener(this);
	    
	    btn05.setOnClickListener(this);
	    btn06.setOnClickListener(this);
	    btn07.setOnClickListener(this);
	    btn08.setOnClickListener(this);
	    
	    btn09.setOnClickListener(this);
	    btn10.setOnClickListener(this);
	    btn11.setOnClickListener(this);
	    btn12.setOnClickListener(this);
	    
	    btn13.setOnClickListener(this);
	    btn14.setOnClickListener(this);
	    btn15.setOnClickListener(this);
	    btn16.setOnClickListener(this);
	    
	    btn17.setOnClickListener(this);
	    btn18.setOnClickListener(this);
	    btn19.setOnClickListener(this);
	    btn20.setOnClickListener(this);
	    
	    btn21.setOnClickListener(this);
	    btn22.setOnClickListener(this);
	    btn23.setOnClickListener(this);
	    btn24.setOnClickListener(this);
	    
	    btn25.setOnClickListener(this);
	    
	    btn_help.setOnClickListener(this);
	    
	    //set button sates 
	    btn01_clicked = btn02_clicked = btn03_clicked = btn04_clicked =
        btn05_clicked = btn06_clicked = btn07_clicked = btn08_clicked = 
        btn09_clicked = btn10_clicked = btn11_clicked = btn12_clicked = 
        btn13_clicked = btn14_clicked = btn15_clicked = btn16_clicked =
        btn17_clicked = btn18_clicked = btn19_clicked = btn20_clicked =
        btn21_clicked = btn22_clicked = btn23_clicked = btn24_clicked = 
        btn25_clicked = false;  
	    
	    //Methods
	    get_data();
	    read_state();
	}

	//[2] CLICK HANDLER
	/**
	 * @Method name: onClick
	 * @param View
	 * @return null 
	 * @Description: Handles all the buttons clicks and register with the database the button event.
	 */
	public void onClick(View arg0) 
	{
		switch(arg0.getId())
		{
		case R.id.btn01_paper:
			if(btn01_clicked==false){btnId =1; message(arg0); btn01_clicked = true;}else{btnId =-1; message_undo(arg0);btn01_clicked = false;}
			break;
		case R.id.btn02_paper:
			if(btn02_clicked==false){btnId =2; message(arg0); btn02_clicked = true;}else{btnId =-2; message_undo(arg0);btn02_clicked = false;}
			break;
		case R.id.btn03_paper:
			if(btn03_clicked==false){btnId =3; message(arg0); btn03_clicked = true;}else{btnId =-3; message_undo(arg0);btn03_clicked = false;}
			break;
		case R.id.btn04_paper:
			if(btn04_clicked==false){btnId =4; message(arg0); btn04_clicked = true;}else{btnId =-4; message_undo(arg0);btn04_clicked = false;}
			break;
		case R.id.btn05_paper:
			if(btn05_clicked==false){btnId =5; message(arg0); btn05_clicked = true;}else{btnId =-5; message_undo(arg0);btn05_clicked = false;}
			break;
		case R.id.btn06_paper:
			if(!btn06_clicked){btnId =6; message(arg0); btn06_clicked = true;}else{btnId =-6; message_undo(arg0);btn06_clicked = false;}
			break;
		case R.id.btn07_paper:
			if(!btn07_clicked){btnId =7; message(arg0); btn07_clicked = true;}else{btnId =-7; message_undo(arg0);btn07_clicked = false;}
			break;
		case R.id.btn08_paper:
			if(!btn08_clicked){btnId =8; message(arg0); btn08_clicked = true;}else{btnId =-8; message_undo(arg0);btn08_clicked = false;}
			break;
			
		case R.id.btn09_paper:
			if(!btn09_clicked){btnId =9; message(arg0); btn09_clicked = true;}else{btnId =-9; message_undo(arg0);btn09_clicked = false;}
			break;
		case R.id.btn10_paper:
			if(!btn10_clicked){btnId =10; message(arg0); btn10_clicked = true;}else{btnId =-10; message_undo(arg0);btn10_clicked = false;}
			break;
		case R.id.btn11_paper:
			if(!btn11_clicked){btnId =11; message(arg0); btn11_clicked = true;}else{btnId =-11; message_undo(arg0);btn11_clicked = false;}
			break;
		case R.id.btn12_paper:
			if(!btn12_clicked){btnId =12; message(arg0); btn12_clicked = true;}else{btnId =-12; message_undo(arg0);btn12_clicked = false;}
			break;
			
		case R.id.btn13_paper:
			if(!btn13_clicked){btnId =13; message(arg0); btn13_clicked = true;}else{btnId =-13; message_undo(arg0);btn13_clicked = false;}
			break;
		case R.id.btn14_paper:
			if(!btn14_clicked){btnId =14; message(arg0); btn14_clicked = true;}else{btnId =-14; message_undo(arg0);btn14_clicked = false;}
			break;
		case R.id.btn15_paper:
			if(!btn15_clicked){btnId =15; message(arg0); btn15_clicked = true;}else{btnId =-15; message_undo(arg0);btn15_clicked = false;}
			break;
		case R.id.btn16_paper:
			if(!btn16_clicked){btnId =16; message(arg0); btn16_clicked = true;}else{btnId =-16; message_undo(arg0);btn16_clicked = false;}
			break;
			
		case R.id.btn17_paper:
			if(!btn17_clicked){btnId =17; message(arg0); btn17_clicked = true;}else{btnId =-17; message_undo(arg0);btn17_clicked = false;}
			break;
		case R.id.btn18_paper:
			if(!btn18_clicked){btnId =18; message(arg0); btn18_clicked = true;}else{btnId =-18; message_undo(arg0);btn18_clicked = false;}
			break;
		case R.id.btn19_paper:
			if(!btn19_clicked){btnId =19; message(arg0); btn19_clicked = true;}else{btnId =-19; message_undo(arg0);btn19_clicked = false;}
			break;
			
		case R.id.btn20_paper:
			if(!btn20_clicked){btnId =20; message(arg0); btn20_clicked = true;}else{btnId =-20; message_undo(arg0);btn20_clicked = false;}
			break;
			
		case R.id.btn21_paper:
			if(!btn21_clicked){btnId =21; message(arg0); btn21_clicked = true;}else{btnId =-21; message_undo(arg0);btn21_clicked = false;}
			break;
			
		case R.id.btn22_paper:
			if(!btn22_clicked){btnId =22; message(arg0); btn22_clicked = true;}else{btnId =-22; message_undo(arg0);btn22_clicked = false;}
			break;
			
		case R.id.btn23_paper:
			if(!btn23_clicked){btnId =23; message(arg0); btn23_clicked = true;}else{btnId =-23; message_undo(arg0);btn23_clicked = false;}
			break;
			
		case R.id.btn24_paper:
			if(!btn24_clicked){btnId =24; message(arg0); btn24_clicked = true;}else{btnId =-24; message_undo(arg0);btn24_clicked = false;}
			break;
			
		case R.id.btn25_paper:
			if(!btn25_clicked){btnId =25; message(arg0); btn25_clicked = true;}else{btnId =-25; message_undo(arg0);btn25_clicked = false;}
			break;
		case R.id.btn_help_papers:
			try{help_msg(arg0);}catch(Exception e){e.printStackTrace();};
			break;
		}
	}
	
	//[3] GET DATA
	/**
	 * @Method name: get_data 
	 * @param null
	 * @return: null
	 * @Description: Get all the data required by the methods within this class.
	 */
	public void get_data()
	{
        getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    	d = new Data(this);
    	d.open();
    	user = d.read_user(getData.getString("user_name", "User"));
    	total_points = d.read_total_pts(user); 
    	daily_points = d.read_daily_pts(user);
    	d.close();
	}
	
	
	//[4] POP UP NOTIFICATION OF POINTS EARNRED
	/**
	 * @Method name: pop
	 * @param null
	 * @return null
	 * @Description: Pop a message to notify the user that 10 points were earned. 
	 */
    public void popup()
    {
    	Context c = getApplicationContext();
    	CharSequence text = "You have earned "+points+" points";
    	int duration = Toast.LENGTH_SHORT;

    	Toast t= Toast.makeText(c, text, duration);
    	t.setGravity(Gravity.TOP|Gravity.TOP,0,100);
    	t.show();
    }
	
	//[5A] UPDATE POINTS
    /**
	 * @Method name: update_points
	 * @param null
	 * @return integer
	 * @Description: Update all the points and store.
	 */
	public int update_points()
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
	    
	    return points;
	}
	
	//[5B] DECREMENT POINTS
    /**
	 * @Method name: decrement_points
	 * @param null
	 * @return integer
	 * @Description: Decrement all the points and store.
	 */
	public int decrement_points()
	{
		int total = 0;
		int day = 0;
		
		//convert retrieved data from database to string
	    try{total = Integer.parseInt(total_points);
	        day = Integer.parseInt(daily_points);
	        }catch(Exception e){};
	        
	    //Add points
	    day-=points;
	    total-=points;
	    
	    //convert back to string
	    try{
			daily_points= Integer.toString(day);
			total_points = Integer.toString(total);
		}catch(Exception e){}
	    
	    return points;
	}
		
	//[6] SAVE POINTS TO DATABASE
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
	
	//[7] SAVE STATE OF BUTTONS
	/**
	 * @Method name: save_state
	 * @param String
	 * @return null
	 * @Description: Check the button state and save the sate to the database.
	 */
	public void save_state(String name)
	{
		 String state ="";
		 String all_state="";
		 String [] btns_state = new String[25];
		 char [] chars;
		 
		 try{
			 d.open();
			 state = d.btn_read_state(user); //(a)bring state as stored in Main screen i.e all zeros
			 d.close();
			 chars = state.toCharArray(); //(b)convert string to characters

			 for(int i=0;i<chars.length;i++)
			 {
				 btns_state[i] = Character.toString(chars[i]); //(c)save characters in array
			 } 
		 }catch(Exception e){}
		
		if(name =="btn01"){btns_state[0]="1";} //(d)if button is clicked update array
		if(name =="btn02"){btns_state[1]="1";}	
		if(name =="btn03"){btns_state[2]="1";}
		if(name =="btn04"){btns_state[3]="1";}
		
		if(name =="btn05"){btns_state[4]="1";}
		if(name =="btn06"){btns_state[5]="1";}	
		if(name =="btn07"){btns_state[6]="1";}
		if(name =="btn08"){btns_state[7]="1";}
		
		if(name =="btn09"){btns_state[8]="1";}
		if(name =="btn10"){btns_state[9]="1";}	
		if(name =="btn11"){btns_state[10]="1";}
		if(name =="btn12"){btns_state[11]="1";}
		
		if(name =="btn13"){btns_state[12]="1";}
		if(name =="btn14"){btns_state[13]="1";}	
		if(name =="btn15"){btns_state[14]="1";}
		if(name =="btn16"){btns_state[15]="1";}
		
		if(name =="btn17"){btns_state[16]="1";}
		if(name =="btn18"){btns_state[17]="1";}	
		if(name =="btn19"){btns_state[18]="1";}
		if(name =="btn20"){btns_state[19]="1";}
		
		if(name =="btn21"){btns_state[20]="1";}
		if(name =="btn22"){btns_state[21]="1";}	
		if(name =="btn23"){btns_state[22]="1";}
		if(name =="btn24"){btns_state[23]="1";}
		
		if(name =="btn25"){btns_state[24]="1";}

		
		//(e) convert back to string
		for(int i=0; i<btns_state.length;i++)
		{
			all_state = all_state+btns_state[i];
		}
		
		//(f) save updates string to database
		try{
			d.open();
			d.btn_write_state(user,all_state);
			d.close();
		}catch(Exception e){}
	}
	
	//[8] READ STATE OF BUTTONS
	/**
	 * @Method name: read_state
	 * @param null
	 * @return null
	 * @Description: Read the state of the button before draw it to screen. 
	 */
	public void read_state()
	{
		try{
			d.open();
			String state = d.btn_read_state(user);
			char[] individual_state = state.toCharArray();

			for(int i=0; i<individual_state.length;i++)
			{
				if(individual_state[0]== '1'){btn01.setBackgroundResource(R.drawable.check);btn01_clicked = true;}
				if(individual_state[1]== '1'){btn02.setBackgroundResource(R.drawable.check);btn02_clicked = true;}
				if(individual_state[2]== '1'){btn03.setBackgroundResource(R.drawable.check);btn03_clicked = true;}
				if(individual_state[3]== '1'){btn04.setBackgroundResource(R.drawable.check);btn04_clicked = true;}
				
				if(individual_state[4]== '1'){btn05.setBackgroundResource(R.drawable.check);btn05_clicked = true;
				if(individual_state[5]== '1'){btn06.setBackgroundResource(R.drawable.check);btn06_clicked = true;}
				if(individual_state[6]== '1'){btn07.setBackgroundResource(R.drawable.check);btn07_clicked = true;}
				if(individual_state[7]== '1'){btn08.setBackgroundResource(R.drawable.check);btn08_clicked = true;}
				
				if(individual_state[8]== '1'){btn09.setBackgroundResource(R.drawable.check);btn09_clicked = true;}
				if(individual_state[9]== '1'){btn10.setBackgroundResource(R.drawable.check);btn10_clicked = true;}
				if(individual_state[10]== '1'){btn11.setBackgroundResource(R.drawable.check);btn11_clicked = true;}
				if(individual_state[11]== '1'){btn12.setBackgroundResource(R.drawable.check);btn12_clicked = true;}
				
				if(individual_state[12]== '1'){btn13.setBackgroundResource(R.drawable.check);btn13_clicked = true;}
				if(individual_state[13]== '1'){btn14.setBackgroundResource(R.drawable.check);btn14_clicked = true;}
				if(individual_state[14]== '1'){btn15.setBackgroundResource(R.drawable.check);btn15_clicked = true;}
				if(individual_state[15]== '1'){btn16.setBackgroundResource(R.drawable.check);btn16_clicked = true;}
				
				if(individual_state[16]== '1'){btn17.setBackgroundResource(R.drawable.check);btn17_clicked = true;}
				if(individual_state[17]== '1'){btn18.setBackgroundResource(R.drawable.check);btn18_clicked = true;}
				if(individual_state[18]== '1'){btn19.setBackgroundResource(R.drawable.check);btn19_clicked = true;}
				if(individual_state[19]== '1'){btn20.setBackgroundResource(R.drawable.check);btn20_clicked = true;}
				
				if(individual_state[20]== '1'){btn21.setBackgroundResource(R.drawable.check);btn21_clicked = true;}
				if(individual_state[21]== '1'){btn22.setBackgroundResource(R.drawable.check);btn22_clicked = true;}
				if(individual_state[22]== '1'){btn23.setBackgroundResource(R.drawable.check);btn23_clicked = true;}
				if(individual_state[23]== '1'){btn24.setBackgroundResource(R.drawable.check);btn24_clicked = true;}
			  
				if(individual_state[24]== '1'){btn25.setBackgroundResource(R.drawable.check);btn25_clicked = true;}
			
			}
			d.close();
		}}catch(Exception e){}
	}

	//[9] SET HELP
	/**
	 * Method name: message
	 * @param View
	 * @return null
	 * @Description: Ouput the abstract of the paper
	 */
	public void message(View view)
	{
		AlertDialog d = new AlertDialog.Builder(this).create();
		d.setTitle("Abstract");
		d.setMessage(paper_abstract(btnId));    
			         
		d.setButton("READ", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
			  if (btnId==1){popup(); update_points();save_pts_database();save_state("btn01");btn01.setBackgroundResource(R.drawable.check);}
			  if (btnId==2){popup(); update_points();save_pts_database();save_state("btn02");btn02.setBackgroundResource(R.drawable.check);}
			  if (btnId==3){popup(); update_points();save_pts_database();save_state("btn03");btn03.setBackgroundResource(R.drawable.check);}
			  if (btnId==4){popup(); update_points();save_pts_database();save_state("btn04");btn04.setBackgroundResource(R.drawable.check);}
				  
			  if (btnId==5){popup(); update_points();save_pts_database();save_state("btn05");btn05.setBackgroundResource(R.drawable.check);}
			  if (btnId==6){popup(); update_points();save_pts_database();save_state("btn06");btn06.setBackgroundResource(R.drawable.check);}
			  if (btnId==7){popup(); update_points();save_pts_database();save_state("btn07");btn07.setBackgroundResource(R.drawable.check);}
			  if (btnId==8){popup(); update_points();save_pts_database();save_state("btn08");btn08.setBackgroundResource(R.drawable.check);}
			 
			  if (btnId==9) {popup(); update_points();save_pts_database();save_state("btn09");btn09.setBackgroundResource(R.drawable.check);}
			  if (btnId==10){popup(); update_points();save_pts_database();save_state("btn10");btn10.setBackgroundResource(R.drawable.check);}
			  if (btnId==11){popup(); update_points();save_pts_database();save_state("btn11");btn11.setBackgroundResource(R.drawable.check);}
			  if (btnId==12){popup(); update_points();save_pts_database();save_state("btn12");btn12.setBackgroundResource(R.drawable.check);}
			 
			  if (btnId==13){popup(); update_points();save_pts_database();save_state("btn13");btn13.setBackgroundResource(R.drawable.check);}
			  if (btnId==14){popup(); update_points();save_pts_database();save_state("btn14");btn14.setBackgroundResource(R.drawable.check);}
			  if (btnId==15){popup(); update_points();save_pts_database();save_state("btn15");btn15.setBackgroundResource(R.drawable.check);}
			  if (btnId==16){popup(); update_points();save_pts_database();save_state("btn16");btn16.setBackgroundResource(R.drawable.check);}
						  
			  if (btnId==17){popup(); update_points();save_pts_database();save_state("btn17");btn17.setBackgroundResource(R.drawable.check);}
			  if (btnId==18){popup(); update_points();save_pts_database();save_state("btn18");btn18.setBackgroundResource(R.drawable.check);}
			  if (btnId==19){popup(); update_points();save_pts_database();save_state("btn19");btn19.setBackgroundResource(R.drawable.check);}
			  if (btnId==20){popup(); update_points();save_pts_database();save_state("btn20");btn20.setBackgroundResource(R.drawable.check);}
								  
			  if (btnId==21){popup(); update_points();save_pts_database();save_state("btn21");btn21.setBackgroundResource(R.drawable.check);}
			  if (btnId==22){popup(); update_points();save_pts_database();save_state("btn22");btn22.setBackgroundResource(R.drawable.check);}
	          if (btnId==23){popup(); update_points();save_pts_database();save_state("btn23");btn23.setBackgroundResource(R.drawable.check);}
			  if (btnId==24){popup(); update_points();save_pts_database();save_state("btn24");btn24.setBackgroundResource(R.drawable.check);}
								
			  if (btnId==25){popup(); update_points();save_pts_database();save_state("btn25");btn25.setBackgroundResource(R.drawable.check);}						
			}
		});
		d.setIcon(R.drawable.note);
		d.show();	
	}
	
	//[10] MESSAGE UNDO
	/**
	 * Method name: Message Undo
	 * @param View
	 * @return null
	 * @Description: Undo decision 
	 */
	@SuppressWarnings("deprecation")
	public void message_undo(View view)
	{
		AlertDialog d = new AlertDialog.Builder(this).create();
		d.setTitle("Cancel");
		d.setMessage("Are you sure you want to cancel?");    
			         
		d.setButton("Yes", new DialogInterface.OnClickListener() 
		{
			@SuppressLint("NewApi")
			public void onClick(DialogInterface dialog, int which) 
			{
			  if(btnId==-1){undo_state("btn01");decrement_points(); save_pts_database();btn01.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-2){undo_state("btn02");decrement_points(); save_pts_database();btn02.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-3){undo_state("btn03");decrement_points(); save_pts_database();btn03.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-4){undo_state("btn04");decrement_points(); save_pts_database();btn04.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  
			  if(btnId==-5){undo_state("btn05");decrement_points(); save_pts_database();btn05.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-6){undo_state("btn06");decrement_points(); save_pts_database();btn06.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-7){undo_state("btn07");decrement_points(); save_pts_database();btn07.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-8){undo_state("btn08");decrement_points(); save_pts_database();btn08.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  
			  if(btnId==-9){undo_state("btn09");decrement_points(); save_pts_database();btn09.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-10){undo_state("btn10");decrement_points(); save_pts_database();btn10.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-11){undo_state("btn11");decrement_points(); save_pts_database();btn11.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-12){undo_state("btn12");decrement_points(); save_pts_database();btn12.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  
			  if(btnId==-13){undo_state("btn13");decrement_points(); save_pts_database();btn13.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-14){undo_state("btn14");decrement_points(); save_pts_database();btn14.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-15){undo_state("btn15");decrement_points(); save_pts_database();btn15.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-16){undo_state("btn16");decrement_points(); save_pts_database();btn16.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  
			  if(btnId==-17){undo_state("btn17");decrement_points(); save_pts_database();btn17.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-18){undo_state("btn18");decrement_points(); save_pts_database();btn18.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-19){undo_state("btn19");decrement_points(); save_pts_database();btn19.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-20){undo_state("btn20");decrement_points(); save_pts_database();btn20.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  
			  if(btnId==-21){undo_state("btn21");decrement_points(); save_pts_database();btn21.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-22){undo_state("btn22");decrement_points(); save_pts_database();btn22.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-23){undo_state("btn23");decrement_points(); save_pts_database();btn23.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  if(btnId==-24){undo_state("btn24");decrement_points(); save_pts_database();btn24.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			  
			  if(btnId==-25){undo_state("btn25");decrement_points(); save_pts_database();btn25.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_design_whisky));}
			}
		});
		d.setIcon(R.drawable.exclamation);
		d.show();	
	}
	
	
	//[11] CHANGE STATE OF BUTTON FROM DATABASE 
	/**
	 * Method name: undo_state
	 * @param View
	 * @return null
	 * @Description: Change state of button
	 */
	public void undo_state(String name)
	{
		 String state ="";
		 String all_state="";
		 String [] btns_state = new String[25];
		 char [] chars;
		 
		 try{
			 d.open();
			 state = d.btn_read_state(user); //(a)bring state as stored in Main screen i.e all zeros
			 d.close();
			 chars = state.toCharArray(); //(b)convert string to characters

			 for(int i=0;i<chars.length;i++)
			 {
				 btns_state[i] = Character.toString(chars[i]); //(c)save characters in array
			 } 
		 }catch(Exception e){}
		
		if(name =="btn01"){btns_state[0]="0";} //(d)if button is clicked update array to pref state
		if(name =="btn02"){btns_state[1]="0";}	
		if(name =="btn03"){btns_state[2]="0";}
		if(name =="btn04"){btns_state[3]="0";}
		
		if(name =="btn05"){btns_state[4]="0";}
		if(name =="btn06"){btns_state[5]="0";}	
		if(name =="btn07"){btns_state[6]="0";}
		if(name =="btn08"){btns_state[7]="0";}
		
		if(name =="btn09"){btns_state[8]="0";}
		if(name =="btn10"){btns_state[9]="0";}	
		if(name =="btn11"){btns_state[10]="0";}
		if(name =="btn12"){btns_state[11]="0";}
		
		if(name =="btn13"){btns_state[12]="0";}
		if(name =="btn14"){btns_state[13]="0";}	
		if(name =="btn15"){btns_state[14]="0";}
		if(name =="btn16"){btns_state[15]="0";}
		
		if(name =="btn17"){btns_state[16]="0";}
		if(name =="btn18"){btns_state[17]="0";}	
		if(name =="btn19"){btns_state[18]="0";}
		if(name =="btn20"){btns_state[19]="0";}
		
		if(name =="btn21"){btns_state[20]="0";}
		if(name =="btn22"){btns_state[21]="0";}	
		if(name =="btn23"){btns_state[22]="0";}
		if(name =="btn24"){btns_state[23]="0";}
		
		if(name =="btn25"){btns_state[24]="1";}

		
		//(e) convert back to string
		for(int i=0; i<btns_state.length;i++)
		{
			all_state = all_state+btns_state[i];
		}
		
		//(f) save updates string to database
		try{
			d.open();
			d.btn_write_state(user,all_state);
			d.close();
		}catch(Exception e){}
	}
	
	//[12] PAPER ABSTRACTS
	/**
	 * Method name: paper_abstract
	 * @param View
	 * @return null
	 * @Description: Abstracts of papers
	 */
	public String paper_abstract(int btnId)
	{
		if(btnId ==1){ppr_abstract =  readText(btnId);}
		if(btnId ==2){ppr_abstract =  readText(btnId);}
		if(btnId ==3){ppr_abstract =  readText(btnId);}
		if(btnId ==4){ppr_abstract =  readText(btnId);}
		
		if(btnId ==5){ppr_abstract =  readText(btnId);}
		if(btnId ==6){ppr_abstract =  readText(btnId);}
		if(btnId ==7){ppr_abstract =  readText(btnId);}
		if(btnId ==8){ppr_abstract =  readText(btnId);}
		
		if(btnId ==9){ppr_abstract =  readText(btnId);}
		if(btnId ==10){ppr_abstract =  readText(btnId);}
		if(btnId ==11){ppr_abstract =  readText(btnId);}
		if(btnId ==12){ppr_abstract =  readText(btnId);}
		
		if(btnId ==13){ppr_abstract =  readText(btnId);}
		if(btnId ==14){ppr_abstract =  readText(btnId);}
		if(btnId ==15){ppr_abstract =  readText(btnId);}
		if(btnId ==16){ppr_abstract =  readText(btnId);}
		
		if(btnId ==17){ppr_abstract =  readText(btnId);}
		if(btnId ==18){ppr_abstract =  readText(btnId);}
		if(btnId ==19){ppr_abstract =  readText(btnId);}
		if(btnId ==20){ppr_abstract =  readText(btnId);}
		
		if(btnId ==21){ppr_abstract =  readText(btnId);}
		if(btnId ==22){ppr_abstract =  readText(btnId);}
		if(btnId ==23){ppr_abstract =  readText(btnId);}
		if(btnId ==24){ppr_abstract =  readText(btnId);}
		
		if(btnId ==25){ppr_abstract =  readText(btnId);}
		
		return ppr_abstract;
	}
	
	//[13] SET HELP
	/**
	 * Method name: message
	 * @param View
	 * @return null
	 * @Description: Set the help 
	 */
	public void help_msg(View view)
	{
		AlertDialog d = new AlertDialog.Builder(this).create();
		d.setTitle("Progress Papers");
		d.setMessage(
		            "Click on the Buttons to discover paper's authors and abstract."+
				    " Read papers and earn 15 points with every read."+
		            " Clicked the wrong paper? No problem. Just re-click to undo your decision.");    
			         
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
	
	public String readText(int btnId)
	{
		StringBuilder contents = new StringBuilder();
		String sep = System.getProperty("line.separator");
		
		try {			
			if(btnId==1){is = getResources().openRawResource(R.raw.abstract01);}
			if(btnId==2){is = getResources().openRawResource(R.raw.abstract02);}
			if(btnId==3){is = getResources().openRawResource(R.raw.abstract03);}
			if(btnId==4){is = getResources().openRawResource(R.raw.abstract04);}
			
			if(btnId==5){is = getResources().openRawResource(R.raw.abstract05);}
			if(btnId==6){is = getResources().openRawResource(R.raw.abstract06);}
			if(btnId==7){is = getResources().openRawResource(R.raw.abstract07);}
			if(btnId==8){is = getResources().openRawResource(R.raw.abstract08);}
			
			if(btnId==9){is = getResources().openRawResource(R.raw.abstract09);}
			if(btnId==10){is = getResources().openRawResource(R.raw.abstract10);}
			if(btnId==11){is = getResources().openRawResource(R.raw.abstract11);}
			if(btnId==12){is = getResources().openRawResource(R.raw.abstract12);}
			
			if(btnId==13){is = getResources().openRawResource(R.raw.abstract13);}
			if(btnId==14){is = getResources().openRawResource(R.raw.abstract14);}
			if(btnId==15){is = getResources().openRawResource(R.raw.abstract15);}
			if(btnId==16){is = getResources().openRawResource(R.raw.abstract16);}
			
			if(btnId==17){is = getResources().openRawResource(R.raw.abstract17);}
			if(btnId==18){is = getResources().openRawResource(R.raw.abstract18);}
			if(btnId==19){is = getResources().openRawResource(R.raw.abstract19);}
			if(btnId==20){is = getResources().openRawResource(R.raw.abstract20);}
			
			if(btnId==21){is = getResources().openRawResource(R.raw.abstract21);}
			if(btnId==22){is = getResources().openRawResource(R.raw.abstract22);}
			if(btnId==23){is = getResources().openRawResource(R.raw.abstract23);}
			if(btnId==24){is = getResources().openRawResource(R.raw.abstract24);}
			if(btnId==25){is = getResources().openRawResource(R.raw.abstract25);}

			BufferedReader input =  new BufferedReader(new InputStreamReader(is), 1024*8);
			try {
				String line = null; 
				while (( line = input.readLine()) != null){
					contents.append(line);
					contents.append(sep);
				}
			}catch(Exception e){}
		}
		catch (Exception e){}

		return contents.toString();
	}
}

