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
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.DefiRealise;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.defis.QrCode;
import com.polydefisv4.listeDefis.TypeUtilisation;


public class AffichageQrCodeFragment extends Fragment implements OnClickListener {
	private QrCode defis;
	private TypeUtilisation typeUtilisation;
	
	private NumberPicker nbPoint;
	private Button scanBtn;
	private Button boutonValider;
	private Button boutonRefuser;
	private Etudiant etudiant;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        defis = (QrCode) getArguments().getSerializable("defis");
        typeUtilisation = (TypeUtilisation) getArguments().getSerializable("typeUtilisation");
        etudiant = (Etudiant) getArguments().getSerializable("etudiant");
        
        if(typeUtilisation == TypeUtilisation.VisualisationDefisARealiser) {
        	rootView = inflater.inflate(R.layout.fragment_affichage_qrcode, container, false);
    		getActivity().setTitle("Affichage d'un défi");

        	TextView nbPoint = (TextView) rootView.findViewById(R.id.nb_point);
    		nbPoint.setText(defis.getNombrePoint() + " points");
    		
        	scanBtn = (Button) rootView.findViewById(R.id.prendre_qr_code);
        	scanBtn.setOnClickListener(this);
        } else if (typeUtilisation == TypeUtilisation.AdministrationPropositionDefis) {
        	rootView = inflater.inflate(R.layout.fragment_affichage_qrcode_administration, container, false);
    		getActivity().setTitle("Administration d'un QrCode");
        	
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
		SQLManager manager = new SQLManager(getActivity());
		
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			if (scanContent.equals(defis.getQrCode())) {
				Toast.makeText(getActivity(), "Félicitation vous avez retrouvé le bon QrCode", Toast.LENGTH_LONG).show();
				manager.defiEffectue(defis, etudiant.getIdEtudiant(),DefiRealise.ETAT_REUSSI);
				getActivity().onBackPressed();
			} else {
				Toast.makeText(getActivity(), "Désolé il ne s'agit pas du bon QrCode", Toast.LENGTH_LONG).show();
				manager.defiEffectue(defis, etudiant.getIdEtudiant(),DefiRealise.ETAT_ECHEC);
				getActivity().onBackPressed();
			}
		} else {
			Toast.makeText(getActivity(),"No scan data received!", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void onClick(View v) {
		SQLManager manager = new SQLManager(getActivity());
		
		if (v.equals(scanBtn)) {
			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
			scanIntegrator.initiateScan();
			return;
		} else if(v.equals(boutonRefuser)) {
			Toast.makeText(getActivity(), "Le défi QrCode a bien été supprimé", Toast.LENGTH_LONG).show();
			manager.removeQrCode(defis);
			getActivity().onBackPressed();
		} else if(v.equals(boutonValider)) {
			Toast.makeText(getActivity(), "Le défi QrCode a bien été validé", Toast.LENGTH_LONG).show();
			manager.validerDefi(defis, nbPoint.getValue());
			getActivity().onBackPressed();
		} else {
			Log.e(getClass().getName(), "OnClick inconnu");
			return;
		}
	}
}