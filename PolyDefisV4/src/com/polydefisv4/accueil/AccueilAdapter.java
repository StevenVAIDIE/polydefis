package com.polydefisv4.accueil;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.polydefisv3.R;
import com.polydefisv4.metier.Etudiant;

public class AccueilAdapter extends BaseAdapter {
	Integer[] listeBouton3A = { R.string.listeDefisRealise,
			R.string.listeDefisARealiser, R.string.profil,
			R.string.classement3A, R.string.classement4A };
	Integer[] listeBouton4A = { R.string.listeDefisRealise,
			R.string.proposerDefis, R.string.profil, R.string.classement3A,
			R.string.classement4A };
	Integer[] listeBouton4AAdmin = { R.string.listeDefisRealise,
			R.string.proposerDefis, R.string.profil, R.string.classement3A,
			R.string.classement4A, R.string.administration };

	List<Integer> listeBouton;
	private Activity contexte;

	public AccueilAdapter(Activity contexte, Etudiant etudiant) {
		this.contexte = contexte;
		
		if (etudiant.getAnnee() == 4 && etudiant.isAdmin()) {
			listeBouton = Arrays.asList(listeBouton4AAdmin);
		} else if (etudiant.getAnnee() == 4) {
			listeBouton = Arrays.asList(listeBouton4A);
		} else if (etudiant.getAnnee() == 3) {
			listeBouton = Arrays.asList(listeBouton3A);
		}
	}

	@Override
	public int getCount() {
		return listeBouton.size();
	}

	@Override
	public Object getItem(int position) {
		return (Object) listeBouton.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Button buttonItem;
		if (convertView == null) {
			buttonItem = (Button) LayoutInflater.from(contexte).inflate(R.layout.accueil_liste_item, parent, false);
		} else {
			buttonItem = (Button) convertView;
		}
		
		DisplayMetrics metrics = new DisplayMetrics();
		contexte.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int height = metrics.heightPixels;
		
		buttonItem.setText(listeBouton.get(position));
		buttonItem.setMinimumHeight(height / 6);
		return buttonItem;
	}
}