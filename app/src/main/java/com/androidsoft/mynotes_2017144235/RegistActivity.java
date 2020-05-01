package com.androidsoft.mynotes_2017144235;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.androidsoft.mynotes_2017144235.dao.NoteDao;
import com.androidsoft.mynotes_2017144235.pojo.User;
import com.androidsoft.mynotes_2017144235.utils.UserUtils;

import Views.InputView;

public class RegistActivity extends BaseActivity {

    private InputView input_phone, input_password, input_check_password;
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        initView();
        init();
    }

    /**
     * 初始化View--NavigationBar显示(即页面上面)
     */
    private void initView(){
        initNavBar(true, "注册", false);
    }

    private void init(){

        input_phone= fd(R.id.input_phone);
        input_password= fd(R.id.input_password);
        input_check_password= fd(R.id.input_check_password);
    }

    /**
     * 注册按钮点击事件
     * @param v
     */
    public void onRegistClick(View v) {

        String phone = input_phone.getInputStr();
        String userName = phone;
        String userPassword = input_password.getInputStr();
        String userCheckPassword = input_check_password.getInputStr();

        //判读输入手机号是否合法
        if (!UserUtils.validateLogin(this, phone, userPassword)) {
            return;
        }

        User registResult = null;
        try {
            noteDao = new NoteDao(this);
            registResult = noteDao.findUserByUserId(phone);
            //判断该用户是否存在
            if (registResult == null) {

                //判断用户两次输入密码是否相同
                if (userPassword.equals(userCheckPassword)) {
                    User user = new User(phone, userName, userPassword, 0, 0);
                        boolean addResult = noteDao.addUser(user);

                        //判断注册是否成功
                        if (addResult == true) {

                            System.out.println("注册成功--" + user.toString());
                            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
                        }


                } else {
                    Toast.makeText(RegistActivity.this, "两次输入密码不一致",
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(RegistActivity.this, "该手机号已存在!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
