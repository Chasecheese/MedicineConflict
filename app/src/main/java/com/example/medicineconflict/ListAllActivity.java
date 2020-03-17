package com.example.medicineconflict;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.HashMap;

public class ListAllActivity extends AppCompatActivity {


    public static final String DB_NAME = "new_medicine_db";
    public static final String TABLE_NAME = "newMedicineItem";
    public static final String NAME = "Name";
    public static final String LEVEL4 = "level_4";
    public static final String LEVEL3 = "level_3";
    public static final String LEVEL2 = "level_2";
    public static final String LEVEL1 = "level_1";

    private DBOpenHelper myHelper;
    private SearchView searchView;
//    private ListAllAdapter adapter;
//    private ListView lv;
//    private SQLiteDatabase db;
    private ListView listView;
    private HashMap<String, MedicineItem> checkMap = new HashMap<>();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        init();
        initArrayList();
    }

    public void init(){
        myHelper = new DBOpenHelper(this, DB_NAME, null, 1);
//        db = myHelper.getWritableDatabase();
        listView = findViewById(R.id.listView_medicine);
    }

    public void initArrayList(){

        searchView = findViewById(R.id.sv);
        ArrayList<MedicineItem> temp = new ArrayList<>();
        temp = myHelper.getListInfo();
        ArrayList<String> li = new ArrayList<>();

        for(MedicineItem item:temp){
            li.add(item.getName());
            checkMap.put(item.getName(),item);
        }

        listView.setAdapter(new ListAllAdapter(this,android.R.layout.simple_list_item_1, li));

        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int tempID = checkMap.get(arg0.getItemAtPosition(arg2).toString()).getID();
                Toast.makeText(ListAllActivity.this, "当前药物："+arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListAllActivity.this, ShowItemActivity.class);
                intent.putExtra("id", tempID);
                startActivity(intent);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    listView.setFilterText(newText);
                }else{
                    listView.clearTextFilter();
                }
                return false;
            }
        });

    }

    /*
    public void initList(){

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        ArrayList<MedicineItem> temp = new ArrayList<>();
        temp = myHelper.getListInfo();
        for (MedicineItem a : temp) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("Item", a.getName());
            checkMap.put(a.getName(),a);
            listItem.add(map);
        }
        SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem, R.layout.medicine_item,
                new String[]{"Item"}, new int[]{R.id.tv_name});

        listView.setAdapter(listItemAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                String result = arg0.getItemAtPosition(arg2).toString();//获取选择项的值
                String name = result.substring(result.indexOf("=")+1,result.indexOf("}"));

                Intent intent = new Intent(ListAllActivity.this, ShowItemActivity.class);
                intent.putExtra("id", checkMap.get(name).getID());

                startActivity(intent);

            }
        });

    }
    */
    /*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        initView();
    }

    private void initView(){
        lv = findViewById(R.id.listView_medicine);
        myHelper = new DBOpenHelper(this,DB_NAME,null,1);
        adapter = new ListAllAdapter(this,myHelper.getBasicInfo());
        lv.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        myHelper.close();
    }

    @Override
    public void onClick(View v) {
    }
    */

}
