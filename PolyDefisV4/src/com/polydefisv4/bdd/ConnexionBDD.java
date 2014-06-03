package com.polydefisv4.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.polydefisv4.bean.Connexion;
import com.polydefisv4.sql.SQL_Connexion;

public class ConnexionBDD {
	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
 
	private static final String TABLE_CONNEXION = "U_CONNEXION";
	private static final String COL_ID_ETUDIANT = "ID_Etudiant";
	private static final String COL_IDENTIFIANT = "Identifiant";
	private static final String COL_MOTDEPASSE = "Motdepasse";
	
	private static final int NUM_COL_ID_ETUDIANT = 0;
	private static final int NUM_COL_IDENTIFIANT = 1;
	private static final int NUM_COL_MOTDEPASSE = 2;
 
	private SQLiteDatabase bdd;
	private SQL_Connexion SQLConnexion;
 
	public ConnexionBDD(Context context) {
		SQLConnexion = new SQL_Connexion(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		bdd = SQLConnexion.getWritableDatabase();
	}
	
	public void openReadable(){
		bdd = SQLConnexion.getReadableDatabase();
	}
 
	public void close(){
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	
	public SQL_Connexion getSQL(){
		return SQLConnexion;
	}
 
	public long insertConnexion(Connexion connexion){
		ContentValues values = new ContentValues();
		values.put(COL_IDENTIFIANT, connexion.getIdentifiant());
		values.put(COL_MOTDEPASSE, connexion.getMotDePasse());
		return bdd.insert(TABLE_CONNEXION, null, values);
	}
 
	public int updateConnexion(String identifiant, Connexion connexion){
		ContentValues values = new ContentValues();
		values.put(COL_MOTDEPASSE, connexion.getMotDePasse());
		return bdd.update(TABLE_CONNEXION, values, COL_IDENTIFIANT + " = " + identifiant, null);
	}
 
	public int removeConnexion(String identifiant){
		return bdd.delete(TABLE_CONNEXION, COL_IDENTIFIANT + " = " +identifiant, null);
	}
	
	public Connexion getConnexion(String login){
	   Cursor cursor =
	    		bdd.query(TABLE_CONNEXION, // table
	    		new String[] {COL_ID_ETUDIANT, COL_IDENTIFIANT, COL_MOTDEPASSE}, // column names
	    		COL_IDENTIFIANT + " = ?", // selections
	            new String[] {login}, // args
	            null, // group by
	            null, // having
	            null, // order by
	            null); // limit
	 

	    cursor.moveToFirst();

	    // Si aucun enregistrement n'est retourné
	    if(cursor.getCount() < 1)
	    	return null;
	    
	    Connexion connexion = new Connexion(cursor.getInt(NUM_COL_ID_ETUDIANT),cursor.getString(NUM_COL_IDENTIFIANT), cursor.getString(NUM_COL_MOTDEPASSE));
	    return connexion;
	}
}
