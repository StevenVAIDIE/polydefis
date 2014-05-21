package com.polydefisv4.adapter;

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

import com.polydefisv4.R;
import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.defis.Geolocalisation;
import com.polydefisv4.bean.defis.Photo;
import com.polydefisv4.bean.defis.QrCode;
import com.polydefisv4.bean.defis.Quizz;

public class ListeDefisAdapter extends BaseAdapter {
	private ArrayList<Defi> listeDefis;
	private Context contexte;

	public ListeDefisAdapter(Context contexte, ArrayList<Defi> listeDefis) {
		this.contexte = contexte;
		this.listeDefis = listeDefis;
	}

	@Override
	public int getCount() {
		return listeDefis.size();
	}

	@Override
	public Object getItem(int position) {
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
		if (listeDefis.get(position) instanceof Quizz)
			img = contexte.getResources().getDrawable(R.drawable.logo_quizz);

		else if (listeDefis.get(position) instanceof Photo)
			img = contexte.getResources().getDrawable(R.drawable.logo_photo);

		else if (listeDefis.get(position) instanceof Geolocalisation)
			img = contexte.getResources().getDrawable(
					R.drawable.logo_geolocalisation);

		else if (listeDefis.get(position) instanceof QrCode)
			img = contexte.getResources().getDrawable(R.drawable.logo_qr_code);

		ImageView typeDefis = (ImageView) layoutItem
				.findViewById(R.id.typeDefis);
		typeDefis.setImageDrawable(img);

		TextView nomDefis = (TextView) layoutItem.findViewById(R.id.NomDefis);
		nomDefis.setText(listeDefis.get(position).getIntitule());

		TextView nombrePoint = (TextView) layoutItem.findViewById(R.id.nbPoint);
		nombrePoint.setText(String.valueOf(listeDefis.get(position).getNombrePoint()));

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
