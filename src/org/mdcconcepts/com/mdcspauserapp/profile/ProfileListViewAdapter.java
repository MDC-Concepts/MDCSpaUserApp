package org.mdcconcepts.com.mdcspauserapp.profile;

import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileListViewAdapter extends BaseAdapter {

	Activity activity;
	private static LayoutInflater inflater = null;
	ImageView ImageView_TabIcon;
	TextView TextView_TabText;

	public ProfileListViewAdapter(Activity a) {
		// TODO Auto-generated constructor stub
		this.activity = a;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
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

		// LayoutInflater inflater = activity.getLayoutInflater();
		final View rootView = inflater.inflate(R.layout.custom_listview, null,
				true);

		ImageView_TabIcon = (ImageView) rootView
				.findViewById(R.id.ImgeView_TabIcon);
		TextView_TabText = (TextView) rootView
				.findViewById(R.id.FontTextView_TabText);

		switch (position) {

				case 0:// Personal Information
						TextView_TabText.setText("Personal information");
						ImageView_TabIcon.setImageResource(R.drawable.ic_personal_info);
						break;
				case 1:// Injuries/Diseases
					TextView_TabText.setText("Fitness Details");
					ImageView_TabIcon.setImageResource(R.drawable.ic_injuries);
					break;
				case 2:// Payment Details
					TextView_TabText.setText("Payment Details");
					ImageView_TabIcon.setImageResource(R.drawable.ic_payment_details);
					break;
		}

		return rootView;
	}

}
