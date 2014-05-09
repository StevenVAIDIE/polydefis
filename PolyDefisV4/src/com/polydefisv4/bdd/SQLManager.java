package com.polydefisv4.bdd;

import java.util.ArrayList;

import android.content.Context;

import com.polydefisv4.bean.Connexion;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.defis.Photo;

public class SQLManager {

	private ConnexionBDD connexions;
	private EtudiantBDD etudiants;
	private DefiBDD defis;
	private ParrainageBDD parrainage;
	
	public SQLManager (Context context) {
		this.connexions = new ConnexionBDD(context);
		this.etudiants = new EtudiantBDD(context);
		this.defis = new DefiBDD(context);
		this.parrainage = new ParrainageBDD(context);
	}
	
	public long insertConnexion(Connexion connexion)
	{
		this.connexions.open();
		long result = this.connexions.insertConnexion(connexion);
		this.connexions.close();
		return(result);
	}
	
	public Connexion getConnexion(String id)
	{
		this.connexions.openReadable();
		Connexion result = this.connexions.getConnexion(id);
		this.connexions.close();
		return(result);
	}
	
	public int updateConnexion(String id, Connexion connexion)
	{
		this.connexions.open();
		int result = this.connexions.updateConnexion(id, connexion);
		this.connexions.close();
		return(result);
	}
	
	public int removeConnexion(String id)
	{
		this.connexions.open();
		int result = this.connexions.removeConnexion(id);
		this.connexions.close();
		return(result);
	}
	
	public long insertEtudiant(Etudiant etu)
	{
		this.etudiants.open();
		long result = this.etudiants.insertEtudiant(etu);
		this.etudiants.close();
		return (result);
	}
	
	public Etudiant getEtudiant(String id)
	{
		this.etudiants.open();
		Etudiant result = this.etudiants.getEtudiant(id);
		this.etudiants.close();
		return (result);		
	}
	
	public ArrayList<Etudiant> getEtudiantAnnee(int annee)
	{
		this.etudiants.open();
		ArrayList<Etudiant> result = this.etudiants.getEtudiantsAnnee(annee);
		this.etudiants.close();
		return (result);	
	}
	
	public ArrayList<Etudiant> getEtudiantPromo(String departement, int annee)
	{
		this.etudiants.open();
		ArrayList<Etudiant> result = this.etudiants.getEtudiantsPromo(departement,annee);
		this.etudiants.close();
		return (result);				
	}
	
	public boolean bonMdp(String pseudo, String password)
	{
		this.connexions.openReadable();
		Connexion result = this.connexions.getConnexion(pseudo);
		this.connexions.close();		
		if(result != null)
			return (password.equals(result.getMotDePasse()));
		else
			return false;
	}
	
	public long insertDefiPhoto(Photo photo)
	{
		this.defis.open();
		long result = this.defis.insertDefi(photo, 1);
		this.defis.close();
		return (result);		
	}
	
	public void create()
	{
		this.etudiants.open();
		this.etudiants.getSQL().onCreate(this.etudiants.getBDD());
		this.etudiants.close();
		
		this.connexions.open();
		this.connexions.getSQL().onCreate(this.connexions.getBDD());
		this.connexions.close();
		
		this.connexions.open();
		this.defis.getSQL().onCreate(this.defis.getBDD());
		this.connexions.close();
	}
	
	public void upgrade()
	{
		this.etudiants.open();
		this.etudiants.getSQL().onUpgrade(this.etudiants.getBDD(), 1, 2);
		this.etudiants.close();
		
		this.connexions.open();
		this.connexions.getSQL().onUpgrade(this.connexions.getBDD(), 1, 2);
		this.connexions.close();
		
		this.defis.open();
		this.defis.getSQL().onUpgrade(this.defis.getBDD(), 1, 2);
		this.defis.close();
		
		this.parrainage.open();
		this.parrainage.getSQL().onCreate(this.parrainage.getBDD());
		this.parrainage.close();
		
	}
	
	public void insererJeuDeTest()
	{
		Etudiant etudiant;
		Connexion connexion;
		
		etudiant = new Etudiant("e000000s","Guerre", "Martin", Etudiant.INFO, 4, true,15);
		connexion = new Connexion("e000000s","000");
		this.insertEtudiant(etudiant);
		this.insertConnexion(connexion);
		
		etudiant = new Etudiant("e111111s","Payen", "Mathieu", Etudiant.INFO, 3, false, 10);
		connexion = new Connexion("e111111s","111");
		this.insertEtudiant(etudiant);
		this.insertConnexion(connexion);
		
		etudiant = new Etudiant("e222222s","Kodelja", "Dorian", Etudiant.INFO, 3, false,2);
		connexion = new Connexion("e222222s","222");
		this.insertEtudiant(etudiant);
		this.insertConnexion(connexion);
		
		etudiant = new Etudiant("e333333s","Vaidie", "Steven", Etudiant.INFO, 3, false,0);
		connexion = new Connexion("e333333s","333");
		this.insertEtudiant(etudiant);
		this.insertConnexion(connexion);
		
		etudiant = new Etudiant("e444444s","Perez", "Nicolas", Etudiant.INFO, 3, false,7);
		connexion = new Connexion("e444444s","444");
		this.insertEtudiant(etudiant);
		this.insertConnexion(connexion);
		
		etudiant = new Etudiant("e555555s","Tollec", "David", Etudiant.INFO, 3, false,24);
		connexion = new Connexion("e555555s","555");
		this.insertEtudiant(etudiant);
		this.insertConnexion(connexion);
		
		etudiant = new Etudiant("e666666s","Bergeron", "Stephane", Etudiant.TE, 3, false,8);
		connexion = new Connexion("e666666s","666");
		this.insertEtudiant(etudiant);
		this.insertConnexion(connexion);
		
		etudiant = new Etudiant("e777777s","Guillon", "Emmanuel", Etudiant.TE, 3, false,5);
		connexion = new Connexion("e777777s","777");
		this.insertEtudiant(etudiant);
		this.insertConnexion(connexion);
		
		etudiant = new Etudiant("e888888s","Moreau", "Tom", Etudiant.TE, 4, true,23);
		connexion = new Connexion("e888888s","888");
		this.insertEtudiant(etudiant);
		this.insertConnexion(connexion);
		
		etudiant = new Etudiant("e999999s","Bonachera", "David", Etudiant.TE, 4, false,14);
		connexion = new Connexion("e999999s","999");
		this.insertEtudiant(etudiant);
		this.insertConnexion(connexion);
	}
}
