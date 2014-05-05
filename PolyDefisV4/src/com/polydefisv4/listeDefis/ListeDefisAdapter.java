package com.polydefisv4.listeDefis;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.polydefisv3.R;
import com.polydefisv4.metier.Defis;
import com.polydefisv4.metier.TypeDefis;

public class ListeDefisAdapter extends BaseAdapter {
	private ArrayList<Defis> listeDefis;
	private Context contexte;

	public ListeDefisAdapter(Context contexte, ArrayList<Defis> listeDefis) {
		this.contexte = contexte;
		this.listeDefis = listeDefis;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listeDefis.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listeDefis.get(position);
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
			layoutItem = (LinearLayout) LayoutInflater.from(contexte).inflate(
					R.layout.item_defis_realise, parent, false);
		} else {
			layoutItem = (LinearLayout) convertView;
		}

		Drawable img = null;
		if (listeDefis.get(position).getTypeDefis() == TypeDefis.QUIZZ)
			img = contexte.getResources().getDrawable(R.drawable.logo_quizz);

		else if (listeDefis.get(position).getTypeDefis() == TypeDefis.PHOTO)
			img = contexte.getResources().getDrawable(R.drawable.logo_photo);

		else if (listeDefis.get(position).getTypeDefis() == TypeDefis.GEOLOCALISATION)
			img = contexte.getResources().getDrawable(
					R.drawable.logo_geolocalisation);

		else if (listeDefis.get(position).getTypeDefis() == TypeDefis.QR_CODE)
			img = contexte.getResources().getDrawable(R.drawable.logo_qr_code);

		ImageView typeDefis = (ImageView) layoutItem
				.findViewById(R.id.typeDefis);
		typeDefis.setImageDrawable(img);

		TextView nomDefis = (TextView) layoutItem.findViewById(R.id.NomDefis);
		nomDefis.setText(listeDefis.get(position).getIntitule());

		TextView nombrePoint = (TextView) layoutItem.findViewById(R.id.nbPoint);
		nombrePoint.setText(String.valueOf(listeDefis.get(position)
				.getNbPoint()));

		if (position % 2 == 0) {
			layoutItem.setBackgroundColor(contexte.getResources().getColor(
					R.color.list_color_pair));
		} else {
			layoutItem.setBackgroundColor(contexte.getResources().getColor(
					R.color.list_color_impair));
		}

		return layoutItem;
	}

}
