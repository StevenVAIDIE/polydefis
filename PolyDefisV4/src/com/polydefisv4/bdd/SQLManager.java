package com.polydefisv4.bdd;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import android.content.Context;

import com.polydefisv4.bean.Connexion;
import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.DefiPhotoAValider;
import com.polydefisv4.bean.DefiRealise;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.Parrainage;
import com.polydefisv4.bean.defis.Geolocalisation;
import com.polydefisv4.bean.defis.Photo;
import com.polydefisv4.bean.defis.QrCode;
import com.polydefisv4.bean.defis.QuestionQuizz;
import com.polydefisv4.bean.defis.Quizz;
import com.polydefisv4.connexion.Util;

public class SQLManager {
	private ConnexionBDD connexions;
	private EtudiantBDD etudiants;
	private DefiBDD defis;
	private DefiGeolocalisationBDD geolocalisations;
	private DefiQrCodeBDD qrcodes;
	private DefiQuizzBDD quizz;
	private ParrainageBDD parrainage;
	private DefiRealiseBDD realise;
	private DefiPhotoAValiderBDD defiPhotoAValider;

	public SQLManager (Context context) {
		this.connexions = new ConnexionBDD(context);
		this.etudiants = new EtudiantBDD(context);
		this.defis = new DefiBDD(context);
		this.parrainage = new ParrainageBDD(context);
		this.geolocalisations = new DefiGeolocalisationBDD(context);
		this.quizz = new DefiQuizzBDD(context);
		this.qrcodes = new DefiQrCodeBDD(context);
		this.realise = new DefiRealiseBDD(context);
		this.defiPhotoAValider = new DefiPhotoAValiderBDD(context);
	}
	
	public int updateParrainage(Parrainage parrainage) {
		this.parrainage.open();
		int result = this.parrainage.updateParrainage(parrainage);
		this.parrainage.close();
		return(result);
	}
	
	public int removeParrainage(Parrainage parrainage) {
		this.parrainage.open();
		int result = this.parrainage.removeParrainage(parrainage);
		this.parrainage.close();
		return(result);
	}
	
	public long insertParrainage(Parrainage parrainage) {
		this.parrainage.open();
		long result = this.parrainage.insertParrainage(parrainage);
		this.parrainage.close();
		return(result);
	}
	
	public long insertConnexion(Connexion connexion) {
		connexion.setIdentifiant(connexion.getIdentifiant().toLowerCase(Locale.getDefault()));
		this.connexions.open();
		long result = this.connexions.insertConnexion(connexion);
		this.connexions.close();
		return(result);
	}
	
	public Connexion getConnexion(String id) {
		this.connexions.openReadable();
		Connexion result = this.connexions.getConnexion(id);
		this.connexions.close();
		return(result);
	}
	
	public int updateConnexion(String id, Connexion connexion) {
		this.connexions.open();
		int result = this.connexions.updateConnexion(id, connexion);
		this.connexions.close();
		return(result);
	}
	
	public int removeConnexion(String id) {
		this.connexions.open();
		int result = this.connexions.removeConnexion(id);
		this.connexions.close();
		return(result);
	}
	
	public long insertEtudiant(Etudiant etu) {
		this.etudiants.open();
		long result = this.etudiants.insertEtudiant(etu);
		this.etudiants.close();
		return (result);
	}
	
	public int updateEtudiant(Etudiant etudiant) {
		this.etudiants.open();
		int result = this.etudiants.updateEtudiant(etudiant);
		this.etudiants.close();
		return (result);		
	}
	
	public Etudiant getEtudiant(int id) {
		this.etudiants.open();
		Etudiant result = this.etudiants.getEtudiant(id);
		this.etudiants.close();
		return (result);		
	}
	
	public void accepterDefi(Defi defi) {
		this.defis.open();
		this.defis.accepterDefi(defi);
		this.defis.close();
	}
	
	public ArrayList<Etudiant> getEtudiantAnnee(int annee) {
		this.etudiants.open();
		ArrayList<Etudiant> result = this.etudiants.getEtudiantsAnnee(annee);
		this.etudiants.close();
		return (result);	
	}
	
