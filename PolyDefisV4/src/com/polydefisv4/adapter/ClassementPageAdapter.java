package com.polydefisv4.adapter;

import java.util.Locale;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.polydefisv4.R;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.classement.ClassementFragmentFragment;
import com.polydefisv4.classement.PlaceClassement;


public class ClassementPageAdapter extends FragmentStatePagerAdapter  {
	private Context contexte;
	private Etudiant etudiant;
	private int anneeClassement;
	final static int NB_PAGE = 5;
	
	public ClassementPageAdapter(Context contexte, FragmentManager fm, Etudiant etudiant, int anneeClassement) {
		super(fm);
		this.contexte = contexte;
		this.etudiant = etudiant;
		this.anneeClassement = anneeClassement;
		Log.e("ClassementPageAdapter", "anneeClassement : "+anneeClassement);
	}
	
	@Override
	public Fragment getItem(int position) {
		Log.e("", anneeClassement+"");
	    return ClassementFragmentFragment.newInstance(position, anneeClassement, etudiant);
	}
	
	@Override
	public int getCount() {
		return NB_PAGE;
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