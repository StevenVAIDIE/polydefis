package com.polydefisv4.profil;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.polydefisv4.R;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.menu_principal.ListeParrainageAdapter;
import com.polydefisv4.parrainage.ParrainageFragment;

public class ProfilFragment extends Fragment {
	private TextView nomEtudiant;
	private TextView promotion;
	private TextView nbPoint;
	private TextView nbPointPhoto;
	private TextView nbPointQuizz;
	private TextView nbPointGeolocalisation;
	private TextView nbPointQrCode;
	private ListView nomParrain;
	private Button boutonParrainage;

	private Etudiant etudiant;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profil, container, false);
	    getActivity().setTitle("Profil");

		Bundle i = getArguments();
	    etudiant = (Etudiant) i.getSerializable("etudiant");
	    
	    SQLManager manager = new SQLManager(getActivity());
	    etudiant = manager.getEtudiant(etudiant.getIdEtudiant());
	    
	    ArrayList<Etudiant> listeEtudiant = new ArrayList<Etudiant>();
		if(etudiant.getAnneePromo() == 3) {
			Etudiant temp = manager.getParrain(etudiant.getIdEtudiant());
			if(temp != null) {
				listeEtudiant.add(temp);
			}
		} else if (etudiant.getAnneePromo() == 4){
			ArrayList<Etudiant> temp = manager.getAllFilleul(etudiant); 
			if(temp != null) {
				listeEtudiant.addAll(temp);
			}
		}
			    
		nomEtudiant = (TextView) rootView.findViewById(R.id.nomEtudiant);
		nomEtudiant.setText(etudiant.getNom()+" "+etudiant.getPrenom());
		
		promotion = (TextView) rootView.findViewById(R.id.promotion);
		promotion.setText(etudiant.getAnneePromo() + "A " +  etudiant.getDepartement());
		
		nbPoint = (TextView) rootView.findViewById(R.id.nombrePoint);
		nbPoint.setText(String.valueOf(etudiant.getPoints()+" points"));
		
		nbPointPhoto = (TextView) rootView.findViewById(R.id.point_photo);
		nbPointPhoto.setText(String.valueOf(manager.getNbPointsPhoto(etudiant)));

		nbPointQuizz = (TextView) rootView.findViewById(R.id.point_quizz);
		nbPointQuizz.setText(String.valueOf(manager.getNbPointsQuizz(etudiant)));

		nbPointGeolocalisation = (TextView) rootView.findViewById(R.id.point_geolocalisation);
		nbPointGeolocalisation.setText(String.valueOf(manager.getNbPointsGeolocalisation(etudiant)));

		nbPointQrCode = (TextView) rootView.findViewById(R.id.point_qr_code);
		nbPointQrCode.setText(String.valueOf(manager.getNbPointsQrCode(etudiant)));
		
		nomParrain = (ListView) rootView.findViewById(R.id.nomParrain);
		nomParrain.setAdapter(new ListeParrainageAdapter(this, listeEtudiant));
		
		boutonParrainage = (Button) rootView.findViewById(R.id.bouton_parrainage);
		
		if (!listeEtudiant.isEmpty() && etudiant.getAnneePromo() == 3) {
			boutonParrainage.setVisibility(View.INVISIBLE);
		}
		
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