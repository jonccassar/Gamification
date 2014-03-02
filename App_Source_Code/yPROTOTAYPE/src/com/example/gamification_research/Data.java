package com.example.gamification_research;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Jonathan Cassar
 * @Class Name: Data
 * @Description: Provides methods to read and write in the SQLlite database.
 */
public class Data 
{
	//DATABASE NAME 
	private static final String DATABASE_NAME = "db_yprototaype";

	//DATABASE TABLE
	public static final String TBL_USERS = "tbl_users";

	//DATABASE TABLE HEADERS
	public static final String KEY_NAME = "user_name";
	public static final String KEY_DAILY_PTS = "daily_pts";
	public static final String KEY_START_DATE = "start_date";
	public static final String KEY_LEVEL = "level";
	public static final String KEY_DISSERTATION_LENGTH = "dissertation_length";
	public static final String KEY_STATE = "state";
	public static final String KEY_INTRO_STATE = "intro_state";
	public static final String KEY_LITREVIEW_STATE = "litreview_state";
	public static final String KEY_METOD_STATE = "metod_state";
	public static final String KEY_RESUL_STATE = "result_state";
	public static final String KEY_CONCL_STATE = "concl_state";
	public static final String KEY_EVALUATION_STATE = "evaluation_state";
	public static final String KEY_TOTAL_POINTS = "total_pts";
	
	
	//DATABASE METADATA - VERSION
	private static final int DATABASE_VERSION = 1;

	//VARIABLES 
	private dbHelper ypr_helper;
	private final Context ypr_context;
	private SQLiteDatabase ypr_my_database;


	//[1]CONSTRUCTOR
	/**
	 * @Method name: Data
	 * @param Context
	 * @return null
	 * @Description: Class constructor.
	 */ 
	public Data(Context c)
	{
		this.ypr_context = c;
	}

	//[2]DATABASE HELPER
	/**
	 * @Method name: dbHelper
	 * @param null
	 * @return null
	 * @Extends SQLiteOpenHelper
	 * @Description: Inner class which creates a database helper.
	 */ 
	public static class dbHelper extends SQLiteOpenHelper
	{
		//CONSTRUCTOR - dbHelper class
		public dbHelper(Context context) 
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		//CREATE SQL TABLE
		/**
		 * @Method name: onCreate
		 * @param Datatbase 
		 * @return null
		 * @Description: TSQL to create the database 
		 */ 
		public void onCreate(SQLiteDatabase db) 
		{
			db.execSQL("CREATE TABLE "+TBL_USERS +" (" +
					KEY_NAME + " TEXT PRIMARY KEY NOT NULL, "+
					KEY_DAILY_PTS + " INTEGER, " +
					KEY_START_DATE + " INTEGER, " +
					KEY_LEVEL + " INTEGER, " +
					KEY_DISSERTATION_LENGTH + " INTEGER, " +
					KEY_STATE + " TEXT, " +
					KEY_INTRO_STATE + " TEXT, " +
                    KEY_LITREVIEW_STATE + " TEXT, " +
                    KEY_METOD_STATE  + " TEXT, " +
                    KEY_RESUL_STATE + " TEXT, " +
                    KEY_CONCL_STATE + " TEXT, " +
                    KEY_EVALUATION_STATE + " TEXT, "+
					KEY_TOTAL_POINTS + " INTEGER);"  
					);
		}

