package com.polydefisv4.adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polydefisv4.R;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.parrainage.ParrainageFragment;

public class ListeAcceptationParrainnageAdapter extends BaseAdapter{
	private List<Etudiant> listeEtudiant;
	private ParrainageFragment contexte;

	public ListeAcceptationParrainnageAdapter(ParrainageFragment contexte, List<Etudiant> listeEtudiant) {
		this.contexte = contexte;
		this.listeEtudiant = listeEtudiant;
	}
	
	@Override
	public int getCount() {
		return listeEtudiant.size();
	}

	@Override
	public Object getItem(int position) {
		return listeEtudiant.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout layoutItem;
		if (convertView == null) {
			layoutItem = (LinearLayout) LayoutInflater.from(contexte.getActivity()).inflate(R.layout.item_parrainage4a, parent, false);
		} else {
			layoutItem = (LinearLayout) convertView;
		}

		TextView nom = (TextView) layoutItem.findViewById(R.id.nomDemandeParrainage);
		nom.setText(listeEtudiant.get(position).getNom()+" "+listeEtudiant.get(position).getPrenom());
		
		Button boutonValiderParrainage = (Button) layoutItem.findViewById(R.id.buttonAccepterParrainage);
		boutonValiderParrainage.setTag(position);
		boutonValiderParrainage.setOnClickListener(contexte);
		
		Button boutonRefuserParrainage = (Button) layoutItem.findViewById(R.id.buttonRefuserParrainage);
		boutonRefuserParrainage.setTag(position);
		boutonRefuserParrainage.setOnClickListener(contexte);
				
		return layoutItem;
	}

}
