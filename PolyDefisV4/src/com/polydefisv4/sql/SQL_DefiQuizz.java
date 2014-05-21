package com.polydefisv4.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQL_DefiQuizz extends SQLiteOpenHelper {

	private static final String TABLE_QUIZZ = "D_QUIZZ";
	private static final String COL_IDENTIFIANT_DEFI = "IdDefi";
	private static final String COL_NUMERO_QUESTION = "NumeroQuestion";
	private static final String COL_BONNE_REPONSE_1 = "BonneReponse1";
	private static final String COL_MAUVAISE_REPONSE_2 = "BonneReponse2";
	private static final String COL_MAUVAISE_REPONSE_3 = "BonneReponse3";
	private static final String COL_MAUVAISE_REPONSE_4 = "BonneReponse4";
	
	public static final String CREATE_BDD = 
			"CREATE TABLE " + TABLE_QUIZZ + " (" + 
					COL_IDENTIFIANT_DEFI + " INT NOT NULL, " + 
					COL_NUMERO_QUESTION + " INT NOT NULL," + 
					COL_BONNE_REPONSE_1 + " TEXT NOT NULL," + 
					COL_MAUVAISE_REPONSE_2 + " TEXT NOT NULL," + 
					COL_MAUVAISE_REPONSE_3 + " TEXT NOT NULL," + 
					COL_MAUVAISE_REPONSE_4 + " TEXT NOT NULL," + 
					" PRIMARY KEY (" + COL_IDENTIFIANT_DEFI + "," + COL_NUMERO_QUESTION + "));";

	
	
	public SQL_DefiQuizz(Context context, String name, CursorFactory factory, int version) {
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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZ + ";");
		onCreate(db);
	}

}