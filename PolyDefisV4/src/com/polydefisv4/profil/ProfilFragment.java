package com.polydefisv4.profil;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.polydefisv3.R;
import com.polydefisv4.metier.Etudiant;
import com.polydefisv4.parrainage.ParrainageFragment;

public class ProfilFragment extends Fragment {
	private TextView nomEtudiant;
	private TextView promotion;
	private TextView nbPoint;
	private TextView nbPointPhoto;
	private TextView nbPointQuizz;
	private TextView nbPointGeolocalisation;
	private TextView nbPointQrCode;
	private Button boutonParrainage;

	private Etudiant etudiant;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profil, container, false);
		
		Bundle i = getArguments();
	    etudiant = (Etudiant) i.getSerializable("etudiant");
	    
		nomEtudiant = (TextView) rootView.findViewById(R.id.nomEtudiant);
		nomEtudiant.setText(etudiant.getNom()+" "+etudiant.getPrenom());
		
		promotion = (TextView) rootView.findViewById(R.id.promotion);
		promotion.setText(etudiant.getAnnee() + "A " +  etudiant.getSpecialite());
		
		nbPoint = (TextView) rootView.findViewById(R.id.nombrePoint);
		nbPoint.setText(String.valueOf(etudiant.getNbPoint()));
		
		nbPointPhoto = (TextView) rootView.findViewById(R.id.point_photo);
		nbPointPhoto.setText(String.valueOf(etudiant.getNbPointPhoto()));

		nbPointQuizz = (TextView) rootView.findViewById(R.id.point_quizz);
		nbPointQuizz.setText(String.valueOf(etudiant.getNbPointQuizz()));

		nbPointGeolocalisation = (TextView) rootView.findViewById(R.id.point_geolocalisation);
		nbPointGeolocalisation.setText(String.valueOf(etudiant.getNbPointGeolocalisation()));

		nbPointQrCode = (TextView) rootView.findViewById(R.id.point_qr_code);
		nbPointQrCode.setText(String.valueOf(etudiant.getNbPointQrCode()));		
	
		boutonParrainage = (Button) rootView.findViewById(R.id.bouton_parrainage);
		boutonParrainage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Fragment fragment = new ParrainageFragment();

				Bundle bundle = new Bundle();
				bundle.putSerializable("etudiant", etudiant);
				fragment.setArguments(bundle);
				
				FragmentManager fragmentManager = getFragmentManager();
				FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				fragmentTransaction.replace(R.id.frame_container, fragment);
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();				
			}
		});
		
		return rootView;
	}
}