package com.example.medicineconflict;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ShowItemActivity extends AppCompatActivity {


    public static final String DB_NAME = "new_medicine_db";
    /*
    public static final String TABLE_NAME = "newMedicineItem";
    public static final String ID = "ID";
    public static final String NAME = "Name";
    public static final String LEVEL4 = "level_4";
    public static final String LEVEL3 = "level_3";
    public static final String LEVEL2 = "level_2";
    public static final String LEVEL1 = "level_1";
    */
    private final static Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);
    private DBOpenHelper myHelper;

    private RadioButton rb_all;
    private RadioButton rb4;
    private RadioButton rb3;
    private RadioButton rb2;
    private RadioButton rb1;

    private TextView tv_name;
    private ShowItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);
        initView();
        initLsit();
        initButtons();
    }

    public void initButtons(){
        RadioGroup rg = findViewById(R.id.RG);
        rb_all = findViewById(R.id.rb_all);
        rb4 = findViewById(R.id.rb4);
        rb3 = findViewById(R.id.rb3);
        rb2 = findViewById(R.id.rb2);
        rb1 = findViewById(R.id.rb1);
        rg.setOnCheckedChangeListener(new getItem());
    }


    private class getItem implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if(checkedId==rb4.getId()){
                Toast.makeText(ShowItemActivity.this,"禁忌：严禁同时时服用", Toast.LENGTH_LONG).show();
                adapter.getFilter().filter("禁忌");
            }
            if(checkedId==rb3.getId()){
                Toast.makeText(ShowItemActivity.this,"严重", Toast.LENGTH_LONG).show();
                adapter.getFilter().filter("严重");
            }
            if(checkedId==rb2.getId()){
                Toast.makeText(ShowItemActivity.this,"中度", Toast.LENGTH_LONG).show();
                adapter.getFilter().filter("中度");
            }
            if(checkedId==rb1.getId()){
                Toast.makeText(ShowItemActivity.this,"轻度", Toast.LENGTH_LONG).show();
                adapter.getFilter().filter("轻度");
            }
            if(checkedId==rb_all.getId()){
                Toast.makeText(ShowItemActivity.this,"全部", Toast.LENGTH_LONG).show();
                adapter.getFilter().filter("");
            }
        }
    }

    private void initView() {
        myHelper = new DBOpenHelper(this, DB_NAME, null, 1);
        tv_name = findViewById(R.id.tv_itemname);
    }

    private void initLsit(){
        List<LevelInfo> data = new ArrayList<>();
        int id = getIntent().getIntExtra("id", 0);
        MedicineInfo temp = myHelper.getMedicineInfo(id);
        tv_name.setText(temp.getName());
        setTitle(temp.getName());

        String level4 = temp.getLevel4();
        String level3 = temp.getLevel3();
        String level2 = temp.getLevel2();
        String level1 = temp.getLevel1();

        String[] coListLevel4 = level4.split(",");
        String[] coListLevel3 = level3.split(",");
        String[] coListLevel2 = level2.split(",");
        String[] coListLevel1 = level1.split(",");

        ListView listView = findViewById(R.id.ListView);

        if(!coListLevel4[0].equals("#")){
            for (String s : coListLevel4) {
                data.add(new LevelInfo(s, "禁忌"));
            }
        }
        if(!coListLevel3[0].equals("#")){
            for (String s : coListLevel3) {
                data.add(new LevelInfo(s, "严重"));
            }
        }
        if(!coListLevel2[0].equals("#")){
            for (String s : coListLevel2) {
                data.add(new LevelInfo(s, "中度"));
            }
        }
        if(!coListLevel1[0].equals("#")){
            for (String s : coListLevel1) {
                data.add(new LevelInfo(s, "轻度"));
            }
        }

        adapter = new ShowItemAdapter(this,data);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
            }
        });
    }

}
