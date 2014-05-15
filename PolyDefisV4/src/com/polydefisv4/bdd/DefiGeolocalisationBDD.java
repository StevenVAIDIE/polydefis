package com.polydefisv4.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.polydefisv4.bean.defis.Geolocalisation;
import com.polydefisv4.sql.SQL_DefiGeolocalisation;

public class DefiGeolocalisationBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
 
	private static final String TABLE_GEOLOCALISATION = "D_GEOLOCALISATION";
	private static final String COL_IDENTIFIANT = "Identifiant";
	private static final String COL_LATITUDE = "Latitude";
	private static final String COL_LONGITUDE = "Longitude";
	
	private static final int NUM_COL_IDENTIFIANT = 1;
	private static final int NUM_COL_LATITUDE = 2;
	private static final int NUM_COL_LONGITUDE = 3;
 
	private SQLiteDatabase bdd;
 
	private SQL_DefiGeolocalisation SQLDefiGeolocalisation;
 
	public DefiGeolocalisationBDD(Context context)
	{
		//On crée la BDD et sa table
		SQLDefiGeolocalisation = new SQL_DefiGeolocalisation(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en écriture
		bdd = SQLDefiGeolocalisation.getWritableDatabase();
	}
	
	public void openReadable(){
		//on ouvre la BDD en lecture
		bdd = SQLDefiGeolocalisation.getReadableDatabase();
	}
 
	public void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	
	public SQL_DefiGeolocalisation getSQL(){
		return SQLDefiGeolocalisation;
	}
 
	public long insertGeolocalisation(Geolocalisation geoloc){
		ContentValues values = new ContentValues();
		values.put(COL_IDENTIFIANT, geoloc.getId());
		values.put(COL_LATITUDE, geoloc.getLatitude());
		values.put(COL_LONGITUDE, geoloc.getLongitude());
		return bdd.insert(TABLE_GEOLOCALISATION, null, values);
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
