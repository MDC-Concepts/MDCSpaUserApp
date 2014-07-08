package org.mdcconcepts.com.mdcspauserapp.makeappointment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SpaListAdapter extends BaseAdapter {

	private static LayoutInflater inflater = null;
	Activity activity;
	ArrayList<HashMap<String, String>> data;
	TextView Txt_Spa_Name, Txt_Spa_Area;
	String Spa_Id;
	int count;

	public SpaListAdapter(Activity a, ArrayList<HashMap<String, String>> data) {
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
		LayoutInflater inflater = activity.getLayoutInflater();
		final View rootView = inflater.inflate(R.layout.spa_list_item, null,
				true);
		final Context context = rootView.getContext();

//		Button btn_choose = (Button) rootView.findViewById(R.id.btn_choose);

		Txt_Spa_Name = (TextView) rootView.findViewById(R.id.txt_list_spa_name);

		Txt_Spa_Area = (TextView) rootView
				.findViewById(R.id.txt_list_spa_name_area);

		Typeface font = Typeface.createFromAsset(activity.getAssets(),
				"Raleway-Light.otf");

		Txt_Spa_Name.setTypeface(font);
		Txt_Spa_Area.setTypeface(font);
//		btn_choose.setTypeface(font);

		

		HashMap<String, String> spaDetails = new HashMap<String, String>();
		
		spaDetails=data.get(position);
		
		Txt_Spa_Name.setText(spaDetails.get("spa_name"));
		Txt_Spa_Area.setText(spaDetails.get("spa_addr"));
//		Txt_Spa_Name.setText(spaDetails.get(MakeAppointmentFragment.SPA_NAME));
//		Txt_Spa_Area.setText(spaDetails.get(MakeAppointmentFragment.SPA_Address));
		
		Spa_Id=spaDetails.get("spa_id");
//		btn_choose.setTag(Integer.parseInt(Spa_Id));
//		String Spa_name = data.get(position);
//		Txt_Spa_Name.setText(Spa_name);
//
//		btn_choose.setOnClickListener(new View.OnClickListener() {

//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent i = new Intent(context, Select_Therapy_Activity.class);
//				i.putExtra("Spa_Id", String.valueOf(v.getTag()));
//				
////				Toast.makeText(activity, String.valueOf(v.getTag()), Toast.LENGTH_SHORT).show();
//				activity.startActivity(i);
//
//			}
//		});
		return rootView;
	}

}
