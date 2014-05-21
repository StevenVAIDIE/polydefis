package com.polydefisv4.affichageDefis;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.polydefisv4.R;
import com.polydefisv4.bean.defis.Geolocalisation;

public class AffichageGeolocalisationFragment extends Fragment implements OnClickListener {
	private static View rootView;
	private TextView titreDefis;
	private TextView descriptionDefi;
	private GoogleMap map;
	private TextView nbPoint;
	private TextView dateLimite;
	private Button boutonActiverGeolocalisation;
	private Geolocalisation defis;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        defis = (Geolocalisation) getArguments().getSerializable("defis");

	    int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity().getBaseContext());
	    // Showing status
	    if(status!=ConnectionResult.SUCCESS){ // Google Play Services are not available
	        int requestCode = 10;
	        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), requestCode);
	        dialog.show();
	        
	    } else {
	        try {
	        	rootView = inflater.inflate(R.layout.fragment_affichage_geolocalisation, container, false);
	        } catch (InflateException e) {}
		
			titreDefis = (TextView) rootView.findViewById(R.id.intitule_defi);
			titreDefis.setText(defis.getIntitule());

			descriptionDefi = (TextView) rootView.findViewById(R.id.description_defi);
			descriptionDefi.setText(defis.getDescription());

			nbPoint = (TextView) rootView.findViewById(R.id.nb_point);
			nbPoint.setText(defis.getNombrePoint() + " points");

			dateLimite = (TextView) rootView.findViewById(R.id.date_limite);
			dateLimite.setText(defis.getDateFin().toString());

			boutonActiverGeolocalisation = (Button) rootView.findViewById(R.id.bouton_activer_geolocalisation);
			boutonActiverGeolocalisation.setOnClickListener(this);

			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			map.setMyLocationEnabled(true);
			map.getUiSettings().setCompassEnabled(true);
			map.addMarker(new MarkerOptions().position(new LatLng(defis.getLatitude(), defis.getLongitude())));
	    }
	    
		return rootView;
	}

	@Override
	public void onClick(View v) {
	    final LocationManager mlocManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
	    final Location currentGeoLocation = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	    
	    Location location = new Location("Point");
	    location.setLatitude(defis.getLatitude());
	    location.setLongitude(defis.getLongitude());
	    
	    float distance = location.distanceTo(currentGeoLocation);
	    
		Toast.makeText(getActivity(), "Distance avec le marqueur : " + distance, Toast.LENGTH_LONG).show();
	}
}