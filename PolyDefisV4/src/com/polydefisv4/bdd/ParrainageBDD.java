package com.polydefisv4.bdd;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.polydefisv4.bean.Parrainage;
import com.polydefisv4.sql.SQL_Parrainage;

public class ParrainageBDD {


	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
 
	private static final String TABLE_PARRAINAGE = "U_PARRAINAGE";
	private static final String COL_PARRAIN = "Parrain";
	private static final String COL_FILLEUL = "Filleul";
	private static final String COL_ETAT = "Etat";
	private static final int NUM_COL_PARRAIN = 0;
	private static final int NUM_COL_FILLEUL = 1;
	private static final int NUM_COL_ETAT = 2;
 
	private SQLiteDatabase bdd;
 
	private SQL_Parrainage SQLParrainage;
 
	public ParrainageBDD(Context context)
	{
		//On crée la BDD et sa table
		SQLParrainage = new SQL_Parrainage(context, NOM_BDD, null, VERSION_BDD);
	}
 
	public void open(){
		//on ouvre la BDD en écriture
		bdd = SQLParrainage.getWritableDatabase();
	}
	
	public void openReadable(){
		//on ouvre la BDD en lecture
		bdd = SQLParrainage.getReadableDatabase();
	}
 
	public void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
 
	public long insertParrainage(Parrainage parrainage){
		ContentValues values = new ContentValues();
		values.put(COL_PARRAIN, parrainage.getParrain());
		values.put(COL_FILLEUL, parrainage.getFilleul());
		values.put(COL_ETAT, parrainage.getEtat());
		return bdd.insert(TABLE_PARRAINAGE, null, values);
	}
 
	public int updateParrainage(String parrain, String filleul, int etat){
		ContentValues values = new ContentValues();
		values.put(COL_ETAT, etat);
		return bdd.update(TABLE_PARRAINAGE, values, COL_PARRAIN + " = " + parrain + " AND " + COL_FILLEUL + " = " + filleul, null);
	}
 
	public int removeParrainage(String parrain, String filleul){
		return bdd.delete(TABLE_PARRAINAGE, COL_PARRAIN + " = " + parrain + " AND " + COL_FILLEUL + " = " + filleul, null);
	}
	
	
	public Parrainage getParrainage(String parrain, String filleul){
		
	   Cursor cursor =
	    		bdd.query(TABLE_PARRAINAGE, // table
	    		new String[] {COL_PARRAIN, COL_FILLEUL, COL_ETAT}, // column names
	    		COL_PARRAIN + " = ? AND " + COL_FILLEUL + " = ?", // selections
	            new String[] { parrain, filleul }, // args
	            null, // group by
	            null, // having
	            null, // order by
	            null); // limit
	 

	    cursor.moveToFirst();

	    // Si aucun enregistrement n'est retourné
	    if(cursor.getCount() < 1)
	    	return null;
	    
	    Parrainage parrainage = new Parrainage(cursor.getString(NUM_COL_PARRAIN), cursor.getString(NUM_COL_FILLEUL), cursor.getInt(NUM_COL_ETAT));
	    return parrainage;
	}
 
	public ArrayList<Parrainage> getAllFilleul(String parrain)
	{
		// A FAIRE
		return null;
	}

	public SQL_Parrainage getSQL() {
		return SQLParrainage;
	}
	
	
}
