package com.polydefisv4.bdd;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;

import com.polydefisv4.bean.Connexion;
import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.DefiRealise;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.defis.Geolocalisation;
import com.polydefisv4.bean.defis.Photo;
import com.polydefisv4.bean.defis.QrCode;
import com.polydefisv4.bean.defis.Quizz;

public class SQLManager {

	private ConnexionBDD connexions;
	private EtudiantBDD etudiants;
	private DefiBDD defis;
	private DefiGeolocalisationBDD geolocalisations;
	private DefiPhotoBDD photos;
	private DefiQrCodeBDD qrcodes;
	private DefiQuizzBDD quizz;
	private ParrainageBDD parrainage;
	private DefiRealiseBDD realise;
	
	public SQLManager (Context context) {
		this.connexions = new ConnexionBDD(context);
		this.etudiants = new EtudiantBDD(context);
		this.defis = new DefiBDD(context);
		this.parrainage = new ParrainageBDD(context);
		this.geolocalisations = new DefiGeolocalisationBDD(context);
		this.photos = new DefiPhotoBDD(context);
		this.quizz = new DefiQuizzBDD(context);
		this.qrcodes = new DefiQrCodeBDD(context);
		this.realise = new DefiRealiseBDD(context);
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
	
	public void accepterDefi(int id)
	{
		this.defis.open();
		this.defis.accepterDefi(id);
		this.defis.close();
		
	}
	
	public void validerDefi(int idDefi, String idEtu)
	{
		this.defis.open();
		this.etudiants.open();
		this.realise.open();
		int nbPoints = this.defis.nbPointsDefis(idDefi);
		this.etudiants.ajouterPoints(idEtu, nbPoints);
		DefiRealise df = new DefiRealise(idDefi,idEtu,new Date());// DATE NOW
		this.realise.insertDefiRealise(df);
		this.realise.close();	
		this.defis.close();	
		this.etudiants.close();
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
	
	public void updateDefiDate()
	{
		this.defis.open();
		this.defis.updateEtatDefiDate();
		this.defis.close();
		
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
	
	public Geolocalisation getGeolocalisation(int id)
	{
		this.geolocalisations.open();
		Geolocalisation geoloc = null;
		try {
			geoloc = this.geolocalisations.getGeolocalisation(id);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.geolocalisations.close();
		return geoloc;
	}
	
	public void removeGeolocalisation(int id)
	{
		this.geolocalisations.open();
		this.geolocalisations.removeGeolocalisation(id);
		this.geolocalisations.close();
		this.defis.open();
		this.defis.removeDefi(id);
		this.defis.close();
	}
	
	public void create()
	{
		this.etudiants.open();
		this.etudiants.getSQL().onCreate(this.etudiants.getBDD());
		this.etudiants.close();
		
		this.connexions.open();
		this.connexions.getSQL().onCreate(this.connexions.getBDD());
		this.connexions.close();
		
		this.defis.open();
		this.defis.getSQL().onCreate(this.defis.getBDD());
		this.defis.close();
		
		this.geolocalisations.open();
		this.geolocalisations.getSQL().onCreate(this.geolocalisations.getBDD());
		this.geolocalisations.close();
		
		this.quizz.open();
		this.quizz.getSQL().onCreate(this.quizz.getBDD());
		this.quizz.close();
		
		this.qrcodes.open();
		this.qrcodes.getSQL().onCreate(this.qrcodes.getBDD());
		this.qrcodes.close();
		
		this.photos.open();
		this.photos.getSQL().onCreate(this.photos.getBDD());
		this.photos.close();
		
		this.realise.open();
		this.realise.getSQL().onCreate(this.realise.getBDD());
		this.realise.close();
		
		this.parrainage.open();
		this.parrainage.getSQL().onCreate(this.defis.getBDD());
		this.parrainage.close();
	}
	
	public void etudiantRealiseDefi(String idEtu, int idDefi)
	{
		this.etudiants.open();
		Etudiant etudiant = this.etudiants.getEtudiant(idEtu);
		this.etudiants.close();
		// A FAIRE
	}
	
	public void insertGeolocalisation(Geolocalisation geoloc)
	{
		this.defis.open();
		this.geolocalisations.open();
		this.defis.insertDefi(geoloc, Defi.TYPE_GEOLOCALIATION);
		int id = this.defis.getLastId();
		geoloc.setId(id);
		this.geolocalisations.insertGeolocalisation(geoloc);
		this.defis.close();
		this.geolocalisations.close();
	}
	
	public void insertPhoto(Photo photo)
	{
		this.defis.open();
		this.photos.open();
		this.defis.getSQL().onUpgrade(this.defis.getBDD(),1,2);
		this.photos.getSQL().onUpgrade(this.geolocalisations.getBDD(),1,2);
		this.defis.insertDefi(photo, Defi.TYPE_GEOLOCALIATION);
		this.photos.insertPhoto(photo);
		this.defis.close();
		this.photos.close();
	}
	
	public void insertQrCode(QrCode qrcode)
	{
		this.defis.open();
		this.qrcodes.open();
		this.defis.getSQL().onUpgrade(this.defis.getBDD(),1,2);
		this.qrcodes.getSQL().onUpgrade(this.geolocalisations.getBDD(),1,2);
		this.defis.insertDefi(qrcode, Defi.TYPE_GEOLOCALIATION);
		this.qrcodes.insertQrCode(qrcode);
		this.defis.close();
		this.photos.close();
	}
	
	public void insertQuizz(Quizz quizz)
	{
		this.defis.open();
		this.quizz.open();
		this.defis.getSQL().onUpgrade(this.defis.getBDD(),1,2);
		this.quizz.getSQL().onUpgrade(this.geolocalisations.getBDD(),1,2);
		this.defis.insertDefi(quizz, Defi.TYPE_GEOLOCALIATION);
		//this.quizz.insertQuizz(quizz);
		this.defis.close();
		this.photos.close();
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
		this.parrainage.getSQL().onUpgrade(this.parrainage.getBDD(), 1, 2);
		this.parrainage.close();
		
		this.geolocalisations.open();
		this.geolocalisations.getSQL().onUpgrade(this.geolocalisations.getBDD(), 1, 2);
		this.geolocalisations.close();
		
		this.quizz.open();
		this.quizz.getSQL().onUpgrade(this.quizz.getBDD(), 1, 2);
		this.quizz.close();
		
		this.photos.open();
		this.photos.getSQL().onUpgrade(this.photos.getBDD(), 1, 2);
		this.photos.close();
		
		this.qrcodes.open();
		this.qrcodes.getSQL().onUpgrade(this.qrcodes.getBDD(), 1, 2);
		this.qrcodes.close();
		
		this.realise.open();
		this.realise.getSQL().onUpgrade(this.realise.getBDD(), 1, 2);
		this.realise.close();
		
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
