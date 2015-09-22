package ceri.m2.projet.coeptus;

import java.util.ArrayList;

import android.app.Application;

public class Global extends Application {
	ArrayList<Long> times;
	ArrayList<String> rlist;
	public ArrayList<Long> getTimes() {
		return times;
	}
	public void setTimes(ArrayList<Long> times) {
		this.times = times;
	}
	public ArrayList<String> getRlist() {
		return rlist;
	}
	public void setRlist(ArrayList<String> rlist) {
		this.rlist = rlist;
	}

}
