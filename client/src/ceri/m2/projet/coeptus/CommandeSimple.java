package ceri.m2.projet.coeptus;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CommandeSimple extends ActionBarActivity implements
		OnClickListener {
	private Socket client;
	private PrintWriter printwriter;
	String messsage = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commande_simple);
		Button button1 = (Button) findViewById(R.id.button1); // Récupération de
																// l'instance
																// bouton 1
		button1.setOnClickListener((OnClickListener) this); // Positionnons un
															// listener sur ce
															// bouton

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.commande_simple, menu);
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

	public void onClick(View view) {
		if (view.getId() == R.id.button1) {
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
					"donnez un ordre à Coeptus");
			startActivityForResult(intent, 1);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			TextView speechText = (TextView) findViewById(R.id.speechText);
			String result = matches.get(0).toString();
			speechText.setText(result);
			String[] sp = result.split(" ");
			if (filtrer(sp[0]).compareTo("avance") == 0) {
				try {
					int i = Integer.parseInt(sp[1]);
					messsage = "avance," + sp[1];
				} catch (Exception e) {
					messsage = "avance,50";
				}

				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
			} else if (sp[0].compareTo("stop") == 0) {
				messsage = "arret";
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
			} else if (sp[0].compareTo("gauche") == 0) {
				messsage = "avance-gauche,100";
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
			} else if (sp[0].compareTo("droite") == 0) {
				messsage = "avance-droite,100";
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
			} else if (sp[0].compareTo("recul") == 0) {
				messsage = "recule,50";
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public String filtrer(String result) {
		if (result.compareTo("orange") == 0
				|| result.compareTo("provence") == 0
				|| result.compareTo("avant") == 0
				|| result.compareTo("annonce") == 0
				|| result.compareTo("entre") == 0
				|| result.compareTo("ambiance") == 0)
			return "avance";
		else
			return result;

	}

	private class SendMessage extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {

				client = new Socket("192.168.42.1", 9001); // connect to the
															// server
				printwriter = new PrintWriter(client.getOutputStream(), true);
				printwriter.write(messsage); // write the message to output
												// stream

				printwriter.flush();
				printwriter.close();
				client.close(); // closing the connection

			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