	public ArrayList<Etudiant> getEtudiantPromo(String departement, int annee) {
		this.etudiants.open();
		ArrayList<Etudiant> result = this.etudiants.getEtudiantsPromo(departement,annee);
		this.etudiants.close();
		return (result);				
	}
	
	public void updateDefiDate() {
		this.defis.open();
		this.defis.updateEtatDefiDate();
		this.defis.close();
	}
	
	public void removePhoto(Photo photo) {
		this.defis.open();
		this.defis.removeDefi(photo);
		this.defis.close();
	}
	
	public boolean bonMdp(String pseudo, String password) {
		this.connexions.openReadable();
		Connexion result = this.connexions.getConnexion(pseudo.toLowerCase(Locale.getDefault()));
		this.connexions.close();		
		if(result != null)
			return (password.equals(result.getMotDePasse()));
		else
			return false;
	}
	
	public void removeGeolocalisation(Geolocalisation geolocalisation) {
		this.geolocalisations.open();
		this.geolocalisations.removeGeolocalisation(geolocalisation);
		this.geolocalisations.close();
		this.defis.open();
		this.defis.removeDefi(geolocalisation);
		this.defis.close();
	}
	
	public void create() {
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
		
		this.realise.open();
		this.realise.getSQL().onCreate(this.realise.getBDD());
		this.realise.close();
		
		this.parrainage.open();
		this.parrainage.getSQL().onCreate(this.parrainage.getBDD());
		this.parrainage.close();
		
		this.defiPhotoAValider.open();
		this.defiPhotoAValider.getSQL().onCreate(this.defiPhotoAValider.getBDD());
		this.defiPhotoAValider.close();
	}
	
	public void insertGeolocalisation(Geolocalisation geoloc) {
		this.defis.open();
		this.defis.insertDefi(geoloc, Defi.TYPE_GEOLOCALISATION);
		geoloc.setId(this.defis.getLastId());
		this.defis.close();
		
		this.geolocalisations.open();
		this.geolocalisations.insertGeolocalisation(geoloc);
		this.geolocalisations.close();
	}
	
	public void insertPhoto(Photo photo) {
		this.defis.open();
		this.defis.insertDefi(photo,Defi.TYPE_PHOTO);
		photo.setId(this.defis.getLastId());
		this.defis.close();
	}
	
	public void insertQrCode(QrCode qrcode) {
		this.defis.open();
		this.defis.insertDefi(qrcode,Defi.TYPE_QRCODE);
		qrcode.setId(this.defis.getLastId());
		this.defis.close();

		this.qrcodes.open();
		this.qrcodes.insertQrCode(qrcode);
		this.qrcodes.close();
	}
	
	public void insertQuizz(Quizz quizz) {
		this.defis.open();
		this.defis.insertDefi(quizz, Defi.TYPE_QUIZZ);
		quizz.setId(this.defis.getLastId());
		this.defis.close();
		
		this.quizz.open();
		this.quizz.insertQuizz(quizz);
		this.quizz.close();
	}
	
	public ArrayList<Etudiant> getAllDemandeParrainage(Etudiant etudiant) {
		this.parrainage.open();
		ArrayList<Etudiant> demandeParrainage = parrainage.getAllFilleul(etudiant,Parrainage.DEMANDE_EN_COURS);
		this.parrainage.close();
		return demandeParrainage;
	}
	
	public ArrayList<Etudiant> getAllFilleul(Etudiant etudiant) {
		this.parrainage.open();
		ArrayList<Etudiant> demandeParrainage = parrainage.getAllFilleul(etudiant,Parrainage.DEMANDE_ACCEPTE);
		this.parrainage.close();
		return demandeParrainage;
	}
	
	public void upgrade() {
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
		
		this.qrcodes.open();
		this.qrcodes.getSQL().onUpgrade(this.qrcodes.getBDD(), 1, 2);
		this.qrcodes.close();
		
		this.realise.open();
		this.realise.getSQL().onUpgrade(this.realise.getBDD(), 1, 2);
		this.realise.close();
		
		this.defiPhotoAValider.open();
		this.defiPhotoAValider.getSQL().onUpgrade(this.defiPhotoAValider.getBDD(), 1, 2);
		this.defiPhotoAValider.close();
		
	}
	
