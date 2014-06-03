package com.polydefisv4.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL_Defi extends SQLiteOpenHelper {


	private static final String TABLE_DEFI = "D_DEFI";
	private static final String COL_IDENTIFIANT_DEFI = "IdentifiantDefi";
	private static final String COL_IDENTIFIANT_ETUDIANT = "IdentifiantEtudiant";
	private static final String COL_TYPE = "Type";
	private static final String COL_INTITULE = "Intitule";
	private static final String COL_DESCRIPTION = "Description";
	private static final String COL_DATE_FIN = "DateFin";
	private static final String COL_POINTS = "Points";
	private static final String COL_PORTEE = "Portee";
	private static final String COL_ETAT_ACCEPTE = "EtatAccepte";
 
	public static final String CREATE_BDD = 
			"CREATE TABLE " + TABLE_DEFI + " (" + 
					COL_IDENTIFIANT_DEFI + " INTEGER PRIMARY KEY AUTOINCREMENT, " + 
					COL_IDENTIFIANT_ETUDIANT + " TEXT NOT NULL, " +
					COL_TYPE + " INTEGER NOT NULL," +
					COL_INTITULE + " TEXT NOT NULL, " + 
					COL_DESCRIPTION + " TEXT NOT NULL, " + 
					COL_DATE_FIN + " TEXT, " + 
					COL_POINTS + " INTEGER NOT NULL, " + 
					COL_PORTEE + " TEXT NOT NULL, " + 
					COL_ETAT_ACCEPTE + " INTEGER NOT NULL);";
 
	public SQL_Defi(Context context, String name, CursorFactory factory, int version) {
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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEFI + ";");
		onCreate(db);
	}
}