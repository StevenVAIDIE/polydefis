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
import com.polydefisv4.bean.defis.Photo;
import com.polydefisv4.connexion.Util;
import com.polydefisv4.sql.SQL_Defi;

public class DefiBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
 
	public static final String TABLE_DEFI = "D_DEFI";
	public static final String COL_IDENTIFIANT_DEFI = "IdentifiantDefi";
	public static final String COL_IDENTIFIANT_ETUDIANT = "IdentifiantEtudiant";
	public static final String COL_TYPE = "Type";
	public static final String COL_INTITULE = "Intitule";
	public static final String COL_DESCRIPTION = "Description";
	public static final String COL_DATE_FIN = "DateFin";
	public static final String COL_POINTS = "Points";
	public static final String COL_PORTEE = "Portee";
	public static final String COL_ETAT_ACCEPTE = "EtatAccepte";
	
	public static final int NUM_COL_IDENTIFIANT_DEFI = 0;
	public static final int NUM_COL_IDENTIFIANT_ETUDIANT = 1;
	public static final int NUM_COL_TYPE = 2;
	public static final int NUM_COL_INTITULE = 3;
	public static final int NUM_COL_DESCRIPTION = 4;
	public static final int NUM_COL_DATE_FIN = 5;
	public static final int NUM_COL_POINTS = 6;
	public static final int NUM_COL_PORTEE = 7;
	public static final int NUM_COL_ETAT_ACCEPTE = 8;
 
	public static final int NUMBER_OF_COLUMS = 9;
	
	private SQLiteDatabase bdd;
 
	private SQL_Defi SQLDefi;
 
	public DefiBDD(Context context) {
		SQLDefi = new SQL_Defi(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		bdd = SQLDefi.getWritableDatabase();
	}
	
	public void openReadable(){
		bdd = SQLDefi.getReadableDatabase();
	}
 
	public void close(){
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	
	public SQL_Defi getSQL(){
		return SQLDefi;
	}
 
	public long insertDefi(Defi defi, int type){
		ContentValues values = new ContentValues();
		values.put(COL_IDENTIFIANT_ETUDIANT, defi.getIdEtudiant());
		values.put(COL_TYPE, type);
		values.put(COL_INTITULE, defi.getIntitule());
		values.put(COL_DESCRIPTION, defi.getDescription());
		if (defi.getDateFin() != null) {
			values.put(COL_DATE_FIN, Util.dateFormat.format(defi.getDateFin()));
		} else { 
			values.putNull(COL_DATE_FIN);
		}
		values.put(COL_POINTS, defi.getNombrePoint());
		values.put(COL_PORTEE, String.valueOf(defi.getPortee()));
		values.put(COL_ETAT_ACCEPTE, Defi.ETAT_EN_COURS_ACCEPTATION);
		return bdd.insert(TABLE_DEFI, null, values);
	}
 
	
	public int updateDefi(Defi defi){
		ContentValues values = new ContentValues();
		values.put(COL_IDENTIFIANT_ETUDIANT, defi.getIdEtudiant());
		values.put(COL_INTITULE, defi.getIntitule());
		values.put(COL_DESCRIPTION, defi.getDescription());
		if(defi.getDateFin() == null) {
			values.putNull(COL_DATE_FIN);
		} else {
			values.put(COL_DATE_FIN, Util.dateFormat.format(defi.getDateFin()));
		}
		values.put(COL_POINTS, defi.getNombrePoint());
		values.put(COL_PORTEE, String.valueOf(defi.getPortee()));
		values.put(COL_ETAT_ACCEPTE, defi.getEtatAcceptation());
		return bdd.update(TABLE_DEFI, values, COL_IDENTIFIANT_DEFI + " = " + defi.getId(), null);
	}
	
	public int accepterDefi(Defi defi) {
			ContentValues values = new ContentValues();
			values.put(COL_ETAT_ACCEPTE, Defi.ETAT_ACCEPTE);
			return bdd.update(TABLE_DEFI, values, COL_IDENTIFIANT_DEFI + " = " + defi.getId(), null);
	}
	
	public int removeDefi(Defi defi){
		return bdd.delete(TABLE_DEFI, COL_IDENTIFIANT_DEFI+"="+defi.getId(), null);
	}
	
	public void updateEtatDefiDate() {
		String MY_QUERY = "SELECT * FROM " + DefiBDD.TABLE_DEFI + " WHERE " + COL_ETAT_ACCEPTE + "!=? AND "+COL_DATE_FIN+" IS NOT NULL";

		Cursor cursor = bdd.rawQuery(MY_QUERY, new String[]{String.valueOf(Defi.ETAT_TERMINE)});		
	    cursor.moveToFirst();
	    while(!cursor.isAfterLast()) {
	    	Date dateDefi = null;
	    	try {
				dateDefi = Util.dateFormat.parse(cursor.getString(NUM_COL_DATE_FIN));
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    	
	    	Date aujourdhui = new Date();
	    	if(aujourdhui.after(dateDefi)) {
				ContentValues values = new ContentValues();
				values.put(COL_ETAT_ACCEPTE, Defi.ETAT_TERMINE);
	    		bdd.update(TABLE_DEFI, values, COL_IDENTIFIANT_DEFI + " = " + cursor.getString(NUM_COL_IDENTIFIANT_DEFI), null);
	    	}
			cursor.moveToNext();
	    }
	}
	
	public ArrayList<Defi> getAllPhotosARealiser(Etudiant etudiant,
			int idParrain) {
		ArrayList<Defi> listePhotoARealiser = new ArrayList<Defi>();

		String MY_QUERY = "SELECT * FROM " + DefiBDD.TABLE_DEFI + " defis INNER JOIN "
				+ EtudiantBDD.TABLE_ETUDIANT + " etudiant ON etudiant."
				+ EtudiantBDD.COL_IDENTIFIANT + "=defis."
				+ DefiBDD.COL_IDENTIFIANT_ETUDIANT + " WHERE defis."
				+ DefiBDD.COL_ETAT_ACCEPTE + "=?" + " AND (etudiant."
				+ EtudiantBDD.COL_IDENTIFIANT + "=?" + " OR defis."
				+ DefiBDD.COL_PORTEE + "=?" + " OR (defis."
				+ DefiBDD.COL_PORTEE + "=? AND etudiant."
				+ EtudiantBDD.COL_DEPARTEMENT + "=?)) AND "+COL_TYPE+"=? AND NOT EXISTS ( " 
						+ "SELECT * FROM " + DefiRealiseBDD.TABLE_DEFIREALISE +" realise "
								+ " WHERE realise."+DefiRealiseBDD.COL_ID_ETUDIANT+"=? "
										+ "AND realise."+DefiRealiseBDD.COL_ID_DEFI+"=defis."+DefiBDD.COL_IDENTIFIANT_DEFI+")"
						+ "AND NOT EXISTS ( " 
						+ "SELECT * FROM " + DefiPhotoAValiderBDD.TABLE_DEFI_PHOTO_A_VALIDER +" photoAValider "
								+ " WHERE photoAValider."+DefiPhotoAValiderBDD.COL_IDENTIFIANT_ETUDIANT+"=? "
										+ "AND photoAValider."+DefiPhotoAValiderBDD.COL_IDENTIFIANT_DEFI+"=defis."+DefiBDD.COL_IDENTIFIANT_DEFI+")";

		Cursor cursor = bdd.rawQuery(
				MY_QUERY,
				new String[] { String.valueOf(Defi.ETAT_ACCEPTE),
						String.valueOf(idParrain),
						String.valueOf(Defi.PORTEE_ALL),
						String.valueOf(Defi.PORTEE_PROMO),
						etudiant.getDepartement(),
						String.valueOf(Defi.TYPE_PHOTO),
						String.valueOf(etudiant.getIdEtudiant()),
						String.valueOf(etudiant.getIdEtudiant())});

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Date date = null;
			try {
				if (!cursor.isNull(DefiBDD.NUM_COL_DATE_FIN)) {
					date = Util.dateFormat.parse(cursor.getString(DefiBDD.NUM_COL_DATE_FIN));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Photo photo = new Photo(
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_DEFI),
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_ETUDIANT),
					cursor.getString(DefiBDD.NUM_COL_INTITULE),
					cursor.getString(DefiBDD.NUM_COL_DESCRIPTION), date,
					cursor.getInt(DefiBDD.NUM_COL_POINTS),
					cursor.getInt(DefiBDD.NUM_COL_ETAT_ACCEPTE),
					cursor.getString(DefiBDD.NUM_COL_PORTEE));
			listePhotoARealiser.add(photo);
			cursor.moveToNext();
		}
		return listePhotoARealiser;
	}

	public ArrayList<Defi> getAllPhotoAAccepter() {
		ArrayList<Defi> listePhotoAAccepter = new ArrayList<Defi>();

		String MY_QUERY = "SELECT * FROM " + DefiBDD.TABLE_DEFI + " WHERE "
				+ DefiBDD.COL_ETAT_ACCEPTE + "=? AND type=?";
		
		Cursor cursor = bdd.rawQuery(
				MY_QUERY,
				new String[] { String.valueOf(Defi.ETAT_EN_COURS_ACCEPTATION),String.valueOf(Defi.TYPE_PHOTO)});

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Date date = null;
			try {
				if (!cursor.isNull(DefiBDD.NUM_COL_DATE_FIN)) {
					date = Util.dateFormat.parse(cursor.getString(DefiBDD.NUM_COL_DATE_FIN));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Photo photo = new Photo(
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_DEFI),
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_ETUDIANT),
					cursor.getString(DefiBDD.NUM_COL_INTITULE),
					cursor.getString(DefiBDD.NUM_COL_DESCRIPTION), date,
					cursor.getInt(DefiBDD.NUM_COL_POINTS),
					cursor.getInt(DefiBDD.NUM_COL_ETAT_ACCEPTE),
					cursor.getString(DefiBDD.NUM_COL_PORTEE));
			listePhotoAAccepter.add(photo);
			cursor.moveToNext();
		}
		return listePhotoAAccepter;
	}

	public int getNbPointsPhoto3A(Etudiant etudiant) {
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
				new String[] {String.valueOf(etudiant.getIdEtudiant()),String.valueOf(Defi.TYPE_PHOTO), DefiRealise.ETAT_REUSSI});
		
		cursor.moveToFirst();

		// Si aucun enregistrement n'est retourné
		if (cursor.getCount() < 1)
			return 0;
		
		return cursor.getInt(0);
	}
	
	public int getNbPointsPhoto4A(Etudiant etudiant) {
		String MY_QUERY = "SELECT SUM("+ DefiBDD.COL_POINTS +") FROM "
				+ DefiBDD.TABLE_DEFI + " defis INNER JOIN "
				+ DefiRealiseBDD.TABLE_DEFIREALISE
				+ " defiRealise ON defiRealise." + DefiRealiseBDD.COL_ID_DEFI
				+ "=defis." + DefiBDD.COL_IDENTIFIANT_DEFI + " WHERE defis."
				+ DefiBDD.COL_IDENTIFIANT_ETUDIANT + "=? AND defis."
				+ DefiBDD.COL_TYPE + "=? AND defiRealise."
				+ DefiRealiseBDD.COL_ETAT + "=?";
		
		Cursor cursor = bdd.rawQuery(
				MY_QUERY,
				new String[] {String.valueOf(etudiant.getIdEtudiant()),String.valueOf(Defi.TYPE_PHOTO),DefiRealise.ETAT_REUSSI});
		
		cursor.moveToFirst();

		// Si aucun enregistrement n'est retourné
		if (cursor.getCount() < 1)
			return 0;
		
		return cursor.getInt(0);
	}

	public int getLastId() {
		String MY_QUERY = "SELECT last_insert_rowid()";
		Cursor cursor = bdd.rawQuery(MY_QUERY, null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}	
}
