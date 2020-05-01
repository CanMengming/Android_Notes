package com.androidsoft.mynotes_2017144235;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.androidsoft.mynotes_2017144235.dao.NoteDao;
import com.androidsoft.mynotes_2017144235.db.NoteDatabase;
import com.androidsoft.mynotes_2017144235.pojo.User;
import com.androidsoft.mynotes_2017144235.utils.UserUtils;

import Views.InputView;
/**
 * NavigationBar--顶部风格
 */

public class LoginActivity extends BaseActivity {

    private InputView mInputPhone, mInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //创建一个名--note.db的数据库(数据库中有 user, notes表)
        NoteDatabase noteDB= new NoteDatabase(LoginActivity.this);

        initView();

        /**
         * 1.需要对用户是否登录进行简单判断，用户如果登录，那么直接跳转页面
         * 2.如果没有登录那么当前页面作为登录页面
         */
        SharedPreferences sp= this.getSharedPreferences("login_info", Context.MODE_PRIVATE);
        long userId= sp.getLong("userId", -1);
        if (-1== userId){
            System.out.println("没有该用户登录信息!");
        }else {
            System.out.println("用户已登录!");
            //如果存在用户登录信息，直接跳转至对应的记事本页面
            Intent intent= new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("userId", userId);       //将SharedPreferences的编号传入到下一个Activity
            startActivity(intent);
        }

    }

    /**
     * 顶部NavgationBar显示
     * 初始化View--调用BaseActivity中创建的initNavBar()方法，
     *                            用于显示NavigationBar的不同页面的显示
     */
    private void initView(){
        //登录页面   isShowBack--false登录页面不会设置后退图片   title--登录页面文字显示
        //           isShowMe--false不会展示个人中心点击图片
        initNavBar(false, "登录", false);

        mInputPhone= fd(R.id.input_phone);
        mInputPassword= fd(R.id.input_password);
    }

    /**
     * 跳转注册页面点击事件
     * @param v
     */
    public void onRegist(View v){

        Intent intent= new Intent(this, RegistActivity.class);
        startActivity(intent);
    }

    /**
     * 登录按钮点击事件
     */
    public void onLoginClick(View v){
        /**
         *1.当用户点击登录按钮时，验证用户输入电话、密码的合法性，通过跳转至应用主页
         *2.根据用户名和密码查询用户是否存在，然后得到用户完整信息(用户编号)
         */

        String phone= mInputPhone.getInputStr();
        String password= mInputPassword.getInputStr();
        System.out.println("用户输入的信息: "+phone+ ":"+password);


        if (!UserUtils.validateLogin(this, phone, password)){
            return;
        }

        User user= null;
        try {
            NoteDao noteDao= new NoteDao(this);
            user= noteDao.findUserByUserId(phone);
            if (user== null){
                Toast.makeText(LoginActivity.this, "该用户不存!", Toast.LENGTH_SHORT).show();

            }else {
                String dbPassword= user.getUserPassword();
                if (dbPassword.equals(password)){
                    /**
                     * 1.第一次登录，如果登录成功，那么则保存登录信息
                     */
                    SharedPreferences sharedPreferences= this.getSharedPreferences("login_info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    //用户信息写入editor
                    editor.putLong("userId", user.getUserId());
                    editor.putString("userPhone", user.getUserPhone());
                    //editor.putString("userPassword", user.getUserPassword());  不安全
                    editor.commit();

                    Intent intent= new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userPhone", user.getUserPhone());
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    startActivity(intent);      //验证通过跳转至MainActivity
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
