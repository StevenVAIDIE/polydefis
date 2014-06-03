package com.polydefisv4.bdd;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.DefiRealise;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.defis.Geolocalisation;
import com.polydefisv4.connexion.Util;
import com.polydefisv4.sql.SQL_DefiGeolocalisation;

public class DefiGeolocalisationBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";

	public static final String TABLE_GEOLOCALISATION = "D_GEOLOCALISATION";
	public static final String COL_IDENTIFIANT = "Identifiant";
	public static final String COL_LATITUDE = "Latitude";
	public static final String COL_LONGITUDE = "Longitude";

	public static final int NUM_COL_IDENTIFIANT = 0;
	public static final int NUM_COL_LATITUDE = 1;
	public static final int NUM_COL_LONGITUDE = 2;

	private SQLiteDatabase bdd;

	private SQL_DefiGeolocalisation SQLDefiGeolocalisation;

	public DefiGeolocalisationBDD(Context context) {
		SQLDefiGeolocalisation = new SQL_DefiGeolocalisation(context, NOM_BDD,
				null, VERSION_BDD);
	}

	public void open() {
		bdd = SQLDefiGeolocalisation.getWritableDatabase();
	}

	public void openReadable() {
		bdd = SQLDefiGeolocalisation.getReadableDatabase();
	}

	public void close() {
		bdd.close();
	}

	public SQLiteDatabase getBDD() {
		return bdd;
	}

	public SQL_DefiGeolocalisation getSQL() {
		return SQLDefiGeolocalisation;
	}

	public int getNbPointsGeolocalisation3A(Etudiant etudiant) {
		String MY_QUERY = "SELECT SUM(" + DefiBDD.COL_POINTS + ") FROM "
				+ DefiBDD.TABLE_DEFI + " defis" + " INNER JOIN "
				+ DefiRealiseBDD.TABLE_DEFIREALISE
				+ " defisRealise ON defisRealise." + DefiRealiseBDD.COL_ID_DEFI
				+ " = defis." + DefiBDD.COL_IDENTIFIANT_DEFI
				+ " WHERE defisRealise." + DefiRealiseBDD.COL_ID_ETUDIANT
				+ "=? AND " + DefiBDD.COL_TYPE + "=? AND defisRealise."
				+ DefiRealiseBDD.COL_ETAT + "=?";

		Cursor cursor = bdd.rawQuery(
				MY_QUERY,
				new String[] { String.valueOf(etudiant.getIdEtudiant()),
						String.valueOf(Defi.TYPE_GEOLOCALISATION),
						DefiRealise.ETAT_REUSSI});

		cursor.moveToFirst();

		// Si aucun enregistrement n'est retourné
		if (cursor.getCount() < 1)
			return 0;

		return cursor.getInt(0);
	}

	public int getNbPointsGeolocalisation4A(Etudiant etudiant) {
		String MY_QUERY = "SELECT SUM("+ DefiBDD.COL_POINTS +") FROM "
				+ DefiBDD.TABLE_DEFI + " defis INNER JOIN "
				+ DefiRealiseBDD.TABLE_DEFIREALISE
				+ " defiRealise ON defiRealise." + DefiRealiseBDD.COL_ID_DEFI
				+ "=defis." + DefiBDD.COL_IDENTIFIANT_DEFI + " WHERE defis."
				+ DefiBDD.COL_IDENTIFIANT_ETUDIANT + "=? AND defis."
				+ DefiBDD.COL_TYPE + "=?  AND defiRealise."
				+ DefiRealiseBDD.COL_ETAT + "=?";

		Cursor cursor = bdd.rawQuery(
				MY_QUERY,
				new String[] { String.valueOf(etudiant.getIdEtudiant()),
						String.valueOf(Defi.TYPE_GEOLOCALISATION),DefiRealise.ETAT_REUSSI });

		cursor.moveToFirst();

		if (cursor.getCount() < 1)
			return 0;

		return cursor.getInt(0);
	}

	public long insertGeolocalisation(Geolocalisation geoloc) {
		ContentValues values = new ContentValues();
		values.put(COL_IDENTIFIANT, geoloc.getId());
		values.put(COL_LATITUDE, geoloc.getLatitude());
		values.put(COL_LONGITUDE, geoloc.getLongitude());
		return bdd.insert(TABLE_GEOLOCALISATION, null, values);
	}

	public int updateGeolocalisation(int identifiant, Geolocalisation geoloc) {
		ContentValues values = new ContentValues();
		values.put(COL_IDENTIFIANT, geoloc.getId());
		values.put(COL_LATITUDE, geoloc.getLatitude());
		values.put(COL_LONGITUDE, geoloc.getLongitude());
		return bdd.update(TABLE_GEOLOCALISATION, values, COL_IDENTIFIANT
				+ " = " + identifiant, null);
	}

	public int removeGeolocalisation(Geolocalisation geolocalisation) {
		return bdd.delete(TABLE_GEOLOCALISATION, COL_IDENTIFIANT + " = "
				+ geolocalisation.getId(), null);
	}

	public Geolocalisation getGeolocalisation(int idDefi) throws ParseException {
		String MY_QUERY = "SELECT * FROM "
				+ DefiBDD.TABLE_DEFI
				+ " defis INNER JOIN  "
				+ TABLE_GEOLOCALISATION
				+ " geoloc ON geoloc.Identifiant=defis.IdentifiantDefi WHERE defis.IdentifiantDefi=?";
		Cursor cursor = bdd.rawQuery(MY_QUERY,
				new String[] { String.valueOf(idDefi) });
		cursor.moveToFirst();

		// Si aucun enregistrement n'est retourné
		if (cursor.getCount() < 1)
			return null;

		Date date = null;
		if (!cursor.isNull(DefiBDD.NUM_COL_DATE_FIN)) {
			date = Util.dateFormat.parse(cursor
					.getString(DefiBDD.NUM_COL_DATE_FIN));
		}

		Geolocalisation geolocalisation = new Geolocalisation(
				cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_DEFI),
				cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_ETUDIANT),
				cursor.getString(DefiBDD.NUM_COL_INTITULE),
				cursor.getString(DefiBDD.NUM_COL_DESCRIPTION), date,
				cursor.getInt(DefiBDD.NUM_COL_POINTS),
				cursor.getInt(DefiBDD.NUM_COL_ETAT_ACCEPTE),
				cursor.getString(DefiBDD.NUM_COL_PORTEE),
				cursor.getDouble(NUM_COL_LATITUDE + DefiBDD.NUMBER_OF_COLUMS),
				cursor.getDouble(NUM_COL_LONGITUDE + DefiBDD.NUMBER_OF_COLUMS));
		return geolocalisation;
	}

	public ArrayList<Defi> getAllGeolocalisationsARealiser(Etudiant etudiant,
			int idParrain) {
		ArrayList<Defi> listeGeolocalisationARealiser = new ArrayList<Defi>();

		String MY_QUERY = "SELECT * FROM " + DefiBDD.TABLE_DEFI
				+ " defis INNER JOIN " + TABLE_GEOLOCALISATION
				+ " geoloc ON geoloc." + COL_IDENTIFIANT + "=defis."
				+ DefiBDD.COL_IDENTIFIANT_DEFI + " INNER JOIN "
				+ EtudiantBDD.TABLE_ETUDIANT + " etudiant ON etudiant."
				+ EtudiantBDD.COL_IDENTIFIANT + "=defis."
				+ DefiBDD.COL_IDENTIFIANT_ETUDIANT + " WHERE defis."
				+ DefiBDD.COL_ETAT_ACCEPTE + "=?" + " AND (etudiant."
				+ EtudiantBDD.COL_IDENTIFIANT + "=?" + " OR defis."
				+ DefiBDD.COL_PORTEE + "=?" + " OR (defis."
				+ DefiBDD.COL_PORTEE + "=? AND etudiant."
				+ EtudiantBDD.COL_DEPARTEMENT + "=?)) AND NOT EXISTS ("
				+ "SELECT * FROM " + DefiRealiseBDD.TABLE_DEFIREALISE
				+ " realise " + " WHERE realise."
				+ DefiRealiseBDD.COL_ID_ETUDIANT + "=? " + "AND realise."
				+ DefiRealiseBDD.COL_ID_DEFI + "=defis."
				+ DefiBDD.COL_IDENTIFIANT_DEFI+")";

		Cursor cursor = bdd.rawQuery(
				MY_QUERY,
				new String[] { String.valueOf(Defi.ETAT_ACCEPTE),
						String.valueOf(idParrain),
						String.valueOf(Defi.PORTEE_ALL),
						String.valueOf(Defi.PORTEE_PROMO),
						etudiant.getDepartement(),
						String.valueOf(etudiant.getIdEtudiant()) });

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Date date = null;
			try {
				if (!cursor.isNull(DefiBDD.NUM_COL_DATE_FIN)) {
					date = Util.dateFormat.parse(cursor
							.getString(DefiBDD.NUM_COL_DATE_FIN));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Geolocalisation geolocalisation = new Geolocalisation(
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_DEFI),
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_ETUDIANT),
					cursor.getString(DefiBDD.NUM_COL_INTITULE),
					cursor.getString(DefiBDD.NUM_COL_DESCRIPTION), date,
					cursor.getInt(DefiBDD.NUM_COL_POINTS),
					cursor.getInt(DefiBDD.NUM_COL_ETAT_ACCEPTE),
					cursor.getString(DefiBDD.NUM_COL_PORTEE),
					cursor.getDouble(NUM_COL_LATITUDE
							+ DefiBDD.NUMBER_OF_COLUMS - 1),
					cursor.getDouble(NUM_COL_LONGITUDE
							+ DefiBDD.NUMBER_OF_COLUMS - 1));
			listeGeolocalisationARealiser.add(geolocalisation);
			cursor.moveToNext();
		}
		return listeGeolocalisationARealiser;
	}

	public ArrayList<Defi> getAllDefiAAccepter() {
		ArrayList<Defi> listePhotoAAccepter = new ArrayList<Defi>();

		String MY_QUERY = "SELECT * FROM " + DefiBDD.TABLE_DEFI
				+ " defis INNER JOIN " + TABLE_GEOLOCALISATION
				+ " geoloc ON geoloc." + COL_IDENTIFIANT + "=defis."
				+ DefiBDD.COL_IDENTIFIANT_DEFI + " WHERE defis."
				+ DefiBDD.COL_ETAT_ACCEPTE + "=?";

		Cursor cursor = bdd
				.rawQuery(MY_QUERY, new String[] { String
						.valueOf(Defi.ETAT_EN_COURS_ACCEPTATION) });

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Date date = null;
			try {
				if (!cursor.isNull(DefiBDD.NUM_COL_DATE_FIN)) {
					date = Util.dateFormat.parse(cursor
							.getString(DefiBDD.NUM_COL_DATE_FIN));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Geolocalisation photo = new Geolocalisation(
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_DEFI),
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_ETUDIANT),
					cursor.getString(DefiBDD.NUM_COL_INTITULE),
					cursor.getString(DefiBDD.NUM_COL_DESCRIPTION), date,
					cursor.getInt(DefiBDD.NUM_COL_POINTS),
					cursor.getInt(DefiBDD.NUM_COL_ETAT_ACCEPTE),
					cursor.getString(DefiBDD.NUM_COL_PORTEE),
					cursor.getDouble(NUM_COL_LATITUDE
							+ DefiBDD.NUMBER_OF_COLUMS - 1),
					cursor.getDouble(NUM_COL_LONGITUDE
							+ DefiBDD.NUMBER_OF_COLUMS - 1));
			listePhotoAAccepter.add(photo);
			cursor.moveToNext();
		}
		return listePhotoAAccepter;
	}
}