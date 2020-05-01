package com.androidsoft.mynotes_2017144235.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.androidsoft.mynotes_2017144235.db.NoteDatabase;
import com.androidsoft.mynotes_2017144235.pojo.Note;
import com.androidsoft.mynotes_2017144235.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class NoteDao {
    private SQLiteDatabase db;
    private NoteDatabase noteDatabase;

    private static final String[] columns = {
            NoteDatabase.NOTEID,
            NoteDatabase.USERID,
            NoteDatabase.CONTENT,
            NoteDatabase.TIME,
    };

    public NoteDao(Context context){
        noteDatabase = new NoteDatabase(context);
    }

    //获取可写入数据库
    public void open(){
        db = noteDatabase.getWritableDatabase();
    }

    //关闭数据库
    public void close(){
        noteDatabase.close();
    }

    //添加笔记
    public boolean addNote(Note note){
        open();
        String sql= "INSERT INTO notes(userPhone, content, time)" +
                    "VALUES(?, ?, ?)";
        try {
            db.execSQL(sql, new Object[]{note.getUserPhone(), note.getContent(), note.getTime()});
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally{
            close();
        }

        return true;

    }

    //获取用户全部笔记
    public List<Note> getAllNotes(String userPhone){

        db= noteDatabase.getReadableDatabase();           //获取可读数据库
        String sql= "SELECT * FROM notes WHERE userPhone= ?";
        Cursor cursor= db.rawQuery(sql, new String[]{userPhone});
        /*
        如果需要多个条件写法如下：(name与password需要自己定义)
        String sql= "select * from user where name= ? and password= ?";
        Cursor cursor= db.rawQuery(sql, new String[]{name, password});
         */
        List<Note> notes= new ArrayList<>();     //需要初始化为null否则返回值会报错
        //解析游标中的数据，调用User构造函数(存储查询获得的数据)
        while (cursor.moveToNext()){
            //根据自定义的User构造函数，依次取值，并进行赋值操作,将查询数据放入User对象，
            // 接着将User对象放入List链表
            Note note= new Note(
                    cursor.getLong(cursor.getColumnIndex("noteId")),
                    cursor.getString(cursor.getColumnIndex("userPhone")),
                    cursor.getString(cursor.getColumnIndex("content")),
                    cursor.getString(cursor.getColumnIndex("time"))
            );
            notes.add(note);
        }
        cursor.close();

        return notes;
    }

    //更新已经存在的笔记
    public boolean updateNote(Note note) {

        /*
        ContentValues values = new ContentValues();
        values.put(NoteDatabase.USERID, note.getUserId());
        values.put(NoteDatabase.CONTENT, note.getContent());
        values.put(NoteDatabase.TIME, note.getTime());
        values.put(NoteDatabase.MODE, note.getTag());

        return db.update(NoteDatabase.TABLE_NAME, values,
                NoteDatabase.NOTEID + "=?",new String[] { String.valueOf(note.getNoteId())});
         */

        db= noteDatabase.getWritableDatabase();
        String sql= "UPDATE notes SET "+ NoteDatabase.CONTENT +"= ? WHERE noteId= ?";
        try {
            db.execSQL(sql, new Object[]{note.getContent(), note.getNoteId()});
            System.out.println("笔记修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    //删除笔记
    public boolean removeNote(Note note) {

        open();
        String sql= "DELETE FROM notes WHERE noteId= ?";
        try{
            db.execSQL(sql, new Object[]{note.getNoteId()});
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            close();
        }

        return true;
    }

    //根据用户手机号查询对应用户对象
    public User findUserByUserId(String phone) throws Exception{

        db= noteDatabase.getReadableDatabase();  //获取可读数据库
        String sql= "SELECT * FROM user WHERE userPhone= ?";
        Cursor cursor= db.rawQuery(sql, new String[]{phone});
        /*
        如果需要多个条件写法如下：(name与password需要自己定义)
        String sql= "select * from user where name= ? and password= ?";
        Cursor cursor= db.rawQuery(sql, new String[]{name, password});
         */
        User user= null;     //需要初始化为null否则返回值会报错
        //解析游标中的数据，调用User构造函数(存储查询获得的数据)
        while (cursor.moveToNext()){
            //根据自定义的User构造函数，依次取值，并进行赋值操作,将查询数据放入User对象，
            // 接着将User对象放入List链表
            user= new User(
                    cursor.getString(cursor.getColumnIndex("userPhone")),
                    cursor.getString(cursor.getColumnIndex("userName")),
                    cursor.getString(cursor.getColumnIndex("userPassword")),
                    cursor.getInt(cursor.getColumnIndex("userStatus")),
                    cursor.getInt(cursor.getColumnIndex("userRole"))
            );
        }

        db.close();
        cursor.close();
        return user;
    }

    //注册新用户信息
    public boolean addUser(User user) throws  Exception{
        open();
        String sql= "INSERT INTO user(userPhone, userName, userPassword, userStatus, userRole)" +
                                "VALUES(?, ?, ?, 1, 1)";
        try {
            db.execSQL(sql, new Object[]{user.getUserPhone(), user.getUserName(), user.getUserPassword()});
        }catch (Exception e){
            System.out.println(e);
            return false;
        }finally {
            close();
        }

        return true;
    }

    //根据用户名修改用户输入密码
    public boolean updateUserByName(User user) throws Exception {
        SQLiteDatabase db= noteDatabase.getWritableDatabase();
        String sql= "UPDATE user SET userPassword= ? WHERE userPhone= ?";

        try {
            db.execSQL(sql, new String[]{user.getUserPassword(), user.getUserPhone()});
            System.out.println("跟新成功!");
        }catch (Exception e){
            System.out.println(e);
            return false;
        }

        return true;
    }


}
