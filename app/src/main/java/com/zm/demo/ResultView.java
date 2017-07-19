package com.zm.demo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zm.demo.base.BaseAppCompatActivity;
import com.zm.demo.base.activityresult.ActivityResultListener;

/**
 * Created by zm on 2017/7/19.
 */

public class ResultView extends LinearLayout implements ActivityResultListener{

    private final static int REQUEST_CODE = 1;

    private Context mContext;

    private Button btnStart;
    private TextView tvResult;

    public ResultView(Context context) {
        this(context, null);
    }

    public ResultView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResultView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        init();
    }

    private void init(){
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.view_result, this, true);

        initView(rootView);
        initEvent();
    }

    private void initView(View rootView) {
        btnStart = (Button) rootView.findViewById(R.id.btn_start);
        tvResult = (TextView) rootView.findViewById(R.id.tv_result);
    }

    private void initEvent() {
        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 不要忘记继承BaseAppCompatActivity额
                if (mContext instanceof BaseAppCompatActivity){
                    TestResultActivity.startActivityForResult((BaseAppCompatActivity) mContext, REQUEST_CODE);
                    ((BaseAppCompatActivity)mContext).addActivityResultListener(ResultView.this);
                }
            }
        });
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            tvResult.setText("我是ResultView能收到Activity的onActivityResult方法\ntrue，Activity的onActivityResult将不再收到回调\nfalse,相反");
            return true; // true拦截后Activity的onActivityResult将不再收到回调
        }
        return false;
    }
}
