package com.polydefisv4.affichageDefis;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
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
import com.polydefisv4.listeDefis.TypeUtilisation;

public class AffichageGeolocalisationFragment extends Fragment implements
		OnClickListener {
	private static View rootView;
	private TextView titreDefis;
	private TextView descriptionDefi;
	private GoogleMap map;
	private TextView nbPoint;
	private TextView dateLimite;
	private TextView promo;
	private Button boutonActiverGeolocalisation;
	private Geolocalisation defis;
	private TypeUtilisation typeUtilisation;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		defis = (Geolocalisation) getArguments().getSerializable("defis");
        typeUtilisation = (TypeUtilisation) getArguments().getSerializable("typeUtilisation");

	    if (servicesOK()) {
			rootView = inflater.inflate(R.layout.fragment_affichage_geolocalisation, container,false);

			titreDefis = (TextView) rootView.findViewById(R.id.intitule_defi);
			titreDefis.setText(defis.getIntitule());

			descriptionDefi = (TextView) rootView.findViewById(R.id.description_defi);
			descriptionDefi.setText(defis.getDescription());

			nbPoint = (TextView) rootView.findViewById(R.id.nb_point);
			nbPoint.setText(defis.getNombrePoint() + " points");

			promo = (TextView) rootView.findViewById(R.id.promotion);
			promo.setText(defis.getPortee());

			dateLimite = (TextView) rootView.findViewById(R.id.date_limite);
			dateLimite.setText(defis.getDateFin().toString());

			boutonActiverGeolocalisation = (Button) rootView
					.findViewById(R.id.bouton_activer_geolocalisation);
			boutonActiverGeolocalisation.setOnClickListener(this);

			map = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			map.setMyLocationEnabled(true);
			map.getUiSettings().setCompassEnabled(true);
			map.addMarker(new MarkerOptions().position(new LatLng(defis.getLatitude(), defis.getLongitude())));
			
			if (typeUtilisation == TypeUtilisation.VisualisationDefisARealiser) {
				nbPoint.setBackgroundColor(Color.TRANSPARENT);
			    nbPoint.setFocusable(false);
			}
		}

		return rootView;
	}

	@Override
	public void onClick(View v) {
		String meilleurFournisseur;

		LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		meilleurFournisseur = locationManager.getBestProvider(new Criteria(), false);
		Location location = locationManager.getLastKnownLocation(meilleurFournisseur);

		if (location == null) {
			Toast.makeText(getActivity(), "Location Not found",
					Toast.LENGTH_LONG).show();
		} else {
			Location locationDefis = new Location("Point");
			location.setLatitude(defis.getLatitude());
			location.setLongitude(defis.getLongitude());
			float distance = location.distanceTo(locationDefis);

			Toast.makeText(getActivity(), "Distance avec le marqueur : " + distance, Toast.LENGTH_LONG).show();
		}
	}
	
	public boolean servicesOK() {
	    int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
	    if (isAvailable == ConnectionResult.SUCCESS) {
	        return true;
	    } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
	        GooglePlayServicesUtil.getErrorDialog(isAvailable, getActivity(), 9001).show();
	    } else {
	        Toast.makeText(getActivity(), "Cant connect!!", Toast.LENGTH_SHORT).show();
	    }
	    return false;
	}
}