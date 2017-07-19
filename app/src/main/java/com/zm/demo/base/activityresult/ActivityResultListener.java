package com.zm.demo.base.activityresult;

import android.content.Intent;

/**
 * Created by zm on 2017/7/19.
 */

public interface ActivityResultListener {

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     * @return interrupt ? true -> interrupt  false -> super.onActivityResult(requestCode, resultCode, data)
     */
    boolean onActivityResult(int requestCode, int resultCode, Intent data);
}
