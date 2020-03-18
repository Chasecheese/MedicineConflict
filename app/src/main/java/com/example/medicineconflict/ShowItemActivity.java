package com.example.medicineconflict;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShowItemActivity extends AppCompatActivity {
    private TextView tv_name;

    public static final String DB_NAME = "new_medicine_db";
    public static final String TABLE_NAME = "newMedicineItem";
    public static final String ID = "ID";
    public static final String NAME = "Name";
    public static final String LEVEL4 = "level_4";
    public static final String LEVEL3 = "level_3";
    public static final String LEVEL2 = "level_2";
    public static final String LEVEL1 = "level_1";

    private DBOpenHelper myHelper;
    private SQLiteDatabase db;
    private RadioGroup rg;
    private RadioButton rb_all;
    private RadioButton rb4;
    private RadioButton rb3;
    private RadioButton rb2;
    private RadioButton rb1;
    private ListView list;
    private SimpleAdapter listItemAdapter;
    private List<Recomend> data;
    private ShowItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        initView();
        initLsit();
        initButtons();
    }

    public void initButtons(){
        rg = findViewById(R.id.RG);
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
        db = myHelper.getWritableDatabase();
        tv_name = findViewById(R.id.tv_itemname);
        data = new ArrayList<>();
    }

    private void initLsit(){
        int id = getIntent().getIntExtra("id", 0);
        MedicineItem temp = setMedicineInfo(id);
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

        ListView list = findViewById(R.id.ListView);

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();

        if(!coListLevel4[0].equals("#")){
            for (int i = 0; i < coListLevel4.length; i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemTitle", coListLevel4[i]);
                map.put("ItemText", "禁忌");
                listItem.add(map);
                data.add(new Recomend(coListLevel4[i],"禁忌"));
            }
        }
        if(!coListLevel3[0].equals("#")){
            for (int i = 0; i < coListLevel3.length; i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemTitle", coListLevel3[i]);
                map.put("ItemText", "严重");
                listItem.add(map);
                data.add(new Recomend(coListLevel3[i],"严重"));
            }
        }
        if(!coListLevel2[0].equals("#")){
            for (int i = 0; i < coListLevel2.length; i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemTitle", coListLevel2[i]);
                map.put("ItemText", "中度");
                listItem.add(map);
                data.add(new Recomend(coListLevel2[i],"中度"));
            }
        }
        if(!coListLevel1[0].equals("#")){
            for (int i = 0; i < coListLevel1.length; i++) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemTitle", coListLevel1[i]);
                map.put("ItemText", "轻度");
                listItem.add(map);
                data.add(new Recomend(coListLevel1[i],"轻度"));
            }
        }

        adapter = new ShowItemAdapter(this,data);

        listItemAdapter = new SimpleAdapter(this, listItem,// 数据源
                android.R.layout.simple_list_item_2,
                // 动态数组与ImageItem对应的子项
                new String[]{"ItemTitle", "ItemText"},
                // ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{android.R.id.text1, android.R.id.text2});

        //list.setAdapter(listItemAdapter);
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
            }
        });
    }

    private MedicineItem setMedicineInfo(int id) {
        Cursor c = db.query(TABLE_NAME, new String[]{}, "ID=" + id, null, null, null, null);
        if (c.moveToFirst()) {
            String name = c.getString(c.getColumnIndex(NAME));

            String level4 = c.getString(c.getColumnIndex(LEVEL4));
            String level3 = c.getString(c.getColumnIndex(LEVEL3));
            String level2 = c.getString(c.getColumnIndex(LEVEL2));
            String level1 = c.getString(c.getColumnIndex(LEVEL1));
            MedicineItem d = new MedicineItem(name, level4, level3, level2, level1);
            d.setID(c.getInt(c.getColumnIndex("ID")));
            c.close();
            return d;
        } else {
            c.close();
            return null;
        }
    }

}
