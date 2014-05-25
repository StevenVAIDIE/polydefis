package com.polydefisv4.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.polydefisv4.bean.DefiRealise;
import com.polydefisv4.sql.SQL_DefiRealise;
import com.polydefisv4.testActivityMartin.Util;

public class DefiRealiseBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
 
	private static final String TABLE_DEFIREALISE = "U_PARRAINAGE";
	private static final String COL_ID_ETUDIANT = "IdEtudiant";
	private static final String COL_ID_DEFI = "IdDefi";
	private static final String COL_ETAT = "Etat";
	private static final String COL_DATE_REALISE = "Date";
	private static final int NUM_COL_ID_ETUDIANT = 1;
	private static final int NUM_COL_ID_DEFI = 2;
	private static final int NUM_COL_ETAT = 3;
	private static final int NUM_COL_DATE_REALISE = 4;
 
	private SQLiteDatabase bdd;
 
	private SQL_DefiRealise SQLDefiRealise;
 
	public DefiRealiseBDD(Context context)
	{
		//On crée la BDD et sa table
		SQLDefiRealise = new SQL_DefiRealise(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en écriture
		bdd = SQLDefiRealise.getWritableDatabase();
	}
	
	public void openReadable(){
		//on ouvre la BDD en lecture
		bdd = SQLDefiRealise.getReadableDatabase();
	}
 
	public void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	
	public SQL_DefiRealise getSQL(){
		return SQLDefiRealise;
	}
	
	public long insertDefiRealise(DefiRealise defiRealise){
		ContentValues values = new ContentValues();
		values.put(COL_ID_ETUDIANT, defiRealise.getIdEtudiant());
		values.put(COL_ID_DEFI, defiRealise.getIdDefi());
		values.put(COL_DATE_REALISE, Util.dateFormat.format(defiRealise.getDateRealise()));
		values.put(COL_ETAT, defiRealise.getEtat());
		return bdd.insert(TABLE_DEFIREALISE, null, values);
	}
	

	public int updateDefiRealise(DefiRealise defiRealise, int idDefi, String idEtudiant){
		ContentValues values = new ContentValues();
		values.put(COL_ID_ETUDIANT, defiRealise.getIdEtudiant());
		values.put(COL_ID_DEFI, defiRealise.getIdDefi());
		values.put(COL_ETAT, defiRealise.getEtat());
		values.put(COL_DATE_REALISE, Util.dateFormat.format(defiRealise.getDateRealise()));
		return bdd.update(TABLE_DEFIREALISE, values, COL_ID_ETUDIANT + " = " + idEtudiant + " AND " + COL_ID_DEFI + " = " + idDefi, null);
	}
 
	public int removeDefiRealise(int idDefi, String idEtudiant){
		return bdd.delete(TABLE_DEFIREALISE, COL_ID_ETUDIANT + " = " + idEtudiant + " AND " + COL_ID_DEFI + " = " + idDefi, null);
	}	
}
