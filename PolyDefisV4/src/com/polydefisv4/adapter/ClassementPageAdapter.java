package com.polydefisv4.adapter;

import java.util.Locale;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;

import com.polydefisv4.R;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.classement.ClassementFragmentFragment;
import com.polydefisv4.classement.PlaceClassement;

public class ClassementPageAdapter extends FragmentPagerAdapter {
	private Context contexte;
	private Etudiant etudiant;
	private static int anneeClassement;
	
	public ClassementPageAdapter(Context contexte, FragmentManager fm, Etudiant etudiant, int anneeClassement) {
		super(fm);
		this.contexte = contexte;
		this.etudiant = etudiant;
		this.anneeClassement = anneeClassement;
	}
	
	@Override
	public Fragment getItem(int position) {
		Bundle bundle = new Bundle();
		ClassementFragmentFragment classementFragmentFragment = new ClassementFragmentFragment();
		bundle.putInt("section_number", position);
		Log.e("getItem", "anneeClassement " + anneeClassement);
		bundle.putSerializable("etudiant", etudiant);
		bundle.putInt("anneePromotion", anneeClassement);
		classementFragmentFragment.setArguments(bundle);
		return classementFragmentFragment;
	}

	@Override
	public int getCount() {
		return 5;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();

		if (position == PlaceClassement.placeClassementTotal.getEmplacement()) {
			Log.e("getPageTitle", "anneeClassement " + anneeClassement);
			return contexte.getString(R.string.titreClassementTotal).toUpperCase(l);
		} else if (position == PlaceClassement.placeClassementINFO.getEmplacement()) {
			Log.e("getPageTitle", "anneeClassement " + anneeClassement);
			return contexte.getString(R.string.titreClassementINFO).toUpperCase(l);
		} else if (position == PlaceClassement.placeClassementETN.getEmplacement()) {
			Log.e("getPageTitle", "anneeClassement " + anneeClassement);
			return contexte.getString(R.string.titreClassementETN).toUpperCase(l);
		} else if (position == PlaceClassement.placeClassementTE.getEmplacement()) {
			Log.e("getPageTitle", "anneeClassement " + anneeClassement);
			return contexte.getString(R.string.titreClassementTE).toUpperCase(l);
		} else if (position == PlaceClassement.placeClassementMAT.getEmplacement()) {
			Log.e("getPageTitle", "anneeClassement " + anneeClassement);
			return contexte.getString(R.string.titreClassementMAT).toUpperCase(l);
		} else {
			Log.e(getClass().getName(), "Pas de titre a la position " + position);
		}
		return null;
	}
}