package com.polydefisv4.affichageDefis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.defis.QrCode;
import com.polydefisv4.listeDefis.TypeUtilisation;


public class AffichageQrCodeFragment extends Fragment implements OnClickListener {
	private QrCode defis;
	private TypeUtilisation typeUtilisation;
	
	private NumberPicker nbPoint;
	private Button scanBtn;
	private Button boutonValider;
	private Button boutonRefuser;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_affichage_qrcode, container, false);
        defis = (QrCode) getArguments().getSerializable("defis");
        typeUtilisation = (TypeUtilisation) getArguments().getSerializable("typeUtilisation");
        
        if(typeUtilisation == TypeUtilisation.VisualisationDefisARealiser) {
        	rootView = inflater.inflate(R.layout.fragment_affichage_photo, container, false);
        	
        	TextView nbPoint = (TextView) rootView.findViewById(R.id.nb_point);
    		nbPoint.setText(defis.getNombrePoint() + " points");
    		
        	scanBtn = (Button) rootView.findViewById(R.id.prendre_qr_code);
        	scanBtn.setOnClickListener(this);
        } else if (typeUtilisation == TypeUtilisation.AdministrationPropositionDefis) {
        	rootView = inflater.inflate(R.layout.fragment_affichage_photo_administration, container, false);
        	
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
    
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			if (scanContent.equals(defis.getQrCode())) {
				defis.setEtatAcceptation(Defi.ETAT_TERMINE);
			} else {
				Toast.makeText(getActivity(), "Désolé il ne s'agit pas du bon QrCode", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(getActivity(),"No scan data received!", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void onClick(View v) {
		if (v.equals(scanBtn)) {
			IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
			scanIntegrator.initiateScan();
		} else if(v.equals(boutonRefuser)) {
			//defis.setEtatAcceptation();
		} else if(v.equals(boutonValider)) {
			defis.setNombrePoint(nbPoint.getValue());
			defis.setEtatAcceptation(Defi.ETAT_ACCEPTE);
		}
	}
}
