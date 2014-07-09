package org.mdcconcepts.com.mdcspauserapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.todddavies.components.progressbar.ProgressWheel;

public class DiseaseFragment extends Fragment {

	private CheckBox CheckBox_Heart;
	private CheckBox CheckBox_Hepatitis;
	private CheckBox CheckBox_Diabeties;
	private CheckBox CheckBox_Lung;
	private CheckBox CheckBox_Hypotension;
	private CheckBox CheckBox_Skin;
	private CheckBox CheckBox_BP;
	private CheckBox CheckBox_Arthritis;
	private CheckBox CheckBox_Pregnant;
	private CheckBox CheckBox_Other;

	private Button Btn_Submit_Disease,Btn_Skip_Disease_Fragment;
	private Typeface font;

	JSONParser jsonParser = new JSONParser();

	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	public DiseaseFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.any_disease, container, false);
		CheckBox_Heart = (CheckBox) v.findViewById(R.id.CheckBox_HeartDisease);
		CheckBox_Hepatitis = (CheckBox) v.findViewById(R.id.CheckBox_Hepatitis);
		CheckBox_Diabeties = (CheckBox) v.findViewById(R.id.CheckBox_Diabeties);
		CheckBox_Lung = (CheckBox) v.findViewById(R.id.CheckBox_LungDisease);
		CheckBox_Hypotension = (CheckBox) v
				.findViewById(R.id.CheckBox_Hypotension);
		CheckBox_Skin = (CheckBox) v.findViewById(R.id.CheckBox_SkinDisease);
		CheckBox_BP = (CheckBox) v.findViewById(R.id.CheckBox_BP);
		CheckBox_Arthritis = (CheckBox) v.findViewById(R.id.CheckBox_Arthritis);
		CheckBox_Pregnant = (CheckBox) v.findViewById(R.id.CheckBox_Pregnant);
		CheckBox_Other = (CheckBox) v.findViewById(R.id.CheckBox_Other);

		font = Typeface.createFromAsset(getActivity().getAssets(),
				Util.fontPath);

		CheckBox_Heart.setTypeface(font);
		CheckBox_Hepatitis.setTypeface(font);
		CheckBox_Diabeties.setTypeface(font);
		CheckBox_Lung.setTypeface(font);
		CheckBox_Hypotension.setTypeface(font);
		CheckBox_Skin.setTypeface(font);
		CheckBox_BP.setTypeface(font);
		CheckBox_Arthritis.setTypeface(font);
		CheckBox_Pregnant.setTypeface(font);
		CheckBox_Other.setTypeface(font);
		
		if(Util.isChecked_Heart)
			CheckBox_Heart.setChecked(true);
		
		if(Util.isChecked_Hepatitis)
			CheckBox_Hepatitis.setChecked(true);
		
		if(Util.isChecked_Diabeties)
			CheckBox_Diabeties.setChecked(true);
		
		if(Util.isChecked_Lung)
			CheckBox_Hepatitis.setChecked(true);
		
		if(Util.isChecked_Lung)
			CheckBox_Hepatitis.setChecked(true);
		
		if(Util.isChecked_Hypotension)
			CheckBox_Hypotension.setChecked(true);
		
		if(Util.isChecked_Skin)
			CheckBox_Skin.setChecked(true);
		
		if(Util.isChecked_BP)
			CheckBox_BP.setChecked(true);
		
		if(Util.isChecked_BP)
			CheckBox_Arthritis.setChecked(true);
		
		if(Util.isChecked_Arthritis)
			CheckBox_Arthritis.setChecked(true);
		
		if(Util.isChecked_Pregnant)
			CheckBox_Pregnant.setChecked(true);
		
		if(Util.isChecked_Other)
			CheckBox_Other.setChecked(true);

		Btn_Submit_Disease = (Button) v.findViewById(R.id.Btn_Submit_Disease);
		Btn_Submit_Disease.setTypeface(font);

		Btn_Skip_Disease_Fragment=(Button) v.findViewById(R.id.Btn_Skip_Disease_Fragment);
		Btn_Skip_Disease_Fragment.setTypeface(font);
		
		Btn_Submit_Disease.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				new SendInjuriesData().execute();
			}
		});
		
		Btn_Skip_Disease_Fragment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				getActivity().finish();
			}
		});

		return v;
	}

	class SendInjuriesData extends AsyncTask<String, String, String> {

		Dialog pdialog;
		int success;
		TextView Txt_Title;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdialog = new Dialog(getActivity(), R.style.ThemeWithCorners);
			pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			pdialog.setContentView(R.layout.custom_progress_dialog);
			pdialog.setCancelable(false);
			 pdialog.show();

			Txt_Title = (TextView) pdialog.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);

			Txt_Title.setText("Sending Data...");
			/**
			 * custom circular progress bar
			 */
			ProgressWheel pw_four = (ProgressWheel) pdialog
					.findViewById(R.id.progressBarFour);
			pw_four.spin();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			try {
					JSONArray jsonPainArray = new JSONArray();

					for (int i = 0; i < Util.PainAreas.size(); i++)
					{

						JSONObject jsonPainObject = new JSONObject();
//					 	jsonPainObject.put("Uid", 2);

						jsonPainObject.put("Pain_Id", Util.PainAreas.get(i));
						jsonPainArray.put(jsonPainObject);
						Log.d("Util PAIN", Util.PainAreas.get(i));
					}

					
					
					Util.UserDisease.clear();
					
					if(CheckBox_Heart.isChecked())
						Util.UserDisease.add("1");
					if(CheckBox_Hepatitis.isChecked())
						Util.UserDisease.add("2");
					if(CheckBox_Diabeties.isChecked())
						Util.UserDisease.add("3");
					if(CheckBox_Lung.isChecked())
						Util.UserDisease.add("4");
					if(CheckBox_Hypotension.isChecked())
						Util.UserDisease.add("5");
					if(CheckBox_Skin.isChecked())
						Util.UserDisease.add("6");
					if(CheckBox_BP.isChecked())
						Util.UserDisease.add("7");
					if(CheckBox_Arthritis.isChecked())
						Util.UserDisease.add("8");
					if(CheckBox_Pregnant.isChecked())
						Util.UserDisease.add("9");
					if(CheckBox_Other.isChecked())
						Util.UserDisease.add("10");
				
					JSONArray jsonDiseaseArray=new JSONArray();
					for (int i = 0; i < Util.UserDisease.size(); i++)
					{

						JSONObject jsonDiseaseObject = new JSONObject();
//					 	jsonPainObject.put("Uid", 2);

						jsonDiseaseObject.put("Disease_Id", Util.UserDisease.get(i));
						jsonDiseaseArray.put(jsonDiseaseObject);
						Log.d("JSON DISEASE", Util.UserDisease.get(i));
					}

//					List<NameValuePair> jsonDiseaseList = new ArrayList<NameValuePair>(
//						1);
//					jsonDiseaseList.add(new BasicNameValuePair("DiseaseArray",
//							jsonDiseaseArray.toString()));
//					Log.d("JSON DISEASE  OBJECT", jsonDiseaseList.toString());
				
					
					List<NameValuePair> jsonInjuriesList = new ArrayList<NameValuePair>(
							3);
					jsonInjuriesList.add(new BasicNameValuePair("PainArray",
							jsonPainArray.toString()));
					jsonInjuriesList.add(new BasicNameValuePair("Uid", String
								.valueOf(Util.Uid)));
					jsonInjuriesList.add(new BasicNameValuePair("DiseaseArray",
								jsonDiseaseArray.toString()));
						
						Log.d("JSON PAIN  OBJECT", jsonInjuriesList.toString());
				 JSONObject json =
				 jsonParser.makeHttpRequest(Util.sendPainData,
				 "POST", jsonInjuriesList);
				
				 Log.d("JSON PAIN RETURN OBJECT", json.toString());
				 // Log.d("JSON PAIN RETURN OBJECT", json.toString());
				 success = json.getInt(TAG_SUCCESS);
				
				 if (success == 1) {
					 
					 
//				 Util.Uid = json.getInt("Uid");
				 return json.getString(TAG_MESSAGE);
				 } else
				 {
				 // Log.d("Login Failure!", json.getString(TAG_MESSAGE));
				 // Toast.makeText(LoginActivity.this,
				 // "Wrong Username or Password", Toast.LENGTH_LONG)
				 // .show();
				 return json.getString(TAG_MESSAGE);
				
				 } 
			} catch (Exception e) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// pdialog.dismiss();
			if (success == 1) {
				Txt_Title.setText("Data Sent");
				SystemClock.sleep(2000);
				pdialog.dismiss();
			}
			getActivity().finish();
		}
	}
}
