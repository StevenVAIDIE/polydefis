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

import com.polydefisv3.R;
import com.polydefisv4.classement.ClassementFragment;
import com.polydefisv4.listeDefis.ListeDefisRealiseFragment;
import com.polydefisv4.menu_principal.MenuPrincipalFragment;
import com.polydefisv4.metier.Etudiant;
import com.polydefisv4.profil.ProfilFragment;

public class FenetrePrincipaleActivity extends Activity implements OnItemClickListener {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] navMenuTitles;	
	private Etudiant etudiant;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fenetre_principale);

		mTitle = mDrawerTitle = getTitle();
		
		etudiant = (Etudiant) getIntent().getSerializableExtra("etudiant");
		
		// load slide menu items
		if (etudiant.getAnnee() == 3) {
			navMenuTitles = getResources().getStringArray(R.array.menuLateral3A);
		} else if (etudiant.getAnnee() == 4 && etudiant.isAdmin()) {
			navMenuTitles = getResources().getStringArray(R.array.menuLateral4AAdmin);
		} else if (etudiant.getAnnee() == 4) {
			navMenuTitles = getResources().getStringArray(R.array.menuLateral4A);
		} else {
			Log.e("FenetrePrincipaleAcctivity", "L'etudiant n'est ni 3A ni 4A");
		}
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		// Recycle the typed array
		mDrawerList.setOnItemClickListener(this);

	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.menu_lateral_list_item, navMenuTitles);
		mDrawerList.setAdapter(adapter);
	
		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			displayView(0);
		}
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		displayView(position);
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
	
	private void quitter() {
		finish();
		System.exit(0);
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.menu_quitter).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new MenuPrincipalFragment();
			break;
		case 1:
			fragment = new ListeDefisRealiseFragment();
			break;
		case 2:
			fragment = new ListeDefisRealiseFragment();
			break;
		case 3:
			fragment = new ProfilFragment();
			break;
		case 4:
			fragment = new ClassementFragment();
			break;
		case 5:
			fragment = new ClassementFragment();
			break;

		case 6:
			quitter();

		default:
			break;
		}

		if (fragment != null) {
			Bundle bundle = new Bundle();
			bundle.putSerializable("etudiant", etudiant);

			fragment.setArguments(bundle);
			
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.frame_container, fragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
			
			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
