package ceri.m2.projet.coeptus;

import ceri.m2.projet.coeptus.R;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements OnClickListener {
Button button1;
Button button2;
Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1=(Button)findViewById(R.id.record); // Récupération de l'instance bouton 1
        button1.setOnClickListener((OnClickListener) this);
        button2=(Button)findViewById(R.id.Button01); // Récupération de l'instance bouton 1
        button2.setOnClickListener((OnClickListener) this);
        button3=(Button)findViewById(R.id.button2); // Récupération de l'instance bouton 1
        button3.setOnClickListener((OnClickListener) this);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0 == button1)
		{ Intent intentMyAccount = new Intent(getApplicationContext(), CommandeSimple.class);
	        startActivity(intentMyAccount);
	
		}else if(arg0 == button2)
		{
			Intent intentMyAccount = new Intent(getApplicationContext(), Accelerometer.class);
	        startActivity(intentMyAccount);
		}else if(arg0 == button3)
		{
			Intent intentMyAccount = new Intent(getApplicationContext(), ReplayPath.class);
	        startActivity(intentMyAccount);
		}
	}
}
