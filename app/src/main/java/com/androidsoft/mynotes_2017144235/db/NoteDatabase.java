package com.androidsoft.mynotes_2017144235.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDatabase extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "notes";
    public static final String CONTENT = "content";
    public static final String NOTEID = "noteId";
    public static final String USERID= "userId";
    public static final String TIME = "time";

    public static final String TABLEUSER_NAME = "user";
    public static final String USERPHONE = "userPhone";
    public static final String USERNAME = "userName";
    public static final String USERPASSWORD= "userPassword";
    public static final String USERSTATUS = "userStatus";
    public static final String USERROLE = "userRole";


    public NoteDatabase(Context context){
        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建用户表
        db.execSQL("CREATE TABLE " + TABLEUSER_NAME
                + "("
                + USERID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USERPHONE + " TEXT NOT NULL,"
                + USERNAME + " TEXT NOT NULL,"
                + USERPASSWORD + " TEXT NOT NULL,"
                + USERSTATUS + " INTEGER DEFAULT 1,"
                + USERROLE+ " INTEGER DEFAULT 1)"
        );
        db.execSQL("INSERT INTO user VALUES(001, '13773578068', '残梦', '1234', 1, 1)");

        //创建笔记本表
        db.execSQL("CREATE TABLE "+ TABLE_NAME
                + "("
                + NOTEID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USERPHONE + " Text NOT NULL,"
                + CONTENT + " TEXT NOT NULL,"
                + TIME + " TEXT NOT NULL)"
        );
        db.execSQL("INSERT INTO notes VALUES(1, '13773578068', '这是残梦的笔记啊！', '2020-05-01 13:31:40')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
