package com.polydefisv4.bdd;

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
		   Cursor cursor =
		    		bdd.query(TABLE_DEFI, // table
		    		new String[] {COL_IDENTIFIANT_DEFI, COL_IDENTIFIANT_ETUDIANT,COL_INTITULE,COL_DESCRIPTION,COL_TYPE_DEFI,COL_DATE_FIN,COL_POINTS,COL_PORTEE,COL_ETAT_ACCEPTE}, // column names
		    		COL_IDENTIFIANT_DEFI + " = ?", // selections
		            new String[] { String.valueOf(id) }, // args
		            null, // group by
		            null, // having
		            COL_DATE_FIN, // order by
		            null); // limit

		    cursor.moveToFirst();

		    // Si aucun enregistrement n'est retourné
		    if(cursor.getCount() < 1)
		    	return 0;
			  
			ContentValues values = new ContentValues();
			
			switch(cursor.getInt(NUM_COL_TYPE_DEFI))
			{
			case Defi.TYPE_QUIZZ:
				values.put(COL_TYPE_DEFI, Defi.TYPE_QUIZZ);
				break;
			case Defi.TYPE_PHOTO:
				values.put(COL_TYPE_DEFI, Defi.TYPE_PHOTO);				
				break;
			case Defi.TYPE_QRCODE:
				values.put(COL_TYPE_DEFI, Defi.TYPE_QRCODE);	
				break;
			case Defi.TYPE_GEOLOCALIATION:
				values.put(COL_TYPE_DEFI, Defi.TYPE_GEOLOCALIATION);	
				break;
			default:
					
			}
			
			values.put(COL_IDENTIFIANT_ETUDIANT, cursor.getString(NUM_COL_IDENTIFIANT_ETUDIANT));
			values.put(COL_INTITULE, cursor.getString(NUM_COL_INTITULE));
			values.put(COL_DESCRIPTION, cursor.getString(NUM_COL_DESCRIPTION));
			values.put(COL_DATE_FIN, cursor.getString(NUM_COL_DATE_FIN));
			values.put(COL_POINTS, cursor.getString(NUM_COL_POINTS));
			values.put(COL_PORTEE, cursor.getString(NUM_COL_PORTEE));
			values.put(COL_ETAT_ACCEPTE, Defi.ETAT_ACCEPTE);
			return bdd.update(TABLE_DEFI, values, COL_IDENTIFIANT_DEFI + " = " + id, null);
	}
 
	public int removeDefi(int identifiant){
		return bdd.delete(TABLE_DEFI, COL_IDENTIFIANT_DEFI + " = " +identifiant, null);
	}
	
}
