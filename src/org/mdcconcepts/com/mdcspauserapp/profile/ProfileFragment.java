package org.mdcconcepts.com.mdcspauserapp.profile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.todddavies.components.progressbar.ProgressWheel;

public class ProfileFragment extends FragmentActivity {

	public static View rootView;

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// ids
	// private static final String TAG_SUCCESS = "success";
	// private static final String TAG_MESSAGE = "message";

	TextView Name;
	TextView PersonalInfoTitle;
	TextView Mobile;
	TextView Email;
	TextView Address;
	TextView DOB;
	TextView Anniversary;

	EditText Edt_Mobile;
	EditText Edt_Email;
	EditText Edt_Addr;
	EditText Edt_DOB;
	EditText Edt_Anniversary;

	Button Sign_Up_ButtonController;
	Button Button_Edit;
	Button Button_Save;

	ImageView ImageView_Edit_Number;
	ImageView ImageView_Edit_Addr;
	ImageView ImageView_Edit_DOB;
	ImageView ImageView_Edit_Anniversary;

	private Calendar cal;
	private int day;
	private int month;
	private int year;

	Typeface font;
	public ProfileFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_profile);

		// Name = (TextView) findViewById(R.id.Name);
		Mobile = (TextView) findViewById(R.id.Mobile);
		Email = (TextView) findViewById(R.id.Email);
		Address = (TextView) findViewById(R.id.Address);
		DOB = (TextView) findViewById(R.id.DOB);
		Anniversary = (TextView) findViewById(R.id.Anniversary);

		ImageView_Edit_Number = (ImageView) findViewById(R.id.ImageView_Edit_Number);
		ImageView_Edit_Addr = (ImageView) findViewById(R.id.ImageView_Edit_Addr);
		ImageView_Edit_DOB = (ImageView) findViewById(R.id.ImageView_Edit_DOB);
		ImageView_Edit_Anniversary = (ImageView) findViewById(R.id.ImageView_Edit_Anniversary);
		//
		Edt_Mobile = (EditText) findViewById(R.id.EditText_Mobile);
		Edt_Addr = (EditText) findViewById(R.id.EditText_Address);
		Edt_DOB = (EditText) findViewById(R.id.EditText_DOB);
		Edt_Anniversary = (EditText) findViewById(R.id.EditText_Anniversary);

		Button_Save = (Button) findViewById(R.id.btn_save_profile);

		PersonalInfoTitle = (TextView) findViewById(R.id.FontTextView_PersonalInfo);
		PersonalInfoTitle.setTextSize(20);
		PersonalInfoTitle.setTextColor(Color.parseColor("#ffffff"));

		 font = Typeface.createFromAsset(getAssets(),
				"Raleway-Light.otf");
		// Name.setTypeface(font);
		Mobile.setTypeface(font);
		Email.setTypeface(font);
		Address.setTypeface(font);
		DOB.setTypeface(font);
		Anniversary.setTypeface(font);

		Edt_Mobile.setTypeface(font);
		Edt_Addr.setTypeface(font);
		Edt_DOB.setTypeface(font);
		Edt_Anniversary.setTypeface(font);
		//
		Button_Save.setTypeface(font);

		// Name.setText(Util.User_Name);
		Mobile.setText(Util.User_Contact_Number);
		Email.setText(Util.User_EmailId);
		Address.setText(Util.User_Address);
		DOB.setText(Util.User_DOB);
		Anniversary.setText(Util.User_Anniversary);

		Edt_Mobile.setText(Util.User_Contact_Number);
		Edt_Addr.setText(Util.User_Address);
		Edt_DOB.setText(Util.User_DOB);
		Edt_Anniversary.setText(Util.User_Anniversary);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);

		Edt_DOB.setInputType(InputType.TYPE_NULL);
		Edt_Anniversary.setInputType(InputType.TYPE_NULL);
		ImageView_Edit_Number.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Button_Save.setVisibility(View.VISIBLE);
				Mobile.setVisibility(View.GONE);
				Edt_Mobile.setVisibility(View.VISIBLE);
				ImageView_Edit_Number.setVisibility(View.GONE);
			}
		});

		ImageView_Edit_Addr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Button_Save.setVisibility(View.VISIBLE);
				Address.setVisibility(View.GONE);
				Edt_Addr.setVisibility(View.VISIBLE);
				ImageView_Edit_Addr.setVisibility(View.GONE);
			}
		});

		ImageView_Edit_DOB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Button_Save.setVisibility(View.VISIBLE);
				DOB.setVisibility(View.GONE);
				Edt_DOB.setVisibility(View.VISIBLE);
				ImageView_Edit_DOB.setVisibility(View.GONE);
			}
		});
		ImageView_Edit_Anniversary.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Button_Save.setVisibility(View.VISIBLE);
				Anniversary.setVisibility(View.GONE);
				Edt_Anniversary.setVisibility(View.VISIBLE);
				ImageView_Edit_Anniversary.setVisibility(View.GONE);
			}
		});

		Button_Save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Edt_Mobile.getText().toString().trim().isEmpty())
					Edt_Mobile.setError("Please Enter Mobile Number");
				else
					if(Edt_Addr.getText().toString().trim().isEmpty())
						Edt_Addr.setError("Please Enter Adrress");
					else
						if(Edt_DOB.getText().toString().trim().isEmpty())
							Edt_DOB.setError("Please Enter DOB");
						else
							new UpdateUserDetails().execute();
			}
		});
		 
	}

	public void showDatepickerDialog(final View v) {
		InputMethodManager imm = (InputMethodManager)getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(Edt_DOB.getWindowToken(), 0);
		DatePickerDialog mDatePicker=new DatePickerDialog(ProfileFragment.this, new OnDateSetListener() {                  
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub                      
                /*      Your code   to get date and time    */
            	if(v==Edt_DOB)
            	Edt_DOB.setText(selectedyear+"-"+(selectedmonth+1)+"-"+selectedday);
            	else
            		Edt_Anniversary.setText(selectedyear+"-"+(selectedmonth+1)+"-"+selectedday);
            }
        },year, month, day);
        mDatePicker.setTitle("Select date");                
        mDatePicker.show(); 
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// NavUtils.navigateUpFromSameTask(this);

			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class UpdateUserDetails extends AsyncTask<String, String, String>
	{
		private Dialog dialog;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		String Name1;
		String Mobile1;
		String Email1;
		String Address1;
		String DOB1;
		String Anniversary1;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			dialog = new Dialog(ProfileFragment.this, R.style.ThemeWithCorners);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.custom_progress_dialog);
			dialog.setCancelable(false);
			dialog.show();

			TextView Txt_Title = (TextView) dialog
					.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);
			Txt_Title.setText("Updating Details....");

			ProgressWheel pw_four = (ProgressWheel) dialog
					.findViewById(R.id.progressBarFour);
			pw_four.spin();

			Mobile1= Edt_Mobile.getText().toString();
			Address1=Edt_Addr.getText().toString();
			DOB1=Edt_DOB.getText().toString();
			Anniversary1=Edt_Anniversary.getText().toString();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			final String TAG_SUCCESS = "success";;
			int success;

			try {
				
				// Building Parameters
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("Uid", String
						.valueOf(Util.Uid)));
				param.add(new BasicNameValuePair("Contact", Mobile1));
				param.add(new BasicNameValuePair("Addr", Address1));
				param.add(new BasicNameValuePair("DOB", DOB1));
				param.add(new BasicNameValuePair("Anniversary", Anniversary1));


				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.UpdateUserDetails, "POST", param);

				// full json response
				Log.d("Update Status", json.toString());
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					
					
				}
			}
			catch(Exception e)
			{
				
			}
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			Mobile.setText(Mobile1);
			Address.setText(Address1);
			DOB.setText(DOB1);
			Anniversary.setText(Anniversary1);
			
			Button_Save.setVisibility(View.GONE);
			
			Edt_Mobile.setVisibility(View.GONE);
			Edt_Addr.setVisibility(View.GONE);
			Edt_DOB.setVisibility(View.GONE);
			Edt_Anniversary.setVisibility(View.GONE);
			
			Mobile.setVisibility(View.VISIBLE);
			Address.setVisibility(View.VISIBLE);
			DOB.setVisibility(View.VISIBLE);
			Anniversary.setVisibility(View.VISIBLE);
			
			
			ImageView_Edit_Number.setVisibility(View.VISIBLE);
			ImageView_Edit_Addr.setVisibility(View.VISIBLE);
			ImageView_Edit_DOB .setVisibility(View.VISIBLE);
			ImageView_Edit_Anniversary .setVisibility(View.VISIBLE);
			
			Util.User_Contact_Number=Mobile1;
			Util.User_Address=Address1;
			Util.User_DOB=DOB1;
			Util.User_Anniversary=Anniversary1;
			
			
			dialog.dismiss();
			
		}
	}
}