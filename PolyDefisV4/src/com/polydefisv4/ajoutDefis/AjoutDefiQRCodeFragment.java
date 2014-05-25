package com.polydefisv4.ajoutDefis;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.affichageDefis.IntentIntegrator;
import com.polydefisv4.affichageDefis.IntentResult;
import com.polydefisv4.bean.defis.QrCode;

public class AjoutDefiQRCodeFragment extends Fragment {
	QrCode qrCode;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		qrCode = (QrCode) getArguments().get("defi");

		IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
		scanIntegrator.initiateScan();
		Toast.makeText(getActivity(), "Veuillez scanner le QrCode à retrouver", Toast.LENGTH_LONG).show();

		return container;
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		
		// retrieve scan result
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanningResult != null) {
			String scanContent = scanningResult.getContents();
			Toast.makeText(getActivity(), "Resultat du scan : "+scanContent, Toast.LENGTH_LONG).show();
			qrCode.setQrCode(scanContent);
			
			Bundle bundle = new Bundle();
			bundle.putSerializable("defi", qrCode);
			Fragment newFragment = new AjoutDefiFinaFragment();
			newFragment.setArguments(bundle);
			
			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.replace(R.id.frame_container, newFragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		} else {
			Toast.makeText(getActivity(),"No scan data received!", Toast.LENGTH_SHORT).show();
		}
	}
}