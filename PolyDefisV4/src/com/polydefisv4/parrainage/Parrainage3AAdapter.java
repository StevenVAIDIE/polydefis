package com.polydefisv4.parrainage;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.polydefisv4.metier.Etudiant;

public class Parrainage3AAdapter extends ArrayAdapter<Etudiant> implements
		Filterable {

	private ArrayList<Etudiant> fullList;
	private ArrayList<Etudiant> mOriginalValues;
	private ArrayFilter mFilter;

	public Parrainage3AAdapter(Context context, int resource,
			int textViewResourceId, List<Etudiant> objects) {

		super(context, resource, textViewResourceId, objects);
		fullList = (ArrayList<Etudiant>) objects;
		mOriginalValues = new ArrayList<Etudiant>(fullList);
	}

	@Override
	public int getCount() {
		return fullList.size();
	}

	@Override
	public Etudiant getItem(int position) {
		return fullList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new ArrayFilter();
		}
		return mFilter;
	}

	private class ArrayFilter extends Filter {
		private Object lock;

		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();

			if (mOriginalValues == null) {
				synchronized (lock) {
					mOriginalValues = new ArrayList<Etudiant>(fullList);
				}
			}

			if (prefix == null || prefix.length() == 0) {
				synchronized (lock) {
					ArrayList<Etudiant> list = new ArrayList<Etudiant>(
							mOriginalValues);
					results.values = list;
					results.count = list.size();
				}
			} else {
				String prefixString = prefix.toString().toLowerCase();

				ArrayList<Etudiant> values = mOriginalValues;
				int count = values.size();

				ArrayList<Etudiant> newValues = new ArrayList<Etudiant>(count);

				for (int i = 0; i < count; i++) {
					Etudiant item = values.get(i);
					if (item.toString().toLowerCase().contains(prefixString)) {
						newValues.add(item);
					}

				}

				results.values = newValues;
				results.count = newValues.size();
			}

			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			if (results.values != null) {
				fullList = (ArrayList<Etudiant>) results.values;
			} else {
				fullList = new ArrayList<Etudiant>();
			}
			if (results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}
	}
}