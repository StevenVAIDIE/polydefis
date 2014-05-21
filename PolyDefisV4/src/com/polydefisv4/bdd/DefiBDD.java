package com.polydefisv4.bdd;

import java.text.ParseException;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.polydefisv4.bean.Defi;
import com.polydefisv4.sql.SQL_Defi;
import com.polydefisv4.testActivityMartin.Debug;
import com.polydefisv4.testActivityMartin.Util;



public class DefiBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
 
	public static final String TABLE_DEFI = "D_DEFI";
	public static final String COL_IDENTIFIANT_DEFI = "IdentifiantDefi";
	public static final String COL_IDENTIFIANT_ETUDIANT = "IdentifiantEtudiant";
	public static final String COL_INTITULE = "Intitule";
	public static final String COL_DESCRIPTION = "Description";
	public static final String COL_TYPE_DEFI = "TypeDefi";
	public static final String COL_DATE_FIN = "DateFin";
	public static final String COL_POINTS = "Points";
	public static final String COL_PORTEE = "Portee";
	public static final String COL_ETAT_ACCEPTE = "EtatAccepte";
	
	public static final int NUM_COL_IDENTIFIANT_DEFI = 0;
	public static final int NUM_COL_IDENTIFIANT_ETUDIANT = 1;
	public static final int NUM_COL_INTITULE = 2;
	public static final int NUM_COL_DESCRIPTION = 3;
	public static final int NUM_COL_TYPE_DEFI = 4;
	public static final int NUM_COL_DATE_FIN = 5;
	public static final int NUM_COL_POINTS = 6;
	public static final int NUM_COL_PORTEE = 7;
	public static final int NUM_COL_ETAT_ACCEPTE = 8;
 
	public static final int NUMBER_OF_COLUMS = 9;
	
	private SQLiteDatabase bdd;
 
	private SQL_Defi SQLDefi;
 
	public DefiBDD(Context context)
	{
		//On crée la BDD et sa table
		SQLDefi = new SQL_Defi(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en écriture
		bdd = SQLDefi.getWritableDatabase();
	}
	
	public void openReadable(){
		//on ouvre la BDD en lecture
		bdd = SQLDefi.getReadableDatabase();
	}
 
	public void close(){
		//on ferme l'accès à la BDD
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
		values.put(COL_INTITULE, defi.getIntitule());
		values.put(COL_DESCRIPTION, defi.getDescription());
		values.put(COL_TYPE_DEFI, type);
		Debug.Log(defi.getDateFin().toString());
		values.put(COL_DATE_FIN, Util.dateFormat.format(defi.getDateFin()));
		values.put(COL_POINTS, defi.getNombrePoint());
		values.put(COL_PORTEE, String.valueOf(defi.getPortee()));
		values.put(COL_ETAT_ACCEPTE, defi.getEtatAcceptation());
		return bdd.insert(TABLE_DEFI, null, values);
	}
 
	
	public int updateDefi(Defi defi, int identifiant, int type){
		ContentValues values = new ContentValues();
		values.put(COL_IDENTIFIANT_ETUDIANT, defi.getIdEtudiant());
		values.put(COL_INTITULE, defi.getIntitule());
		values.put(COL_DESCRIPTION, defi.getDescription());
		values.put(COL_TYPE_DEFI, type);
		values.put(COL_DATE_FIN, Util.dateFormat.format(defi.getDateFin()));
		values.put(COL_POINTS, defi.getNombrePoint());
		values.put(COL_PORTEE, String.valueOf(defi.getPortee()));
		values.put(COL_ETAT_ACCEPTE, defi.getEtatAcceptation());
		return bdd.update(TABLE_DEFI, values, COL_IDENTIFIANT_DEFI + " = " + identifiant, null);
	}
	
	public int accepterDefi(int id)
	{
			ContentValues values = new ContentValues();
			values.put(COL_ETAT_ACCEPTE, Defi.ETAT_ACCEPTE);
			return bdd.update(TABLE_DEFI, values, COL_IDENTIFIANT_DEFI + " = " + id, null);
	}
	
	public int nbPointsDefis(int id)
	{
		   Cursor cursor =
		    		bdd.query(TABLE_DEFI, // table
		    		new String[] {COL_POINTS}, // column names
		    		COL_IDENTIFIANT_DEFI + " = ?", // selections
		            new String[] { String.valueOf(id) }, // args
		            null, // group by
		            null, // having
		            null, // order by
		            null); // limit
		 

		    cursor.moveToFirst();

		    // Si aucun enregistrement n'est retourné
		    if(cursor.getCount() < 1)
		    	return 0;
		    
		    return cursor.getInt(0);
	}
 
	public int removeDefi(int identifiant){
		return bdd.delete(TABLE_DEFI, COL_IDENTIFIANT_DEFI + " = " +identifiant, null);
	}
	
	public void updateEtatDefiDate()
	{
		String MY_QUERY = "SELECT * FROM " + DefiBDD.TABLE_DEFI + " defis WHERE defis." + COL_ETAT_ACCEPTE + "=" + Defi.ETAT_ACCEPTE;

		Cursor cursor = bdd.rawQuery(MY_QUERY, null);

		
	    cursor.moveToFirst();
	    while(!cursor.isAfterLast())
	    {
	    	Date dateDefi = null;
	    	
	    	try {
				dateDefi = Util.dateFormat.parse(cursor.getString(NUM_COL_DATE_FIN));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	Date aujourdhui = new Date();
	    	

	    	if(aujourdhui.after(dateDefi))
	    	{
				ContentValues values = new ContentValues();
				values.put(COL_ETAT_ACCEPTE, Defi.ETAT_TERMINE);
	    		bdd.update(TABLE_DEFI, values, COL_IDENTIFIANT_DEFI + " = " + cursor.getString(NUM_COL_IDENTIFIANT_DEFI), null);
	    	}
	    	
			cursor.moveToNext();
	    }
		
	}

	public int getLastId() {
		String MY_QUERY = "SELECT last_insert_rowid()";
		Cursor cursor = bdd.rawQuery(MY_QUERY, null);
		cursor.moveToFirst();
		Debug.Log(String.valueOf(cursor.getInt(0)));
		return cursor.getInt(0);
	}
	
}
