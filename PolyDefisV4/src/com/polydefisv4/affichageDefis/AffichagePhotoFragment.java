package com.polydefisv4.affichageDefis;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.bean.defis.Photo;
import com.polydefisv4.listeDefis.TypeUtilisation;

public class AffichagePhotoFragment extends Fragment {
	
	// Activity request codes
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	
	// directory name to store captured images and videos
	private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
	
	private Uri fileUri; // file url to store image/video
	private Photo defis;
	private TypeUtilisation typeUtilisation;
	private Button btnCapturePicture;

	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_afficher_defi_photo, container, false);
        
        defis = (Photo) getArguments().getSerializable("defis");
        typeUtilisation = (TypeUtilisation) getArguments().getSerializable("typeUtilisation");

		TextView titreDefis = (TextView) rootView.findViewById(R.id.intitule_defi);
		titreDefis.setText(defis.getIntitule());

		TextView descriptionDefi = (TextView) rootView.findViewById(R.id.description_defi);
		descriptionDefi.setText(defis.getDescription());

		TextView promo = (TextView) rootView.findViewById(R.id.promotion);		
		promo.setText(defis.getPortee());
        
		TextView nbPoint = (TextView) rootView.findViewById(R.id.nb_point);
		nbPoint.setText(defis.getNombrePoint() + " points");

		TextView dateLimite = (TextView) rootView.findViewById(R.id.date_limite);
		dateLimite.setText(defis.getDateFin().toString());

		btnCapturePicture = (Button) rootView.findViewById(R.id.prendre_photo);

		/*
		 * Capture image button click event
		 */
		btnCapturePicture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				captureImage();
			}
		});

		// Checking camera availability
		if (!isDeviceSupportCamera()) {
			Toast.makeText(getActivity(), "Sorry! Your device doesn't support camera", Toast.LENGTH_LONG).show();
			//finish();
		}
		
		if (typeUtilisation == TypeUtilisation.VisualisationDefisARealiser) {
			nbPoint.setBackgroundColor(Color.TRANSPARENT);
		    nbPoint.setFocusable(false);
		}
		
		return rootView;
	}
	
	

	/**
	 * Receiving activity result method will be called after closing the camera
	 * */

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

	/*
	 * Display image from a path to ImageView
	 */

	private void previewCapturedImage() {
		try {
			String pathImage = fileUri.getPath();
			Toast.makeText(getActivity(), "Image enregistrée a l'adresse suivante : "+pathImage, Toast.LENGTH_LONG).show();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checking device has camera hardware or not
	 * */

	private boolean isDeviceSupportCamera() {
		if (getActivity().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}

	/*
	 * Capturing Camera Image will lauch camera app requrest image capture
	 */

	private void captureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		// start the image capture Intent
		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}

	/**
	 * Here we store the file url as it will be null after returning from camera
	 * app
	 */

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    // save file url in bundle as it will be null on scren orientation
	    // changes
	    outState.putParcelable("file_uri", fileUri);
	}
	
	/*
	 * Creating file uri to store image/video
	 */

	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
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
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			//.getPath()
			mediaFile = new File(mediaStorageDir + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}
		Log.d("Fichier retour", "afficher le fichier " + mediaFile);
		return mediaFile;

	}
}