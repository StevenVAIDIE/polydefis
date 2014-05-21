package com.polydefisv4.bdd;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.sql.SQL_Etudiant;

public class EtudiantBDD {

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "polydefi.db";
 
	private static final String TABLE_ETUDIANT = "U_ETUDIANT";
	private static final String COL_IDENTIFIANT = "Identifiant";
	private static final String COL_NOM = "Nom";
	private static final String COL_PRENOM = "Prenom";
	private static final String COL_DEPARTEMENT = "Departement";
	private static final String COL_ANNEE = "Annee";
	private static final String COL_RESPO = "Respo";
	private static final String COL_POINTS = "Points";	
	private static final int NUM_COL_IDENTIFIANT = 0;
	private static final int NUM_COL_NOM = 1;
	private static final int NUM_COL_PRENOM = 2;
	private static final int NUM_COL_DEPARTEMENT = 3;
	private static final int NUM_COL_ANNEE = 4;
	private static final int NUM_COL_RESPO = 5;
	private static final int NUM_COL_POINTS = 6;
	
	private SQLiteDatabase bdd;
	 
	private SQL_Etudiant SQLEtudiant;
 
	public EtudiantBDD(Context context){
		//On crée la BDD et sa table
		SQLEtudiant = new SQL_Etudiant(context, NOM_BDD, null, VERSION_BDD);
	}
	

	public void open(){
		//on ouvre la BDD en écriture
		bdd = SQLEtudiant.getWritableDatabase();
	}
	
	public void openReadable(){
		//on ouvre la BDD en lecture
		bdd = SQLEtudiant.getReadableDatabase();
	}
 
	public void close(){
		//on ferme l'accès à la BDD
		bdd.close();
	}
 
	public SQLiteDatabase getBDD(){
		return bdd;
	}
 
	public SQL_Etudiant getSQL(){
		return SQLEtudiant;
	}
	
	public long insertEtudiant(Etudiant etudiant){
		//Création d'un ContentValues (fonctionne comme une HashMap)
		ContentValues values = new ContentValues();
		//on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
		values.put(COL_IDENTIFIANT, etudiant.getIdEtudiant());
		values.put(COL_NOM, etudiant.getNom());
		values.put(COL_PRENOM, etudiant.getPrenom());
		values.put(COL_DEPARTEMENT, etudiant.getDepartement());
		values.put(COL_ANNEE, etudiant.getAnneePromo());
		if(etudiant.isRespo()) 
			values.put(COL_RESPO, 1);
		else
			values.put(COL_RESPO, 0);
		values.put(COL_POINTS, etudiant.getPoints());
		//on insère l'objet dans la BDD via le ContentValues
		return bdd.insert(TABLE_ETUDIANT, null, values);
	}
 
	public int updateEtudiant(String identifiant, Etudiant etudiant){
		//La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
		//il faut simplement préciser quel livre on doit mettre à jour grâce à l'ID
		ContentValues values = new ContentValues();
		values.put(COL_NOM, etudiant.getNom());
		values.put(COL_PRENOM, etudiant.getPrenom());
		values.put(COL_DEPARTEMENT, etudiant.getDepartement());
		values.put(COL_ANNEE, etudiant.getAnneePromo());
		if(etudiant.isRespo()) 
			values.put(COL_RESPO, 1);
		else
			values.put(COL_RESPO, 0);
		values.put(COL_POINTS, etudiant.getPoints());
		return bdd.update(TABLE_ETUDIANT, values, COL_IDENTIFIANT + " = " + identifiant, null);
	}
 
	public int removeEtudiantWithID(String identifiant){
		//Suppression d'un étudiant de la BDD grâce à l'ID
		return bdd.delete(TABLE_ETUDIANT, COL_IDENTIFIANT + " = " +identifiant, null);
	}
 
	public Etudiant getEtudiant(String id){
		
		   Cursor cursor =
		    		bdd.query(TABLE_ETUDIANT, // table
		    		new String[] {COL_IDENTIFIANT, COL_NOM, COL_PRENOM, COL_DEPARTEMENT, COL_ANNEE, COL_RESPO, COL_POINTS}, // column names
		    		COL_IDENTIFIANT + " = ?", // selections
		            new String[] { String.valueOf(id) }, // args
		            null, // group by
		            null, // having
		            null, // order by
		            null); // limit
		 

		    cursor.moveToFirst();

		    // Si aucun enregistrement n'est retourné
		    if(cursor.getCount() < 1)
		    	return null;
		    Etudiant etudiant;
		    
			if(cursor.getInt(NUM_COL_RESPO) == 1)
				etudiant = new Etudiant(cursor.getString(NUM_COL_IDENTIFIANT),cursor.getString(NUM_COL_NOM),cursor.getString(NUM_COL_PRENOM), cursor.getString(NUM_COL_DEPARTEMENT), cursor.getInt(NUM_COL_ANNEE), true, cursor.getInt(NUM_COL_POINTS));
			else
				etudiant = new Etudiant(cursor.getString(NUM_COL_IDENTIFIANT),cursor.getString(NUM_COL_NOM),cursor.getString(NUM_COL_PRENOM), cursor.getString(NUM_COL_DEPARTEMENT), cursor.getInt(NUM_COL_ANNEE), false, cursor.getInt(NUM_COL_POINTS));
			
			return etudiant;
		}
	
