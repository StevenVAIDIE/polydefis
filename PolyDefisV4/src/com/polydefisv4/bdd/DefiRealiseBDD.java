package com.polydefisv4.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.polydefisv4.bean.DefiRealise;
import com.polydefisv4.connexion.Util;
import com.polydefisv4.sql.SQL_DefiRealise;

public class DefiRealiseBDD {
	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
	public static final String TABLE_DEFIREALISE = "L_DEFIREALISE";
	public static final int NUMBER_OF_COLUMS = 4;

	public static final String COL_ID_ETUDIANT = "IdEtudiant";
	public static final String COL_ID_DEFI = "IdDefi";
	public static final String COL_DATE_REALISE = "Date";
	public static final String COL_ETAT = "Etat";

	public static final int NUM_COL_ID_ETUDIANT = 0;
	public static final int NUM_COL_ID_DEFI = 1;
	public static final int NUM_COL_DATE_REALISE = 2;
	public static final int NUM_COL_ETAT = 3;


	private SQLiteDatabase bdd;

	private SQL_DefiRealise SQLDefiRealise;

	public DefiRealiseBDD(Context context) {
		SQLDefiRealise = new SQL_DefiRealise(context, NOM_BDD, null,
				VERSION_BDD);
	}

	public void open() {
		bdd = SQLDefiRealise.getWritableDatabase();
	}

	public void openReadable() {
		bdd = SQLDefiRealise.getReadableDatabase();
	}

	public void close() {
		bdd.close();
	}

	public SQLiteDatabase getBDD() {
		return bdd;
	}

	public SQL_DefiRealise getSQL() {
		return SQLDefiRealise;
	}

	public long insertDefiRealise(DefiRealise defiRealise) {
		ContentValues values = new ContentValues();
		values.put(COL_ID_ETUDIANT, defiRealise.getIdEtudiant());
		values.put(COL_ID_DEFI, defiRealise.getIdDefi());
		if (defiRealise.getDateRealise() == null) {
			values.putNull(COL_DATE_REALISE);
		} else {
			values.put(COL_DATE_REALISE, Util.dateFormat.format(defiRealise.getDateRealise()));
		}
		values.put(COL_ETAT, defiRealise.getEtat());
		return bdd.insert(TABLE_DEFIREALISE, null, values);
	}

	public int updateDefiRealise(DefiRealise defiRealise, int idDefi,
			String idEtudiant) {
		ContentValues values = new ContentValues();
		values.put(COL_ID_ETUDIANT, defiRealise.getIdEtudiant());
		values.put(COL_ID_DEFI, defiRealise.getIdDefi());
		if (defiRealise.getDateRealise() == null) {
			values.putNull(COL_DATE_REALISE);
		} else {
			values.put(COL_DATE_REALISE, Util.dateFormat.format(defiRealise.getDateRealise()));
		}
		values.put(COL_ETAT, defiRealise.getEtat());
		return bdd.update(TABLE_DEFIREALISE, values, COL_ID_ETUDIANT + " = "
				+ idEtudiant + " AND " + COL_ID_DEFI + " = " + idDefi, null);
	}

	public int removeDefiRealise(int idDefi, String idEtudiant) {
		return bdd.delete(TABLE_DEFIREALISE, COL_ID_ETUDIANT + " = "
				+ idEtudiant + " AND " + COL_ID_DEFI + " = " + idDefi, null);
	}
}
