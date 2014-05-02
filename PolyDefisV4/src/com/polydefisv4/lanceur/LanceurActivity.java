package com.polydefisv4.lanceur;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.polydefisv3.R;
import com.polydefisv4.fenetre_principale.MainActivity;
import com.polydefisv4.metier.Etudiant;
import com.polydefisv4.metier.Specialite;

public class LanceurActivity extends Activity implements OnClickListener {	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lanceur);
	    
	    Button bouton3A = (Button) findViewById(R.id.bouton_3_A);
	    bouton3A.setOnClickListener(this);
	    
	    Button bouton4A = (Button) findViewById(R.id.bouton_4_A);
	    bouton4A.setOnClickListener(this);

	    Button boutonRespo = (Button) findViewById(R.id.bouton_respo);
	    boutonRespo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Button selection = (Button) v;
		Intent intent = new Intent(LanceurActivity.this, MainActivity.class);
		if(selection.getText().toString().equals(getResources().getString(R.string.connexion_troisieme_annee))) {
			intent.putExtra("etudiant", new Etudiant("VAIDIE", "Steven",3,Specialite.INFO,false));
		} else if(selection.getText().equals(getResources().getString(R.string.connexion_quatrieme_annee))) {
			intent.putExtra("etudiant", new Etudiant("VAIDIE", "Steven",4,Specialite.INFO,false));
		} else if(selection.getText().equals(getResources().getString(R.string.connexion_responsable))) {
			intent.putExtra("etudiant", new Etudiant("VAIDIE", "Steven",4,Specialite.INFO,true));
		} 
		startActivity(intent);
	}
}
