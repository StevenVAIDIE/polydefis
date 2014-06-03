package com.polydefisv4.ajoutDefis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.affichageDefis.IntentIntegrator;
import com.polydefisv4.affichageDefis.IntentResult;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.defis.QrCode;

public class AjoutDefiQRCodeFragment extends Fragment {
	private QrCode qrCode;
	private Etudiant etudiant;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_ajout_defi_qrcode, container, false);
		qrCode = (QrCode) getArguments().get("defis");
		etudiant = (Etudiant) getArguments().getSerializable("etudiant");

		Toast.makeText(getActivity(), "Veuillez scanner le QrCode à retrouver", Toast.LENGTH_LONG).show();
		IntentIntegrator scanIntegrator = new IntentIntegrator(this);
		scanIntegrator.initiateScan();

		return rootView;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		
		// retrieve scan result
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			if (scanningResult.getContents() != null) {
				String scanContent = scanningResult.getContents();
				qrCode.setQrCode(scanContent);
				
				Bundle bundle = new Bundle();
				bundle.putSerializable("defis", qrCode);
				bundle.putSerializable("etudiant", etudiant);
				Fragment newFragment = new AjoutDefiFinaFragment();
				newFragment.setArguments(bundle);
			
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				fragmentTransaction.replace(R.id.frame_container, newFragment);
				fragmentTransaction.commit();
			} else {
				Toast.makeText(getActivity(),"Scan null", Toast.LENGTH_SHORT).show();
				Log.e(getClass().getName(), "Scan null");
			}
		} else {
			Toast.makeText(getActivity(),"No scan data received!", Toast.LENGTH_SHORT).show();
			Log.e(getClass().getName(), "No scan data received!");
		}
	}
}