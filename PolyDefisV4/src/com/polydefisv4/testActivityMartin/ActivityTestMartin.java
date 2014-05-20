package com.polydefisv4.testActivityMartin;

import java.text.ParseException;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.bean.Defi;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.bean.defis.Geolocalisation;
import com.polydefisv4.fenetre_principale.FenetrePrincipaleActivity;

public class ActivityTestMartin extends Activity {

	Button bouton = null;
	CheckBox cb = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activity_test_martin);

		final SQLManager bdd = new SQLManager(this);
		//Connexion connexion = new Connexion("e111111s","123");
		final Intent i = new Intent(this, FenetrePrincipaleActivity.class);
		
		
		Date date = null;
		try {
			date = Util.dateFormat.parse("2014-05-15");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//FileInputStream fis;
		bdd.removeGeolocalisation(1);
		Geolocalisation geoloc = new Geolocalisation(1,"e111111s","Test Defi Geoloc","Descriptioooooon",date,Defi.ETAT_EN_COURS_ACCEPTATION,6,Defi.PORTEE_PROMO,47.2440713, -1.5292213);
		bdd.insertGeolocalisation(geoloc);
		Geolocalisation geoloc2 = bdd.getGeolocalisation(1);
		Debug.Log(geoloc2.getDescription());
		bdd.accepterDefi(1);
		/*Debug.Log("before");
		try {
			fis = new FileInputStream("conn_auto");
			Debug.Log("avant");
			Debug.Log(String.valueOf(fis.read()));
			fis.close();
		} 
		catch (FileNotFoundException e) 
		{
			Debug.Log(e.getMessage());
		}
		catch (IOException e) {
			Debug.Log(e.getMessage());
		}
		
		Debug.Log("after");
		*/
		bdd.insererJeuDeTest();
		bouton = (Button) findViewById(R.id.button1);
		cb = (CheckBox) findViewById(R.id.checkBox1);
		bouton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				EditText et1 = (EditText) findViewById(R.id.editText1);
				String id = et1.getText().toString();
				EditText et2 = (EditText) findViewById(R.id.editText2);
				String pw = et2.getText().toString();
				
				boolean connexion = bdd.bonMdp(id, pw);
				if(connexion == true)
				{
					Toast.makeText(ActivityTestMartin.this,"Connexion réussie !", Toast.LENGTH_LONG).show();
					Etudiant etu = bdd.getEtudiant(id);
					i.putExtra("etudiant", etu);
					/*if(cb.isChecked())
					{
						String newline = id + "\n";
						FileOutputStream fos;
						try {
							fos = openFileOutput("data/data/conn_auto", Context.MODE_PRIVATE);
							fos.write(id.getBytes());
							fos.write("\n".getBytes());
							fos.write(pw.getBytes());
							fos.close();
						} 
						catch (FileNotFoundException e) 
						{
							e.printStackTrace();
						}
						catch (IOException e) {
							e.printStackTrace();
						}


					}*/
					startActivity(i);

				}
				else
				{
					Toast.makeText(ActivityTestMartin.this,"Mauvais login !", Toast.LENGTH_LONG).show();
				}
			}	
		});
	}


}
