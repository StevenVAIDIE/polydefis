package com.polydefisv4.bdd;

import java.util.ArrayList;

import com.polydefisv4.bean.Connexion;
import com.polydefisv4.sql.SQL_Connexion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ConnexionBDD {


	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
 
	private static final String TABLE_CONNEXION = "U_CONNEXION";
	private static final String COL_IDENTIFIANT = "Identifiant";
	private static final String COL_MOTDEPASSE = "Motdepasse";
	private static final int NUM_COL_IDENTIFIANT = 0;
	private static final int NUM_COL_MOTDEPASSE = 1;
 
	private SQLiteDatabase bdd;
 
	private SQL_Connexion SQLConnexion;
 
	public ConnexionBDD(Context context)
	{
		//On crée la BDD et sa table
		SQLConnexion = new SQL_Connexion(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en écriture
		bdd = SQLConnexion.getWritableDatabase();
	}
	
	public void openReadable(){
		//on ouvre la BDD en lecture
		bdd = SQLConnexion.getReadableDatabase();
	}
 
	public void close(){
		//on ferme l'accès à la BDD
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
	
	
	public Connexion getConnexion(String id){
		
	   Cursor cursor =
	    		bdd.query(TABLE_CONNEXION, // table
	    		new String[] {COL_IDENTIFIANT, COL_MOTDEPASSE}, // column names
	    		COL_IDENTIFIANT + " = ?", // selections
	            new String[] { String.valueOf(id) }, // args
	            null, // group by
	            null, // having
	            null, // order by
	            null); // limit
	 

	    cursor.moveToFirst();

	    // Si aucun enregistrement n'est retourné
	    if(cursor.getCount() < 1)
	    	return null;
	    
	    Connexion connexion = new Connexion(cursor.getString(NUM_COL_IDENTIFIANT), cursor.getString(NUM_COL_MOTDEPASSE));
	    return connexion;
	}
 
	public ArrayList<Connexion> getAllConnexion(String id)
	{
		// A FAIRE
		return null;
	}
	
}
