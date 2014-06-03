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
import com.polydefisv4.bean.defis.QuestionQuizz;
import com.polydefisv4.bean.defis.Quizz;
import com.polydefisv4.connexion.Util;
import com.polydefisv4.sql.SQL_DefiQuizz;

public class DefiQuizzBDD {
	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";

	public static final String TABLE_QUIZZ = "D_QUIZZ";
	public static final String COL_IDENTIFIANT = "IdDefi";
	public static final String COL_NUM_QUESTION = "NumeroQuestion";
	public static final String COL_QUESTION = "Question";
	public static final String COL_BONNE_REPONSE_1 = "BonneReponse1";
	public static final String COL_MAUVAISE_REPONSE_2 = "BonneReponse2";
	public static final String COL_MAUVAISE_REPONSE_3 = "BonneReponse3";
	public static final String COL_MAUVAISE_REPONSE_4 = "BonneReponse4";

	public static final int NUM_COL_IDENTIFIANT = 0;
	public static final int NUM_COL_NUMERO_QUESTION = 1;
	public static final int NUM_COL_QUESTION = 2;
	public static final int NUM_COL_BONNE_REPONSE_1 = 3;
	public static final int NUM_COL_MAUVAISE_REPONSE_2 = 4;
	public static final int NUM_COL_MAUVAISE_REPONSE_3 = 5;
	public static final int NUM_COL_MAUVAISE_REPONSE_4 = 6;

	private SQLiteDatabase bdd;

	private SQL_DefiQuizz SQLDefiQuizz;

	public DefiQuizzBDD(Context context) {
		SQLDefiQuizz = new SQL_DefiQuizz(context, NOM_BDD, null, VERSION_BDD);
	}

	public void open() {
		bdd = SQLDefiQuizz.getWritableDatabase();
	}

	public void openReadable() {
		bdd = SQLDefiQuizz.getReadableDatabase();
	}

	public void close() {
		bdd.close();
	}

	public SQLiteDatabase getBDD() {
		return bdd;
	}

	public SQL_DefiQuizz getSQL() {
		return SQLDefiQuizz;
	}

	public void insertQuizz(Quizz quizz) {
		for (int i = 0; i < quizz.getNbQuestions(); i++) {
			QuestionQuizz questionQuizz = quizz.getQuestions(i);

			ContentValues values = new ContentValues();
			values.put(COL_IDENTIFIANT, quizz.getId());
			values.put(COL_NUM_QUESTION, i);
			values.put(COL_QUESTION, questionQuizz.getQuestion());
			values.put(COL_BONNE_REPONSE_1, questionQuizz.getReponse());
			values.put(COL_MAUVAISE_REPONSE_2, questionQuizz.getReponse2());
			values.put(COL_MAUVAISE_REPONSE_3, questionQuizz.getReponse3());
			values.put(COL_MAUVAISE_REPONSE_4, questionQuizz.getReponse4());

			bdd.insert(TABLE_QUIZZ, null, values);
		}
	}

	public int getNbPointsQuizz3A(Etudiant etudiant) {
		String MY_QUERY = "SELECT SUM("+DefiBDD.COL_POINTS+") FROM "
				+ DefiBDD.TABLE_DEFI + " defis" + " INNER JOIN "
				+ DefiRealiseBDD.TABLE_DEFIREALISE
				+ " defisRealise ON defisRealise." + DefiRealiseBDD.COL_ID_DEFI
				+ " = defis." + DefiBDD.COL_IDENTIFIANT_DEFI
				+ " WHERE defisRealise." + DefiRealiseBDD.COL_ID_ETUDIANT
				+ "=? AND " + DefiBDD.COL_TYPE + "=? AND defisRealise."
				+ DefiRealiseBDD.COL_ETAT + "=?";
		
		Cursor cursor = bdd.rawQuery(
				MY_QUERY,
				new String[] {String.valueOf(etudiant.getIdEtudiant()),String.valueOf(Defi.TYPE_QUIZZ), DefiRealise.ETAT_REUSSI});
		
		cursor.moveToFirst();
		
		if (cursor.getCount() < 1)
			return 0;
		
		return cursor.getInt(0);
	}

	public int getNbPointsQuizz4A(Etudiant etudiant) {
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
				new String[] {String.valueOf(etudiant.getIdEtudiant()),String.valueOf(Defi.TYPE_QUIZZ),DefiRealise.ETAT_REUSSI});
		
		cursor.moveToFirst();

		if (cursor.getCount() < 1)
			return 0;
		
