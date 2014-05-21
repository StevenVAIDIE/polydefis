package com.polydefisv4.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL_DefiGeolocalisation extends SQLiteOpenHelper {

	private static final String TABLE_GEOLOCALISATION = "D_GEOLOCALISATION";
	private static final String COL_IDENTIFIANT = "Identifiant";
	private static final String COL_LATITUDE = "Latitude";
	private static final String COL_LONGITUDE = "Longitude";
	
	public static final String CREATE_BDD = 
			"CREATE TABLE " + TABLE_GEOLOCALISATION + " (" + 
					COL_IDENTIFIANT + " TEXT PRIMARY KEY, " + 
					COL_LATITUDE + " REAL NOT NULL," + 
					COL_LONGITUDE + " REAL NOT NULL);";
	
	
	public SQL_DefiGeolocalisation(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
		//on crée la table à partir de la requête écrite dans la variable CREATE_BDD
		db.execSQL(CREATE_BDD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//On peut faire ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
		//comme ça lorsque je change la version les id repartent de 0
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GEOLOCALISATION + ";");
		onCreate(db);
	}

}
