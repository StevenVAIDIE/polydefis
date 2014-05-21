package com.polydefisv4.affichageDefis;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.bean.Defi;

public class AffichageQrCodeFragment extends Fragment implements OnClickListener {
	Defi defis;
	
	TextView intituleQrCode;
	TextView descriptionQrCode;
	TextView nbPoint;
	TextView dateLimite;
	TextView promo;
	private Button scanBtn;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_afficher_defis_qrcode, container, false);
        defis = (Defi) getArguments().getSerializable("defis");
        
        intituleQrCode = (TextView) rootView.findViewById(R.id.intitule_defi);		
        descriptionQrCode = (TextView) rootView.findViewById(R.id.description_defi);		
        nbPoint = (TextView) rootView.findViewById(R.id.nb_point);		
        dateLimite = (TextView) rootView.findViewById(R.id.date_limite);		
        promo = (TextView) rootView.findViewById(R.id.promotion);		
		scanBtn = (Button) rootView.findViewById(R.id.prendre_qr_code);		
        
        intituleQrCode.setText(defis.getIntitule());
        descriptionQrCode.setText(defis.getDescription());
        nbPoint.setText(defis.getNombrePoint()+" ");
        dateLimite.setText(defis.getDateFin().toString());
     //   promo.setText(defis.getPortee().toString());
		scanBtn.setOnClickListener(this);
		
		return rootView;
	}
    
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		
		// retrieve scan result
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			Toast.makeText(getActivity(), "Resultat du scan : "+scanContent, Toast.LENGTH_LONG).show();
			//Enregistrer scanContent dans la BD
		} else {
			Toast.makeText(getActivity(),"No scan data received!", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void onClick(View v) {
		if (v.equals(scanBtn)) {
			IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
			scanIntegrator.initiateScan();
		}
	}
}
