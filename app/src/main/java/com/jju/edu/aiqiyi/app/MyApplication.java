package com.jju.edu.aiqiyi.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

public class MyApplication extends Application {
    private List<Activity> activities;

    @Override
    public void onCreate() {
        super.onCreate();
        activities = new ArrayList<Activity>();
        UMShareAPI.get(this);
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

    {
        PlatformConfig.setQQZone("1105837036", "ve9oxiEyGCYZ2Htr");
    }
}
