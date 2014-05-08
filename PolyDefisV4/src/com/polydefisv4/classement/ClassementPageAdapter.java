package com.polydefisv4.classement;

import java.util.Locale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.polydefisv3.R;
import com.polydefisv4.bean.Etudiant;

public class ClassementPageAdapter extends FragmentPagerAdapter {

	private Context contexte;
	private Etudiant etudiant;
	
	public ClassementPageAdapter(Context contexte, FragmentManager fm, Etudiant etudiant) {
		super(fm);
		this.contexte = contexte;
		this.etudiant = etudiant;
	}

	@Override
	public Fragment getItem(int position) {
		return ClassementFragmentFragment.newInstance(position + 1, etudiant);
	}

	@Override
	public int getCount() {
		return 5;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();

		if (position == PlaceClassement.placeClassementTotal.getEmplacement()) {
			return contexte.getString(R.string.titreClassementTotal).toUpperCase(l);
		} else if (position == PlaceClassement.placeClassementINFO.getEmplacement()) {
			return contexte.getString(R.string.titreClassementINFO).toUpperCase(l);
		} else if (position == PlaceClassement.placeClassementETN.getEmplacement()) {
			return contexte.getString(R.string.titreClassementETN).toUpperCase(l);
		} else if (position == PlaceClassement.placeClassementTE.getEmplacement()) {
			return contexte.getString(R.string.titreClassementTE).toUpperCase(l);
		} else if (position == PlaceClassement.placeClassementMAT.getEmplacement()) {
			return contexte.getString(R.string.titreClassementMAT).toUpperCase(l);
		}

		return null;
	}
}