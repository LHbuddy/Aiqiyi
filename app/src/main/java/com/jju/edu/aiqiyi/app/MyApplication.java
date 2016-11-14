package com.jju.edu.aiqiyi.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class MyApplication extends Application {
	private List<Activity> activities;

	@Override
	public void onCreate() {
		super.onCreate();
		activities = new ArrayList<Activity>();
	}

	public void addActivity(Activity activity) {
		if (!activities.contains(activity)) {
			activities.add(activity);
		}
	}

	public void removeAllActivity() {
		for (Activity activity : activities) {
			activity.finish();
			activities.remove(activity);
		}
	}
}
