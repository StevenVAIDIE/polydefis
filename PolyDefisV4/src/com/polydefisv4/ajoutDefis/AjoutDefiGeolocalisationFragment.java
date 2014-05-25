package com.polydefisv4.ajoutDefis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.polydefisv4.R;
import com.polydefisv4.bean.defis.Geolocalisation;

/**
 * @author Dorian KODELJA
 *
 */
public class AjoutDefiGeolocalisationFragment extends Fragment implements OnClickListener, OnMapClickListener {
	private Button boutonValider;
	private GoogleMap map;
	private MarkerOptions marqueur;
	
	private Geolocalisation defi;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_ajout_defi_gps, container, false);
		defi = (Geolocalisation) getArguments().getSerializable("defis");
		
		marqueur = new MarkerOptions();
		marqueur.visible(false);
		
		map = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setOnMapClickListener(this);
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		map.setMyLocationEnabled(true);
		map.getUiSettings().setCompassEnabled(true);
		map.addMarker(marqueur);
		
		boutonValider = ((Button) rootView.findViewById(R.id.boutonValider));
		boutonValider.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		if (!marqueur.isVisible()) {
			Toast.makeText(getActivity(), "Veuillez cliquer sur selectionner un point sur la carte", Toast.LENGTH_LONG).show();
		} else {
			defi.setLongitude(marqueur.getPosition().longitude);
			defi.setLatitude(marqueur.getPosition().latitude);
		}
	}

	@Override
	public void onMapClick(LatLng arg0) {
		marqueur.position(arg0);
		marqueur.visible(true);
	}
}
