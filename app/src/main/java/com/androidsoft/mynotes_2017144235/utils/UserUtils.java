package com.androidsoft.mynotes_2017144235.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.androidsoft.mynotes_2017144235.LoginActivity;
import com.androidsoft.mynotes_2017144235.R;
import com.blankj.utilcode.util.RegexUtils;

public class UserUtils {

    /**
     * 验证登录用户输入合法性
     */
    public static boolean validateLogin(Context context, String phone, String password){

        /*
        两者不同在于使用的正则表达式不同
        Simple--1+10个数
        Exact--符合实际

        isMobileSimple: 简单验证手机号
         public static boolean isMobileSimple(final CharSequence input) {
         参数一：正则表达式，传入的数
        return isMatch(RegexConstants.REGEX_MOBILE_SIMPLE, input);
        }
     public static final String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";---正则表达式
     Regex of simple mobile.

        isMobileExact : 精确验证手机号
        public static boolean isMobileExact(final CharSequence input) {
        return isMobileExact(input, null);
    }

        public static boolean isMobileExact(final CharSequence input, List<String> newSegments) {
        boolean match = isMatch(RegexConstants.REGEX_MOBILE_EXACT, input);
        if (match) return true;
        if (newSegments == null) return false;
        if (input == null || input.length() != 11) return false;
        String content = input.toString();
        for (char c : content.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        for (String newSegment : newSegments) {
            if (content.startsWith(newSegment)) {
                return true;
            }
        }
        return false;
    }

        public static final String REGEX_MOBILE_EXACT  = "^((13[0-9])|(14[57])|(15[0-35-9])|(16[6])
                                                         |(17[0135-8])|(18[0-9])|(19[189]))\\d{8}$";
     * Regex of exact mobile.
     * <p>china mobile: 134(0-8), 135, 136, 137, 138, 139, 147, 150, 151, 152, 157, 158, 159,
                                                               178, 182, 183, 184, 187, 188, 198</p>
     * <p>china unicom: 130, 131, 132, 145, 155, 156, 166, 171, 175, 176, 185, 186</p>
     * <p>china telecom: 133, 153, 173, 177, 180, 181, 189, 199, 191</p>
     * <p>global star: 1349</p>
     * <p>virtual operator: 170</p>
         */
        //RegexUtils.isMobileSimple(phone);    简单的
        if (!RegexUtils.isMobileExact(phone)){
            Toast.makeText(context, "无效手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(password)){
            //判断用户输入的密码是否为空
            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    /**
     * 封装退出登录方法(方便其他activity调用)
     */
    public static void logout(Context context){

        Intent intent= new Intent(context, LoginActivity.class);

        //添加intent标志符，清理task栈(存储activity)，并且新生成一个task栈
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        //注：问题点击退出登录按钮后，过渡动画发生错误?

        context.startActivity(intent);

        /**
         * 定义Activity跳转动画
         *
         * 传入两个参数--参数1：进入时动画  参数2：退出时动画
         * 注：overridePendingTransition()在startActivity()之后调用
         */
        ((Activity)context).overridePendingTransition(R.anim.open_enter, R.anim.open_exit);

    }
}
