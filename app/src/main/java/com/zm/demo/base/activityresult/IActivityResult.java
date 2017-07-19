package com.zm.demo.base.activityresult;

import android.app.Activity;

/**
 * Created by zm on 2017/7/19.
 */

public interface IActivityResult {

    Activity getActivity();

    void addActivityResultListener(ActivityResultListener activityResultListener);

    void removeActivityResultListener(ActivityResultListener activityResultListener);

    void clearActivityResultListener();
}
