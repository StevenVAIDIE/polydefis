package com.polydefisv4.classement;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.polydefisv4.R;
import com.polydefisv4.adapter.ClassementPageAdapter;
import com.polydefisv4.bean.Etudiant;

public class ClassementFragment extends Fragment implements TabListener {
	private ClassementPageAdapter adapter;
	private ViewPager viewPager;
	private ActionBar actionBar;
	private Etudiant etudiant;
	private int anneeClassement;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_classement, container, false);
        etudiant = (Etudiant) getArguments().getSerializable("etudiant");
        anneeClassement = getArguments().getInt("anneeClassement");
        
        Log.e("","Creation d'un classement des " + anneeClassement+"A");
        
	    actionBar = getActivity().getActionBar();
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    actionBar.removeAllTabs();
	    
	    adapter = new ClassementPageAdapter(getActivity(),getFragmentManager(), etudiant, anneeClassement);
	    
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
}