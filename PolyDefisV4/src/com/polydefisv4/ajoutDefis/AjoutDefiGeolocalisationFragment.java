package com.polydefisv4.ajoutDefis;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.polydefisv4.R;
/**
 * @author Dorian KODELJA
 *
 */
public class AjoutDefiGeolocalisationFragment extends Fragment {

	GoogleMap map;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_ajout_defi_gps, container, false);
		//reprise module gps mathieu
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.setMyLocationEnabled(true);
		
		return rootView;
	}
}
