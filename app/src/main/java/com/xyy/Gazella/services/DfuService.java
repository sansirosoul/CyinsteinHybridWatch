package com.xyy.Gazella.services;

import android.app.Activity;

import no.nordicsemi.android.dfu.DfuBaseService;


/**
 * Created by Administrator on 2016/11/16.
 */

public class DfuService extends DfuBaseService {
    @Override
    protected Class<? extends Activity> getNotificationTarget() {
        return null;
    }
}
