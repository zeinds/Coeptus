package ceri.m2.projet.coeptus;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.webkit.WebChromeClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Accelerometer extends ActionBarActivity {
	ArrayList<String> rlist;
	Button buttonp;
	private SensorManager mSensorManager;
	private TextView mTxtViewX;
	Button bouttonPlay;
	ToggleButton tButton;
	ToggleButton tButton2;
	String direction = "marcheAvant";
	private Socket client;
	private PrintWriter printwriter;
	private String messsage;
	String sence = "test";
	String etatRecord = "";
	long timeCom;
	long timeDebut;
	long timeFin;
	boolean pause;
	long time;
	DoRead a;
	WebChromeClient e;
	ArrayList<Long> times;
	private static final String TAG = "MjpegActivity";
	SurfaceView s;
	private MjpegView mv;
	public String url;
	boolean testcontinu = true;
	private final SensorEventListener mSensorListener = new SensorEventListener() {
		// action quand le capteur bouge
		public void onSensorChanged(SensorEvent se) {
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			// met à jour la position dans mes TextView
			Position(x, y, z);
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accelerometer);
		// mon objet mSensorManager de type SensorManager qui prend la main
		mSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
		// Sensor. TYPE_ACCELEROMETER défini le type de capteur
		// SensorManager.SENSOR_DELAY_NORMAL le delai de rafraichissement des
		// informations
		rlist = new ArrayList<String>();
		times = new ArrayList<Long>();
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		// les objet de la fenetre xml
		timeDebut = System.currentTimeMillis();
		mTxtViewX = (TextView) findViewById(R.id.vText1);
		RotateAnimation ranim = (RotateAnimation) AnimationUtils.loadAnimation(
				this, R.anim.myanim);
		ranim.setFillAfter(true);
		mTxtViewX.setAnimation(ranim);
		tButton = (ToggleButton) findViewById(R.id.toggleButton1);
		tButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				direction = "marcheArrière";
				if (isChecked) {
				} else {
					direction = "marcheAvant";
				}
			}
		});
		tButton2 = (ToggleButton) findViewById(R.id.toggleButton2);
		tButton2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				etatRecord = "on";
				times.add(new Long(System.currentTimeMillis()));
				if (isChecked) {

				} else {

					timeFin = System.currentTimeMillis();
					etatRecord = "off";
					final Global globalVariable = (Global) getApplicationContext();
					globalVariable.setRlist(rlist);
					globalVariable.setTimes(times);
				}
			}

		});

		// sample public cam
		String URL = "http://192.168.42.1:9000/stream/video.mjpeg";
		mv = new MjpegView(this);
		FrameLayout layout = (FrameLayout) findViewById(R.id.l1);
		mv = new MjpegView(this);
		layout.addView(mv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.accelerometer, menu);
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

	public void Position(float iX, float iY, float iZ) {
		int vitesse = 0;
		if (direction.compareTo("marcheAvant") == 0) {
			if (iZ >= 4 && iY > (-0.3) && iY < 2.3) {
				sence = "avence";
				mTxtViewX.setText("avance  " + iZ);
				// get the text message on the text field
				if (iZ >= 4 && iZ <= 5 && sence.compareTo("avance1") != 0) {
					vitesse = 20;
					messsage = "avance," + vitesse;
					SendMessage sendMessageTask = new SendMessage();
					sendMessageTask.execute();
					sence = "avance1";
					if (etatRecord.compareTo("on") == 0) {
						times.add(new Long(System.currentTimeMillis()));
						rlist.add(messsage);
					}
				} else if (iZ > 5 && iZ <= 6 && sence.compareTo("avance2") != 0) {
					vitesse = 40;
					messsage = "avance," + vitesse;
					SendMessage sendMessageTask = new SendMessage();
					sendMessageTask.execute();
					sence = "avance2";
					if (etatRecord.compareTo("on") == 0) {
						times.add(new Long(System.currentTimeMillis()));
						rlist.add(messsage);
					}
				} else if (iZ > 6 && iZ <= 7 && sence.compareTo("avance3") != 0) {
					vitesse = 60;
					messsage = "avance," + vitesse;
					SendMessage sendMessageTask = new SendMessage();
					sendMessageTask.execute();
					sence = "avance3";
					if (etatRecord.compareTo("on") == 0) {
						times.add(new Long(System.currentTimeMillis()));
						rlist.add(messsage);
					}
				} else if (iZ > 7 && iZ <= 8 && sence != "avance4") {
					vitesse = 90;
					messsage = "avance," + vitesse;
					SendMessage sendMessageTask = new SendMessage();
					sendMessageTask.execute();
					sence = "avance4";
					if (etatRecord.compareTo("on") == 0) {
						times.add(new Long(System.currentTimeMillis()));
						rlist.add(messsage);
					}
				} else if (iZ > 8 && sence != "avance5") {
					vitesse = 99;
					messsage = "avance," + vitesse;
					SendMessage sendMessageTask = new SendMessage();
					sendMessageTask.execute();
					sence = "avance5";
					if (etatRecord.compareTo("on") == 0) {
						times.add(new Long(System.currentTimeMillis()));
						rlist.add(messsage);
					}
				}
			} else if (iZ > 7.7 && iY < (-0.3)) {
				mTxtViewX.setText("avance  gauche"); // avance
				messsage = "avance-gauche,100";
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
				sence = "gauche1";
				if (etatRecord.compareTo("on") == 0) {
					times.add(new Long(System.currentTimeMillis()));
					rlist.add(messsage);
				}
			} else if (iZ > 7.7 && iY > (0.3)) {
				messsage = "avance-droite,100";
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
				mTxtViewX.setText("avance  droite"); // avance
				sence = "droite1";
				if (etatRecord.compareTo("on") == 0) {
					times.add(new Long(System.currentTimeMillis()));
					rlist.add(messsage);
				}
			} else {
				if (sence != "arret1") {
					messsage = "arret";
					SendMessage sendMessageTask = new SendMessage();
					sendMessageTask.execute();
					sence = "arret1";
					if (etatRecord.compareTo("on") == 0) {
						times.add(new Long(System.currentTimeMillis()));
						rlist.add(messsage);
					}
				}
				mTxtViewX.setText("stop");
			}
		} else {
			sence = "test";
			if (iZ > 5 && iZ < 10 && iY > (-0.3) && iY < 2.3) {
				messsage = "recule,90";
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
				if (etatRecord.compareTo("on") == 0) {
					times.add(new Long(System.currentTimeMillis()));
					rlist.add(messsage);
				}
				mTxtViewX.setText("recule");
			}

			else if (iZ > 5 && iZ < 10 && iY < (-0.3)) {
				mTxtViewX.setText("recule gauche"); // avance
				messsage = "recule-gauche,100";
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
				sence = "gauche2";
				if (etatRecord.compareTo("on") == 0) {
					times.add(new Long(System.currentTimeMillis()));
					rlist.add(messsage);
				}

			}// avance
			else if (iZ > 5 && iZ < 10 && iY > (2.3)) {
				messsage = "recule-droite,100";
				SendMessage sendMessageTask = new SendMessage();
				sendMessageTask.execute();
				mTxtViewX.setText("recule droite"); // avance
				sence = "droite1";
				if (etatRecord.compareTo("on") == 0) {
					times.add(new Long(System.currentTimeMillis()));
					rlist.add(messsage);
				}
			} else {
				if (sence != "arret2") {
					messsage = "arret";
					SendMessage sendMessageTask = new SendMessage();
					sendMessageTask.execute();
					sence = "arret2";
					if (etatRecord.compareTo("on") == 0) {
						times.add(new Long(System.currentTimeMillis()));
						rlist.add(messsage);
					}
				}
				mTxtViewX.setText("stop");
			}
		}

	}

	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStop() {

		mSensorManager.unregisterListener(mSensorListener);
		messsage = "arret";
		SendMessage sendMessageTask = new SendMessage();
		sendMessageTask.execute();

		super.onStop();
	}

	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mSensorListener);
		messsage = "arret";
		SendMessage sendMessageTask = new SendMessage();
		sendMessageTask.execute();

		pause = true;

		super.onPause();
	}

	// cette classe s'occupe d'envoyer les ordres au serveur
	private class SendMessage extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {

				client = new Socket("192.168.42.1", 9001); // connect to the
															// server
				printwriter = new PrintWriter(client.getOutputStream(), true);
				printwriter.write(messsage); // write the message to output
												// stream
				pause = false;
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

	// pour lancer la cam
	public class DoRead extends AsyncTask<String, Void, MjpegInputStream> {

		protected MjpegInputStream doInBackground(String... url) {
			// TODO: if camera has authentication deal with it and don't just
			// not work

			HttpResponse res = null;
			DefaultHttpClient httpclient = new DefaultHttpClient();
			Log.d(TAG, "1. Sending http request");
			try {
				res = httpclient.execute(new HttpGet(URI.create(url[0])));
				Log.d(TAG, "2. Request finished, status = "
						+ res.getStatusLine().getStatusCode());
				if (isCancelled()) {
					// Notify your activity that you had canceled the task
					while (true) {
						return (null); // don't forget to terminate this method
					}
				}

				if (res.getStatusLine().getStatusCode() == 401) {
					// You must turn off camera User Access Control before this
					// will work
					return null;
				}
				return new MjpegInputStream(res.getEntity().getContent());
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.d(TAG, "Request failed-ClientProtocolException", e);
				// Error connecting to camera
			} catch (IOException e) {
				e.printStackTrace();
				Log.d(TAG, "Request failed-IOException", e);
				// Error connecting to camera
			}

			return null;
		}

		protected void onPostExecute(MjpegInputStream result) {
			mv.setSource(result);
			mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
			mv.showFps(true);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();

		}
	}
}
