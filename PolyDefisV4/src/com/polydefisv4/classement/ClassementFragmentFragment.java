package com.polydefisv4.classement;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.polydefisv3.R;
import com.polydefisv3.R.id;
import com.polydefisv3.R.layout;
import com.polydefisv4.metier.Etudiant;

/**
 * A placeholder fragment containing a simple view.
 */
public class ClassementFragmentFragment extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static ClassementFragmentFragment newInstance(int sectionNumber) {
		ClassementFragmentFragment fragment = new ClassementFragmentFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_classement, container, false);
		
		getArguments().getInt(ARG_SECTION_NUMBER);
		ListView gridView = (ListView) rootView.findViewById(R.id.listViewClassement);
		gridView.setAdapter(new ClassementAdapter(this,Etudiant.getAllEtudiant(), Etudiant.getEtudiant()));
		
		return rootView;
	}
}