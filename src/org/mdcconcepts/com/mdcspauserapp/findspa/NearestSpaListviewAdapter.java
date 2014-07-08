package org.mdcconcepts.com.mdcspauserapp.findspa;

import java.util.ArrayList;
import java.util.HashMap;

import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.makeappointment.Select_Therapy_Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NearestSpaListviewAdapter extends BaseAdapter {

	private static LayoutInflater inflater = null;
	Activity activity;
	ArrayList<HashMap<String, String>> data;
	TextView Txt_Spa_Name, Txt_Spa_Area;
	String Spa_Id;
	int count;
	Context context;
	public NearestSpaListviewAdapter(Activity a,
			ArrayList<HashMap<String, String>> data) {
		// TODO Auto-generated constructor stub
		this.activity = a;
		this.data = data;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final View rootView = inflater.inflate(R.layout.spa_list_item, null,
				true);


		context=rootView.getContext();
		
		Txt_Spa_Name = (TextView) rootView.findViewById(R.id.txt_list_spa_name);

		Txt_Spa_Area = (TextView) rootView
				.findViewById(R.id.txt_list_spa_name_area);

		Typeface font = Typeface.createFromAsset(activity.getAssets(),
				"Raleway-Light.otf");

//		Txt_Spa_Name.setTypeface(font);
		Txt_Spa_Area.setTypeface(font);
		Txt_Spa_Name.setTypeface(font, Typeface.BOLD);

		HashMap<String, String> spaDetails = new HashMap<String, String>();

		spaDetails = data.get(position);

	rootView.setTag(spaDetails);
		Txt_Spa_Name.setText(spaDetails.get("spa_name"));
		Txt_Spa_Area.setText(spaDetails.get("spa_addr"));
		
	
		return rootView;
	}

}
