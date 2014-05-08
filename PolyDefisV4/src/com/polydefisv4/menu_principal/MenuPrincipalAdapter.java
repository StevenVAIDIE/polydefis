package com.polydefisv4.menu_principal;

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
import com.polydefisv4.bean.Etudiant;

public class MenuPrincipalAdapter extends BaseAdapter {

	List<String> listeBouton;
	private Activity contexte;

	public MenuPrincipalAdapter(Activity contexte, Etudiant etudiant) {
		this.contexte = contexte;
		
		if (etudiant.getAnneePromo() == 4 && etudiant.isRespo()) {
			listeBouton = Arrays.asList(contexte.getResources().getStringArray(R.array.menu4AAdmin));
		} else if (etudiant.getAnneePromo() == 4) {
			listeBouton = Arrays.asList(contexte.getResources().getStringArray(R.array.menu4A));
		} else if (etudiant.getAnneePromo() == 3) {
			listeBouton = Arrays.asList(contexte.getResources().getStringArray(R.array.menu3A));
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
			buttonItem = (Button) LayoutInflater.from(contexte).inflate(R.layout.item_accueil, parent, false);
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