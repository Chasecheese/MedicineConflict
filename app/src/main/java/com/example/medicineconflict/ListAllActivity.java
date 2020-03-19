package com.example.medicineconflict;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class ListAllActivity extends AppCompatActivity {


    public static final String DB_NAME = "new_medicine_db";
    public static final String TABLE_NAME = "newMedicineItem";
    public static final String NAME = "Name";
    public static final String LEVEL4 = "level_4";
    public static final String LEVEL3 = "level_3";
    public static final String LEVEL2 = "level_2";
    public static final String LEVEL1 = "level_1";
    private final static Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);

    private DBOpenHelper myHelper;
    private SearchView searchView;
    private ListView listView;
    private HashMap<String, MedicineInfo> checkMap = new HashMap<>();
    private ListAllAdapter adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        init();
        initArrayList();
        this.setTitle("药物相互作用查询表");
    }

    public void init(){
        myHelper = new DBOpenHelper(this, DB_NAME, null, 1);
        listView = findViewById(R.id.listView_medicine);
    }

    public void initArrayList(){

        searchView = findViewById(R.id.sv);
        ArrayList<MedicineInfo> temp = new ArrayList<>();
        temp = myHelper.getListInfo();
        ArrayList<String> li = new ArrayList<>();

        for(MedicineInfo item:temp){
            li.add(item.getName());
            checkMap.put(item.getName(),item);
        }

        Collections.sort(li, CHINA_COMPARE);

        adapter = new ListAllAdapter(this,android.R.layout.simple_list_item_1, li);

        listView.setAdapter(adapter);

        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                int tempID = checkMap.get(arg0.getItemAtPosition(arg2).toString()).getID();
                //Toast.makeText(ListAllActivity.this, "当前药物："+arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListAllActivity.this, ShowItemActivity.class);
                intent.putExtra("id", tempID);
                startActivity(intent);
            }
        });

        //设置SearchView自动缩小为图标
        searchView.setIconifiedByDefault(false);//设为true则搜索栏 缩小成俄日一个图标点击展开
        //设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
        //设置默认提示文字
        searchView.setQueryHint("输入您想查找的内容");



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(checkMap.get(query)!=null){
                    int tempID = checkMap.get(query).getID();
                    Intent intent = new Intent(ListAllActivity.this, ShowItemActivity.class);
                    intent.putExtra("id", tempID);
                    startActivity(intent);
                }else{
                    Toast.makeText(ListAllActivity.this, "当前搜索不存在", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    adapter.getFilter().filter(newText.toString());
                    //listView.setFilterText(newText);
                }else{
                    listView.clearTextFilter();
                    adapter.getFilter().filter("");
                }
                return false;
            }
        });

    }

}
