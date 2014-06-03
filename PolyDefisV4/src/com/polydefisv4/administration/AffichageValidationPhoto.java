package com.polydefisv4.administration;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.defis.Photo;

public class AffichageValidationPhoto extends Fragment implements OnClickListener {
	private Photo defis;

	private TextView titreDefis;
	private TextView descriptionDefi;
	private ImageView myImageView;
	private Button boutonOk;
	private Button boutonAnnuler;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_affichage_validation_photo, container, false);
		getActivity().setTitle("Validation d'un photo prise");

		defis = (Photo) getArguments().getSerializable("defis");
		
		titreDefis = (TextView) rootView.findViewById(R.id.intitule);
		titreDefis.setText(defis.getIntitule());

		descriptionDefi = (TextView) rootView.findViewById(R.id.description);
		descriptionDefi.setText(defis.getDescription());

	    myImageView = (ImageView) rootView.findViewById(R.id.photoPrise);

		File imgFile = new  File(defis.getUrlPhoto());
		if(imgFile.exists()){
		    BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 8;
		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(),options);
		    myImageView.setImageBitmap(myBitmap);
		} else {
			Log.e(getClass().getName(), "Url de la photo n'existe pas ("+defis.getUrlPhoto()+")");
		}
		
		boutonOk = (Button) rootView.findViewById(R.id.boutonValider);
		boutonOk.setOnClickListener(this);
		
		boutonAnnuler = (Button) rootView.findViewById(R.id.boutonAnnuler);
		boutonAnnuler.setOnClickListener(this);
		
		return rootView;
	}

	@Override
	public void onClick(View v) {
		SQLManager manager = new SQLManager(getActivity());
		if(v.equals(boutonAnnuler)) {
			Toast.makeText(getActivity(), "La photo à bien été refusée", Toast.LENGTH_LONG).show();
			manager.refuserPhotoPrise(defis);
		} else if(v.equals(boutonOk)) {
			Toast.makeText(getActivity(), "La photo à bien été validée", Toast.LENGTH_LONG).show();
			manager.validerPhotoPrise(defis);
		} else {
			Log.e("AffichageValidationPhoto", "On click inconnu");
			return;
		}
		
		getActivity().onBackPressed();
	}
}
