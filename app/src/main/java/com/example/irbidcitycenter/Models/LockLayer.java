package com.example.irbidcitycenter.Models;

import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

public class LockLayer {
    private Activity mActivty;
    private WindowManager mWindowManager;
    private View mLockView;
    private WindowManager.LayoutParams mLockViewLayoutParams;
    private static LockLayer mLockLayer;
    private boolean isLocked;

    public static LockLayer getInstance(Activity act){
        if(mLockLayer == null){
            mLockLayer = new LockLayer(act);
        }
        return mLockLayer;
    }

    private LockLayer(Activity act) {
        mActivty = act;
        init();
    }

    private void init(){
        isLocked = false;
        mWindowManager = mActivty.getWindowManager();
        mLockViewLayoutParams = new WindowManager.LayoutParams();
        mLockViewLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mLockViewLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        mLockViewLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
    }
    public void lock() {
        if(mLockView!=null){
            mWindowManager.addView(mLockView, mLockViewLayoutParams);
        }
        isLocked = true;
    }
    public void unlock() {
        if(mWindowManager!=null && isLocked){
            mWindowManager.removeView(mLockView);
        }
        isLocked = false;
    }
    public void setLockView(View v){
        mLockView = v;
    }
}