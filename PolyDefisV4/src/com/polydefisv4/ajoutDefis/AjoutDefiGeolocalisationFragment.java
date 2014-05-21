package com.polydefisv4.ajoutDefis;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.defis.Geolocalisation;
/**
 * @author Dorian KODELJA
 *
 */
public class AjoutDefiGeolocalisationFragment extends Fragment implements OnClickListener {
	private EditText longitude;
	private EditText latitude;
	Button boutonValider;
	private Geolocalisation defi;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_ajout_defi_gps, container, false);
		defi = (Geolocalisation) getArguments().getSerializable("defis");
		
		longitude = ((EditText) rootView.findViewById(R.id.longitude));		
		latitude = ((EditText) rootView.findViewById(R.id.latitude));
		
		boutonValider = ((Button) rootView.findViewById(R.id.boutonValider));
		boutonValider.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		if (longitude.getText().toString().isEmpty() || latitude.getText().toString().isEmpty()) {
			Toast.makeText(getActivity(), "Veuillez renseigner tous les champs", Toast.LENGTH_LONG).show();
		} else {
			defi.setLongitude(Double.valueOf(longitude.getText().toString()));
			defi.setLatitude(Double.valueOf(latitude.getText().toString()));
		}
	}
}
