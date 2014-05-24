package com.polydefisv4.affichageDefis;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.polydefisv4.bean.defis.Photo;
import com.polydefisv4.listeDefis.TypeUtilisation;

public class AffichagePhotoFragment extends Fragment implements OnClickListener {
	
	// Activity request codes
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int MEDIA_TYPE_IMAGE = 1;
	
	// directory name to store captured images and videos
	private static final String IMAGE_DIRECTORY_NAME = "Polydefis";
	
	private Uri fileUri; // file url to store image/video
	private Photo defis;
	private NumberPicker nbPoint;
	private Button btnCapturePicture;
	private Button boutonValider;
	private Button boutonRefuser;

	private TypeUtilisation typeUtilisation;
	
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        defis = (Photo) getArguments().getSerializable("defis");
        typeUtilisation = (TypeUtilisation) getArguments().getSerializable("typeUtilisation");
        
        if(typeUtilisation == TypeUtilisation.VisualisationDefisARealiser) {
        	rootView = inflater.inflate(R.layout.fragment_affichage_photo, container, false);
        
        	TextView nbPoint = (TextView) rootView.findViewById(R.id.nb_point);
    		nbPoint.setText(defis.getNombrePoint() + " points");
    		
        	btnCapturePicture = (Button) rootView.findViewById(R.id.prendre_photo);
        	btnCapturePicture.setOnClickListener(this);
			
			if (!isDeviceSupportCamera()) {
				Toast.makeText(getActivity(), "Sorry! Your device doesn't support camera", Toast.LENGTH_LONG).show();
			}
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
	
	@Override
	public void onClick(View v) {
		if(v.equals(btnCapturePicture)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileUri = Uri.fromFile(getOutputMediaFile(MEDIA_TYPE_IMAGE));
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
		} else if(v.equals(boutonRefuser)) {
			//defis.setEtatAcceptation();
		} else if(v.equals(boutonValider)) {
			defis.setNombrePoint(nbPoint.getValue());
			defis.setEtatAcceptation(Defi.ETAT_ACCEPTE);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		// if the result is capturing Image
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			getActivity();
			if (resultCode == Activity.RESULT_OK) {
				// successfully captured the image
				// display it in image view
				previewCapturedImage();
			} else if (resultCode == Activity.RESULT_CANCELED) {
				// user cancelled Image capture
				Toast.makeText(getActivity(), "User cancelled image capture", Toast.LENGTH_SHORT).show();
			} else {
				// failed to capture image
				Toast.makeText(getActivity(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void previewCapturedImage() {
		try {
			String pathPhoto = fileUri.getPath();
			defis.setUrlPhoto(pathPhoto);
			Toast.makeText(getActivity(), "Image enregistrée a l'adresse suivante : "+pathPhoto, Toast.LENGTH_LONG).show();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checking device has camera hardware or not
	 * */
	private boolean isDeviceSupportCamera() {
		return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
	}

	/**
	 * Here we store the file url as it will be null after returning from camera
	 * app
	 */

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    outState.putParcelable("file_uri", fileUri);
	}

	/*
	 * returning image / video
	 */

	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			//.getPath()
			mediaFile = new File(mediaStorageDir + File.separator + "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}
		Log.d("Fichier retour", "afficher le fichier " + mediaFile);
		return mediaFile;

	}
}