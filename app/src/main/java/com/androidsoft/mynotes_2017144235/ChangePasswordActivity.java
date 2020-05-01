package com.androidsoft.mynotes_2017144235;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.androidsoft.mynotes_2017144235.dao.NoteDao;
import com.androidsoft.mynotes_2017144235.pojo.User;
import com.androidsoft.mynotes_2017144235.utils.UserUtils;

import Views.InputView;


public class ChangePasswordActivity extends BaseActivity {

    private InputView input_old_password, input_new_password, input_new_password_check;
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initView();
        initComponent();
    }

    private void initComponent(){

        input_old_password= fd(R.id.input_old_password);
        input_new_password= fd(R.id.input_new_password);
        input_new_password_check= fd(R.id.input_new_password_check);
    }

    private void initView(){

        initNavBar(true, "修改密码", false);
    }

    /**
     * 修改密码确认
     * @param v
     */
    public void changePasswordCheck(View v){

        User user= new User();
        User newUser= null;
        boolean updatePasswordResult= false;
        String userInputOldPws= input_old_password.getInputStr();
        String userInputNewPwd= input_new_password.getInputStr();
        String userInputNewPwdCheck= input_new_password_check.getInputStr();

        /**
         * 获取当前用户手机号用于判断旧密码是否正确
         */
        SharedPreferences sp= this.getSharedPreferences("login_info", Context.MODE_PRIVATE);
        String phone= sp.getString("userPhone", "000000");
        String name= phone;

        try {
            noteDao= new NoteDao(ChangePasswordActivity.this);
            user= noteDao.findUserByUserId(phone);
            String userPassword= user.getUserPassword();

            if (userInputOldPws.equals(userPassword)){
                if (userInputNewPwd.equals(userInputNewPwdCheck)&& userInputNewPwd!= null&& userInputNewPwdCheck!= null){

                    newUser= new User(phone, name, userInputNewPwd, 1, 1);
                    updatePasswordResult= noteDao.updateUserByName(newUser);
                    if (updatePasswordResult== true){
                        Toast.makeText(ChangePasswordActivity.this, "跟新密码成功!", Toast.LENGTH_SHORT).show();

                        SharedPreferences.Editor editor= sp.edit();
                        editor.clear();          //清空login_info中的信息
                        editor.commit();

                        //调用UserUtils中的logout()方法---退出登录方法
                        UserUtils.logout(this);

                    }else {
                        Toast.makeText(this, "跟新密码失败!", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(ChangePasswordActivity.this, "密码不一致!", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(ChangePasswordActivity.this, "原密码输入错误", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
