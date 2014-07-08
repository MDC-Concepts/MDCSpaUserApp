package org.mdcconcepts.com.mdcspauserapp.findspa;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.customitems.GPSTracker;
import org.mdcconcepts.com.mdcspauserapp.navigation.NavDrawerItem;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.todddavies.components.progressbar.ProgressWheel;

public class FindSpaMapFragment extends Fragment implements
		android.location.LocationListener, OnInfoWindowClickListener,
		OnScrollListener {

	private static View rootView;
	Typeface font;
	public GoogleMap google_map;
	LocationManager location_manager;
	Location location;
	double latitude, longitude;
	MarkerOptions marker;
	int count = 0;
	Context context;

	Activity rootActivity;
	GPSTracker gps;
	TextView txt_spa_name;
	TextView txt_addr;
	Spa_Data spa_data;
	int Selected_Filter;
	private double mLastLatitude;
	private double mLastLongitude;
	private FetchNearestSpa getTenNearestSpaTask;
	double myCurrentLocationLat = 0.0;
	double myCurrentLocationlong = 0.0;
	private boolean isloading = false;
	private boolean isloadingNearestSpa = false;
	Spinner filter;
	private boolean isDataAvailable = true;
	private boolean isNearestDataAvailable = true;
	ProgressWheel progressbar_footer;
	ProgressWheel progressbar_header;
	int hit_counter = 0;
	ArrayList<HashMap<String, String>> SpaDetails = new ArrayList<HashMap<String, String>>();
	private ArrayList<Spa_Data> NearestLocations = new ArrayList<Spa_Data>();
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	Dialog dialog;
	private View footer;
	private View header;
	int VisiblePosition, distFromTop;
	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	static final String SPA_ID = "spa_id";
	static final String SPA_NAME = "spa_name";
	static final String SPA_Address = "spa_addr";
	static final String SPA_LAT = "spa_lat";
	static final String SPA_LONG = "spa_long";
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NearestSpaListviewAdapter adapter;
	private ArrayList<String> ArrayList_Filter = new ArrayList<String>();
	public FindSpaMapFragment() {
		// TODO Auto-generated constructor stub

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		gps = new GPSTracker(getActivity());
		try {
			rootActivity = getActivity();
			rootView = inflater.inflate(R.layout.fragment_find_spa, container,
					false);
			if (rootView != null) {
				context = rootView.getContext();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		font = Typeface.createFromAsset(getActivity().getAssets(),
				"Raleway-Light.otf");

		mDrawerLayout = (DrawerLayout) rootView
				.findViewById(R.id.drawer_layout1);
		mDrawerList = (ListView) rootView.findViewById(R.id.list_slidermenu1);

		adapter = new NearestSpaListviewAdapter(getActivity(), SpaDetails);
		mDrawerList.setAdapter(adapter);

		mDrawerList.setOnScrollListener(this);
		
		dialog = new Dialog(getActivity(), R.style.ThemeWithCorners);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_progress_dialog);
		dialog.setCancelable(false);


		TextView Txt_Title = (TextView) dialog
				.findViewById(R.id.txt_alert_text);
		Txt_Title.setTypeface(font);
		Txt_Title.setText("Fetching data....");

		ProgressWheel pw_four = (ProgressWheel) dialog
				.findViewById(R.id.progressBarFour);
		pw_four.spin();
		
		
		footer = inflater.inflate(R.layout.footer, null);
		progressbar_footer = (ProgressWheel) footer
				.findViewById(R.id.progressbar_footer);
		progressbar_footer.spin();
		
		if(header==null)
		{
			header=inflater.inflate(R.layout.header, null);
			mDrawerList.addHeaderView(header,null,false);
		}
			
		mDrawerList.addFooterView(footer);
		filter = (Spinner) header.findViewById(R.id.spinner_sort_by);
		
		
		ArrayList_Filter.add("Nearest Spa's");
		ArrayList_Filter.add("Popularity");
		ArrayList_Filter.add("Price- High to Low");
		ArrayList_Filter.add("Price- Low to High");
		ArrayList_Filter.add("Newest");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), R.layout.spinner_item, ArrayList_Filter);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		filter.setAdapter(dataAdapter);
		filter.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				switch (position) {
				
				case 0:
					hit_counter = 0;
					Selected_Filter = 1;
				
//						dialog.show();
						getTenNearestSpaTask = new FetchNearestSpa();
						getTenNearestSpaTask.execute();
						
//										break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		// enabling action bar app icon and behaving it as toggle button
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		getActivity().getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActivity().getActionBar().setTitle(mTitle);
				getActivity().getActionBar().show();
				// calling onPrepareOptionsMenu() to show action bar icons
				getActivity().invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActivity().getActionBar().setTitle(mDrawerTitle);
				getActivity().getActionBar().hide();
				// calling onPrepareOptionsMenu() to hide action bar icons
				getActivity().invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			// getActivity().displayView(0);
		}

		// TODO Auto-generated method stub

		/**
		 * Fetch 10 Nearest Spa's from server
		 */
		
		getTenNearestSpaTask =new FetchNearestSpa();
		getTenNearestSpaTask.execute();

		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				@SuppressWarnings("unchecked")
				HashMap<String, String> spaDetails=(HashMap<String, String>) view.getTag();
				LatLng locateOnMap=new LatLng(Double.parseDouble(spaDetails.get("spa_lat")), Double.parseDouble(spaDetails.get("spa_long")));
				addMarkers(locateOnMap);
				mDrawerLayout.closeDrawers();
			}
		});
		return rootView;
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

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;

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

	public void setTitle(CharSequence title) {
		mTitle = title;
		getActivity().getActionBar().setTitle(mTitle);
	}

	/**
	 * Fetch 10 Nearest Spa
	 * 
	 * @author Pallavi
	 * 
	 */

	public class FetchNearestSpa extends AsyncTask<String, String, String> {

		// private ProgressDialog pDialog;

		int success;
		JSONParser jsonParser = new JSONParser();
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_MESSAGE = "message";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			dialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			SystemClock.sleep(1000);
			isloadingNearestSpa = true;
			try {
				// Building Parameters
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();

				// check if GPS enabled
				if (gps.canGetLocation()) {

					mLastLatitude = gps.getLatitude();
					mLastLongitude = gps.getLongitude();

				}
				Log.d("Sending Lat", String.valueOf(mLastLatitude));
				Log.d("Sending Long", String.valueOf(mLastLongitude));
				
				params1.add(new BasicNameValuePair("CurrentLat", String
						.valueOf(mLastLatitude)));
				params1.add(new BasicNameValuePair("CurrentLong", String
						.valueOf(mLastLongitude)));
				params1.add(new BasicNameValuePair("hit_counter", String
						.valueOf(hit_counter)));

				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.get_nearest_ten_spa, "POST", params1);

				Log.d("Hit count", String.valueOf(hit_counter));

				// json success element
				success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					JSONArray PostJson = json.getJSONArray("posts");
					Log.d("Executing Nearest 10 ", PostJson.toString());
					for (int i = 0; i < PostJson.length(); i++) {

						JSONObject Temp = PostJson.getJSONObject(i);

						HashMap<String, String> spaDetails = new HashMap<String, String>();
						spaDetails.put(SPA_ID, Temp.getString("Spa_Id"));
						spaDetails.put(SPA_NAME, Temp.getString("Spa_Name"));
						spaDetails.put(SPA_Address, Temp.getString("Addresss"));
						spaDetails.put(SPA_LAT, Temp.getString("Spa_Lat"));
						spaDetails.put(SPA_LONG, Temp.getString("Spa_long"));
						NearestLocations.add(new Spa_Data(Temp
								.getString("Spa_Name"), Temp
								.getString("Spa_Id"),
								Temp.getString("Spa_Lat"), Temp
										.getString("Spa_long"), Temp.getString("Addresss")));
//						NearestLocations.add(spa_data);
						SpaDetails.add(spaDetails);

						isNearestDataAvailable = true;
					}

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
		 **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			// pDialog.dismiss();
			if (dialog.isShowing())
				dialog.dismiss();
			isloadingNearestSpa = false;

			if (file_url.contains("Spa Found !")) {
				mDrawerList.setAdapter(adapter);
				mDrawerList.setSelectionFromTop(VisiblePosition, distFromTop);
				hit_counter++;

			} else if (file_url.equalsIgnoreCase("false")) {
				/**
				 * Stop loading items if data finished
				 */
				mDrawerList.setOnScrollListener(null);
				mDrawerList.removeFooterView(footer);
				progressbar_footer.stopSpinning();
				isNearestDataAvailable = false;
			}
			initilizeMap();

		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		int loadedItems = (firstVisibleItem) + visibleItemCount;

		if (isNearestDataAvailable) {
			if ((loadedItems == totalItemCount) && !isloadingNearestSpa) {

				if (getTenNearestSpaTask != null
						&& (getTenNearestSpaTask.getStatus() == AsyncTask.Status.FINISHED)) {
					getTenNearestSpaTask = new FetchNearestSpa();
					getTenNearestSpaTask.execute();

				}
			}
		} else {
			mDrawerList.setOnScrollListener(null);
			mDrawerList.removeFooterView(footer);
			progressbar_footer.stopSpinning();
			// adapter.notifyDataSetChanged();

		}
		/**
		 * When you scroll down and start getting new data store Last Visible
		 * Position in some variables using this method
		 */
		VisiblePosition = mDrawerList.getFirstVisiblePosition();
		View v = mDrawerList.getChildAt(0);
		distFromTop = (v == null) ? 0 : v.getTop();

	}

	/**
	 * Initialize Google Map
	 */
	@SuppressLint("NewApi")
	private void initilizeMap() {
		if (google_map == null) {

			try {
				google_map = ((MapFragment) this.getActivity()
						.getFragmentManager().findFragmentById(R.id.map))
						.getMap();

			} catch (Exception e) {
				e.printStackTrace();
			}

			// check if map is created successfully or not
			if (google_map == null) {
				Toast.makeText(this.getActivity(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			} else {

				// check if GPS enabled
				if (gps.canGetLocation()) {

					mLastLatitude = gps.getLatitude();
					mLastLongitude = gps.getLongitude();
					Log.d("latitude", "" + mLastLatitude);
					Log.d("longitude", "" + mLastLongitude);

				} else {
					// can't get location
					// GPS or Network is not enabled
					// Ask user to enable GPS/network in settings
					gps.showSettingsAlert();
				}

				google_map.setMyLocationEnabled(true);
				// getLocation();

				marker = new MarkerOptions().position(
						new LatLng(mLastLatitude, mLastLongitude)).title(
						"Current Location");

				MarkerOptions options = new MarkerOptions();
				options.position(new LatLng(mLastLatitude, mLastLongitude));

				google_map.setOnInfoWindowClickListener(this);

				/*** ZoomIn ****/

				 CameraPosition cameraPosition = new CameraPosition.Builder()
	              .target(new LatLng(mLastLatitude, mLastLongitude)) // Sets the center of the map to
	                                          // Golden Gate Bridge
	              .zoom(16)                   // Sets the zoom
	              .bearing(0) // Sets the orientation of the camera to east
	              .tilt(90)    // Sets the tilt of the camera to 30 degrees
	              .build();   
				google_map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

				/**
				 * Custom InfoWindow Adapter
				 */
				google_map.setInfoWindowAdapter(new InfoWindowAdapter() {

					@Override
					public View getInfoWindow(Marker arg0) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public View getInfoContents(Marker marker) {
						// TODO Auto-generated method stub
						View v = rootActivity.getLayoutInflater().inflate(
								R.layout.custom_info_window, null);
						font = Typeface.createFromAsset(getActivity().getAssets(),
								"Raleway-Light.otf");
						txt_spa_name = (TextView) v
								.findViewById(R.id.txt_spa_name1);

						txt_addr = (TextView) v.findViewById(R.id.txt_address);

						txt_spa_name.setTypeface(font);
						txt_addr.setTypeface(font);

						double search_lat, search_long;
						search_lat = marker.getPosition().latitude;
						search_long = marker.getPosition().longitude;

						// search_lat=Math.round(search_lat,6);
						// search_long=Math.round(search_long ,6);
						DecimalFormat df = new DecimalFormat("#.000000");
						search_lat = Double.parseDouble(df.format(search_lat));
						search_long = Double.parseDouble(df.format(search_long));

						spa_data = searchDetails(search_lat, search_long);

						txt_spa_name.setText(spa_data.Spa_Name);
						txt_addr.setText(spa_data.Spa_Address);

						Log.d("Spa Address", spa_data.Spa_Address);
						LatLng newLatLong;
						double newLat, newLong;
						newLat = Double.parseDouble(spa_data.Spa_Lat);
						newLong = Double.parseDouble(spa_data.Spa_Long);
						newLatLong = new LatLng(newLat, newLong);

						// options.position(newLatLong);

						String url = getMapsApiDirectionsUrl(new LatLng(
								mLastLatitude, mLastLongitude), newLatLong);
						ReadTask downloadTask = new ReadTask();
						downloadTask.execute(url);

						return v;
					}
				});

//				addMarkers();

			}

		}
	}

	/**
	 * Add Nearest 10 Markers on the map
	 */
	private void addMarkers(LatLng newLatLong) {
		if (google_map != null) {
			google_map.addMarker(new MarkerOptions().position(newLatLong)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.ic_marker))).showInfoWindow();
			  CameraPosition cameraPosition = new CameraPosition.Builder()
              .target(newLatLong) // Sets the center of the map to
                                          // Golden Gate Bridge
              .zoom(16)                   // Sets the zoom
              .bearing(0) // Sets the orientation of the camera to east
              .tilt(90)    // Sets the tilt of the camera to 30 degrees
              .build();   
			google_map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			
//			 google_map.addMarker(new MarkerOptions().position(
//			 new LatLng(mLastLatitude, mLastLongitude)).title(
//			 "Current Location"));
//			LatLng newLatLong;
//			double newLat, newLong;
//			for (int i = 0; i < NearestLocations.size(); i++) {
//				newLat = Double.parseDouble(NearestLocations.get(i).Spa_Lat);
//				newLong = Double.parseDouble(NearestLocations.get(i).Spa_Long);
//				newLatLong = new LatLng(newLat, newLong);
//				google_map.addMarker(new MarkerOptions().position(newLatLong)
//						.icon(BitmapDescriptorFactory
//								.fromResource(R.drawable.ic_marker)));
//
//			}
			

		}
	}

	/**
	 * Find the details of the Spa on Marker Click event.
	 * 
	 * @param searchLat
	 * @param searchLong
	 * @return Spa_Data object
	 */
	public Spa_Data searchDetails(double searchLat, double searchLong) {
		double l1, l2;
		for (int i = 0; i < NearestLocations.size(); i++) {
			l1 = Double.parseDouble(NearestLocations.get(i).Spa_Lat);
			l2 = Double.parseDouble(NearestLocations.get(i).Spa_Long);

			if (l1 == searchLat && l2 == searchLong) {

				Log.d("l1",
						NearestLocations.get(i).Spa_Lat + " "
								+ String.valueOf(searchLat));
				Log.d("l2",
						NearestLocations.get(i).Spa_Long + " "
								+ String.valueOf(searchLong));

				Log.d("l2", NearestLocations.get(i).Spa_Name + " "
						+ NearestLocations.get(i).Spa_Address);

				return NearestLocations.get(i);
			}

		}
		return null;

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @param source
	 *            :- Start Point(LatLong)
	 * @param destination
	 *            :- Destination(LatLong)
	 * @return String:- Url from where points to draw route will be drawn
	 */

	private String getMapsApiDirectionsUrl(LatLng source, LatLng destination) {

		String waypoints = "waypoints=optimize:true|" + +source.latitude + ","
				+ source.longitude + "|" + destination.latitude + ","
				+ destination.longitude;
		// + "|"+STREET1.latitude + ","+ STREET1.longitude;
		String sensor = "sensor=false";
		String params = waypoints + "&" + sensor;
		String output = "json";
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + params;
		return url;
	}

	/**
	 * Download points to draw route
	 * 
	 * @author Pallavi
	 * 
	 */
	private class ReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... url) {
			String data = "";
			try {
				HttpConnection http = new HttpConnection();
				data = http.readUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			new ParserTask().execute(result);
		}
	}

	/**
	 * Download points to draw route
	 * 
	 * @author Pallavi
	 * 
	 */

	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {
			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				PathJSONParser parser = new PathJSONParser();
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
			ArrayList<LatLng> points = null;
			PolylineOptions polyLineOptions = new PolylineOptions();

			// traversing through routes
			for (int i = 0; i < routes.size(); i++) {
				points = new ArrayList<LatLng>();
				// polyLineOptions = new PolylineOptions();
				List<HashMap<String, String>> path = routes.get(i);
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				polyLineOptions.addAll(points);
				polyLineOptions.width(5);
				polyLineOptions.color(Color.BLUE);
				switch (count) {
				case 0:
					polyLineOptions.color(Color.BLUE);
					count++;
					break;
				case 1:
					polyLineOptions.color(Color.RED);
					count++;
					break;
				case 2:
					polyLineOptions.color(Color.GREEN);
					count++;
					break;
				case 3:
					polyLineOptions.color(Color.BLACK);
					count = 0;
					break;

				}

			}
			google_map.addPolyline(polyLineOptions);
		}
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		// Toast.makeText(getActivity(), "Onclick", Toast.LENGTH_LONG).show();
		Intent i = new Intent(getActivity(), SpaProfileActivity.class);
//		i.putExtra("Spa_Name", spa_data.Spa_Name);
//		i.putExtra("Spa_Id", spa_data.Spa_Id);
		startActivity(i);

	}

	/**
	 * Clear Map
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		google_map.clear();
		mDrawerList.removeHeaderView(header);
		mDrawerList.removeFooterView(footer);
	}
}
