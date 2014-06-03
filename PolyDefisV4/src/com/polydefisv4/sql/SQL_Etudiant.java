package com.polydefisv4.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL_Etudiant extends SQLiteOpenHelper {

	private static final String TABLE_CONNEXION = "U_ETUDIANT";
	private static final String COL_IDENTIFIANT = "Identifiant";
	private static final String COL_NOM = "Nom";
	private static final String COL_PRENOM = "Prenom";
	private static final String COL_DEPARTEMENT = "Departement";
	private static final String COL_ANNEE = "Annee";
	private static final String COL_RESPO = "Respo";
	private static final String COL_POINTS = "Points";	
	
	public static final String CREATE_BDD = 
			"CREATE TABLE " + TABLE_CONNEXION + " (" + 
					COL_IDENTIFIANT + " INT PRIMARY KEY, " + 
					COL_NOM + " TEXT NOT NULL," + 
					COL_PRENOM + " TEXT NOT NULL," + 
					COL_DEPARTEMENT + " TEXT NOT NULL," +
					COL_ANNEE + " INT NOT NULL," +
					COL_RESPO + " INT NOT NULL," +
					COL_POINTS + " INT NOT NULL);";
 
	public SQL_Etudiant(Context context, String name, CursorFactory factory, int version) {
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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONNEXION + ";");
		onCreate(db);
	}
	
}
