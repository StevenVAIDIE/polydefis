package com.polydefisv4.bdd;

import java.text.ParseException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.polydefisv4.bean.defis.Geolocalisation;
import com.polydefisv4.sql.SQL_DefiGeolocalisation;
import com.polydefisv4.testActivityMartin.Debug;
import com.polydefisv4.testActivityMartin.Util;

public class DefiGeolocalisationBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
 
	private static final String TABLE_GEOLOCALISATION = "D_GEOLOCALISATION";
	private static final String COL_IDENTIFIANT = "Identifiant";
	private static final String COL_LATITUDE = "Latitude";
	private static final String COL_LONGITUDE = "Longitude";
	
	//private static final int NUM_COL_IDENTIFIANT = 0;
	private static final int NUM_COL_LATITUDE = 1;
	private static final int NUM_COL_LONGITUDE = 2;
	
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
 
	
	public int updateGeolocalisation(int identifiant, Geolocalisation geoloc){
		ContentValues values = new ContentValues();
		values.put(COL_IDENTIFIANT, geoloc.getId());
		values.put(COL_LATITUDE, geoloc.getLatitude());
		values.put(COL_LONGITUDE, geoloc.getLongitude());
		return bdd.update(TABLE_GEOLOCALISATION, values, COL_IDENTIFIANT + " = " + identifiant, null);
	}
 
	public int removeGeolocalisation(int id){
		return bdd.delete(TABLE_GEOLOCALISATION, COL_IDENTIFIANT + " = " +id, null);
	}
	
	
	public Geolocalisation getGeolocalisation(int idDefi) throws ParseException{
		
		String MY_QUERY = "SELECT * FROM " + DefiBDD.TABLE_DEFI + " defis INNER JOIN  " +  TABLE_GEOLOCALISATION + " geoloc ON geoloc.Identifiant=defis.IdentifiantDefi WHERE defis.IdentifiantDefi=?";

	   Cursor cursor = bdd.rawQuery(MY_QUERY, new String[]{String.valueOf(idDefi)});

	    cursor.moveToFirst();

	    // Si aucun enregistrement n'est retourné
	    if(cursor.getCount() < 1)
	    	return null;
	    
	    Debug.Log(cursor.getString(0));
	    Debug.Log(cursor.getString(1));
	    Debug.Log(cursor.getString(2));
	    Debug.Log(cursor.getString(3));
	    Debug.Log(cursor.getString(4));
	    Debug.Log(cursor.getString(5));
	    Debug.Log(cursor.getString(6));
	    Debug.Log(cursor.getString(7));
	    Debug.Log(cursor.getString(8));
	    Debug.Log(cursor.getString(9));
	    Debug.Log(cursor.getString(10));
	    Debug.Log(cursor.getString(11));
	    
	   Geolocalisation geolocalisation = new Geolocalisation(cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_DEFI), 
			   cursor.getString(DefiBDD.NUM_COL_IDENTIFIANT_DEFI),
			   cursor.getString(DefiBDD.NUM_COL_INTITULE),
			   cursor.getString(DefiBDD.NUM_COL_DESCRIPTION),
			   Util.dateFormat.parse(cursor.getString(DefiBDD.NUM_COL_DATE_FIN)),
			   cursor.getInt(DefiBDD.NUM_COL_POINTS),
			   cursor.getInt(DefiBDD.NUM_COL_ETAT_ACCEPTE),
			   cursor.getString(DefiBDD.NUM_COL_PORTEE),
			   cursor.getDouble(NUM_COL_LATITUDE + DefiBDD.NUMBER_OF_COLUMS),
			   cursor.getDouble(NUM_COL_LONGITUDE + DefiBDD.NUMBER_OF_COLUMS));
	    return geolocalisation;
	}
	
}
