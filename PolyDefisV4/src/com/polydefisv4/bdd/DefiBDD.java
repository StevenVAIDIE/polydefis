package com.polydefisv4.bdd;

import java.util.ArrayList;

import com.polydefisv4.bean.Defi;
import com.polydefisv4.sql.SQL_Defi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DefiBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
 
	private static final String TABLE_DEFI = "D_DEFI";
	private static final String COL_IDENTIFIANT_DEFI = "IdentifiantDefi";
	private static final String COL_IDENTIFIANT_ETUDIANT = "IdentifiantEtudiant";
	private static final String COL_INTITULE = "Intitule";
	private static final String COL_DESCRIPTION = "Description";
	private static final String COL_TYPE_DEFI = "TypeDefi";
	private static final String COL_DATE_FIN = "DateFin";
	private static final String COL_POINTS = "Points";
	private static final String COL_PORTEE = "Portee";
	private static final String COL_ETAT_ACCEPTE = "EtatAccepte";
	
	private static final String NUM_COL_IDENTIFIANT_DEFI = "0";
	private static final String NUM_COL_IDENTIFIANT_ETUDIANT = "1";
	private static final String NUM_COL_INTITULE = "2";
	private static final String NUM_COL_DESCRIPTION = "3";
	private static final String NUM_COL_TYPE_DEFI = "4";
	private static final String NUM_COL_DATE_FIN = "5";
	private static final String NUM_COL_POINTS = "6";
	private static final String NUM_COL_PORTEE = "7";
	private static final String NUM_COL_ETAT_ACCEPTE = "8";
 
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
 
	public long insertDefi(Defi defi, int type){
		ContentValues values = new ContentValues();
		values.put(COL_IDENTIFIANT_ETUDIANT, defi.getIdEtudiant());
		values.put(COL_INTITULE, defi.getIntitule());
		values.put(COL_DESCRIPTION, defi.getDescription());
		values.put(COL_TYPE_DEFI, type);
		values.put(COL_DATE_FIN, "");
		values.put(COL_POINTS, defi.getNombrePoint());
		values.put(COL_PORTEE, String.valueOf(defi.getPortee()));
		values.put(COL_ETAT_ACCEPTE, defi.getEtatAcceptation());
		return bdd.insert(TABLE_DEFI, null, values);
	}
 
	/*
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
	*/
}
