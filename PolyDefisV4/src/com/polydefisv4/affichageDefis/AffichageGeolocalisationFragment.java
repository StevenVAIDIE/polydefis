package com.polydefisv4.affichageDefis;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.polydefisv4.R;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.DefiRealise;
import com.polydefisv4.bean.Etudiant;
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
	private Etudiant etudiant;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		defis = (Geolocalisation) getArguments().getSerializable("defis");
        typeUtilisation = (TypeUtilisation) getArguments().getSerializable("typeUtilisation");
        etudiant = (Etudiant) getArguments().getSerializable("etudiant");
        
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        
        if(typeUtilisation == TypeUtilisation.VisualisationDefisARealiser) {
        	if (servicesOK()) {   
            	try {
        			rootView = inflater.inflate(R.layout.fragment_affichage_geolocalisation, container,false);
                } catch (InflateException e) {}
        		getActivity().setTitle("Affichage d'un défi");

    			TextView nbPoint = (TextView) rootView.findViewById(R.id.nb_point);
    			nbPoint.setText(defis.getNombrePoint() + " points");
    			
    			boutonActiverGeolocalisation = (Button) rootView.findViewById(R.id.bouton_activer_geolocalisation);
    			boutonActiverGeolocalisation.setOnClickListener(this);
        	}
        } else if (typeUtilisation == TypeUtilisation.AdministrationPropositionDefis) {
        	try {
               	rootView = inflater.inflate(R.layout.fragment_affichage_geolocalisation_administration, container, false);
            } catch (InflateException e) {}

    		getActivity().setTitle("Administration d'un défi");

        	nbPoint = (NumberPicker) rootView.findViewById(R.id.nb_point);
    		nbPoint.setMinValue(0);
    		nbPoint.setMaxValue(15);
    		nbPoint.setValue(defis.getNombrePoint());
    		
        	boutonValider = (Button) rootView.findViewById(R.id.bouton_valider);
        	boutonValider.setOnClickListener(this);
        	
        	boutonRefuser = (Button) rootView.findViewById(R.id.bouton_refuser);
        	boutonRefuser.setOnClickListener(this);
        } else {
        	Log.e(getClass().getName(), "Type d'utilisation incorrect");
        }

		map = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		map.setMyLocationEnabled(true);
		map.getUiSettings().setCompassEnabled(true);
		map.clear();
		map.addMarker(new MarkerOptions().position(new LatLng(defis.getLatitude(), defis.getLongitude())));
    	AffichageDefi fragmentDefi = new AffichageDefi();
    	fragmentDefi.setArguments(getArguments());
    
    	FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    	FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    	fragmentTransaction.replace(R.id.fragmentAffichageDefis, fragmentDefi);
    	fragmentTransaction.commit();
    	
		return rootView;
	}

	@Override
	public void onClick(View v) {
		SQLManager manager = new SQLManager(getActivity());

		if(v.equals(boutonActiverGeolocalisation)) {
			activerGeolocalisation();
		} else if(v.equals(boutonRefuser)) {
			Toast.makeText(getActivity(), "Le défi géolocalisation a bien été supprimé", Toast.LENGTH_LONG).show();
			manager.removeGeolocalisation(defis);
			getActivity().onBackPressed();
		} else if(v.equals(boutonValider)) {
			Toast.makeText(getActivity(), "Le défi géolocalisation a bien été validé", Toast.LENGTH_LONG).show();
			manager.validerDefi(defis, nbPoint.getValue());
			getActivity().onBackPressed();
		} else {
			Log.e(getClass().getName(),"OnClick inconnu");
			return;
		}
	}
	
	private void activerGeolocalisation() {
		String meilleurFournisseur;

		LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		meilleurFournisseur = locationManager.getBestProvider(new Criteria(), false);
		Location location = locationManager.getLastKnownLocation(meilleurFournisseur);

		if (location == null) {
			Toast.makeText(getActivity(), "Location non trouvée", Toast.LENGTH_LONG).show();
		} else {
			SQLManager manager = new SQLManager(getActivity());
			Location locationDefis = new Location("Point");
			location.setLatitude(defis.getLatitude());
			location.setLongitude(defis.getLongitude());
			float distance = location.distanceTo(locationDefis);

			if (distance < 200) {
				manager.defiEffectue(defis, etudiant.getIdEtudiant(), DefiRealise.ETAT_REUSSI);
				Toast.makeText(getActivity(), "Félicitation vous etes bien au bonne endroit", Toast.LENGTH_LONG).show();
			} else {
				manager.defiEffectue(defis, etudiant.getIdEtudiant(), DefiRealise.ETAT_ECHEC);
				Toast.makeText(getActivity(), "Désolé votre derniere position connue est a " + distance + "m du lieu souhaité", Toast.LENGTH_LONG).show();
			}
			getActivity().onBackPressed();

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