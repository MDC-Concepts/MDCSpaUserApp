package org.mdcconcepts.com.mdcspauserapp.findspa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.makeappointment.SelectTherapyAdapter;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.todddavies.components.progressbar.ProgressWheel;

public class SpaTherapiesFragment extends Fragment implements
OnScrollListener{

	ListView Therapy_List;
	SelectTherapyAdapter adapter;
	Dialog dialog, getDataDialog;
	Button Btn_Service_Ok;
	Button Btn_Service_Cancel;
	String Spa_Id="1";

	TextView Txt_Title;
	ProgressWheel pw_four;

	int hit_counter = 0;
	int VisiblePosition, distFromTop;

	GetTenTherapies getTenTherapies;

	private boolean isloading = false;
	private boolean isDataAvailable = true;

	ArrayList<HashMap<String, String>> TherapyDetails = new ArrayList<HashMap<String, String>>();

	JSONParser jsonParser = new JSONParser();

	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	static final String THERAPY_ID = "therapy_id";
	static final String THERAPY = "therapy_name";
	static final String THERAPY_DETAILS = "therapy_details";

	TextView txt_dialog_title, txt_dialog_time, txt_dialog_price;

	ViewGroup footer;
	SeekBar seekBar_time_for_service;
	ProgressWheel progressbar_footer;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView=inflater.inflate(R.layout.therapies_list, null);
		
		Therapy_List = (ListView)rootView. findViewById(R.id.listview_therapies);
		Therapy_List.setOnScrollListener(this);
		adapter = new SelectTherapyAdapter(getActivity(),
				TherapyDetails);
		Therapy_List.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
				"Raleway-Light.otf");
		
		getTenTherapies = new GetTenTherapies();
		getTenTherapies.execute();

		getDataDialog = new Dialog(getActivity(),
				R.style.ThemeWithCorners);
		getDataDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getDataDialog.setContentView(R.layout.custom_progress_dialog);
		getDataDialog.setCancelable(false);

		Txt_Title = (TextView) getDataDialog.findViewById(R.id.txt_alert_text);
		pw_four = (ProgressWheel) getDataDialog
				.findViewById(R.id.progressBarFour);

		Txt_Title.setTypeface(font);
		return rootView;
	}
	class GetTenTherapies extends AsyncTask<String, String, String> {

		int success;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {

			// TODO Auto-generated method stub
			SystemClock.sleep(1000);
			isloading = true;
			try {
				// Building Parameters
				List<NameValuePair> Newparams = new ArrayList<NameValuePair>();

				Newparams.add(new BasicNameValuePair("hit_counter", String
						.valueOf(hit_counter)));
				Newparams.add(new BasicNameValuePair("Spa_Id", Spa_Id));
				Log.d("requesting 10 therapies!", "starting " + hit_counter);

				// Posting user data to script
				JSONObject json = jsonParser
						.makeHttpRequest(
								Util.get_ten_therapies,
								"POST", Newparams);

				// full json response
				// Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					JSONArray PostJson = json.getJSONArray("posts");
					Log.d("Ten Therapies ", PostJson.toString());
					for (int i = 0; i < PostJson.length(); i++) {

						JSONObject Temp = PostJson.getJSONObject(i);

						HashMap<String, String> spaDetails = new HashMap<String, String>();

						spaDetails.put(THERAPY_ID,
								Temp.getString("Therapies_Id"));
						spaDetails.put(THERAPY, Temp.getString("Therapy_Name"));
						spaDetails.put(THERAPY_DETAILS,
								Temp.getString("Therapy_Description"));

						TherapyDetails.add(spaDetails);

						isDataAvailable = true;
					}

					return json.getString(TAG_MESSAGE);
				} else if (success == 0) {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					isDataAvailable = false;
					Therapy_List.setOnScrollListener(null);
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
			// if(getDataDialog.isShowing())
			// getDataDialog.dismiss();
			isloading = false;

			if (file_url.contains("Spa Found !")) {
				Log.v("onPostLog If", file_url);

				Therapy_List.setAdapter(adapter);
				Therapy_List.setSelectionFromTop(VisiblePosition, distFromTop);
				hit_counter++;

			} else if (file_url.equalsIgnoreCase("false")) {
				Log.v("onPostLog Else", file_url);

				if (Therapy_List.getFooterViewsCount() > 0) {
					// Toast.makeText(Select_Therapy_Activity.this,
					// String.valueOf(Therapy_List.getFooterViewsCount()),
					// Toast.LENGTH_SHORT).show();
					Therapy_List.removeFooterView(footer);
					Therapy_List.setAdapter(adapter);
					adapter.notifyDataSetChanged();
				}

				Therapy_List.setOnScrollListener(null);

				// progressbar_footer.stopSpinning();
				isDataAvailable = false;

			}

		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

		int loadedItems = (firstVisibleItem) + visibleItemCount;

		if (isDataAvailable) {
			if ((loadedItems == totalItemCount) && !isloading) {

				if (getTenTherapies != null
						&& (getTenTherapies.getStatus() == AsyncTask.Status.FINISHED)) {

					getTenTherapies = new GetTenTherapies();
					getTenTherapies.execute();

				}
			}

		} else {
			Therapy_List.setOnScrollListener(null);
			Therapy_List.removeFooterView(footer);
			progressbar_footer.stopSpinning();
			Therapy_List.setAdapter(adapter);
			adapter.notifyDataSetChanged();

		}
	}
}
