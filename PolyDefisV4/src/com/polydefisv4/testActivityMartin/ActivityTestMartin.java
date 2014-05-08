package com.polydefisv4.testActivityMartin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.polydefisv3.R;
import com.polydefisv4.bdd.SQLManager;

public class ActivityTestMartin extends Activity {

	Button bouton = null;
	TextView tv = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activity_test_martin);

		
		final SQLManager bdd = new SQLManager(this);
		//Connexion connexion = new Connexion("e111111s","123");
		//final Intent i = new Intent(this, TestActivity.class);
		
		bouton = (Button) findViewById(R.id.button1);
		bouton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				EditText et1 = (EditText) findViewById(R.id.editText1);
				String id = et1.getText().toString();
				EditText et2 = (EditText) findViewById(R.id.editText2);
				String pw = et2.getText().toString();
				
				//Toast.makeText(MainActivity.this, id + " " + pw, Toast.LENGTH_SHORT).show();
				boolean connexion = bdd.bonMdp(id, pw);
				if(connexion == true)
				{
					Toast.makeText(ActivityTestMartin.this,"Connexion réussie !", Toast.LENGTH_LONG).show();
					//startActivity(i);

				}
				else
				{
					Toast.makeText(ActivityTestMartin.this,"Mauvais login !", Toast.LENGTH_LONG).show();
				}
			}	
		});
	}


}