	public ArrayList<Etudiant> getEtudiantsPromo(String departement, int annee)
	{
		   Cursor cursor =
		    		bdd.query(TABLE_ETUDIANT, // table
		    		new String[] {COL_IDENTIFIANT, COL_NOM, COL_PRENOM, COL_DEPARTEMENT, COL_ANNEE,  COL_RESPO, COL_POINTS}, // column names
		    		COL_DEPARTEMENT + " = ? AND " + COL_ANNEE + " = ?", // selections
		            new String[] { String.valueOf(departement), String.valueOf(annee) }, // args
		            null, // group by
		            null, // having
		            COL_POINTS +" DESC", // order by
		            null); // limit		
		   
		    cursor.moveToFirst();
		    ArrayList<Etudiant> etudiant = new ArrayList<Etudiant>();
		    while(!cursor.isAfterLast())
		    {
				if(cursor.getInt(NUM_COL_RESPO) == 1)
					etudiant.add(new Etudiant(cursor.getString(NUM_COL_IDENTIFIANT),cursor.getString(NUM_COL_NOM),cursor.getString(NUM_COL_PRENOM), cursor.getString(NUM_COL_DEPARTEMENT), cursor.getInt(NUM_COL_ANNEE), true, cursor.getInt(NUM_COL_POINTS)));
				else
					etudiant.add(new Etudiant(cursor.getString(NUM_COL_IDENTIFIANT),cursor.getString(NUM_COL_NOM),cursor.getString(NUM_COL_PRENOM), cursor.getString(NUM_COL_DEPARTEMENT), cursor.getInt(NUM_COL_ANNEE), false, cursor.getInt(NUM_COL_POINTS)));			
				cursor.moveToNext();
		    }
			return etudiant;
	}
	
	public ArrayList<Etudiant> getEtudiantsAnnee(int annee)
	{
		   Cursor cursor =
		    		bdd.query(TABLE_ETUDIANT, // table
		    		new String[] {COL_IDENTIFIANT, COL_NOM, COL_PRENOM, COL_DEPARTEMENT, COL_ANNEE,  COL_RESPO, COL_POINTS}, // column names
		    		COL_ANNEE + " = ?", // selections
		            new String[] { String.valueOf(annee) }, // args
		            null, // group by
		            null, // having
		            COL_POINTS +" DESC", // order by
		            null); // limit		
		   
		    cursor.moveToFirst();
		    ArrayList<Etudiant> etudiant = new ArrayList<Etudiant>();
		    while(!cursor.isAfterLast())
		    {
				if(cursor.getInt(NUM_COL_RESPO) == 1)
					etudiant.add(new Etudiant(cursor.getString(NUM_COL_IDENTIFIANT),cursor.getString(NUM_COL_NOM),cursor.getString(NUM_COL_PRENOM), cursor.getString(NUM_COL_DEPARTEMENT), cursor.getInt(NUM_COL_ANNEE), true, cursor.getInt(NUM_COL_POINTS)));
				else
					etudiant.add(new Etudiant(cursor.getString(NUM_COL_IDENTIFIANT),cursor.getString(NUM_COL_NOM),cursor.getString(NUM_COL_PRENOM), cursor.getString(NUM_COL_DEPARTEMENT), cursor.getInt(NUM_COL_ANNEE), false, cursor.getInt(NUM_COL_POINTS)));			
				cursor.moveToNext();
		    }
			return etudiant;
	}
	
	public int getNumberOfEtudiantsPromo(String departement, int annee)
	{
		   Cursor cursor =
		    		bdd.query(TABLE_ETUDIANT, // table
		    		new String[] {COL_IDENTIFIANT, COL_NOM, COL_PRENOM, COL_DEPARTEMENT, COL_ANNEE,  COL_RESPO, COL_POINTS}, // column names
		    		COL_DEPARTEMENT + " = ? AND " + COL_ANNEE + " = ?", // selections
		            new String[] { String.valueOf(departement), String.valueOf(annee) }, // args
		            null, // group by
		            null, // having
		            null, // order by
		            null); // limit		
		   
		    cursor.moveToFirst();
			return cursor.getCount();
	}
	
	public int getNumberOfEtudiants()
	{
		   Cursor cursor =
		    		bdd.query(TABLE_ETUDIANT, // table
		    		new String[] {COL_IDENTIFIANT, COL_NOM, COL_PRENOM, COL_DEPARTEMENT, COL_ANNEE,  COL_RESPO, COL_POINTS}, // column names
		    		null, // selections
		            null, // args
		            null, // group by
		            null, // having
		            null, // order by
		            null); // limit		
		   
		    cursor.moveToFirst();
			return cursor.getCount();
	}
	
	public int ajouterPoints(String id, int nbPts)
	{
		ContentValues values = new ContentValues();
		values.put(COL_POINTS, nbPts);
		return bdd.update(TABLE_ETUDIANT, values, COL_IDENTIFIANT + " = " + id, null);
	}
	
}
