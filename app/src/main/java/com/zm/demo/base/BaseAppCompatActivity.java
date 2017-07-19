package com.zm.demo.base;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.zm.demo.base.activityresult.ActivityResultDelegate;
import com.zm.demo.base.activityresult.ActivityResultListener;
import com.zm.demo.base.activityresult.IActivityResult;

/**
 * Created by zm on 2017/7/19.
 */

public class BaseAppCompatActivity extends AppCompatActivity implements IActivityResult{

    private final ActivityResultDelegate mDelegate;

    protected BaseAppCompatActivity() {
        mDelegate = createActivityResultDelegate();
    }

    protected ActivityResultDelegate createActivityResultDelegate() {
        return new ActivityResultDelegate(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mDelegate != null && mDelegate.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearActivityResultListener();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void addActivityResultListener(ActivityResultListener activityResultListener) {
        if (mDelegate == null) {
            return;
        }
        mDelegate.addActivityResultListener(activityResultListener);
    }

    @Override
    public void removeActivityResultListener(ActivityResultListener activityResultListener) {
        if (mDelegate == null) {
            return;
        }
        mDelegate.removeActivityResultListener(activityResultListener);
    }

    @Override
    public void clearActivityResultListener() {
        if (mDelegate == null) {
            return;
        }
        mDelegate.clearActivityResultListener();
    }
}
