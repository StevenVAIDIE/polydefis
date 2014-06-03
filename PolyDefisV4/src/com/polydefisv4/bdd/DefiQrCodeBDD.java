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
import com.polydefisv4.bean.defis.QrCode;
import com.polydefisv4.connexion.Util;
import com.polydefisv4.sql.SQL_DefiQrCode;

public class DefiQrCodeBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
 
	public static final String TABLE_QRCODE = "D_QRCODE";
	public static final String COL_ID_DEFI = "IdDefi";
	public static final String COL_QRCODE = "QrCode";
	
	public static final int NUM_COL_IDENTIFIANT = 0;
	public static final int NUM_COL_QRCODE = 1;
	
	private SQLiteDatabase bdd;
	private SQL_DefiQrCode SQLDefiQrCode;
 
	public DefiQrCodeBDD(Context context) {
		SQLDefiQrCode = new SQL_DefiQrCode(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		bdd = SQLDefiQrCode.getWritableDatabase();
	}
	
	public void openReadable(){
		bdd = SQLDefiQrCode.getReadableDatabase();
	}
 
	public void close(){
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	
	public SQL_DefiQrCode getSQL(){
		return SQLDefiQrCode;
	}
 
	public long insertQrCode(QrCode qrcode){
		ContentValues values = new ContentValues();
		values.put(COL_ID_DEFI, qrcode.getId());
		values.put(COL_QRCODE, qrcode.getQrCode());
		return bdd.insert(TABLE_QRCODE, null, values);
	}
 
	
	public int updateQrCode(int identifiant, QrCode qrcode){
		ContentValues values = new ContentValues();
		values.put(COL_ID_DEFI, qrcode.getId());
		values.put(COL_QRCODE, qrcode.getQrCode());
		return bdd.update(TABLE_QRCODE, values, COL_ID_DEFI + " = " + identifiant, null);
	}
 
	public int removeQrCode(QrCode qrCode){
		return bdd.delete(TABLE_QRCODE, COL_ID_DEFI + " = " +qrCode.getId(), null);
	}
	
	public int getNbPointsQrCode4A(Etudiant etudiant) {
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
				new String[] {String.valueOf(etudiant.getIdEtudiant()),String.valueOf(Defi.TYPE_QRCODE),DefiRealise.ETAT_REUSSI});
		
		cursor.moveToFirst();

		// Si aucun enregistrement n'est retourné
		if (cursor.getCount() < 1)
			return 0;
		
		return cursor.getInt(0);
	}
	
	public int getNbPointsQrCode3A(Etudiant etudiant) {
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
				new String[] {String.valueOf(etudiant.getIdEtudiant()),String.valueOf(Defi.TYPE_QRCODE),DefiRealise.ETAT_REUSSI});
		
		cursor.moveToFirst();

		// Si aucun enregistrement n'est retourné
		if (cursor.getCount() < 1)
			return 0;
		
		return cursor.getInt(0);
	}
	
	public ArrayList<Defi> getAllQrCodesARealiser(Etudiant etudiant,
			int idParrain) {
		ArrayList<Defi> listeQrCodeARealiser = new ArrayList<Defi>();

		String MY_QUERY = "SELECT * FROM " + DefiBDD.TABLE_DEFI + " defis INNER JOIN "
				+ TABLE_QRCODE + " qrCode ON qrCode." + COL_ID_DEFI + "=defis."
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
										+ "AND realise."+DefiRealiseBDD.COL_ID_DEFI+"=defis."+DefiBDD.COL_IDENTIFIANT_DEFI+")";

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
				if(!cursor.isNull(DefiBDD.NUM_COL_DATE_FIN)) {
					date = Util.dateFormat.parse(cursor
							.getString(DefiBDD.NUM_COL_DATE_FIN));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			QrCode geolocalisation = new QrCode(
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_DEFI),
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_ETUDIANT),
					cursor.getString(DefiBDD.NUM_COL_INTITULE),
					cursor.getString(DefiBDD.NUM_COL_DESCRIPTION), date,
					cursor.getInt(DefiBDD.NUM_COL_POINTS),
					cursor.getInt(DefiBDD.NUM_COL_ETAT_ACCEPTE),
					cursor.getString(DefiBDD.NUM_COL_PORTEE),
					cursor.getString(NUM_COL_QRCODE + DefiBDD.NUMBER_OF_COLUMS - 1));
			listeQrCodeARealiser.add(geolocalisation);
			cursor.moveToNext();
		}
		return listeQrCodeARealiser;
	}
	
	public ArrayList<Defi> getAllDefiAAccepter() {
		ArrayList<Defi> listeQrCodeAAccepter= new ArrayList<Defi>();

		String MY_QUERY = "SELECT * FROM " + DefiBDD.TABLE_DEFI + " defis INNER JOIN "
				+ TABLE_QRCODE + " qrCode ON qrCode." + COL_ID_DEFI + "=defis."
				+ DefiBDD.COL_IDENTIFIANT_DEFI + " WHERE defis."
				+ DefiBDD.COL_ETAT_ACCEPTE + "=?";
		
		Cursor cursor = bdd.rawQuery(
				MY_QUERY,
				new String[] { String.valueOf(Defi.ETAT_EN_COURS_ACCEPTATION)});

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
			
			QrCode photo = new QrCode(
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_DEFI),
					cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_ETUDIANT),
					cursor.getString(DefiBDD.NUM_COL_INTITULE),
					cursor.getString(DefiBDD.NUM_COL_DESCRIPTION), date,
					cursor.getInt(DefiBDD.NUM_COL_POINTS),
					cursor.getInt(DefiBDD.NUM_COL_ETAT_ACCEPTE),
					cursor.getString(DefiBDD.NUM_COL_PORTEE),
					cursor.getString(NUM_COL_QRCODE
							+ DefiBDD.NUMBER_OF_COLUMS - 1));
			listeQrCodeAAccepter.add(photo);
			cursor.moveToNext();
		}
		return listeQrCodeAAccepter;
	}	
}
