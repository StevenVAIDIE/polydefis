package com.polydefisv4.bdd;


import java.text.ParseException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.polydefisv4.bean.defis.Photo;
import com.polydefisv4.sql.SQL_DefiPhoto;
import com.polydefisv4.testActivityMartin.Util;

public class DefiPhotoBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
 
	private static final String TABLE_PHOTO = "D_PHOTO";
	private static final String COL_ID_DEFI = "IdDefi";
	private static final String COL_URLPHOTO = "urlPhoto";
	
	//private static final int NUM_COL_IDENTIFIANT = 0;
	private static final int NUM_COL_URLPHOTO = 1;
	
	private SQLiteDatabase bdd;
 
	private SQL_DefiPhoto SQLDefiPhoto;
 
	public DefiPhotoBDD(Context context)
	{
		//On crée la BDD et sa table
		SQLDefiPhoto = new SQL_DefiPhoto(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en écriture
		bdd = SQLDefiPhoto.getWritableDatabase();
	}
	
	public void openReadable(){
		//on ouvre la BDD en lecture
		bdd = SQLDefiPhoto.getReadableDatabase();
	}
 
	public void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	
	public SQL_DefiPhoto getSQL(){
		return SQLDefiPhoto;
	}
 
	public long insertPhoto(Photo photo){
		ContentValues values = new ContentValues();
		values.put(COL_ID_DEFI, photo.getId());
		values.put(COL_URLPHOTO, photo.getUrlPhoto());
		return bdd.insert(TABLE_PHOTO, null, values);
	}
 
	
	public int updatePhoto(int identifiant, Photo photo){
		ContentValues values = new ContentValues();
		values.put(COL_ID_DEFI, photo.getId());
		values.put(COL_URLPHOTO, photo.getUrlPhoto());
		return bdd.update(TABLE_PHOTO, values, COL_ID_DEFI + " = " + identifiant, null);
	}
 
	public int removeGeolocalisation(int id){
		return bdd.delete(TABLE_PHOTO, COL_ID_DEFI + " = " +id, null);
	}
	
	
	public Photo getPhoto(int idDefi) throws ParseException{
		
		String MY_QUERY = "SELECT * FROM " + DefiBDD.TABLE_DEFI + " defis INNER JOIN  " +  TABLE_PHOTO + " photo ON photo.Identifiant=defis.IdentifiantDefi WHERE defis.IdentifiantDefi=?";

	   Cursor cursor = bdd.rawQuery(MY_QUERY, new String[]{String.valueOf(idDefi)});

	    cursor.moveToFirst();

	    // Si aucun enregistrement n'est retourné
	    if(cursor.getCount() < 1)
	    	return null;
	    
	    
	    Photo photo = new Photo(cursor.getInt(DefiBDD.NUM_COL_IDENTIFIANT_DEFI), 
			   cursor.getString(DefiBDD.NUM_COL_IDENTIFIANT_DEFI),
			   cursor.getString(DefiBDD.NUM_COL_INTITULE),
			   cursor.getString(DefiBDD.NUM_COL_DESCRIPTION),
			   Util.dateFormat.parse(cursor.getString(DefiBDD.NUM_COL_DATE_FIN)),
			   cursor.getInt(DefiBDD.NUM_COL_POINTS),
			   cursor.getInt(DefiBDD.NUM_COL_ETAT_ACCEPTE),
			   cursor.getString(DefiBDD.NUM_COL_PORTEE),
			   cursor.getString(NUM_COL_URLPHOTO+DefiBDD.NUMBER_OF_COLUMS));
	    return photo;
	}
	
}
