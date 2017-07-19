package com.zm.demo.base.activityresult;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2017/7/19.
 */

public class ActivityResultDelegate implements IActivityResult{

    private final @Nullable WeakReference<Activity> activity;

    List<ActivityResultListener> activityResultListeners;

    public ActivityResultDelegate(Activity activity) {
        this.activity = new WeakReference(activity);
    }

    @Override
    public Activity getActivity() {
        return activity.get();
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {

        if (activityResultListeners == null || activityResultListeners.isEmpty()) {
            return false;
        }

        for (ActivityResultListener activityResultListener : activityResultListeners) {
            if (activityResultListener == null) {
                continue;
            }
            if (activityResultListener.onActivityResult(requestCode, resultCode, data)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addActivityResultListener(ActivityResultListener activityResultListener) {
        if (activityResultListener == null) {
            return;
        }
        if (activityResultListeners == null) {
            activityResultListeners = new ArrayList<>();
        }
        activityResultListeners.add(activityResultListener);
    }

    @Override
    public void removeActivityResultListener(ActivityResultListener activityResultListener) {
        if (activityResultListener == null) {
            return;
        }
        if (activityResultListeners != null && activityResultListeners.size() != 0) {
            activityResultListeners.remove(activityResultListener);
        }
    }

    @Override
    public void clearActivityResultListener() {
        if (activityResultListeners == null) {
            return;
        }
        activityResultListeners.clear();
    }
}
