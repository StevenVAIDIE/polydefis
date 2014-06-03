package com.polydefisv4.bdd;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.Parrainage;
import com.polydefisv4.sql.SQL_Parrainage;

public class ParrainageBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";

	private static final String TABLE_PARRAINAGE = "U_PARRAINAGE";
	private static final String COL_PARRAIN = "Parrain";
	private static final String COL_FILLEUL = "Filleul";
	private static final String COL_ETAT = "Etat";
	private static final int NUM_COL_PARRAIN = 0;
	private static final int NUM_COL_FILLEUL = 1;
	private static final int NUM_COL_ETAT = 2;

	private SQLiteDatabase bdd;

	private SQL_Parrainage SQLParrainage;

	public ParrainageBDD(Context context) {
		// On crée la BDD et sa table
		SQLParrainage = new SQL_Parrainage(context, NOM_BDD, null, VERSION_BDD);
	}

	public void open() {
		// on ouvre la BDD en écriture
		bdd = SQLParrainage.getWritableDatabase();
	}

	public void openReadable() {
		// on ouvre la BDD en lecture
		bdd = SQLParrainage.getReadableDatabase();
	}

	public void close() {
		// on ferme l'accès à la BDD
		bdd.close();
	}

	public SQLiteDatabase getBDD() {
		return bdd;
	}

	public long insertParrainage(Parrainage parrainage) {
		ContentValues values = new ContentValues();
		values.put(COL_PARRAIN, parrainage.getIdParrain());
		values.put(COL_FILLEUL, parrainage.getIdFilleul());
		values.put(COL_ETAT, parrainage.getEtat());
		return bdd.insert(TABLE_PARRAINAGE, null, values);
	}

	public int updateParrainage(Parrainage parrainage) {
		ContentValues values = new ContentValues();
		values.put(COL_ETAT, parrainage.getEtat());
		return bdd.update(TABLE_PARRAINAGE, values, COL_PARRAIN + "='"
				+ parrainage.getIdParrain() + "' AND " + COL_FILLEUL + "='"
				+ parrainage.getIdFilleul() + "'", null);
	}

	public int removeParrainage(Parrainage parrainage) {
		ContentValues values = new ContentValues();
		values.put(COL_PARRAIN, parrainage.getIdParrain());
		values.put(COL_FILLEUL, parrainage.getIdFilleul());

		return bdd.delete(TABLE_PARRAINAGE,
				COL_PARRAIN + "='" + parrainage.getIdParrain() + "' AND "
						+ COL_FILLEUL + "='" + parrainage.getIdFilleul() + "'",
				null);
	}

	public ArrayList<Etudiant> getAllFilleul(Etudiant etudiant, int etat) {
		ArrayList<Etudiant> listeDemandeParrainnage = new ArrayList<Etudiant>();

		String MY_QUERY = "SELECT * FROM " + EtudiantBDD.TABLE_ETUDIANT
				+ " etudiant " + "INNER JOIN " + TABLE_PARRAINAGE
				+ " parrainage ON parrainage." + COL_FILLEUL + "=etudiant."
				+ EtudiantBDD.COL_IDENTIFIANT + " WHERE parrainage."
				+ COL_PARRAIN + "=? AND " + COL_ETAT + "=?";

		Cursor cursor = bdd.rawQuery(
				MY_QUERY,
				new String[] { String.valueOf(etudiant.getIdEtudiant()),
						String.valueOf(etat) });

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			listeDemandeParrainnage.add(new Etudiant(cursor
					.getInt(EtudiantBDD.NUM_COL_IDENTIFIANT), cursor
					.getString(EtudiantBDD.NUM_COL_NOM), cursor
					.getString(EtudiantBDD.NUM_COL_PRENOM), cursor
					.getString(EtudiantBDD.NUM_COL_DEPARTEMENT), cursor
					.getInt(EtudiantBDD.NUM_COL_ANNEE), cursor
					.getInt(EtudiantBDD.NUM_COL_RESPO) > 0, cursor
					.getInt(EtudiantBDD.NUM_COL_POINTS)));

			cursor.moveToNext();
		}

		return listeDemandeParrainnage;
	}

	public Etudiant getParrain(int idEtudiant) {
		String MY_QUERY = "SELECT * FROM " + EtudiantBDD.TABLE_ETUDIANT
				+ " etudiant INNER JOIN " + TABLE_PARRAINAGE
				+ " parrainage ON parrainage." + COL_PARRAIN + "=etudiant."
				+ EtudiantBDD.COL_IDENTIFIANT + " WHERE parrainage."
				+ COL_FILLEUL + "=? AND parrainage." + COL_ETAT + "=?";

		Cursor cursor = bdd.rawQuery(
				MY_QUERY,
				new String[] { String.valueOf(idEtudiant),
						String.valueOf(Parrainage.DEMANDE_ACCEPTE) });

		cursor.moveToFirst();
		if (cursor.getCount() < 1)
			return null;
		
		return new Etudiant(cursor.getInt(EtudiantBDD.NUM_COL_IDENTIFIANT),
				cursor.getString(EtudiantBDD.NUM_COL_NOM),
				cursor.getString(EtudiantBDD.NUM_COL_PRENOM),
				cursor.getString(EtudiantBDD.NUM_COL_DEPARTEMENT),
				cursor.getInt(EtudiantBDD.NUM_COL_ANNEE),
				cursor.getInt(EtudiantBDD.NUM_COL_RESPO) > 0,
				cursor.getInt(EtudiantBDD.NUM_COL_POINTS));
	}

	public Parrainage getParrainage(Etudiant parrain, Etudiant filleul) {
		Cursor cursor = bdd.query(
				TABLE_PARRAINAGE, // table
				new String[] { COL_PARRAIN, COL_FILLEUL, COL_ETAT }, // column
				COL_PARRAIN + " = ? AND " + COL_FILLEUL + " = ?", // selections
				new String[] { String.valueOf(parrain.getIdEtudiant()),
						String.valueOf(filleul.getIdEtudiant()) }, // args
				null, // group by
				null, // having
				null, // order by
				null); // limit

		cursor.moveToFirst();

		// Si aucun enregistrement n'est retourné
		if (cursor.getCount() < 1)
			return null;

		return new Parrainage(cursor.getInt(NUM_COL_PARRAIN),
				cursor.getInt(NUM_COL_FILLEUL), cursor.getInt(NUM_COL_ETAT));
	}

	public SQL_Parrainage getSQL() {
		return SQLParrainage;
	}
}
