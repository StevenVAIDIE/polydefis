package com.polydefisv4.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.polydefisv4.sql.SQL_DefiQuizz;

public class DefiQuizzBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
	private static final String TABLE_QUIZZ = "D_GEOLOCALISATION";
	private static final String COL_IDENTIFIANT = "IdDefi";
	private static final String COL_NUMERO_QUESTION = "NumeroQuestion";
	private static final String COL_BONNE_REPONSE_1 = "BonneReponse1";
	private static final String COL_MAUVAISE_REPONSE_2 = "BonneReponse2";
	private static final String COL_MAUVAISE_REPONSE_3 = "BonneReponse3";
	private static final String COL_MAUVAISE_REPONSE_4 = "BonneReponse3";
	
	private static final int NUM_COL_IDENTIFIANT = 0;
	private static final int NUM_COL_NUMERO_QUESTION = 1;
	private static final int NUM_COL_BONNE_REPONSE_1 = 2;
	private static final int NUM_COL_MAUVAISE_REPONSE_2 = 3;
	private static final int NUM_COL_MAUVAISE_REPONSE_3 = 4;	
	private static final int NUM_COL_MAUVAISE_REPONSE_4 = 5;
	
	private SQLiteDatabase bdd;
 
	private SQL_DefiQuizz SQLDefiQuizz;
 
	public DefiQuizzBDD(Context context)
	{
		//On crée la BDD et sa table
		SQLDefiQuizz = new SQL_DefiQuizz(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en écriture
		bdd = SQLDefiQuizz.getWritableDatabase();
	}
	
	public void openReadable(){
		//on ouvre la BDD en lecture
		bdd = SQLDefiQuizz.getReadableDatabase();
	}
 
	public void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	
	public SQL_DefiQuizz getSQL(){
		return SQLDefiQuizz;
	}
	/*
	public long insertQuizz(Geolocalisation geoloc){
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
	*/

}