		//UPGRATE SQL TABLE
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			db.execSQL("DROP TABLE IF EXIST "+ TBL_USERS);
			onCreate(db);
		}
	}

	//[3] OPEN DATABASE 
	/**
	 * @Method name: open
	 * @param null
	 * @return null
	 * @Description: Open the database. 
	 */ 
	public Data open()
	{
		ypr_helper = new dbHelper(ypr_context);
		ypr_my_database =  ypr_helper.getWritableDatabase();
		return this;
	}

	//[4] CLOSE DATABASE
	/**
	 * @Method name: close
	 * @param null
	 * @return null
	 * @Description: Closes the database.
	 */ 
	public void close()
	{
		ypr_helper.close();
	}
		
	//[5] READ - USER
	/**
	 * @Method name: read_user
	 * @param String
	 * @return String
	 * @Description: Reads the user from the database. 
	 */ 
	public String read_user(String user) 
	{
		String loged_in ="";

		String [] col  =  new String[]{KEY_NAME};
		Cursor c  = ypr_my_database.query(TBL_USERS,col, null, null, null, null, null);

		int iUser = c.getColumnIndex(KEY_NAME);

		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext())
		{
			if(c.getString(iUser).equals(user))
			{
				loged_in = c.getString(iUser);
			}
		}
		return loged_in;
	}
	
	//[6] READ - TOTAL POINTS
	/**
	 * @Method name: read_total_pts
	 * @param String
	 * @return String
	 * @Description: Reads the total points. 
	 */ 
	public String read_total_pts(String user)
	{
		String totalpts="";

		String [] col  =  new String[]{KEY_NAME,KEY_TOTAL_POINTS};
		Cursor c  = ypr_my_database.query(TBL_USERS,col, null, null, null, null, null);

		int iUser = c.getColumnIndex(KEY_NAME);
		int iTotalPts = c.getColumnIndex(KEY_TOTAL_POINTS);

		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext())
		{
			if(c.getString(iUser).equals(user))
			{
				totalpts = c.getString(iTotalPts);
			}
		}
		return totalpts;
	}

	//[7] READ - DAILY POINTS
	/**
	 * @Method name: read_daily_pts
	 * @param String
	 * @return String
	 * @Description: Reads the daily points. 
	 */ 
	public String read_daily_pts(String user)
	{
		String dailypts="";

		String [] col  =  new String[]{KEY_NAME, KEY_DAILY_PTS};
		Cursor c  = ypr_my_database.query(TBL_USERS,col, null, null, null, null, null);

		int iUser = c.getColumnIndex(KEY_NAME);
		int iDailyPts = c.getColumnIndex(KEY_DAILY_PTS);

		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext())
		{
			if(c.getString(iUser).equals(user))
			{
				dailypts = c.getString(iDailyPts);
			}
		}
		return dailypts;
	}

	//[8] WRITE - USER
	/**
	 * @Method name: write_user
	 * @param String
	 * @return null
	 * @Description: Reads a user to the database. 
	 */ 
	public void write_user(String name) 
	{
		ContentValues cv =  new ContentValues();
		cv.put(KEY_NAME, name);
		ypr_my_database.insert(TBL_USERS, null, cv);
	}
	
	//[9] WRITE - DAILY POINTS TO DATABASE
	/**
	 * @Method name: write_daily
	 * @param String
	 * @param String
	 * @return null
	 * @Description: Write daily points to database. 
	 */ 
	public void write_daily(String user, String pt_days)
	{
		ContentValues cv =  new ContentValues();
		String where = "user_name=?";
		String[]where_args = new String[]{user};
		cv.put(KEY_DAILY_PTS,pt_days);
		ypr_my_database.update(TBL_USERS,cv,where,where_args);
	}
	
	//[10] WRITE - TOTAL POINTS TO DATABASES 
	/**
	 * @Method name: write_total
	 * @param String
	 * @param String
	 * @return null
	 * @Description: Write total points to database. 
	 */ 
	public void write_total(String user, String pt_total)
	{
		ContentValues cv =  new ContentValues();
		String where = "user_name=?";
		String[]where_args = new String[]{user};
		cv.put(KEY_TOTAL_POINTS,pt_total);
		ypr_my_database.update(TBL_USERS,cv,where,where_args);
	}

    //[11] DELETE ALL DATA
	/**
	 * @Method name: delete_alla
	 * @param null
	 * @return null
	 * @Description: Deletes all data from database
	 */ 
	public void delete_all()
	{
		ypr_my_database.delete(TBL_USERS, null, null);
	}
	
	//[12] DELETE DAILY POINTS
	/**
	 * @Method name: reset_daily
	 * @param null
	 * @return null
	 * @Description: reset all daily data from database by write all fields with 0
	 */ 
	public void reset_daily()
	{
	   ContentValues cv =  new ContentValues();
	   cv.put(KEY_DAILY_PTS, "0");
	   ypr_my_database.update(TBL_USERS, cv, null, null);
	}
	
	//[13] READ - LEVEL
	/**
	 * @Method name: read_level
	 * @param String
	 * @return String
	 * @Description: Reads the current level reached by the user from the database. 
	 */ 
	public String read_level(String user)
	{
		String level="";

		String [] col  =  new String[]{KEY_NAME, KEY_LEVEL};
		Cursor c  = ypr_my_database.query(TBL_USERS,col, null, null, null, null, null);

		int iUser = c.getColumnIndex(KEY_NAME);
		int iDailyPts = c.getColumnIndex(KEY_LEVEL);

		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext())
		{
			if(c.getString(iUser).equals(user))
			{
				level = c.getString(iDailyPts);
			}
		}
		return level;
		
	}	
	
	//[14] WRITE - LEVEL
	/**
	 * @Method name: write_level
	 * @param String
	 * @param String
	 * @return null
	 * @Description: Write level reached by user to database. 
	 */ 
	public void write_level(String user, String level)
	{
		ContentValues cv =  new ContentValues();
		String where = "user_name=?";
		String[]where_args = new String[]{user};
		cv.put(KEY_LEVEL,level);
		ypr_my_database.update(TBL_USERS,cv,where,where_args);
	}
    
	//[15] WRITE - STATE 
	/**
	 * @Method name: write_total
	 * @param String
	 * @param String
	 * @return null
	 * @Description: Write total points to database.
	 */ 
	public void btn_write_state(String user, String state)
	{
		ContentValues cv =  new ContentValues();
		String where = "user_name=?";
		String[]where_args = new String[]{user};
		cv.put(KEY_STATE,state);
		ypr_my_database.update(TBL_USERS,cv,where,where_args);	
	}
	
	//[16] READ - STATE 
	/**
	 * @Method name: btn_read_state
	 * @param String
	 * @return null
	 * @Description: Read the state of the button.
	 */ 
	public String btn_read_state(String user)
	{
		String state="";

		String [] col  =  new String[]{KEY_NAME, KEY_STATE};
		Cursor c  = ypr_my_database.query(TBL_USERS,col, null, null, null, null, null);

		int iUser = c.getColumnIndex(KEY_NAME);
		int iState = c.getColumnIndex(KEY_STATE);

		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext())
		{
			if(c.getString(iUser).equals(user))
			{
				state = c.getString(iState);
			}
		}
		return state;
	}
	
	//[17] READ - INTRODUCTION STATE
	/**
	 * @Method name: read_intro_state
	 * @param String
	 * @return String
	 * @Description: Read the state of the seek Bar (introduction).
	 */ 
	public String read_intro_state(String user)
	{
		String progress_state="";

		String [] col  =  new String[]{KEY_NAME, KEY_INTRO_STATE};
		Cursor c  = ypr_my_database.query(TBL_USERS,col, null, null, null, null, null);

		int iUser = c.getColumnIndex(KEY_NAME);
		int iProgressState = c.getColumnIndex(KEY_INTRO_STATE);

		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext())
		{
			if(c.getString(iUser).equals(user))
			{
				progress_state = c.getString(iProgressState);
			}
		}
		return progress_state;
	}
	
	//[18] WRITE - PROGRESS STATE
	/**
	 * @Method name: write_intro_state
	 * @param String
	 * @param String
	 * @Description: Write the state of the seek Bar (introduction).
	 */ 
	public void write_intro_state(String user, String state)
	{
		ContentValues cv =  new ContentValues();
		String where = "user_name=?";
		String[]where_args = new String[]{user};
		cv.put(KEY_INTRO_STATE,state);
		ypr_my_database.update(TBL_USERS,cv,where,where_args);	
	}
 
	//[19] READ - LITERATURE REVIEW STATE
	/**
	 * @Method name: read_litreview_state
	 * @param String
	 * @return String
	 * @Description: Read the state of the seek Bar (Lit Review).
	 */ 
	public String read_litreview_state (String user)
	{
		String progress_state="";

		String [] col  =  new String[]{KEY_NAME, KEY_LITREVIEW_STATE};
		Cursor c  = ypr_my_database.query(TBL_USERS,col, null, null, null, null, null);

		int iUser = c.getColumnIndex(KEY_NAME);
		int iProgressState = c.getColumnIndex(KEY_LITREVIEW_STATE);

		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext())
		{
			if(c.getString(iUser).equals(user))
			{
				progress_state = c.getString(iProgressState);
			}
		}
		return progress_state;
	}
	
	//[20] WRITE - LIT REVIEW STATE
	/**
	 * @Method name: write_literview_state
	 * @param String
	 * @param String
	 * @Description: Write the state of the seek Bar (Literature review).
	 */ 
	public void write_literview_state (String user, String state)
	{
		ContentValues cv =  new ContentValues();
		String where = "user_name=?";
		String[]where_args = new String[]{user};
		cv.put(KEY_LITREVIEW_STATE,state);
		ypr_my_database.update(TBL_USERS,cv,where,where_args);	
	}
	
	//[21] READ - METOD STATE
	/**
	 * @Method name: read_metod_state
	 * @param String
	 * @return String
	 * @Description: Read the state of the seek Bar (Methodology).
	 */ 
	public String read_metod_state(String user)
	{
		String progress_state="";

		String [] col  =  new String[]{KEY_NAME, KEY_METOD_STATE};
		Cursor c  = ypr_my_database.query(TBL_USERS,col, null, null, null, null, null);

		int iUser = c.getColumnIndex(KEY_NAME);
		int iProgressState = c.getColumnIndex(KEY_METOD_STATE);

		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext())
		{
			if(c.getString(iUser).equals(user))
			{
				progress_state = c.getString(iProgressState);
			}
		}
		return progress_state;
	}
    
	//[22] WRITE - METOD STATE
	/**
	 * @Method name: write_metod_state
	 * @param String
	 * @param String
	 * @Description: Write the state of the seek Bar (Methodology).
	 */ 
	public void write_metod_state (String user, String state)
	{
		ContentValues cv =  new ContentValues();
		String where = "user_name=?";
		String[]where_args = new String[]{user};
		cv.put(KEY_METOD_STATE,state);
		ypr_my_database.update(TBL_USERS,cv,where,where_args);	
	}
	
	//[23] READ - RESULT STATE 
	/**
	 * @Method name: read_result_state
	 * @param String
	 * @return String
	 * @Description: Read the state of the seek Bar (Result).
	 */ 
	public String read_result_state(String user)
	{
		String progress_state="";

		String [] col  =  new String[]{KEY_NAME, KEY_RESUL_STATE};
		Cursor c  = ypr_my_database.query(TBL_USERS,col, null, null, null, null, null);

		int iUser = c.getColumnIndex(KEY_NAME);
		int iProgressState = c.getColumnIndex(KEY_RESUL_STATE);

		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext())
		{
			if(c.getString(iUser).equals(user))
			{
				progress_state = c.getString(iProgressState);
			}
		}
		return progress_state;
	}
	
	//[24] WRITE - RESULT STATE
	/**
	 * @Method name: write_res_state
	 * @param String
	 * @param String
	 * @Description: Write the state of the seek Bar (Results).
	 */ 
	public void write_res_state (String user, String state)
	{
		ContentValues cv =  new ContentValues();
		String where = "user_name=?";
		String[]where_args = new String[]{user};
		cv.put(KEY_RESUL_STATE,state);
		ypr_my_database.update(TBL_USERS,cv,where,where_args);	
	}
	
	//[26] READ - CONCLUSION STATE
	/**
	 * @Method name: read_concl_state
	 * @param String
	 * @return String
	 * @Description: Read the state of the seek Bar (Conclusion).
	 */ 
	public String read_concl_state(String user)
	{
		String progress_state="";

		String [] col  =  new String[]{KEY_NAME, KEY_CONCL_STATE};
		Cursor c  = ypr_my_database.query(TBL_USERS,col, null, null, null, null, null);

		int iUser = c.getColumnIndex(KEY_NAME);
		int iProgressState = c.getColumnIndex(KEY_CONCL_STATE);

		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext())
		{
			if(c.getString(iUser).equals(user))
			{
				progress_state = c.getString(iProgressState);
			}
		}
		return progress_state;
	}
	
	//[27] WRITE - CONCLUSION STATE
	/**
	 * @Method name: write_conc_state
	 * @param String
	 * @param String
	 * @Description: Write the state of the seek Bar (Conclusion).
	 */ 
	public void write_conc_state(String user, String state)
	{
		ContentValues cv =  new ContentValues();
		String where = "user_name=?";
		String[]where_args = new String[]{user};
		cv.put(KEY_CONCL_STATE,state);
		ypr_my_database.update(TBL_USERS,cv,where,where_args);	
	}
	
	//[28] WRITE - EVALUATION STATE
	/**
	 * @Method name: write_evaluation_state
	 * @param String
	 * @param String
	 * @Description: Write the evaluation of the seek Bar
	 */ 
	public void write_evaluation_state(String user, String state)
	{
		ContentValues cv =  new ContentValues();
		String where = "user_name=?";
		String[]where_args = new String[]{user};
		cv.put(KEY_EVALUATION_STATE,state);
		ypr_my_database.update(TBL_USERS,cv,where,where_args);	
	}
	
	//[29] READ - EVALUATION STATE
	/**
	 * @Method name: read_evaluation_state
	 * @param String
	 * @return String
	 * @Description: Read the evaluation of the seek Bar
	 */ 
	public String read_evaluation_state(String user)
	{
		String evaluation_state="";

		String [] col  =  new String[]{KEY_NAME, KEY_EVALUATION_STATE};
		Cursor c  = ypr_my_database.query(TBL_USERS,col, null, null, null, null, null);

		int iUser = c.getColumnIndex(KEY_NAME);
		int iProgressState = c.getColumnIndex(KEY_EVALUATION_STATE);

		for(c.moveToFirst(); !c.isAfterLast();c.moveToNext())
		{
			if(c.getString(iUser).equals(user))
			{
				evaluation_state = c.getString(iProgressState);
			}
		}
		return evaluation_state;
	}
	
}