	public void insererJeuDeTest() {
		this.insertEtudiant(new Etudiant(1,"Guerre", "Martin", Etudiant.INFO, 4, true,0));
		this.insertConnexion(new Connexion(1,"e000000s","000"));
		
		this.insertEtudiant(new Etudiant(2,"Payen", "Mathieu", Etudiant.INFO, 3, false, 0));
		this.insertConnexion(new Connexion(2,"e111111s","111"));
		
		this.insertEtudiant(new Etudiant(3,"Kodelja", "Dorian", Etudiant.INFO, 3, false,0));
		this.insertConnexion(new Connexion(3,"e222222s","222"));
		
		this.insertEtudiant(new Etudiant(4,"Vaidie", "Steven", Etudiant.INFO, 3, false,0));
		this.insertConnexion(new Connexion(4,"e333333s","333"));
		
		this.insertEtudiant(new Etudiant(5,"Perez", "Nicolas", Etudiant.INFO, 3, false,0));
		this.insertConnexion(new Connexion (5,"e444444s","444"));
		
		this.insertEtudiant(new Etudiant(6,"Tollec", "David", Etudiant.INFO, 3, false,0));
		this.insertConnexion(new Connexion(6,"e555555s","555"));
		
		this.insertEtudiant(new Etudiant(7,"Bergeron", "Stephane", Etudiant.TE, 3, false,0));
		this.insertConnexion(new Connexion(7,"e666666s","666"));
		
		this.insertEtudiant(new Etudiant(8,"Guillon", "Emmanuel", Etudiant.TE, 3, false,0));
		this.insertConnexion(new Connexion(8,"e777777s","777"));
		
		this.insertEtudiant(new Etudiant(9,"Moreau", "Tom", Etudiant.TE, 4, true,0));
		this.insertConnexion(new Connexion(9,"e888888s","888"));
		
		this.insertEtudiant(new Etudiant(10,"Bonachera", "David", Etudiant.TE, 4, false,0));
		this.insertConnexion(new Connexion(10,"e999999s","999"));
		
		insertParrainage(new Parrainage(9, 7, Parrainage.DEMANDE_ACCEPTE));
		insertParrainage(new Parrainage(10, 8, Parrainage.DEMANDE_ACCEPTE));		
	}
	
