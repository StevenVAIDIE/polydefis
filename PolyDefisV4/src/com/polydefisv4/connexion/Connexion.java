package com.polydefisv4.connexion;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.bdd.SQLManager;
import com.polydefisv4.fenetre_principale.FenetrePrincipaleActivity;

public class Connexion extends Activity implements OnClickListener {
	private String username, password;
	private Button ok;
	private EditText editTextUsername, editTextPassword;
	private CheckBox saveLoginCheckBox;
	private SharedPreferences loginPreferences;
	private SharedPreferences.Editor loginPrefsEditor;
	private SharedPreferences bdPreferences;
	private SharedPreferences.Editor bdPrefsEditor;
	private Boolean saveLogin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connexion);

		SQLManager manager = new SQLManager(this);

		ok = (Button) findViewById(R.id.buttonLogin);
		
		editTextUsername = (EditText) findViewById(R.id.editTextUsername);
		editTextPassword = (EditText) findViewById(R.id.editTextPassword);
		saveLoginCheckBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);
		loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		loginPrefsEditor = loginPreferences.edit();

		bdPreferences = getSharedPreferences("BDPrefs", MODE_PRIVATE);
		bdPrefsEditor = bdPreferences.edit();

		if (bdPreferences.getBoolean("firstLaunch", true)){
			manager.upgrade();
			manager.insererJeuDeTest();
			//manager.insererDefi();
			bdPrefsEditor.putBoolean("firstLaunch", false);
			bdPrefsEditor.commit();
		}
		
		manager.updateDefiDate();
		
		saveLogin = loginPreferences.getBoolean("saveLogin", false);
		if (saveLogin == true) {
			lancerApplication(loginPreferences.getString("username", ""));
		}
		
		ok.setOnClickListener(this);
	}

	public void onClick(View view) {
		if (view == ok) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);

			username = editTextUsername.getText().toString().toLowerCase(Locale.getDefault());
			password = editTextPassword.getText().toString();
			
			SQLManager manager = new SQLManager(Connexion.this);
			if (manager.bonMdp(username, password)) {
				if (saveLoginCheckBox.isChecked()) {
					loginPrefsEditor.putBoolean("saveLogin", true);
					loginPrefsEditor.putString("username", username);
					loginPrefsEditor.putString("password", password);
					loginPrefsEditor.commit();
				}
				lancerApplication(username);
			} else {
				Toast.makeText(Connexion.this, "Mot de passe incorrect", Toast.LENGTH_LONG).show();
				editTextPassword.setText("");
			}
		}
	}

	private void lancerApplication(String username) {
		SQLManager manager = new SQLManager(this);
		Intent intent = new Intent(Connexion.this, FenetrePrincipaleActivity.class);
		intent.putExtra("etudiant", manager.getEtudiant(manager.getIdFromPseudo(username)));
		startActivity(intent);
		Connexion.this.finish();
	}
}