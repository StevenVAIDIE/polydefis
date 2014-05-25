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
		defis = (Photo) getArguments().getSerializable("defis");

		titreDefis = (TextView) rootView.findViewById(R.id.intitule);
		titreDefis.setText(defis.getIntitule());

		descriptionDefi = (TextView) rootView.findViewById(R.id.description);
		descriptionDefi.setText(defis.getDescription());

		File imgFile = new  File(defis.getUrlPhoto());
		if(imgFile.exists()){

		    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

		    ImageView myImage = (ImageView) rootView.findViewById(R.id.photoPrise);
		    myImage.setImageBitmap(myBitmap);

		}
		
		boutonOk = (Button) rootView.findViewById(R.id.boutonValider);
		boutonOk.setOnClickListener(this);
		
		boutonAnnuler = (Button) rootView.findViewById(R.id.boutonAnnuler);
		boutonAnnuler.setOnClickListener(this);
		
		return rootView;
	}

	@Override
	public void onClick(View v) {
		Button bouton = (Button) v;
		if(bouton.equals(boutonAnnuler)) {
			Toast.makeText(getActivity(), "Refus du defis " + defis.getIntitule(), Toast.LENGTH_LONG).show();
		} else if(bouton.equals(boutonOk)) {
			Toast.makeText(getActivity(), "Acceptation du defis " + defis.getIntitule(), Toast.LENGTH_LONG).show();
		} else {
			Log.e("AffichageValidationPhoto", "On click inconnu");
		}
	}
}
