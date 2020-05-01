package com.androidsoft.mynotes_2017144235;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;


import com.androidsoft.mynotes_2017144235.dao.NoteDao;
import com.androidsoft.mynotes_2017144235.db.NoteDatabase;
import com.androidsoft.mynotes_2017144235.pojo.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    //test gittt
    private NoteDatabase dbHelper;

    private Context context = this;
    final String TAG = "tag";
    FloatingActionButton btn;
    private ListView lv;
    private NoteAdapter adapter;
    private List<Note> noteList = new ArrayList<Note>();
    private Toolbar myToolbar;


    //弹出菜单
    private PopupWindow popupWindow;
    private PopupWindow popupCover;
    private ViewGroup customView;
    private ViewGroup coverView;
    private LayoutInflater layoutInflater;
    private RelativeLayout main;
    private WindowManager wm;
    private DisplayMetrics metrics;
    private TextView setting_text;
    private ImageView setting_image;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPrefs();
        initView();

        btn = findViewById(R.id.fab);
        lv = findViewById(R.id.lv);

        myToolbar = findViewById(R.id.myToolbar);
        adapter = new NoteAdapter(getApplicationContext(), noteList);
        refreshListView();
        lv.setAdapter(adapter);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //设置toolbar取代actionbar
        initPopUpView();
        myToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: shit");
                showPopUpView();
            }
        });

        lv.setOnItemClickListener(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("mode", 4);
                startActivityForResult(intent, 0);
            }
        });

        Intent intent= new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

    }

    private void initView(){

        initNavBar(false, "记事本", true);
    }

    public void initPopUpView(){
        layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customView = (ViewGroup) layoutInflater.inflate(R.layout.setting_layout, null);
        coverView = (ViewGroup) layoutInflater.inflate(R.layout.setting_cover, null);
        main = findViewById(R.id.main_layout);
        wm = getWindowManager();
        metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
    }

    public void showPopUpView(){
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        popupCover = new PopupWindow(coverView, width, height, false);
        popupWindow = new PopupWindow(customView, (int) (width * 0.7), height, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        //在主界面加载成功之后 显示弹出
        findViewById(R.id.main_layout).post(new Runnable() {
            @Override
            public void run() {
                popupCover.showAtLocation(main, Gravity.NO_GRAVITY, 0, 0);
                popupWindow.showAtLocation(main, Gravity.NO_GRAVITY, 0, 0);

                setting_image = customView.findViewById(R.id.setting_settings_image);
                setting_text = customView.findViewById(R.id.setting_settings_text);

                setting_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, UserSettingsActivity.class));
                    }
                });

                setting_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, UserSettingsActivity.class));
                    }
                });

                coverView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });

                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        popupCover.dismiss();
                        Log.d(TAG, "onDismiss: test");
                    }
                });
            }

        });

    }

    public void initPrefs(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!sharedPreferences.contains("nightMode")) {
            editor.putBoolean("nightMode", false);
            editor.commit();
        }
    }

    // 接受startActivityForResult的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){

        int returnMode;
        long note_Id;
        returnMode = intent.getExtras().getInt("mode", -1);
        note_Id = intent.getExtras().getLong("noteId", 0);
        SharedPreferences sharedPreferences= this.getSharedPreferences("login_info", Context.MODE_PRIVATE);
        String userPhone= sharedPreferences.getString("userPhone", "000000");


        if (returnMode == 1) {
            //跟新当前笔记

            boolean updateNote= false;
            String content = intent.getExtras().getString("content");
            String time = intent.getExtras().getString("time");

            Note newNote = new Note(note_Id, userPhone, content, time);
            NoteDao op = new NoteDao(context);
            updateNote= op.updateNote(newNote);
            if (updateNote== true){
                Toast.makeText(this, "笔记更新成功!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "笔记更新失败!", Toast.LENGTH_SHORT).show();
            }

        } else if (returnMode == 0) {
            //创建新笔记

            boolean createResult= false;
            String content = intent.getExtras().getString("content");
            String time = intent.getExtras().getString("time");

            Note newNote = new Note(userPhone, content, time);
            NoteDao op = new NoteDao(context);
            createResult= op.addNote(newNote);
            if (createResult== true){
                Toast.makeText(this, "笔记创建成功!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "笔记创建失败!", Toast.LENGTH_SHORT).show();
            }
        } else if (returnMode == 2) {
            //删除笔记

            boolean deleteResult= false;
            Note curNote= new Note();
            curNote.setNoteId(note_Id);
            NoteDao op = new NoteDao(context);
            deleteResult= op.removeNote(curNote);
            if (deleteResult== true){
                Toast.makeText(this, "笔记删除成功!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "笔记删除失败!", Toast.LENGTH_SHORT).show();
            }
        }


        refreshListView();
        super.onActivityResult(requestCode, resultCode, intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        //search setting
        MenuItem mSearch = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) mSearch.getActionView();

        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //删除全部笔记
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_clear:
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("删除全部吗？")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper = new NoteDatabase(context);
                                SQLiteDatabase db = dbHelper.getWritableDatabase();
                                db.delete("notes", null, null);
                                db.execSQL("update sqlite_sequence set seq=0 where name='notes'");
                                refreshListView();
                            }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    //刷新并显示ListView页面
    public void refreshListView(){

        SharedPreferences sharedPreferences= this.getSharedPreferences("login_info", Context.MODE_PRIVATE);
        String userPhone= sharedPreferences.getString("userPhone", "000000");
        NoteDao op = new NoteDao(context);
        op.open();

        if (noteList.size() > 0)
            noteList.clear();
        noteList.addAll(op.getAllNotes(userPhone));
        op.close();
        adapter.notifyDataSetChanged();
    }

    //获取ListView点击的笔记并将值传入Intent
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv:
                Note curNote = (Note) parent.getItemAtPosition(position);
                System.out.println(curNote.toString());
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("content", curNote.getContent());
                intent.putExtra("noteId", curNote.getNoteId());
                intent.putExtra("time", curNote.getTime());
                intent.putExtra("mode", 3);     // MODE of 'click to edit'
                startActivityForResult(intent, 1);      //collect data from edit
                Log.d(TAG, "onItemClick: " + position);
                break;
        }


    }


}
