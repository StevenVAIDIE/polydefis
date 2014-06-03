package com.polydefisv4.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL_DefiRealise extends SQLiteOpenHelper {

	private static final String TABLE_DEFIREALISE = "L_DEFIREALISE";
	private static final String COL_ID_ETUDIANT = "IdEtudiant";
	private static final String COL_ID_DEFI = "IdDefi";
	private static final String COL_DATE_REALISE = "Date";
	private static final String COL_ETAT = "Etat";

	private static final String CREATE_BDD = 
			"CREATE TABLE " + TABLE_DEFIREALISE + " (" + 
					COL_ID_ETUDIANT + " TEXT NOT NULL, " + 
					COL_ID_DEFI + " INT NOT NULL, " +
					COL_DATE_REALISE + " TEXT, " +
					COL_ETAT + " TEXT NOT NULL, " +
					" PRIMARY KEY (" + COL_ID_ETUDIANT + "," + COL_ID_DEFI + "));";
 
	public SQL_DefiRealise(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BDD);
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEFIREALISE + ";");
		onCreate(db);
	}	
}
