package org.mdcconcepts.com.mdcspauserapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.customitems.GPSTracker;
import org.mdcconcepts.com.mdcspauserapp.findspa.FindSpaFragment;
import org.mdcconcepts.com.mdcspauserapp.giftcard.GiftCardFragment;
import org.mdcconcepts.com.mdcspauserapp.login.LoginActivity;
import org.mdcconcepts.com.mdcspauserapp.makeappointment.MakeAppointmentFragment;
import org.mdcconcepts.com.mdcspauserapp.navigation.NavDrawerItem;
import org.mdcconcepts.com.mdcspauserapp.navigation.NavDrawerListAdapter;
import org.mdcconcepts.com.mdcspauserapp.profile.ProfileFragment;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	JSONParser jsonParser = new JSONParser();
	Typeface font;
	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		font = Typeface.createFromAsset(getAssets(), "Raleway-Light.otf");

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Profile
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// File Spa
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));

		// Make an appointment
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1), true, "22"));

		// View an appointment
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));

		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1), true, "50+"));

		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
				.getResourceId(6, -1)));
		// LogOut
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons
				.getResourceId(7, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);

				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}

		new GetUserData().execute();

	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;

		switch (position) {
		case 0:

			fragment = new HomeFragment();
			break;

		case 1:

			fragment = new ProfileFragment();
			break;
			
		case 2:
			
			/**
			 * Check if Gps is On
			 */
			
			GPSTracker gps = new GPSTracker(this);

			if (gps.canGetLocation()) {
				fragment = new FindSpaFragment();

			} else {
				// can't get location
				// GPS or Network is not enabled
				// Ask user to enable GPS/network in settings
				gps.showSettingsAlert();
			}
			break;
			
		case 3:

			fragment = new MakeAppointmentFragment();
			break;
			
		case 4:
			
			fragment = new HomeFragment();
			// fragment = new ViewAppointmentFragment();
			break;
			
		case 5:
			// fragment = new WhatsHotFragment();
			break;
			
		case 6:
			// fragment = new HomeFragment();
			fragment = new GiftCardFragment();
			break;
			
		case 7:
				Intent i = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(i);
				finish();
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * Fetch User Details and save it in util class
	 *  details shown in Profile class
	 * @author Pallavi
	 *
	 */
	class GetUserData extends AsyncTask<String, String, String> {

		// Progress Dialog
		// private ProgressDialog pDialog;
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

			dialog = new Dialog(MainActivity.this, R.style.ThemeWithCorners);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.custom_progress_dialog);
			dialog.setCancelable(false);
			dialog.show();

			TextView Txt_Title = (TextView) dialog
					.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);
			Txt_Title.setText("Getting User Details....");
			
		}

		@SuppressLint("SimpleDateFormat")
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("Uid", "" + Util.Uid));
				Log.d("Uid", "" + Util.Uid);
				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.GetUserDetails, "POST", params);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					Name1 = json.getString("Name");
					Mobile1 = json.getString("Mobile");
					Email1 = json.getString("Email");
					Address1 = json.getString("Address");
					DOB1 = json.getString("DOB");
					Anniversary1 = json.getString("Anniversary");

					return json.getString(TAG_MESSAGE);
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
			dialog.cancel();
			if (file_url != null) {
				Util.User_Name = Name1;
				Util.User_Contact_Number = Mobile1;
				Util.User_EmailId = Email1;
				Util.User_Address = Address1;
				Util.User_DOB = DOB1;
				Util.User_Anniversary = Anniversary1;
			}

		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

/**
 * Ask before leaving app
 */
		final Dialog dialog = new Dialog(MainActivity.this,
				R.style.ThemeWithCorners);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_alert_box);
		dialog.setCancelable(false);
		dialog.show();

		TextView title;
		Button btn_yes;
		Button btn_no;

		btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
		btn_no = (Button) dialog.findViewById(R.id.btn_no);
		title = (TextView) dialog.findViewById(R.id.txt_title);

		title.setTypeface(font);
		btn_yes.setTypeface(font);
		btn_no.setTypeface(font);

		btn_yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.this.finish();

			}
		});
		btn_no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				dialog.cancel();
			}
		});

	}

}
