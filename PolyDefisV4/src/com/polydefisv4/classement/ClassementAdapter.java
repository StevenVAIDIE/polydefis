package com.polydefisv4.classement;

import java.util.List;

import android.app.Fragment;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polydefisv3.R;
import com.polydefisv4.metier.Etudiant;

public class ClassementAdapter extends BaseAdapter{
	private List <Etudiant> listeEtudiant;
	private Fragment contexte;
	private Etudiant etudiantConcerne;

	public ClassementAdapter(Fragment contexte, List<Etudiant> listeEtudiant, Etudiant etudiantConcerne) {
		this.listeEtudiant = listeEtudiant;
		this.contexte = contexte;
		this.etudiantConcerne = etudiantConcerne;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listeEtudiant.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listeEtudiant.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layoutItem;
		if (convertView == null) {
			layoutItem =(LinearLayout) View.inflate(contexte.getActivity(), R.layout.item_classement, null);
		} else {
			layoutItem = (LinearLayout) convertView;
		}

		TextView nom = (TextView) layoutItem.findViewById(R.id.nomEtudiant);
		nom.setText(listeEtudiant.get(position).getNom() + " " + listeEtudiant.get(position).getPrenom());
		
		TextView nbPoint = (TextView) layoutItem.findViewById(R.id.nbPoint);
		nbPoint.setText(String.valueOf(listeEtudiant.get(position).getNbPoint()));
		
		if (listeEtudiant.get(position).equals(etudiantConcerne)) {
			layoutItem.setBackgroundColor(Color.BLUE);
		} 
		
		return layoutItem;
	}
}
