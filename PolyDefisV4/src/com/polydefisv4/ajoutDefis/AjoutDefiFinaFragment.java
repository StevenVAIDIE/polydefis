package com.polydefisv4.ajoutDefis;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.defis.Geolocalisation;
import com.polydefisv4.bean.defis.Photo;
import com.polydefisv4.bean.defis.QrCode;
import com.polydefisv4.bean.defis.Quizz;

public class AjoutDefiFinaFragment extends Fragment implements OnClickListener {
	private RadioButton radioUnique;
	private RadioButton radioDate;
	private DatePicker dateDefis;
	private Button valider;
	private NumberPicker numPickerPoints;
	
	private Defi defi;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_ajout_defi_final, container, false);
		defi = (Defi) getArguments().getSerializable("defis");

		radioUnique = (RadioButton) rootView.findViewById(R.id.radioUnique);
		radioDate = (RadioButton) rootView.findViewById(R.id.radioDate);
		dateDefis = (DatePicker) rootView.findViewById(R.id.dateDefi);
		numPickerPoints = ((NumberPicker) rootView.findViewById(R.id.Points));
		radioUnique.setOnClickListener(this);
		radioDate.setOnClickListener(this);
		radioDate.setChecked(true);
		valider = (Button) rootView.findViewById(R.id.FinalValidation);
		valider.setOnClickListener(this);

		dateDefis.setMinDate(System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
		GregorianCalendar calendar = new java.util.GregorianCalendar(); 
		calendar.add(GregorianCalendar.YEAR, 1);
		dateDefis.setMaxDate(calendar.getTimeInMillis());

		numPickerPoints.setMinValue(0);
		numPickerPoints.setMaxValue(15);
		
		return rootView;
	}
	
	@Override
	public void onClick(View v) {
		if (v.equals(radioUnique)) {
			dateDefis.setEnabled(false);
		} else if(v.equals(radioDate)) {
			dateDefis.setEnabled(true);
		} else if (v.equals(valider)) {
			if (numPickerPoints.getValue() == 0) {
				Toast.makeText(getActivity(), "Veuillez selectionner un nombre de points a attribuer au défi", Toast.LENGTH_LONG).show();
			}

			else {
				defi.setNombrePoint(numPickerPoints.getValue());
				if (radioUnique.isChecked()) {
					defi.setDateFin(null);
				} else if (radioDate.isChecked()) {
				    Calendar calendar = Calendar.getInstance();
				    calendar.set(dateDefis.getYear(), dateDefis.getMonth(), dateDefis.getDayOfMonth());

					defi.setDateFin(calendar.getTime());
				}
								
				SQLManager manager = new SQLManager(getActivity());
				if(defi instanceof QrCode) {
					manager.insertQrCode((QrCode)defi);
				} else if(defi instanceof Photo) {
					manager.insertPhoto((Photo)defi);
				} else if(defi instanceof Geolocalisation) {
					manager.insertGeolocalisation((Geolocalisation)defi);
				} else if(defi instanceof Quizz) {
					manager.insertQuizz((Quizz)defi);
				} else {
					Log.e(getClass().getName(), "Instance inconnue");
				}
				
				getActivity().onBackPressed();
			}
		} else {
			Log.e(getClass().getName(), "OnClickInconnu");
		}
	}
}