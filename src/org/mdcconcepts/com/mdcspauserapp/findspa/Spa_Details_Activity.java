package org.mdcconcepts.com.mdcspauserapp.findspa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.notification.MyReceiver;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class Spa_Details_Activity extends FragmentActivity {
	String Spa_Name, Spa_Id1;
	EditText edt_spa_name;

	Button date_ButtonController_SpaDetails;
	Button Time_ButtonController_SpaDetails;
	Button ButtonController_ChooseTherapist_SpaDetails;
	Button MakeAppointment_ButtonController;
	Button btn_view_spa_profile;

	TextView Txt_Spa_name;
	TextView Txt_Spa_Addr;
	TextView Txt_Spa_desc;
	private PendingIntent pendingIntent;
	private boolean isTherapistset = false;
	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	private List<String> ArrayList_AllTherapiesList = new ArrayList<String>();
	private ArrayList<String> ArrayList_AllTherapiesIdList = new ArrayList<String>();

	private List<String> ArrayList_AllTimeForServiceList = new ArrayList<String>();
	private ArrayList<String> ArrayList_AllPriceForServiceList = new ArrayList<String>();
	private ArrayList<String> ArrayList_AllIDForServiceList = new ArrayList<String>();

	Spinner SpinnerController_SpaList_Spa_Details;
	Spinner SpinnerController_TherapyList_Spa_Details;
	Spinner SpinnerController_TimeForServicelist_Spa_Details;

	TextView AppointmentDate_TextViewController_Spa_Details;
	TextView AppointmentTime_TextViewController_Spa_Details;
	TextView TextViewController_YourTherapist_Spa_Details;
	TextView TextViewController_Price_Spa_Details;

	private int Spa_Id;
	private int Therapies_Id;
	private Calendar cal;
	private int day;
	private int month;
	private int year;
	private int hour;
	private int minute;
	private int AM_PM;
	private String Therapist_Id;

	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spa_details);

		Intent i = getIntent();

		Spa_Name = i.getStringExtra("Spa_Name");
		Spa_Id = Integer.parseInt(i.getStringExtra("Spa_Id"));

		Txt_Spa_Addr = (TextView) findViewById(R.id.txt_spa_addr);
		Txt_Spa_desc = (TextView) findViewById(R.id.txt_spa_dec);
		Txt_Spa_name = (TextView) findViewById(R.id.txt_spa_name);

		Typeface font = Typeface.createFromAsset(getAssets(),
				"Raleway-Light.otf");

		Txt_Spa_name.setTypeface(font);
		Txt_Spa_desc.setTypeface(font);
		Txt_Spa_Addr.setTypeface(font);

		Txt_Spa_name.setText(Spa_Name);

		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Tab spa_profile=actionBar.newTab().setText("Spa Profile");
		// actionBar.addTab(spa_profile);

		// edt_spa_name = (EditText) findViewById(R.id.edt_spa_details_name);
		//
		// edt_spa_name.setText(Spa_Name);
		//
		// SpinnerController_TherapyList_Spa_Details = (Spinner)
		// findViewById(R.id.SpinnerController_TherapyList_SpaDetails);
		//
		// SpinnerController_TimeForServicelist_Spa_Details = (Spinner)
		// findViewById(R.id.SpinnerController_TimeForServicelist_SpaDetails);
		//
		// TextViewController_Price_Spa_Details = (TextView)
		// findViewById(R.id.TextViewController_Price_Spa_Details);
		//
		// TextViewController_YourTherapist_Spa_Details = (TextView)
		// findViewById(R.id.TextViewController_YourTherapist_Spa_Details);
		//
		// AppointmentDate_TextViewController_Spa_Details = (TextView)
		// findViewById(R.id.AppointmentDate_TextViewController_Spa_Details);
		//
		// AppointmentTime_TextViewController_Spa_Details = (TextView)
		// findViewById(R.id.AppointmentTime_TextViewController_Spa_Details);
		//
		// date_ButtonController_SpaDetails = (Button)
		// findViewById(R.id.Date_ButtonController_Spa_Details);
		//
		// ButtonController_ChooseTherapist_SpaDetails = (Button)
		// findViewById(R.id.ButtonController_ChooseTherapist_Spa_Details);
		//
		// Time_ButtonController_SpaDetails = (Button)
		// findViewById(R.id.Time_ButtonController_Spa_Details);
		//
		// MakeAppointment_ButtonController = (Button)
		// findViewById(R.id.MakeAppointment_ButtonController_SpaDetails);
		//
		// btn_view_spa_profile=(Button)findViewById(R.id.btn_view_spa_profile);
		//
		// /**
		// * Calendar Instance
		// */
		// cal = Calendar.getInstance();
		// day = cal.get(Calendar.DAY_OF_MONTH);
		// month = cal.get(Calendar.MONTH);
		// year = cal.get(Calendar.YEAR);
		// hour = cal.get(Calendar.HOUR);
		// minute = cal.get(Calendar.MINUTE);
		// AM_PM = cal.get(Calendar.AM_PM);
		//
		// String AM_PM_String[] = { "AM", "PM" };
		// month = month + 1;
		//
		// AppointmentDate_TextViewController_Spa_Details.setText(day + " / "
		// + (month) + " / " + year);
		// AppointmentTime_TextViewController_Spa_Details.setText(hour + " : "
		// + (minute) + " : " + AM_PM_String[this.AM_PM]);
		//
		//
		// SpinnerController_TherapyList_Spa_Details
		// .setOnItemSelectedListener(new OnItemSelectedListener() {
		// @Override
		// public void onItemSelected(AdapterView<?> parentView,
		// View selectedItemView, int position, long id) {
		//
		// Log.d("Inside Spinner", "Spinner");
		// Therapies_Id = Integer
		// .parseInt(ArrayList_AllTherapiesIdList
		// .get(position));
		// new GetTimeForService().execute();
		// ArrayList_AllTimeForServiceList.clear();
		// ArrayList_AllPriceForServiceList.clear();
		// isTherapistset = false;
		// TextViewController_YourTherapist_Spa_Details
		// .setText("No Therapist Selected");
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> parentView) {
		//
		// }
		//
		// });
		//
		// SpinnerController_TimeForServicelist_Spa_Details
		// .setOnItemSelectedListener(new OnItemSelectedListener() {
		// @Override
		// public void onItemSelected(AdapterView<?> parentView,
		// View selectedItemView, int position, long id) {
		// TextViewController_Price_Spa_Details
		// .setText(ArrayList_AllPriceForServiceList
		// .get(position));
		// isTherapistset = false;
		// TextViewController_YourTherapist_Spa_Details
		// .setText("No Therapist Selected");
		//
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> parentView) {
		//
		// }
		//
		// });
		//
		// date_ButtonController_SpaDetails
		// .setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Log.d("Date controller", "Click");
		// final Calendar c = Calendar.getInstance();
		// year = c.get(Calendar.YEAR);
		// month = c.get(Calendar.MONTH);
		// day = c.get(Calendar.DAY_OF_MONTH);
		//
		// DatePickerDialog dpdFromDate = new DatePickerDialog(
		// Spa_Details_Activity.this, datePickerListener,
		// year, month, day);
		// dpdFromDate.show();
		// isTherapistset = false;
		// TextViewController_YourTherapist_Spa_Details
		// .setText("No Therapist Selected");
		//
		// }
		// });
		//
		// Time_ButtonController_SpaDetails
		// .setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// final Calendar c = Calendar.getInstance();
		// hour = c.get(Calendar.HOUR_OF_DAY);
		// minute = c.get(Calendar.MINUTE);
		//
		// TimePickerDialog dpdFromDate = new TimePickerDialog(
		// Spa_Details_Activity.this, timePickerListener,
		// hour, minute, false);
		// dpdFromDate.show();
		// isTherapistset = false;
		// TextViewController_YourTherapist_Spa_Details
		// .setText("No Therapist Selected");
		// }
		// });
		//
		// MakeAppointment_ButtonController
		// .setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		//
		// if (isTherapistset) {
		// new MakeAnAppointment().execute();
		// } else {
		// Toast.makeText(Spa_Details_Activity.this,
		// "Please Choose Therapist ... !",
		// Toast.LENGTH_LONG).show();
		// // Log.d("Price iD", ArrayList_AllIDForServiceList
		// // .get(SpinnerController_TimeForServicelist
		// // .getSelectedItemPosition()));
		//
		// }
		//
		// }
		// });
		//
		// ButtonController_ChooseTherapist_SpaDetails
		// .setOnClickListener(new OnClickListener() {
		//
		// @SuppressLint("SimpleDateFormat")
		// @Override
		// public void onClick(View v) {
		//
		// // Util.Spa_Name =
		// SpinnerController_SpaList_Spa_Details.getSelectedItem().toString();
		// Util.Spa_Name = Spa_Name;
		// Calendar Appointment_TimeStamp = Calendar.getInstance();
		//
		// Appointment_TimeStamp.set(Calendar.DAY_OF_MONTH, day);
		// Appointment_TimeStamp.set(Calendar.MONTH, month - 1);
		// Appointment_TimeStamp.set(Calendar.YEAR, year);
		// Appointment_TimeStamp.set(Calendar.HOUR, hour);
		// Appointment_TimeStamp.set(Calendar.MINUTE, minute);
		// Appointment_TimeStamp.set(Calendar.AM_PM, AM_PM);
		//
		// SimpleDateFormat formatter = new SimpleDateFormat(
		// "yyyy-MM-dd HH:mm:ss");
		// Util.Appointment_Time = formatter
		// .format(Appointment_TimeStamp.getTime());
		// Log.d("Appointment_Time", "" + Util.Appointment_Time);
		//
		// // Create The Intent and Start The Activity to get The
		// // message
		// Intent intentGetMessage = new Intent(
		// Spa_Details_Activity.this,
		// SetTherapistActivity.class);
		// intentGetMessage
		// .putExtra(
		// "Therapies_Id",
		// ArrayList_AllTherapiesIdList
		// .get(SpinnerController_TherapyList_Spa_Details
		// .getSelectedItemPosition()));
		// startActivityForResult(intentGetMessage, 2);// Activity
		// // is
		// // started
		// // with
		// // requestCode
		// // 2
		// isTherapistset = false;
		// TextViewController_YourTherapist_Spa_Details
		// .setText("No Therapist Selected");
		// }
		// });
		//
		// btn_view_spa_profile.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		//
		// Intent i=new Intent(Spa_Details_Activity.this,Spa_Profile.class);
		// startActivity(i);
		// }
		// });
		// // Toast.makeText(getApplicationContext(), Spa_Name+ "\n"+Spa_Id,
		// // Toast.LENGTH_LONG).show();
		//
		// new GetTherapies().execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_spa_profile, menu);
		return true;
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

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		// check if the request code is same as what is passed here it is 2
		if (requestCode == 2) {
			Log.d("Result For Return", data.getStringExtra("Therapist_Name"));
			Therapist_Id = data.getStringExtra("Therapist_Id");
			TextViewController_YourTherapist_Spa_Details.setText(data
					.getStringExtra("Therapist_Name"));
			isTherapistset = true;

		}

	}

	/**
	 * select date
	 */
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {

			day = selectedDay;
			month = selectedMonth + 1;
			year = selectedYear;
			AppointmentDate_TextViewController_Spa_Details.setText(selectedDay
					+ " / " + (month) + " / " + selectedYear);
		}
	};

	/**
	 * Select Time
	 */
	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

			if (hour < 12) {
				AM_PM = 00;

				// set current time into textview
				AppointmentTime_TextViewController_Spa_Details
						.setText(new StringBuilder().append(pad(hour))
								.append(":").append(pad(minute)).append(":")
								.append("AM"));

			} else {
				hour = hour - 12;
				if (hour == 0) {
					hour = 12;
				}

				// set current time into textview
				AppointmentTime_TextViewController_Spa_Details
						.setText(new StringBuilder().append(pad(hour))
								.append(":").append(pad(minute)).append(":")
								.append("PM"));
				AM_PM = 01;
			}

		}
	};

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	/**
	 * Get All Therapies of a particular Spa
	 * 
	 * @author Pallavi
	 * 
	 */
	class GetTherapies extends AsyncTask<String, String, String> {

		// Progress Dialog
		private ProgressDialog pDialog;
		int success;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Spa_Details_Activity.this);
			pDialog.setMessage("Getting Therapies Data ... ");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("Spa_Id", "" + Spa_Id));
				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(Util.GetTherapies,
						"POST", params);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					JSONArray PostJson = json.getJSONArray("posts");
					Log.d("Post Date ", PostJson.toString());
					for (int i = 0; i < PostJson.length(); i++) {

						JSONObject Temp = PostJson.getJSONObject(i);

						ArrayList_AllTherapiesList.add(Temp.getString("Name"));
						ArrayList_AllTherapiesIdList.add(Temp
								.getString("Therapies_Id"));

					}
					return json.getString(TAG_MESSAGE) + " , Please Login !";
				} else {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null) {
				ArrayAdapter<String> SpaListAdapter = new ArrayAdapter<String>(
						Spa_Details_Activity.this,
						android.R.layout.simple_spinner_item,
						ArrayList_AllTherapiesList);
				SpaListAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				SpinnerController_TherapyList_Spa_Details
						.setAdapter(SpaListAdapter);

			}
		}

	}

	class GetTimeForService extends AsyncTask<String, String, String> {

		// Progress Dialog
		private ProgressDialog pDialog;
		int success;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Spa_Details_Activity.this);
			pDialog.setMessage("Getting Pricing Data ... ");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("Therapies_Id", ""
						+ Therapies_Id));
				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.GetTherapiesPricing, "POST", params);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					JSONArray PostJson = json.getJSONArray("posts");
					Log.d("Post Date ", PostJson.toString());
					for (int i = 0; i < PostJson.length(); i++) {

						JSONObject Temp = PostJson.getJSONObject(i);
						ArrayList_AllTimeForServiceList.add(Temp
								.getString("Time"));
						ArrayList_AllPriceForServiceList.add(Temp
								.getString("Pricing"));
						ArrayList_AllIDForServiceList.add(Temp
								.getString("Therapies_Pricing_Table_Id"));
					}
					return json.getString(TAG_MESSAGE) + " , Please Login !";
				} else {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null) {
				ArrayAdapter<String> SpaListAdapter = new ArrayAdapter<String>(
						Spa_Details_Activity.this,
						android.R.layout.simple_spinner_item,
						ArrayList_AllTimeForServiceList);
				SpaListAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				SpinnerController_TimeForServicelist_Spa_Details
						.setAdapter(SpaListAdapter);
			}
		}

	}

	class MakeAnAppointment extends AsyncTask<String, String, String> {

		// Progress Dialog
		private ProgressDialog pDialog;

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Spa_Details_Activity.this);
			pDialog.setMessage("Creating Appointment...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@SuppressWarnings("static-access")
		@SuppressLint("SimpleDateFormat")
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;
			String Uid = "" + Util.Uid;
			String AM_PM_String[] = { "AM", "PM" };
			String Appointment_DateTime = year + "-" + month + "-" + day + " "
					+ hour + ":" + minute + " " + AM_PM_String[AM_PM];
			Log.d("Date TIme", Appointment_DateTime);

			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("Uid", Uid));
				params.add(new BasicNameValuePair("Appointment_DateTime",
						Appointment_DateTime));
				params.add(new BasicNameValuePair("Therapies_Id",
						ArrayList_AllTherapiesIdList
								.get(SpinnerController_TherapyList_Spa_Details
										.getSelectedItemPosition())));
				params.add(new BasicNameValuePair("Therapist_Id", Therapist_Id));
				params.add(new BasicNameValuePair(
						"Therapies_Pricing_Table_Id",
						ArrayList_AllIDForServiceList
								.get(SpinnerController_TimeForServicelist_Spa_Details
										.getSelectedItemPosition())));

				String service_time = ArrayList_AllTimeForServiceList
						.get(SpinnerController_TimeForServicelist_Spa_Details
								.getSelectedItemPosition());

				// try {
				// SimpleDateFormat Service_Time_format = new SimpleDateFormat(
				// "HH:mm:ss");
				// Date date;
				// date = Service_Time_format.parse(service_time);
				//
				// Calendar calendar = GregorianCalendar.getInstance(); //
				// creates
				// // a
				// // new
				// // calendar
				// // instance
				// calendar.setTime(date); // assigns calendar to given date
				// int end_time_hour = calendar.get(Calendar.HOUR);
				// int end_time_minute = calendar.get(Calendar.MINUTE);
				//
				// Calendar End_time = Calendar.getInstance();
				//
				// End_time.set(Calendar.MONTH, month - 1);
				// End_time.set(Calendar.YEAR, year);
				// End_time.set(Calendar.DAY_OF_MONTH, day);
				// int hr = hour + end_time_hour;
				// End_time.set(Calendar.HOUR_OF_DAY, hr + 1);
				// End_time.set(Calendar.MINUTE, (minute + end_time_minute));
				// End_time.set(Calendar.SECOND, 0);
				// End_time.set(Calendar.AM_PM, AM_PM);
				//
				// SimpleDateFormat formatter = new SimpleDateFormat(
				// "yyyy-MM-dd HH:mm");
				// Log.d("End Time",
				// ""
				// + formatter.format((End_time
				// .getTimeInMillis() + 3600000)));
				// params.add(new BasicNameValuePair("End_Time", formatter
				// .format((End_time.getTimeInMillis() + 3600000))));
				// } catch (ParseException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				SimpleDateFormat Service_Time_format = new SimpleDateFormat(
						"HH:mm:ss");
				Date date;
				date = Service_Time_format.parse(service_time);

				Calendar calendar = GregorianCalendar.getInstance(); //

				// a
				// new
				// calendar
				// instance
				calendar.setTime(date); // assigns calendar to given date
				int end_time_hour = calendar.get(Calendar.HOUR);
				int end_time_minit = calendar.get(Calendar.MINUTE);
				int hr;
				int min;
				if (AM_PM == 0) {
					hr = hour + end_time_hour;
					min = minute + end_time_minit;
				} else {
					hr = hour + end_time_hour;
					min = minute + end_time_minit;
					hr = hr + 12;
					if (hr > 24) {
						hr = hr - 24;
						// day = day + 1;

					}
				}
				String End_service_datetime = year + "-" + month + "-" + day
						+ " " + hr + ":" + min;
				params.add(new BasicNameValuePair("End_Time",
						End_service_datetime));
				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.Make_Appointment_URL, "POST", params);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					Calendar Alarm_Time = Calendar.getInstance();

					Alarm_Time.set(Calendar.MONTH, month - 1);
					Alarm_Time.set(Calendar.YEAR, year);
					Alarm_Time.set(Calendar.DAY_OF_MONTH, day);

					Alarm_Time.set(Calendar.HOUR_OF_DAY, hour);
					Alarm_Time.set(Calendar.MINUTE, minute);
					Alarm_Time.set(Calendar.SECOND, 0);
					Alarm_Time.set(Calendar.AM_PM, AM_PM);
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Log.d("End Time", formatter.format(Alarm_Time.getTime()));

					Intent myIntent = new Intent(Spa_Details_Activity.this,
							MyReceiver.class);

					myIntent.putExtra("title", "Appointment for spa");
					myIntent.putExtra("notes", "hour for appointment");
					myIntent.putExtra("Appointment_Id",
							json.getInt("Appointment_Id"));

					pendingIntent = PendingIntent.getBroadcast(
							Spa_Details_Activity.this,
							json.getInt("Appointment_Id"), myIntent, 0);

					AlarmManager alarmManager = (AlarmManager) Spa_Details_Activity.this
							.getSystemService(Spa_Details_Activity.this.ALARM_SERVICE);
					alarmManager.set(AlarmManager.RTC,
							(Alarm_Time.getTimeInMillis()), pendingIntent);
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(Spa_Details_Activity.this, file_url,
						Toast.LENGTH_LONG).show();
			}
			finish();

		}

	}

}
