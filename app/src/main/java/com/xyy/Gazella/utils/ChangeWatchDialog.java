package com.xyy.Gazella.utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;

/**
 * Created by Administrator on 2016/10/24.
 */

public class ChangeWatchDialog extends BaseActivity implements View.OnClickListener{
    private TextView cancel;
    private TextView confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_watch_dialog);

        cancel= (TextView) findViewById(R.id.cancel);
        confirm= (TextView) findViewById(R.id.confirm);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.confirm:
                Intent intent = new Intent(this,ChangeWatchList.class);
                startActivity(intent);
                finish();
                break;
        }
    }


}
