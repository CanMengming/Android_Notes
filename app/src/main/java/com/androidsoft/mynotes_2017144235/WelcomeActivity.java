package com.androidsoft.mynotes_2017144235;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 功能1：实现3秒钟后跳转至MainActivity
 *      1、延迟三秒  2、跳转页面
 */

public class WelcomeActivity extends BaseActivity {

    private Timer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();
    }

    /**
     *初始化---规定时间后实现页面跳转
     */
    private void init(){
        mTimer= new Timer();
        /**
         * 规定时间后实现页面跳转
         *
         * schedule()方法---该方法接受两个参数
         * 1、回调方法new TimerTask(){}
         * 2、延迟时间---单位为：毫秒(表示经过该延迟时间后会调用TimerTask(){}中方法
         *                            ，并且TimeTasker(){}被调用时是在主线程)
         */
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e("WelcomeActivity", "当前线程为:"+ Thread.currentThread());
                //toMainActivity();
                toLoginActivity();
            }
        }, 3000);
    }

    /**
     * 跳转至MainActivity
     */
    private void toMainActivity(){
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * 跳转至LoginActivity
     */
    private void toLoginActivity(){
        Intent intent= new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}
