package com.polydefisv4.bdd;


import java.text.ParseException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.polydefisv4.bean.defis.Photo;
import com.polydefisv4.bean.defis.QrCode;
import com.polydefisv4.sql.SQL_DefiQrCode;
import com.polydefisv4.testActivityMartin.Util;

public class DefiQrCodeBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
 
	private static final String TABLE_QRCODE = "D_QRCODE";
	private static final String COL_ID_DEFI = "IdDefi";
	private static final String COL_QRCODE = "QrCode";
	
	//private static final int NUM_COL_IDENTIFIANT = 0;
	private static final int NUM_COL_QRCODE = 1;
	
	private SQLiteDatabase bdd;
 
	private SQL_DefiQrCode SQLDefiQrCode;
 
	public DefiQrCodeBDD(Context context)
	{
		//On crée la BDD et sa table
		SQLDefiQrCode = new SQL_DefiQrCode(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en écriture
		bdd = SQLDefiQrCode.getWritableDatabase();
	}
	
	public void openReadable(){
		//on ouvre la BDD en lecture
		bdd = SQLDefiQrCode.getReadableDatabase();
	}
 
	public void close(){
		//on ferme l'accès à la BDD
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
 
	public int removeQrCode(int id){
		return bdd.delete(TABLE_QRCODE, COL_ID_DEFI + " = " +id, null);
	}
	
	
	public Photo getPhoto(int idDefi) throws ParseException{
		
		String MY_QUERY = "SELECT * FROM " + DefiBDD.TABLE_DEFI + " defis INNER JOIN  " +  TABLE_QRCODE + " qrcode ON qrcode.Identifiant=defis.IdentifiantDefi WHERE defis.IdentifiantDefi=?";

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
			   cursor.getString(NUM_COL_QRCODE+DefiBDD.NUMBER_OF_COLUMS));
	    return photo;
	}
	
}
