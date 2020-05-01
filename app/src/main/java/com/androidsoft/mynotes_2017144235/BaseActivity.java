package com.androidsoft.mynotes_2017144235;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private ImageView mIvBack, mIvMe;
    private TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setNightMode();
    }

    public boolean isNightMode(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        return sharedPreferences.getBoolean("nightMode", false);

    }
    public void setNightMode(){
        setTheme(R.style.DayTheme);

    }

    /**
     * findViewById()
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T fd(@IdRes int id){
        //接受一个资源文件id，然后返回T，T是View的子类
        return findViewById(id);
    }

    /**
     * 初始化NavigationBar
     * @param isShowBack
     * @param title
     * @param isShowMe
     */
    //isShowBack--back回退图片是否显示   title--标题   isShowMe--个人中心图片是否显示
    protected void initNavBar(boolean isShowBack, String title, boolean isShowMe){

        mIvBack= fd(R.id.iv_back);    //初始化mIvBack
        mTvTitle= fd(R.id.tv_title);
        mIvMe= fd(R.id.iv_me);

        //isShowBack为true，View.VISIBLE显示，否则View.GONE隐藏
        mIvBack.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        mIvMe.setVisibility(isShowMe ? View.VISIBLE : View.GONE);
        mTvTitle.setText(title);         //TextView---mTvTitle显示传入的title

        //mIvBack设置点击事件(当被点击时执行后退操作)
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //个人中心按钮点击事件(跳转至个人中心)
        mIvMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, MeActivity.class));
            }
        });

    }
}
