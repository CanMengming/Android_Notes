package com.androidsoft.mynotes_2017144235;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidsoft.mynotes_2017144235.utils.UserUtils;

public class MeActivity extends BaseActivity {

    private TextView tv_userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        /**
         * 显示当前用户手机号
         */
        tv_userName= findViewById(R.id.tv_userName);
        SharedPreferences sp= this.getSharedPreferences("login_info", Context.MODE_PRIVATE);
        String phone= sp.getString("userPhone", "000000");
        tv_userName.setText("当前用户: "+ phone);

        initView();
    }

    private void initView(){
        initNavBar(true, "个人中心", false);
    }

    /**
     *  修改密码点击事件
     * @param v
     */
    public void changePassword(View v){

        Intent intent= new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    /**
     * 退出登录时间
     * @param v
     */
    public void onLogoutClick(View v){

        SharedPreferences sp= this.getSharedPreferences("login_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.clear();          //清空login_info中的信息
        editor.commit();

        //调用UserUtils中的logout()方法---退出登录方法
        UserUtils.logout(this);

        //注：用户从个人中心退出至LoginActivity页面，再点手机退出按钮，则退出整个应用
    }
}
