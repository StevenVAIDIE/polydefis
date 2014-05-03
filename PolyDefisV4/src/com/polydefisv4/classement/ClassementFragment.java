package com.polydefisv4.classement;

import java.util.Locale;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.polydefisv3.R;
import com.polydefisv3.R.id;
import com.polydefisv3.R.layout;
import com.polydefisv3.R.string;
import com.polydefisv4.metier.Etudiant;

public class ClassementFragment extends Fragment implements TabListener {
	public final static int placeClassementTotal = 0;
	public final static int placeClassementINFO = 1;
	public final static int placeClassementETN = 2;
	public final static int placeClassementTE = 3;
	public final static int placeClassementMAT = 4;
	
	private SectionsPagerAdapter adapter;
	private ViewPager viewPager;
	private ActionBar actionBar;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_test_swip, container, false);
	    
	    actionBar = getActivity().getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	    adapter = new SectionsPagerAdapter(getFragmentManager());
	    
	    viewPager = (ViewPager) rootView.findViewById(R.id.pager);
		viewPager.setAdapter(adapter);
		
		for (int i = 0; i < adapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab().setText(adapter.getPageTitle(i)).setTabListener(this));
		}
		
		viewPager.setOnPageChangeListener(new SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
		
        return rootView;
    }

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return ClassementFragmentFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();

			switch (position) {
			case placeClassementTotal:
				return getString(R.string.titreClassementTotal).toUpperCase(l);
			case placeClassementINFO:
				return getString(R.string.titreClassementINFO).toUpperCase(l);
			case placeClassementETN:
				return getString(R.string.titreClassementETN).toUpperCase(l);
			case placeClassementTE:
				return getString(R.string.titreClassementTE).toUpperCase(l);
			case placeClassementMAT:
				return getString(R.string.titreClassementMAT).toUpperCase(l);
			}
			return null;
		}
	}
}