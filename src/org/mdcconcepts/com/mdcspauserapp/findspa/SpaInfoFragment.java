package org.mdcconcepts.com.mdcspauserapp.findspa;

import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SpaInfoFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootview=inflater.inflate(R.layout.activity_spa_details, null);
		return rootview;
	}
}