	public void insererDefi() {
		Date date = null;
		try {
			date = Util.dateFormat.parse("03/06/2014");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Quizz quizz;
		QuestionQuizz questions = new QuestionQuizz("Question 1", "Reponse 1", "Reponse 2", "Reponse 3", "Reponse 4");
		QuestionQuizz questions1 = new QuestionQuizz("Question 2", "Reponse 5", "Reponse 6", "Reponse 7", "Reponse 8");

		insertPhoto(new Photo(1, 9, "Photo - test1", "test1", date, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_FILLEUL));
		insertGeolocalisation(new Geolocalisation(1, 9, "geoloc - test1", "test1", date, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_FILLEUL, 10.10, 10.12));
		quizz = new Quizz(1, 9, "Quizz - test1", "test1", date, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_FILLEUL);
		quizz.addQuestion(questions);
		quizz.addQuestion(questions1);
		insertQuizz(quizz);
		insertQrCode(new QrCode(1, 9, "QrCode - test1", "test1", date, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_FILLEUL, "test"));
		
		insertPhoto(new Photo(1, 9, "Photo - test1", "test1", null, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_FILLEUL));
		insertGeolocalisation(new Geolocalisation(1, 9, "geoloc - test1", "test1", null, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_FILLEUL, 10.10, 10.12));
		quizz = new Quizz(1, 9, "Quizz - test1", "test1", null, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_FILLEUL);
		quizz.addQuestion(questions);
		quizz.addQuestion(questions1);
		insertQuizz(quizz);
		insertQrCode(new QrCode(1, 9, "QrCode - test1", "test1", null, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_FILLEUL, "test"));
		
		insertPhoto(new Photo(1, 9, "Photo - test1", "test1", date, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_PROMO));
		insertGeolocalisation(new Geolocalisation(1, 9, "geoloc - test1", "test1", date, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_PROMO, 10.10, 10.12));
		quizz = new Quizz(1, 9, "Quizz - test1", "test1", date, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_PROMO);
		quizz.addQuestion(questions);
		quizz.addQuestion(questions1);
		insertQuizz(quizz);
		insertQrCode(new QrCode(1, 9, "QrCode - test1", "test1", date, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_PROMO, "test"));
		
		insertPhoto(new Photo(1, 9, "Photo - test1", "test1", null, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_PROMO));
		insertGeolocalisation(new Geolocalisation(1, 9, "geoloc - test1", "test1", null, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_PROMO, 10.10, 10.12));
		quizz = new Quizz(1, 9, "Quizz - test1", "test1", null, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_PROMO);
		quizz.addQuestion(questions);
		quizz.addQuestion(questions1);
		insertQuizz(quizz);
		insertQrCode(new QrCode(1, 9, "QrCode - test1", "test1", null, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_PROMO, "test"));
		
		insertPhoto(new Photo(1, 9, "Photo - test1", "test1", date, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_ALL));
		insertGeolocalisation(new Geolocalisation(1, 9, "geoloc - test1", "test1", date, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_ALL, 10.10, 10.12));
		quizz = new Quizz(1, 9, "Quizz - test1", "test1", date, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_ALL);
		quizz.addQuestion(questions);
		quizz.addQuestion(questions1);
		insertQuizz(quizz);
		insertQrCode(new QrCode(1, 9, "QrCode - test1", "test1", date, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_ALL, "test"));
		
		insertPhoto(new Photo(1, 9, "Photo - test1", "test1", null, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_ALL));
		insertGeolocalisation(new Geolocalisation(1, 9, "geoloc - test1", "test1", null, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_ALL, 10.10, 10.12));
		quizz = new Quizz(1, 9, "Quizz - test1", "test1", null, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_FILLEUL);
		quizz.addQuestion(questions);
		quizz.addQuestion(questions1);
		insertQuizz(quizz);
		insertQrCode(new QrCode(1, 9, "QrCode - test1", "test1", null, 1, Defi.ETAT_ATTENTE_VALIDATION, Defi.PORTEE_ALL, "test"));
	
	}

	public ArrayList<Defi> getAllDefiAAccepter() {
		ArrayList<Defi> listeDefisARealiser = new ArrayList<Defi>();
		
		this.defis.open();
		ArrayList<Defi> listePhotoARealiser = this.defis.getAllPhotoAAccepter();
		
		this.geolocalisations.open();
		ArrayList<Defi> listeGeolocalisationARealiser = this.geolocalisations.getAllDefiAAccepter();
		this.geolocalisations.close();
		
		this.qrcodes.open();
		ArrayList<Defi> listeQrCodeARealiser = this.qrcodes.getAllDefiAAccepter();
		this.qrcodes.close();
		
		this.quizz.open();
		ArrayList<Defi> listeQuizzARealiser = this.quizz.getAllDefiAAccepter();
		this.quizz.close();
		this.defis.close();
		
		listeDefisARealiser.addAll(listeQrCodeARealiser);
		listeDefisARealiser.addAll(listeGeolocalisationARealiser);
		listeDefisARealiser.addAll(listePhotoARealiser);
		listeDefisARealiser.addAll(listeQuizzARealiser);
		
		Collections.sort(listeDefisARealiser);
		return listeDefisARealiser;
	}

	public ArrayList<Defi> getAllDefiARealiser(Etudiant etudiant) {
		ArrayList<Defi> listeDefisARealiser = new ArrayList<Defi>();
		
		Etudiant parrain = getParrain(etudiant.getIdEtudiant());
		int idParrain;
		if (parrain != null) 
			idParrain = parrain.getIdEtudiant();
		else 
			idParrain =  -1;
		 
		this.defis.open();
		ArrayList<Defi> listePhotoARealiser = this.defis.getAllPhotosARealiser(etudiant,idParrain);
		
		this.geolocalisations.open();
		ArrayList<Defi> listeGeolocalisationARealiser = this.geolocalisations.getAllGeolocalisationsARealiser(etudiant, idParrain);
		this.geolocalisations.close();
		
		this.qrcodes.open();
		ArrayList<Defi> listeQrCodeARealiser = this.qrcodes.getAllQrCodesARealiser(etudiant, idParrain);
		this.qrcodes.close();
		
		this.quizz.open();
		ArrayList<Defi> listeQuizzARealiser = this.quizz.getAllQuizzARealiser(etudiant, idParrain);
		this.quizz.close();
		this.defis.close();
		
		listeDefisARealiser.addAll(listeQrCodeARealiser);
		listeDefisARealiser.addAll(listeGeolocalisationARealiser);
		listeDefisARealiser.addAll(listePhotoARealiser);
		listeDefisARealiser.addAll(listeQuizzARealiser);

		Collections.sort(listeDefisARealiser);
		return listeDefisARealiser;
	}
	
	public ArrayList<Defi> getAllPhotoAValider() {
		this.defis.open();
		this.defiPhotoAValider.open();
		ArrayList<Defi> photoAValider = this.defiPhotoAValider.getAllPhotoAValider();
		this.defis.close();
		this.defiPhotoAValider.close();
		return photoAValider;
	}

	public void updateDefi(Defi defi) {
		this.defis.open();
		this.defis.updateDefi(defi);
		this.defis.close();
	}
	
	public void removeQrCode(QrCode qrCode) {
		this.qrcodes.open();
		this.qrcodes.removeQrCode(qrCode);
		this.qrcodes.close();
		this.defis.open();
		this.defis.removeDefi(qrCode);
		this.defis.close();
	}

	public Etudiant getParrain(int idEtudiant) {
		this.parrainage.open();
		this.etudiants.open();
		Etudiant parrain = this.parrainage.getParrain(idEtudiant);
		this.etudiants.close();
		this.parrainage.close();
		return parrain;
	}

	public int getIdFromPseudo(String pseudo) {
		this.connexions.openReadable();
		Connexion result = this.connexions.getConnexion(pseudo);
		this.connexions.close();		
		return result.getIdEtudiant();
	}

	public Parrainage getParrainnage(Etudiant parrain,Etudiant filleul) {
		this.parrainage.open();
		Parrainage parrainage = this.parrainage.getParrainage(parrain, filleul);
		this.parrainage.close();
		return parrainage;
	}

	public void photoEffectue(Photo photo, Etudiant etudiant) {
		DefiPhotoAValider defiPhotoAValider = new DefiPhotoAValider(photo.getId(), etudiant.getIdEtudiant(), photo.getUrlPhoto(), new Date());
		this.defiPhotoAValider.open();
		this.defiPhotoAValider.insertDefiPhotoAValider(defiPhotoAValider);
		this.defiPhotoAValider.close();
	}
	
	public void defiEffectue(Defi defi, int idEtudiant, String etat) {
		DefiRealise defiRealise = new DefiRealise(defi.getId(), idEtudiant, new Date(), etat);
		this.realise.open();
		this.realise.insertDefiRealise(defiRealise);
		this.realise.close();
		
		if(defi.getDateFin() == null && etat.equals(DefiRealise.ETAT_REUSSI)) {
			defi.setEtatAcceptation(Defi.ETAT_TERMINE);
			this.defis.open();
			this.defis.updateDefi(defi);
			this.defis.close();
		}
		
		System.out.println("Nb points " + defi.getNombrePoint());
		if (etat.equals(DefiRealise.ETAT_REUSSI)) {
			ajouterPointEtudiant(defi.getIdEtudiant(), defi.getNombrePoint());
			ajouterPointEtudiant(idEtudiant, defi.getNombrePoint());
		}
	}

	private void ajouterPointEtudiant(int idEtudiant, int nombrePoint) {
		this.etudiants.open();
		this.etudiants.ajouterPoints(idEtudiant, nombrePoint);
		this.etudiants.close();
	}

	public void validerPhotoPrise(Photo photo) {
		this.defiPhotoAValider.open();
		DefiPhotoAValider defiAValider = this.defiPhotoAValider.getDefiPhotoAValider(photo.getUrlPhoto());
		this.defiPhotoAValider.close();
		
		defiEffectue(photo, defiAValider.getIdEtudiant(), DefiRealise.ETAT_REUSSI);

		this.defiPhotoAValider.open();
		if (photo.getDateFin() == null) {
			this.defiPhotoAValider.removeDefiPhotoAValider(defiAValider.getIdDefi());
		} else {
			this.defiPhotoAValider.removeDefiPhotoAValider(defiAValider.getIdDefi(),defiAValider.getIdEtudiant());
		}
		this.defiPhotoAValider.close();
	}

	public void refuserPhotoPrise(Photo photo) {
		this.defiPhotoAValider.open();
		DefiPhotoAValider defiAValider = this.defiPhotoAValider.getDefiPhotoAValider(photo.getUrlPhoto());
		this.defiPhotoAValider.close();
		
		defiEffectue(photo, defiAValider.getIdEtudiant(), DefiRealise.ETAT_ECHEC);

		this.defiPhotoAValider.open();
		this.defiPhotoAValider.removeDefiPhotoAValider(defiAValider.getIdDefi(),defiAValider.getIdEtudiant());
		this.defiPhotoAValider.close();
	}

	public int getNbPointsPhoto(Etudiant etudiant) {
		int resultat;
		
		this.defis.open();
		this.realise.open();
		if(etudiant.getAnneePromo() == 4) {
			resultat = this.defis.getNbPointsPhoto4A(etudiant);
		} else {
			resultat = this.defis.getNbPointsPhoto3A(etudiant);
		}
		this.realise.close();
		this.defis.close();
		return resultat;
	}
	
	public int getNbPointsQuizz(Etudiant etudiant) {
		int resultat;
		
		this.defis.open();
		this.realise.open();
		this.quizz.open();
		if(etudiant.getAnneePromo() == 4) {
			System.out.println("1");
			resultat = this.quizz.getNbPointsQuizz4A(etudiant);
		} else {
			System.out.println("2");
			resultat = this.quizz.getNbPointsQuizz3A(etudiant);
		}
		this.quizz.close();
		this.realise.close();
		this.defis.close();
		return resultat;
	}

	public int getNbPointsQrCode(Etudiant etudiant) {
		int resultat;
		
		this.defis.open();
		this.realise.open();
		this.qrcodes.open();
		if(etudiant.getAnneePromo() == 4) {
			resultat = this.qrcodes.getNbPointsQrCode4A(etudiant);
		} else {
			resultat = this.qrcodes.getNbPointsQrCode3A(etudiant);
		}
		this.qrcodes.close();
		this.realise.close();
		this.defis.close();
		return resultat;
	}

	public int getNbPointsGeolocalisation(Etudiant etudiant) {
		int resultat;
		
		this.defis.open();
		this.realise.open();
		this.geolocalisations.open();
		if(etudiant.getAnneePromo() == 4) {
			resultat = this.geolocalisations.getNbPointsGeolocalisation3A(etudiant);
		} else {
			resultat = this.geolocalisations.getNbPointsGeolocalisation4A(etudiant);
		}
		this.geolocalisations.close();
		this.realise.close();
		this.defis.close();
		return resultat;
	}

	public void removeQuizz(Quizz quizz) {
		this.quizz.open();
		this.quizz.removeQuizz(quizz);
		this.quizz.close();
		this.defis.open();
		this.defis.removeDefi(quizz);
		this.defis.close();
	}

	public void makeRespo(Etudiant etudiant4a) {
		etudiant4a.setRespo(true);
		updateEtudiant(etudiant4a);
	}

	public void validerDefi(Defi defis, int nbPoint) {
		defis.setNombrePoint(nbPoint);
		defis.setEtatAcceptation(Defi.ETAT_ACCEPTE);
		updateDefi(defis);
	}
}