		return cursor.getInt(0);
	}
	
	public int removeQuizz(Quizz quizz) {
		return bdd.delete(TABLE_QUIZZ, COL_IDENTIFIANT + " = " + quizz.getId(), null);
	}

	public ArrayList<Defi> getAllQuizzARealiser(Etudiant etudiant,
			int idParrain) {
		ArrayList<Defi> listeQrCodeARealiser = new ArrayList<Defi>();

		String MY_QUERY = "SELECT * FROM " + DefiBDD.TABLE_DEFI + " defis INNER JOIN "
				+ TABLE_QUIZZ + " quizz ON quizz." + COL_IDENTIFIANT + "=defis."
				+ DefiBDD.COL_IDENTIFIANT_DEFI + " INNER JOIN "
				+ EtudiantBDD.TABLE_ETUDIANT + " etudiant ON etudiant."
				+ EtudiantBDD.COL_IDENTIFIANT + "=defis."
				+ DefiBDD.COL_IDENTIFIANT_ETUDIANT + " WHERE defis."
				+ DefiBDD.COL_ETAT_ACCEPTE + "=?" + " AND (etudiant."
				+ EtudiantBDD.COL_IDENTIFIANT + "=?" + " OR defis."
				+ DefiBDD.COL_PORTEE + "=?" + " OR (defis." + DefiBDD.COL_PORTEE
				+ "=? AND etudiant." + EtudiantBDD.COL_DEPARTEMENT + "=?)) AND NOT EXISTS ("
						+ "SELECT * FROM " + DefiRealiseBDD.TABLE_DEFIREALISE +" realise "
								+ " WHERE realise."+DefiRealiseBDD.COL_ID_ETUDIANT+"=? "
										+ "AND realise."+DefiRealiseBDD.COL_ID_DEFI+"=defis."+DefiBDD.COL_IDENTIFIANT_DEFI+") GROUP BY "+COL_IDENTIFIANT;

		Cursor cursor = bdd.rawQuery(
				MY_QUERY,
				new String[] { String.valueOf(Defi.ETAT_ACCEPTE),
						String.valueOf(idParrain),
						String.valueOf(Defi.PORTEE_ALL),
						String.valueOf(Defi.PORTEE_PROMO),
						etudiant.getDepartement(), 						
						String.valueOf(etudiant.getIdEtudiant())});

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

			Quizz quizz = new Quizz (
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_DEFI),
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_ETUDIANT),
					cursor.getString(DefiBDD.NUM_COL_INTITULE),
					cursor.getString(DefiBDD.NUM_COL_DESCRIPTION), date,
					cursor.getInt(DefiBDD.NUM_COL_POINTS),
					cursor.getInt(DefiBDD.NUM_COL_ETAT_ACCEPTE),
					cursor.getString(DefiBDD.NUM_COL_PORTEE));
			
			String REQUETE_QUESTION = "SELECT * FROM "+TABLE_QUIZZ+ " WHERE "	+ COL_IDENTIFIANT + "=? ORDER BY "+COL_NUM_QUESTION;
			
			Cursor cursor1 = bdd.rawQuery(
					REQUETE_QUESTION,
					new String[] {String.valueOf(quizz.getId())});
			
			cursor1.moveToFirst();
			while (!cursor1.isAfterLast()) {
				quizz.addQuestion(new QuestionQuizz(cursor1.getString(NUM_COL_QUESTION), cursor1.getString(NUM_COL_BONNE_REPONSE_1), cursor1.getString(NUM_COL_MAUVAISE_REPONSE_2), cursor1.getString(NUM_COL_MAUVAISE_REPONSE_3), cursor1.getString(NUM_COL_MAUVAISE_REPONSE_4)));
				cursor1.moveToNext();
			}

			listeQrCodeARealiser.add(quizz);
			cursor.moveToNext();
		}
		return listeQrCodeARealiser;
	}

	public ArrayList<Defi> getAllDefiAAccepter() {
		ArrayList<Defi> listeQrCodeAAccepter= new ArrayList<Defi>();

		String MY_QUERY = "SELECT * FROM " + DefiBDD.TABLE_DEFI + " defis INNER JOIN "
				+ TABLE_QUIZZ + " quizz ON quizz." + COL_IDENTIFIANT + "=defis."
				+ DefiBDD.COL_IDENTIFIANT_DEFI + " WHERE defis."
				+ DefiBDD.COL_ETAT_ACCEPTE + "=? GROUP BY "+COL_IDENTIFIANT;
		
		Cursor cursor = bdd.rawQuery(
				MY_QUERY,
				new String[] {String.valueOf(Defi.ETAT_EN_COURS_ACCEPTATION)});
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Date date = null;
			try {
				if(!cursor.isNull(DefiBDD.NUM_COL_DATE_FIN)) {
					date = Util.dateFormat.parse(cursor.getString(DefiBDD.NUM_COL_DATE_FIN));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Quizz quizz = new Quizz(
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_DEFI),
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_ETUDIANT),
					cursor.getString(DefiBDD.NUM_COL_INTITULE),
					cursor.getString(DefiBDD.NUM_COL_DESCRIPTION), date,
					cursor.getInt(DefiBDD.NUM_COL_POINTS),
					cursor.getInt(DefiBDD.NUM_COL_ETAT_ACCEPTE),
					cursor.getString(DefiBDD.NUM_COL_PORTEE));
			
			String REQUETE_QUESTION = "SELECT * FROM "+TABLE_QUIZZ+ " WHERE " + COL_IDENTIFIANT + "=? ORDER BY "+ COL_NUM_QUESTION;
			
			Cursor cursor1 = bdd.rawQuery(
					REQUETE_QUESTION,
					new String[] {String.valueOf(quizz.getId())});
			
			cursor1.moveToFirst();
			while (!cursor1.isAfterLast()) {
				quizz.addQuestion(new QuestionQuizz(cursor1.getString(NUM_COL_QUESTION), cursor1.getString(NUM_COL_BONNE_REPONSE_1), cursor1.getString(NUM_COL_MAUVAISE_REPONSE_2), cursor1.getString(NUM_COL_MAUVAISE_REPONSE_3), cursor1.getString(NUM_COL_MAUVAISE_REPONSE_4)));
				cursor1.moveToNext();
			}
			
			listeQrCodeAAccepter.add(quizz);
			cursor.moveToNext();
		}
		return listeQrCodeAAccepter;
	}
}
