package com.polydefisv4.affichageDefis;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.polydefisv4.R;
import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.defis.Geolocalisation;
import com.polydefisv4.listeDefis.TypeUtilisation;

public class AffichageGeolocalisationFragment extends Fragment implements
		OnClickListener {
	private static View rootView;
	private GoogleMap map;
	private NumberPicker nbPoint;
	private Button boutonActiverGeolocalisation;
	private Geolocalisation defis;
	private TypeUtilisation typeUtilisation;
	private Button boutonValider;
	private Button boutonRefuser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		defis = (Geolocalisation) getArguments().getSerializable("defis");
        typeUtilisation = (TypeUtilisation) getArguments().getSerializable("typeUtilisation");
        
        if(typeUtilisation == TypeUtilisation.VisualisationDefisARealiser) {
        	if (servicesOK()) {   
    			rootView = inflater.inflate(R.layout.fragment_affichage_geolocalisation, container,false);

    			TextView nbPoint = (TextView) rootView.findViewById(R.id.nb_point);
    			nbPoint.setText(defis.getNombrePoint() + " points");

    			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
    			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    			map.setMyLocationEnabled(true);
    			map.getUiSettings().setCompassEnabled(true);
    			map.addMarker(new MarkerOptions().position(new LatLng(defis.getLatitude(), defis.getLongitude())));
    			
    			boutonActiverGeolocalisation = (Button) rootView.findViewById(R.id.bouton_activer_geolocalisation);
    			boutonActiverGeolocalisation.setOnClickListener(this);
        	}
        } else if (typeUtilisation == TypeUtilisation.AdministrationPropositionDefis) {
        	rootView = inflater.inflate(R.layout.fragment_affichage_geolocalisation_administration, container, false);
    		
        	nbPoint = (NumberPicker) rootView.findViewById(R.id.nb_point);
    		nbPoint.setValue(defis.getNombrePoint());
    		nbPoint.setMinValue(0);
    		nbPoint.setMaxValue(15);
    		
        	boutonValider = (Button) rootView.findViewById(R.id.bouton_valider);
        	boutonValider.setOnClickListener(this);
        	
        	boutonRefuser = (Button) rootView.findViewById(R.id.bouton_refuser);
        	boutonRefuser.setOnClickListener(this);
        } else {
        	Log.e(getClass().getName(), "Type d'utilisation incorrect");
        }
		
    	AffichageDefi fragmentDefi = new AffichageDefi();
    	fragmentDefi.setArguments(getArguments());
    
    	FragmentManager fragmentManager = getFragmentManager();
    	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    	fragmentTransaction.replace(R.id.fragmentAffichageDefis, fragmentDefi);
    	fragmentTransaction.commit();
    	
		return rootView;
	}

	@Override
	public void onClick(View v) {
		if(v.equals(boutonActiverGeolocalisation)) {
			activerGeolocalisation();
		} else if(v.equals(boutonRefuser)) {
			//defis.setEtatAcceptation();
		} else if(v.equals(boutonValider)) {
			defis.setNombrePoint(nbPoint.getValue());
			defis.setEtatAcceptation(Defi.ETAT_ACCEPTE);
		}
	}
	
	private void activerGeolocalisation() {
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