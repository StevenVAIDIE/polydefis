package com.polydefisv4.ajoutDefis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.polydefisv4.R;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.defis.Geolocalisation;

/**
 * @author Dorian KODELJA
 * 
 */
public class AjoutDefiGeolocalisationFragment extends Fragment implements
		OnClickListener, OnMapClickListener {
	private static View rootView;
	private Button boutonValider;
	private GoogleMap map;
	private MarkerOptions marqueur;

	private Geolocalisation defi;
	private Etudiant etudiant;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        
		try {
			rootView = inflater.inflate(R.layout.fragment_ajout_defi_gps, container, false);
		} catch (InflateException e) {}

		defi = (Geolocalisation) getArguments().getSerializable("defis");
		etudiant = (Etudiant) getArguments().getSerializable("etudiant");

		map = ((SupportMapFragment) getActivity().getSupportFragmentManager()
				.findFragmentById(R.id.map1)).getMap();
		
		map.setOnMapClickListener(this);
		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		map.setMyLocationEnabled(true);
		map.getUiSettings().setCompassEnabled(true);
		map.clear();

		boutonValider = ((Button) rootView.findViewById(R.id.boutonValider));
		boutonValider.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		if (!marqueur.isVisible()) {
			Toast.makeText(getActivity(),
					"Veuillez cliquer sur selectionner un point sur la carte",
					Toast.LENGTH_LONG).show();
		} else {
			Fragment newFragment = new AjoutDefiFinaFragment();

			defi.setLongitude(marqueur.getPosition().longitude);
			defi.setLatitude(marqueur.getPosition().latitude);
			
			Bundle bundle = new Bundle();
			bundle.putSerializable("etudiant", etudiant);
			bundle.putSerializable("defis", defi);
			newFragment.setArguments(bundle);

			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.frame_container, newFragment);
			fragmentTransaction.commit();
		}
	}

	@Override
	public void onMapClick(LatLng arg0) {
		map.clear();
		marqueur = new MarkerOptions().position(arg0);
		map.addMarker(marqueur);
	}
}
