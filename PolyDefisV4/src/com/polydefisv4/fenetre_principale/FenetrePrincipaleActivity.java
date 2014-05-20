package com.polydefisv4.fenetre_principale;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.polydefisv4.R;
import com.polydefisv4.ajoutAdministrateur.AjoutAdministrateurFragment;
import com.polydefisv4.ajoutDefis.AjoutDefiFragment;
import com.polydefisv4.bean.Etudiant;
import com.polydefisv4.classement.ClassementFragment;
import com.polydefisv4.listeDefis.ListeDefisRealiseFragment;
import com.polydefisv4.menu_principal.MenuPrincipalFragment;
import com.polydefisv4.profil.ProfilFragment;

public class FenetrePrincipaleActivity extends Activity implements OnItemClickListener {
	private DrawerLayout layoutMenuLateral;
	private ListView listViewMenuLateral;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence titreMenuLateral;
	private CharSequence titreActionBar;
	private String[] itemMenuLateral;
	private Etudiant etudiant;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fenetre_principale);
		titreActionBar = titreMenuLateral = getTitle();
		etudiant = (Etudiant) getIntent().getSerializableExtra("etudiant");

		if (etudiant.getAnneePromo() == 3) {
			itemMenuLateral = getResources().getStringArray(
					R.array.menuLateral3A);
		} else if (etudiant.getAnneePromo() == 4 && etudiant.isRespo()) {
			itemMenuLateral = getResources().getStringArray(
					R.array.menuLateral4AAdmin);
		} else if (etudiant.getAnneePromo() == 4) {
			itemMenuLateral = getResources().getStringArray(
					R.array.menuLateral4A);
		} else {
			Log.e("FenetrePrincipaleAcctivity", "L'etudiant n'est ni 3A ni 4A");
		}

		listViewMenuLateral = (ListView) findViewById(R.id.list_slidermenu);
		listViewMenuLateral.setOnItemClickListener(this);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.item_menu_lateral, itemMenuLateral);
		listViewMenuLateral.setAdapter(adapter);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		layoutMenuLateral = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, layoutMenuLateral,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(titreActionBar);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(titreMenuLateral);
				invalidateOptionsMenu();
			}
		};
		layoutMenuLateral.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			displayView(getString(R.string.accueil));
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		TextView bouton = (TextView) view;
		displayView(bouton.getText().toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.header, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.menu_quitter:
			quitter();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		if (getTitle().equals(getString(R.string.accueil))) {
			quitter();
		} else {
			displayView(getString(R.string.accueil));
		}
	}

	private void quitter() {
		finish();
		System.exit(0);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean menuLateralOuvert = layoutMenuLateral
				.isDrawerOpen(listViewMenuLateral);
		menu.findItem(R.id.menu_quitter).setVisible(!menuLateralOuvert);
		return super.onPrepareOptionsMenu(menu);
	}

	private void displayView(String nomFragment) {
		Fragment fragment = null;
		Bundle bundle = new Bundle();

		if (nomFragment.equals(getString(R.string.accueil))) {
			fragment = new MenuPrincipalFragment();
		} else if (nomFragment
				.equals(getString(R.string.liste_des_defis_realises))) {
			fragment = new ListeDefisRealiseFragment();
		} else if (nomFragment
				.equals(getString(R.string.liste_des_defis_a_realiser))) {
			fragment = new ListeDefisRealiseFragment();
		} else if (nomFragment.equals(getString(R.string.profil))) {
			fragment = new ProfilFragment();
		} else if (nomFragment.equals(getString(R.string.classement_des_3a))) {
			bundle.putInt("anneeClassement", 3);
			fragment = new ClassementFragment();
		} else if (nomFragment.equals(getString(R.string.classement_des_4a))) {
			bundle.putInt("anneeClassement", 4);
			fragment = new ClassementFragment();
		} else if (nomFragment.equals(getString(R.string.proposer_defi))) {
			fragment = new AjoutDefiFragment();
		} else if (nomFragment.equals(getString(R.string.ajout_respo))) {
			fragment = new AjoutAdministrateurFragment();
		} else if (nomFragment.equals(getString(R.string.valider_proposition_defis))) {
			Toast.makeText(this, "Pas implementé", Toast.LENGTH_LONG).show();
		} else if (nomFragment.equals(getString(R.string.valider_defis_realise))) {
			Toast.makeText(this, "Pas implementé", Toast.LENGTH_LONG).show();
		} else if (nomFragment.equals(getString(R.string.quitter))) {
			quitter();
		} else {
			Log.e(getClass().getName(), "Fragment introuvable");
		}

		if (fragment != null) {
			bundle.putSerializable("etudiant", etudiant);
			fragment.setArguments(bundle);

			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.replace(R.id.frame_container, fragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();

			// update selected item and title, then close the drawer
			listViewMenuLateral.setItemChecked(3, true);
			listViewMenuLateral.setSelection(4);
			setTitle(nomFragment);
			layoutMenuLateral.closeDrawer(listViewMenuLateral);
		} else {
			Log.e("FenetrePrincipaleActivity",
					"Erreur lors de la creation du fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		titreActionBar = title;
		getActionBar().setTitle(titreActionBar);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration configuration) {
		super.onConfigurationChanged(configuration);
		mDrawerToggle.onConfigurationChanged(configuration);
	}
}
